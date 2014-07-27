package org.awkard.safe.local;

import org.awkard.safe.MD5Utils;
import org.awkard.safe.index.Revision;
import org.awkard.safe.model.DirectoryNode;
import org.awkard.safe.model.Node;

import java.io.File;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 21.07.14
 */
public class LocalNodeFactory
{
    private static final LocalNodeFactory INSTANCE = new LocalNodeFactory();

    public static LocalNodeFactory getInstance()
    {
        return INSTANCE;
    }

    public Node create(File file, DirectoryNode parent)
    {
        if (file.isDirectory())
        {
            return new LocalDirectoryNode(file, parent, new Revision(-1, file.lastModified(), null));
        }

        return new LocalFileNode(file, parent, new Revision(-1, file.lastModified(), MD5Utils.getChecksum(file)));
    }
}