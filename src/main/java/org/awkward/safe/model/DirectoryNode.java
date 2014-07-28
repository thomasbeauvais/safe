package org.awkward.safe.model;

import org.awkward.safe.CompareResults;
import org.awkward.safe.visitor.CompareVisitor;
import org.awkward.safe.visitor.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 21.07.14
 */
@Entity
public class DirectoryNode extends Node
{
    private static final Logger log = LoggerFactory.getLogger(DirectoryNode.class);

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Node> children = new ArrayList<Node>();

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
            Collections.sort(children);

            for (Node localNode : children)
            {
                localNode.visit(visitor);
            }
        }
    }

    public int getChildrenCount()
    {
        if (this.children == null)
        {
            return 0;
        }

        return this.children.size();
    }

    public Node find(String path)
    {
        for (Node node : children)
        {
            if (node.version.path.equals(path))
            {
                return node;
            }
        }

        return null;
    }

    public CompareResults compare(DirectoryNode node)
    {
        final CompareVisitor sourceVisitor = new CompareVisitor(node);

        this.visitChildren(sourceVisitor);

        return sourceVisitor.getCompareResults();
    }

    public DirectoryNode findRoot()
    {
        if (parent == null)
        {
            return this;
        }

        return parent.findRoot();
    }

    @Override
    public String toString()
    {
        return version == null ? super.toString() : version.path;
    }
}
