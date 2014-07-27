package org.awkard.safe.model;

import org.awkard.safe.index.Revision;
import org.awkard.safe.visitor.Visitor;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 10.07.14
 */
public interface Node
{
    String getLabel();

    String getPath();

    void initialize();

    void visit(Visitor visitor);

    Node getParent();

    long getLastModified();

    Revision getRevision();
}
