package org.awkward.safe.local;

import org.awkard.safe.CompareResults;
import org.awkard.safe.local.LocalFileTree;
import org.awkard.safe.model.Node;
import org.awkard.strategy.IncrementalStrategy;
import org.awkward.DefaultUnitTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Queue;

import static org.awkard.strategy.IncrementalStrategy.*;
import static org.awkard.strategy.IncrementalStrategy.SynchronizationType.*;

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
    static final File localRoot = new File("/safe/Code/personal/safe/data/local");
    static final File serverRoot = new File("/safe/Code/personal/safe/data/server");

    static final LocalFileTree local = new LocalFileTree(localRoot);
    static final LocalFileTree server = new LocalFileTree(serverRoot);

    final String[] expectedPaths;
    final SynchronizationType[] synchronizationTypes;

    private final LocalFileTree source;
    private final LocalFileTree destination;

    @BeforeClass
    public static void initialize() throws InterruptedException, IOException
    {
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][]{
                {
                        local,
                        server,
                        new String[]{
                                "/safe/Code/personal/safe/data/local/dir2"
                        },
                        new SynchronizationType[]{NEW_DIRECTORIES}
                },
                {
                        local,
                        server,
                        new String[]{
                                "/safe/Code/personal/safe/data/local/dir1/fil7",
                        },
                        new SynchronizationType[]{NEW_FILES}
                },
                {
                        local,
                        server,
                        new String[]{
                                "/safe/Code/personal/safe/data/local/fil5",
                        },
                        new SynchronizationType[]{MODIFIED_FILES}
                },
                {
                        server,
                        local,
                        new String[]{
                                "/safe/Code/personal/safe/data/server/dir3"
                        },
                        new SynchronizationType[]{NEW_DIRECTORIES}
                },
                {
                        server,
                        local,
                        new String[]{
                                "/safe/Code/personal/safe/data/server/fil2",
                                "/safe/Code/personal/safe/data/server/dir1/fil3"
                        },
                        new SynchronizationType[]{NEW_FILES}
                },
                {
                        server,
                        local,
                        new String[]{
                                "/safe/Code/personal/safe/data/server/fil1"
                        },
                        new SynchronizationType[]{MODIFIED_FILES}
                }
        });
    }

    public IncrementalStrategyTest(LocalFileTree source, LocalFileTree destination, String[] expectedPaths, SynchronizationType... synchronizationTypes)
    {
        this.source = source;
        this.destination = destination;
        this.expectedPaths = expectedPaths;
        this.synchronizationTypes = synchronizationTypes;
    }

    @Test
    public void test()
    {
        System.out.println("Source                  :" + source.getRoot().getPath());
        System.out.println("Destination             :" + destination.getRoot().getPath());
        System.out.println("SynchronizationType[]   :" + Arrays.toString(synchronizationTypes));

        final CompareResults compare = source.compare(destination);
        final IncrementalStrategy incrementalStrategy = new IncrementalStrategy(synchronizationTypes);
        final Queue<Node> transferQueue = incrementalStrategy.createTransferQueue(compare);

        System.out.println(transferQueue);

        for (String expectedPath : expectedPaths)
        {
            final Node node = transferQueue.poll();
            
            Assert.assertNotNull("Found null node.  Expected path: " + expectedPath, node);
            Assert.assertEquals("Path mismatch", expectedPath, node.getPath());
        }
    }

//        final SimpleFileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>()
//        {
//            @Override
//            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
//            {
//                System.out.println(dir);
//
//                return dir.toFile().canRead() ? FileVisitResult.CONTINUE : FileVisitResult.SKIP_SUBTREE;
//            }
//
//            @Override
//            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
//            {
//                System.out.println(file);
//
//                return super.visitFile(file, attrs);
//            }
//
//            @Override
//            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
//            {
//                exc.printStackTrace();
//
//                return FileVisitResult.CONTINUE;
//            }
//        };
//
//        Files.walkFileTree(root.toPath(), fileVisitor);
}
