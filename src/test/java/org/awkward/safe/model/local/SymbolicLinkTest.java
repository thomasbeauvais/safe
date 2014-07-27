package org.awkward.safe.model.local;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
public class SymbolicLinkTest
{
    @Test
    public void create() throws IOException
    {
        final Path path = new File("/safe/Applications/java/latest").toPath();
        System.out.println(Files.isSymbolicLink(path));
        System.out.println(Files.readSymbolicLink(path));
    }
}
