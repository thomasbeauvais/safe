package org.awkward.safe.model;

import org.awkward.safe.server.PathUtils;

import javax.persistence.*;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
@Entity
public class Version
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne
    public FileNode target;

    public String path;

    public long lastModified;

    public String checksum;

    public String getName()
    {
        return PathUtils.getName(path);
    }
}
