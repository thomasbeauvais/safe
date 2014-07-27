package org.awkward.safe.model;

import org.awkward.safe.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    private static final Logger log = LoggerFactory.getLogger(LocalNodeFactory.class);

    public static LocalNodeFactory getInstance()
    {
        return INSTANCE;
    }

    public Node create(File file, DirectoryNode parent)
    {
        final Node node;
        if (file.isDirectory())
        {
            final DirectoryNode directoryNode = new DirectoryNode();
            directoryNode.children = createChildren(file, directoryNode);

            node = directoryNode;
        }
        else if (file.isFile())
        {
            final FileNode fileNode = new FileNode();

            node = fileNode;
        }
        else
        {
            throw new RuntimeException("Couldn't could file type!");
        }

        final Version version = new Version();
        version.checksum = MD5Utils.getChecksum(file);
        version.lastModified = file.lastModified();
        version.path = file.getAbsolutePath();

        node.version = version;
        node.parent = parent;

        return node;
    }

    private List<Node> createChildren(File directory, DirectoryNode parent)
    {
        if (directory.isDirectory())
        {
            final List<Node> localNodeList = new ArrayList<Node>();
            final File[] files = directory.listFiles();
            if (files != null)
            {
                for (File file : files)
                {
                    localNodeList.add(LocalNodeFactory.getInstance().create(file, parent));
                }
            }
            else
            {
                log.warn("You probably don't have permission to read.. " + directory.getAbsolutePath());
            }

            return localNodeList;
        }

        return null;
    }
}