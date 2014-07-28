package org.awkward.safe;

import org.awkward.safe.client.ClientRepository;
import org.awkward.safe.model.Node;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
public class PullCallback
{
    private String node;

    public PullCallback(ClientRepository clientRepository, Node node)
    {
    }

    public String getNode()
    {
        return node;
    }
}
