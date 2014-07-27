package org.awkward.safe.visitor;

import org.awkward.safe.model.DirectoryNode;
import org.awkward.safe.model.FileNode;
import org.awkward.safe.visitor.Visitor;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 21.07.14
 */
public abstract class AbstractVisitor implements Visitor
{
    @Override
    public boolean preVisit(DirectoryNode node)
    {
        if (!shouldVisit(node))
        {
            return true;
        }

        return true;
    }

    @Override
    public void postVisit(DirectoryNode directoryNode)
    {

    }

    @Override
    public void visit(FileNode node)
    {

    }

    protected boolean shouldVisit(DirectoryNode node)
    {
        return true;
    }
}