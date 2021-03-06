package org.awkward.safe.data;

import org.awkward.safe.model.DirectoryNode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
public interface DirectoryNodeRepository extends JpaRepository<DirectoryNode, String>
{
}
