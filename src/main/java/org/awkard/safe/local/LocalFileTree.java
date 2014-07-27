package org.awkard.safe.local;

import org.awkard.safe.CompareResults;
import org.awkard.safe.visitor.Visitor;

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
    private final LocalDirectoryNode root;

    public LocalFileTree(File root)
    {
        this.root = new LocalDirectoryNode(root, null, null);
    }

    public void visit(Visitor visitor)
    {
        root.visit(visitor);
    }

    public LocalDirectoryNode getRoot()
    {
        return root;
    }

    public CompareResults compare(LocalFileTree destination)
    {
        return getRoot().compare(destination.getRoot());
    }
}
