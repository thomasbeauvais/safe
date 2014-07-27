package org.awkward;

import org.awkward.safe.model.LocalFileTree;
import org.awkward.safe.visitor.PrintTreeVisitor;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.junit.After;
import org.junit.Before;

import java.io.File;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 26.07.14
 */
public class DefaultUnitTest
{
    protected static final File localRoot = new File("/safe/Code/personal/safe/data/local");
    protected static final File serverRoot = new File("/safe/Code/personal/safe/data/server");

    protected static final LocalFileTree local = new LocalFileTree(localRoot);
    protected static final LocalFileTree server = new LocalFileTree(serverRoot);

    static final PeriodFormatter formatter = new PeriodFormatterBuilder()
            .appendDays()
            .appendSuffix("d")
            .appendHours()
            .appendSuffix("h")
            .appendMinutes()
            .appendSuffix("m")
            .appendSeconds()
            .appendSuffix("s")
            .toFormatter();

    private long start;

    private File root;
    private LocalFileTree tree;

    @Before
    public void start()
    {
        local.visit(new PrintTreeVisitor());

        this.start = System.currentTimeMillis();
    }

    @After
    public void stop()
    {
        System.out.println(System.currentTimeMillis() - start + " ms");
    }
}
