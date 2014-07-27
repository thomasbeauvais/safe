package org.awkard.safe.local;

import org.awkard.safe.index.Revision;
import org.awkard.safe.model.DirectoryNode;
import org.awkard.safe.model.Node;
import org.awkard.safe.visitor.Visitor;

import java.io.File;
import java.io.IOException;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 21.07.14
 */
public abstract class AbstractNode implements Node, Comparable
{
    private final Revision revision;

    private final File file;

    private final DirectoryNode parent;

    public AbstractNode(File file, DirectoryNode parent, Revision revision)
    {
        this.file = file;
        this.parent = parent;
        this.revision = revision;
    }

    @Override
    public Revision getRevision()
    {
        return revision;
    }

    @Override
    public Node getParent()
    {
        return parent;
    }

    @Override
    public long getLastModified()
    {
        return file.lastModified();
    }

    @Override
    public String getPath()
    {
        try
        {
            return file.getCanonicalPath();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public File getFile()
    {
        return file;
    }

    public abstract void visit(Visitor visitor);

    public String getName()
    {
        return file.getName();
    }

    @Override
    public String getLabel()
    {
        return getName();
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public int compareTo(Object o)
    {
        if (o instanceof Node)
        {
            return ((Node) o).getLabel().compareTo(this.getLabel());
        }

        return -1;
    }
}
