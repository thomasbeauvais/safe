package org.awkward.safe;

import org.awkward.safe.client.ClientRepository;
import org.awkward.safe.model.Node;
import org.awkward.safe.server.PathUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
public class PushCallback
{
    private final Node node;

    private final ClientRepository clientRepository;

    private InputStream inputStream;

    public PushCallback(ClientRepository clientRepository, Node node)
    {
        this.clientRepository = clientRepository;
        this.node = node;
    }

    public ClientRepository getClientRepository()
    {
        return clientRepository;
    }

    public Node getNode()
    {
        return node;
    }

    public InputStream getInputStream()
    {
        try
        {
            return new FileInputStream(PathUtils.getFile(clientRepository.file, node.version.path));
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
