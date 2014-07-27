package org.awkard.safe.local;

import org.awkard.safe.CompareResults;
import org.awkard.safe.index.Revision;
import org.awkard.safe.model.DirectoryNode;
import org.awkard.safe.model.Node;
import org.awkard.safe.visitor.CompareVisitor;
import org.awkard.safe.visitor.Visitor;
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
public class LocalDirectoryNode extends AbstractNode implements DirectoryNode
{
    private static final Logger log = LoggerFactory.getLogger(LocalDirectoryNode.class);

    private final Node[] children;

    public LocalDirectoryNode(File file, DirectoryNode parent, Revision revision)
    {
        super(file, parent, revision);

        this.children = createLocalNodes();
    }

    private Node[] createLocalNodes()
    {
        if (getFile().isDirectory())
        {
            final List<Node> localNodeList = new ArrayList<Node>();
            final File[] files = getFile().listFiles();
            if (files != null)
            {
                for (File file : files)
                {
                    localNodeList.add(LocalNodeFactory.getInstance().create(file, this));
                }
            }
            else
            {
                log.warn("You probably don't have permission to read.. " + getFile().getAbsolutePath());
            }

            return localNodeList.toArray(new Node[localNodeList.size()]);
        }

        return null;
    }

    public void visit(Visitor visitor)
    {
        if (visitor.preVisit(this))
        {
            visitChildren(visitor);

            visitor.postVisit(this);
        }
    }

    public void visitChildren(Visitor visitor)
    {
        if (children != null)
        {
            for (Node localNode : children)
            {
                localNode.visit(visitor);
            }
        }
    }

    @Override
    public String toString()
    {
        return getPath();
    }

    @Override
    public int getChildCount()
    {
        if (this.children == null)
        {
            return 0;
        }

        return this.children.length;
    }

    @Override
    public Node[] getChildren()
    {
        if (this.children == null)
        {
            return new Node[0];
        }

        return this.children;
    }

    public Node find(String label)
    {
        for (Node node : getChildren())
        {
            if (node.getLabel().equals(label))
            {
                return node;
            }
        }

        return null;
    }

    public CompareResults compare(LocalDirectoryNode root)
    {
        final CompareVisitor sourceVisitor = new CompareVisitor(root);

        this.visitChildren(sourceVisitor);

        return sourceVisitor.getCompareResults();
    }
}
