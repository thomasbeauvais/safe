package org.awkard.safe.local;

import org.awkard.safe.index.Revision;
import org.awkard.safe.model.DirectoryNode;
import org.awkard.safe.model.FileNode;
import org.awkard.safe.visitor.Visitor;

import java.io.File;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 21.07.14
 */
public class LocalFileNode extends AbstractNode implements FileNode
{
    public LocalFileNode(File file, DirectoryNode parent, Revision revision)
    {
        super(file, parent, revision);

        if (file.isDirectory())
        {
            throw new RuntimeException("File is a directory!");
        }
    }

    public void visit(Visitor visitor)
    {
        visitor.visit(this);
    }

    @Override
    public String toString()
    {
        return getPath() + "(" + getRevision() + ")";
    }
}
