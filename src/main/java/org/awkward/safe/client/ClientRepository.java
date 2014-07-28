package org.awkward.safe.client;

import org.awkward.safe.model.DirectoryNode;
import org.awkward.safe.model.Repository;

import java.io.File;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
public class ClientRepository extends DirectoryNode
{
    // transient client
    public transient Repository server;
    public transient File file;

    public ClientRepository(File root, Repository server)
    {
        this.file = root;
        this.server = server;

        this.id = server.id;
    }
}
