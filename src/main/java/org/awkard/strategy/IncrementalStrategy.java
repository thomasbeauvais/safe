package org.awkard.strategy;

import org.awkard.safe.CompareResults;
import org.awkard.safe.model.DirectoryNode;
import org.awkard.safe.model.FileNode;
import org.awkard.safe.model.Node;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
public class IncrementalStrategy
{

    private final SynchronizationType[] synchronizationTypes;

    public enum SynchronizationType
    {
        MODIFIED_FILES,
        NEW_FILES,
        NEW_DIRECTORIES,
        FULL
    }

    public IncrementalStrategy(SynchronizationType ... synchronizationTypes)
    {
        this.synchronizationTypes = synchronizationTypes;
    }

    public Queue<Node> createTransferQueue(CompareResults compare)
    {
        Arrays.sort(synchronizationTypes);

        final boolean isFull = Arrays.binarySearch(synchronizationTypes, SynchronizationType.FULL) > -1;

        final Queue<Node> transferQueue = new LinkedList<Node>();

        if (Arrays.binarySearch(synchronizationTypes, SynchronizationType.NEW_DIRECTORIES) > -1 || isFull)
        {
            for (Node node : compare.missing)
            {
                if (node instanceof DirectoryNode)
                {
                    transferQueue.offer(node);
                }
            }
        }

        if (Arrays.binarySearch(synchronizationTypes, SynchronizationType.NEW_FILES) > -1 || isFull)
        {
            for (Node node : compare.missing)
            {
                if (node instanceof FileNode)
                {
                    transferQueue.offer(node);
                }
            }
        }

        if (Arrays.binarySearch(synchronizationTypes, SynchronizationType.MODIFIED_FILES) > -1 || isFull)
        {
            for (Node node : compare.modified)
            {
                transferQueue.offer(node);
            }
        }

        return transferQueue;
    }
}
