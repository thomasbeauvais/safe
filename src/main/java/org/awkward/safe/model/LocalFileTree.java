package org.awkward.safe.model;

import org.awkward.safe.CompareResults;
import org.awkward.safe.visitor.Visitor;

import java.io.File;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 21.07.14
 */
public class LocalFileTree
{
    private final DirectoryNode root;

    public LocalFileTree(File root)
    {
        this.root = (DirectoryNode) LocalNodeFactory.getInstance().create(root, null);
    }

    public void visit(Visitor visitor)
    {
        root.visit(visitor);
    }

    public DirectoryNode getRoot()
    {
        return root;
    }

    public CompareResults compare(LocalFileTree destination)
    {
        return getRoot().compare(destination.getRoot());
    }
}
