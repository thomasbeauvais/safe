package org.awkward.safe.visitor;

import org.awkward.safe.model.DirectoryNode;
import org.awkward.safe.model.FileNode;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 21.07.14
 */
public interface Visitor
{
    boolean preVisit(DirectoryNode directoryNode);

    void postVisit(DirectoryNode directoryNode);

    void visit(FileNode node);

}
