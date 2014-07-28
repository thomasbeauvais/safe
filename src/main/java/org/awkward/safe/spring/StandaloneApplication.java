package org.awkward.safe.spring;

import org.awkward.safe.SynchronizationEngine;
import org.awkward.safe.client.AcceptAllClientCallback;
import org.awkward.safe.client.ClientRepository;
import org.awkward.safe.model.NodeFactory;
import org.awkward.safe.model.Repository;
import org.awkward.safe.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
@Component
public class StandaloneApplication
{
    @Autowired
    Server server;

    @Autowired
    SynchronizationEngine synchronizationEngine;

    @Autowired
    NodeFactory nodeFactory;

    public static void main(String[] args)
    {
        new ClassPathXmlApplicationContext("classpath:spring-server.xml").getBean(StandaloneApplication.class).go(args);
    }

    private void go(String[] args)
    {
        server.start();

        final Repository[] allRepositories = server.findAllRepositories();

        System.out.println(Arrays.toString(allRepositories));

        for (Repository repository : allRepositories)
        {
            server.deleteRepository(repository.id);
        }

        // create a new repository
        final Repository repository = server.createRepository("client-" + System.currentTimeMillis());

        // select the local directory to link in the repository
        final File root = new File("/safe/Code/personal/safe/data/local");

        final ClientRepository clientRepository = (ClientRepository) nodeFactory.create(root, null, repository);

        synchronizationEngine.synchronize(clientRepository, server, new AcceptAllClientCallback());
    }
}
