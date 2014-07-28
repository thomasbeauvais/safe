package org.awkward.safe;

import org.awkward.safe.client.ClientCallback;
import org.awkward.safe.client.ClientRepository;
import org.awkward.safe.model.DirectoryNode;
import org.awkward.safe.model.FileNode;
import org.awkward.safe.model.Node;
import org.awkward.safe.server.Server;
import org.awkward.safe.visitor.AbstractVisitor;
import org.awkward.safe.visitor.Visitor;
import org.awkward.strategy.IncrementalStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Queue;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
@Service
public class SynchronizationEngine
{
    private static final Logger logger = LoggerFactory.getLogger(SynchronizationEngine.class);

    @Autowired
    IncrementalStrategy pushStrategy;

    @Autowired
    IncrementalStrategy pullStrategy;

    public void synchronize(ClientRepository clientRepository, Server server, ClientCallback callback)
    {
        obtainLock(clientRepository, server);

        // TODO validate the clientRepository
        push(clientRepository, server, callback);

        pull(clientRepository, server, callback);

        releaseLock(clientRepository, server);
    }

    private void push(ClientRepository clientRepository, Server server, ClientCallback callback)
    {
        logger.info("Pushing from " + clientRepository.file + " to " + server);

        final CompareResults compareResults = clientRepository.compare(clientRepository.server);

        final Queue<Node> transferQueue = pushStrategy.createTransferQueue(compareResults);

        if (compareResults.isEmpty())
        {
            logger.info("Nothing to push.");
        }
        else if (callback == null)
        {
            logger.warn("No callback, so we aren't able to synchronize.  Please create your own or use a default like the AcceptAllClientCallback");
        }
        else if (callback.doPush(transferQueue))
        {
            for (Node node : transferQueue)
            {
                node.visit(new PushVisitor(clientRepository, server));
            }
        }
    }

    private void pull(ClientRepository clientRepository, Server server, ClientCallback callback)
    {
        logger.info("Pulling from " + server + " to " + clientRepository.file);

        final CompareResults compareResults = clientRepository.server.compare(clientRepository);

        final Queue<Node> transferQueue = pullStrategy.createTransferQueue(compareResults);

        if (compareResults.isEmpty())
        {
            logger.info("Nothing to pull.");
        }
        else if (callback == null)
        {
            logger.warn("No callback, so we aren't able to synchronize.  Please create your own or use a default like the AcceptAllClientCallback");
        }
        else if (callback.doPull(transferQueue))
        {
            for (Node node : transferQueue)
            {
                server.pull(new PullCallback(clientRepository, node));
            }
        }
    }

    private void obtainLock(ClientRepository clientRepository, Server server)
    {
        // TODO implement

        logger.debug("Obtaining lock: " + clientRepository.file);

        server.obtainLock(clientRepository.id);
    }

    private void releaseLock(ClientRepository clientRepository, Server server)
    {
        // TODO implement

        logger.debug("Releasing lock: " + clientRepository.file);

        server.releaseLock(clientRepository.id);
    }

    private class PushVisitor extends AbstractVisitor implements Visitor
    {
        private final ClientRepository clientRepository;
        private final Server server;

        public PushVisitor(ClientRepository clientRepository, Server server)
        {
            this.clientRepository = clientRepository;
            this.server = server;
        }

        @Override
        public boolean preVisit(DirectoryNode directoryNode)
        {
            server.createDirectory(clientRepository.id, directoryNode.version.path);

            return true;
        }

        @Override
        public void visit(FileNode node)
        {
            try
            {
                final FileInputStream inputStream = new FileInputStream(new File(clientRepository.file, node.version.path));
                final String path = node.parent.version.path;
                final String filename = node.getName();

                server.createFile(clientRepository.id, path, filename, inputStream);
            }
            catch (FileNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}

