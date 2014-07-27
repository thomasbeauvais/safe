package org.awkard.safe.visitor;

import org.awkard.safe.CompareResults;
import org.awkard.safe.local.LocalDirectoryNode;
import org.awkard.safe.local.LocalFileNode;
import org.awkard.safe.model.DirectoryNode;
import org.awkard.safe.model.FileNode;
import org.awkard.safe.model.Node;

import java.util.Stack;

/**
* TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
* TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
*
* @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
* @since 27.07.14
*/
public class CompareVisitor implements Visitor
{
    private final Stack<DirectoryNode> directoryNodes = new Stack<DirectoryNode>();

    private final CompareResults compareResults = new CompareResults();

    public CompareVisitor(LocalDirectoryNode root)
    {
        directoryNodes.push(root);
    }

    @Override
    public boolean preVisit(DirectoryNode node)
    {
        final Node found = directoryNodes.peek().find(node.getLabel());

        if (found == null)
        {
            compareResults.missing.add(node);

            return false;
        }

        directoryNodes.push((DirectoryNode) found);

        return true;
    }

    @Override
    public void postVisit(DirectoryNode directoryNode)
    {
        directoryNodes.pop();
    }

    @Override
    public void visit(FileNode node)
    {
        final LocalFileNode found = (LocalFileNode) directoryNodes.peek().find(node.getLabel());

        if (found == null)
        {
            compareResults.missing.add(node);
        }
        else if (node.getLastModified() > found.getLastModified())
        {
            if (!node.getRevision().getChecksum().equals(found.getRevision().getChecksum()))
            {
                compareResults.modified.add(node);
            }
        }
    }

    public CompareResults getCompareResults()
    {
        return compareResults;
    }
}
