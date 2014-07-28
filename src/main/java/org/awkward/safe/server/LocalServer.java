package org.awkward.safe.server;

import org.awkward.safe.PullCallback;
import org.awkward.safe.data.RepositoryRepository;
import org.awkward.safe.model.*;
import org.awkward.safe.visitor.CompareVisitor;
import org.awkward.safe.visitor.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
@Component
public class LocalServer implements Server
{
    private static final Logger logger = LoggerFactory.getLogger(LocalServer.class);

    private final File root = new File("/safe/Code/personal/safe/data/repositories");

    static final Boolean changed = false;

    @Autowired
    NodeFactory nodeFactory;

    @Autowired
    RepositoryRepository repositoryRepository;

    public void start()
    {
    }

    @Override
    public Repository[] findAllRepositories()
    {
        return repositoryRepository.findAll().toArray(new Repository[0]);
    }

    @Override
    public void deleteRepository(String id)
    {
        try
        {
            final File file = locateRepository(id);
            if (file.exists())
            {
                FileUtils.forceDelete(file);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        repositoryRepository.delete(id);
    }

    @Override
    public Repository createRepository(String name)
    {
        final Repository repository = repositoryRepository.save(new Repository(name));

        locateRepository(repository.id).mkdir();

        return repository;
    }

    private File locateRepository(String repositoryId)
    {
        return new File(root, repositoryId);
    }

    // file store
    @Override
    public void createDirectory(String repositoryId, String path)
    {
        // TODO maintain permissions

        new File(locateRepository(repositoryId), path).mkdirs();
    }

    @Override
    public void createFile(String repositoryId, String parentPath, String filename, InputStream inputStream)
    {
        // TODO maintain permissions

        final File parentFile = new File(locateRepository(repositoryId), parentPath);
        parentFile.mkdirs();

        final File file = new File(parentFile, filename);

        FileUtils.writeInputStreamToFile(file, inputStream);
    }

    @Override
    public void pull(PullCallback pullCallback)
    {
        logger.info("pulling: " + pullCallback.getNode());
    }

    @Override
    public void obtainLock(String repositoryId)
    {

    }

    @Override
    public void releaseLock(String repositoryId)
    {
        // TODO index thread
        final Repository existing = repositoryRepository.findOne(repositoryId);

        final File file = locateRepository(repositoryId);

        final Node actual = nodeFactory.create(file, null);

        actual.visit(new IndexVisitor(existing));
    }

    private class IndexVisitor extends CompareVisitor implements Visitor
    {
        public IndexVisitor(Repository existing)
        {
            super(existing);
        }

        @Override
        protected void onMissing(DirectoryNode node)
        {
            System.out.println("onMissing:" + node);
        }

        @Override
        protected void onMissing(FileNode node)
        {
            System.out.println("onMissing:" + node);
        }

        @Override
        protected void onModified(FileNode found, FileNode node)
        {
            System.out.println("onModified:" + node);
        }
    }
}
