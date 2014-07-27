package org.awkward.safe.model;

import org.awkward.safe.visitor.Visitor;

import javax.persistence.Entity;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 21.07.14
 */
@Entity
public class FileNode extends Node
{
    public void visit(Visitor visitor)
    {
        visitor.visit(this);
    }

    @Override
    public String toString()
    {
        return version.path + "(" + version.id + ")";
    }
}
