package org.awkward.safe.model;

import org.awkward.safe.MD5Utils;
import org.awkward.safe.client.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 21.07.14
 */
@Component
public class NodeFactory
{
    private static final Logger log = LoggerFactory.getLogger(NodeFactory.class);

    public Node create(File file, DirectoryNode parent)
    {
        return create(file, parent, null);
    }

    public Node create(File file, DirectoryNode parent, Repository sererRepository)
    {
        final Node node;
        if (file.isDirectory() && sererRepository == null)
        {
            node = new DirectoryNode();
        }
        else if (file.isDirectory())
        {
            node = new ClientRepository(file, sererRepository);
        }
        else if (file.isFile())
        {
            node = new FileNode();
        }
        else
        {
            throw new RuntimeException("Couldn't could file type!");
        }

        final Version version = new Version();
        version.checksum = MD5Utils.getChecksum(file);
        version.lastModified = file.lastModified();
        version.path = parent == null || parent.version == null ? "" : parent.version.path + "/" + file.getName();

        node.version = version;
        node.parent = parent;

        // TODO don't do this
        if (node instanceof DirectoryNode)
        {
            createChildren(file, (DirectoryNode) node);
        }

        return node;
    }

    private void createChildren(File directory, DirectoryNode parent)
    {
        if (directory.isDirectory())
        {
            final File[] files = directory.listFiles();
            if (files != null)
            {
                for (File file : files)
                {
                    parent.children.add(create(file, parent));
                }
            }
            else
            {
                log.warn("You probably don't have permission to read.. " + directory.getAbsolutePath());
            }
        }
    }
}