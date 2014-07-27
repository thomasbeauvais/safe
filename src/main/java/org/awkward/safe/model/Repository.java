package org.awkward.safe.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
@Entity
public class Repository
{
    @Id
    public String id;

    public String name;

}
