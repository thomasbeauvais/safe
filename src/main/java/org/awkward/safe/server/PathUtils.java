package org.awkward.safe.server;

import java.io.File;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
public class PathUtils
{
    public static File getParentFile(File root, String path)
    {
        return new File(root, getParentPath(path));
    }

    public static String getParentPath(String path)
    {
        return path.substring(0, path.lastIndexOf("/"));
    }

    public static String getName(String path)
    {
        return path.substring(path.lastIndexOf("/"));
    }

    public static File getFile(File root, String path)
    {
        return new File(root, path);
    }
}
