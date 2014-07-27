package org.awkward.safe.visitor;

import org.awkward.safe.CompareResults;
import org.awkward.safe.model.DirectoryNode;
import org.awkward.safe.model.FileNode;
import org.awkward.safe.model.Node;

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

    public CompareVisitor(DirectoryNode root)
    {
        directoryNodes.push(root);
    }

    @Override
    public boolean preVisit(DirectoryNode node)
    {
        final Node found = directoryNodes.peek().find(node.version.path);

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
        final FileNode found = (FileNode) directoryNodes.peek().find(node.version.path);

        if (found == null)
        {
            compareResults.missing.add(node);
        }
        else if (node.version.lastModified > found.version.lastModified)
        {
            if (!node.version.checksum.equals(found.version.checksum))
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
