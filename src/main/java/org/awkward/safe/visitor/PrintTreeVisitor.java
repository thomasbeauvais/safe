package org.awkward.safe.visitor;

import org.apache.commons.lang3.StringUtils;
import org.awkward.safe.model.DirectoryNode;
import org.awkward.safe.model.FileNode;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
public class PrintTreeVisitor implements Visitor
{
    private int indent;

    @Override
    public boolean preVisit(DirectoryNode directoryNode)
    {
        System.out.println(StringUtils.repeat("  |", indent) + directoryNode.version.path);

        indent++;

        return true;
    }

    @Override
    public void postVisit(DirectoryNode directoryNode)
    {
        indent--;
    }

    @Override
    public void visit(FileNode node)
    {
        System.out.println(StringUtils.repeat("  |", indent) + "_ " + node.version.path);
    }
}
