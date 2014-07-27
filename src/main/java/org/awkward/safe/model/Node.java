package org.awkward.safe.model;

import org.awkward.safe.visitor.Visitor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 21.07.14
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Node
{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;

    @ManyToOne
    @JoinColumn
    public DirectoryNode parent;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    public Version version;

    public abstract void visit(Visitor visitor);
}
