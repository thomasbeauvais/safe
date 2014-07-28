package org.awkward.safe.strategy;

import org.awkward.DefaultUnitTest;
import org.awkward.safe.CompareResults;
import org.awkward.safe.model.Node;
import org.awkward.safe.model.NodeFactory;
import org.awkward.safe.model.Repository;
import org.awkward.strategy.IncrementalStrategy;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Queue;

import static org.awkward.strategy.IncrementalStrategy.*;
import static org.awkward.strategy.IncrementalStrategy.SynchronizationType.*;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 21.07.14
 */
@RunWith(Parameterized.class)
public class IncrementalStrategyTest extends DefaultUnitTest
{
    private static NodeFactory nodeFactory;

    final String[] expectedPaths;
    final SynchronizationType[] synchronizationTypes;

    private final Repository source;
    private final Repository destination;

    @BeforeClass
    public static void initialize() throws InterruptedException, IOException
    {
        nodeFactory.create(localRoot, local);
        nodeFactory.create(serverRoot, server);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][]{
                {
                        local,
                        server,
                        new String[]{
                                "/dir2"
                        },
                        new SynchronizationType[]{NEW_DIRECTORIES}
                },
                {
                        local,
                        server,
                        new String[]{
                                "/dir1/fil7",
                        },
                        new SynchronizationType[]{NEW_FILES}
                },
                {
                        local,
                        server,
                        new String[]{
                                "/fil5",
                        },
                        new SynchronizationType[]{MODIFIED_FILES}
                },
                {
                        server,
                        local,
                        new String[]{
                                "/dir3"
                        },
                        new SynchronizationType[]{NEW_DIRECTORIES}
                },
                {
                        server,
                        local,
                        new String[]{
                                "/dir1/fil3",
                                "/fil2"
                        },
                        new SynchronizationType[]{NEW_FILES}
                },
                {
                        server,
                        local,
                        new String[]{
                                "/fil1"
                        },
                        new SynchronizationType[]{MODIFIED_FILES}
                }
        });
    }

    public IncrementalStrategyTest(Repository source, Repository destination, String[] expectedPaths, SynchronizationType... synchronizationTypes)
    {
        this.source = source;
        this.destination = destination;
        this.expectedPaths = expectedPaths;
        this.synchronizationTypes = synchronizationTypes;
    }

    @Test
    public void test()
    {
        System.out.println("Source                  :" + source);
        System.out.println("Destination             :" + destination);
        System.out.println("SynchronizationType[]   :" + Arrays.toString(synchronizationTypes));

        final CompareResults compare = source.compare(destination);
        final IncrementalStrategy incrementalStrategy = new IncrementalStrategy(synchronizationTypes);
        final Queue<Node> transferQueue = incrementalStrategy.createTransferQueue(compare);

        System.out.println(transferQueue);

        for (String expectedPath : expectedPaths)
        {
            final Node node = transferQueue.poll();

            Assert.assertNotNull("Found null node.  Expected path: " + expectedPath, node);
            Assert.assertEquals("Path mismatch", expectedPath, node.version.path);
        }
    }
}
