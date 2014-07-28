package org.awkward.safe.client;

import org.awkward.safe.model.Node;

import java.util.Queue;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
public interface ClientCallback
{
    boolean doPush(Queue<Node> transferQueue);

    boolean doPull(Queue<Node> transferQueue);
}
