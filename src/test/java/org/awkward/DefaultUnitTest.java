package org.awkward;

import org.awkard.safe.local.LocalFileTree;
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
        this.start = System.currentTimeMillis();
    }

    @After
    public void stop()
    {
        System.out.println(System.currentTimeMillis() - start);
    }
}
