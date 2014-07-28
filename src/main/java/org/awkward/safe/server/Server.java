package org.awkward.safe.server;

import org.awkward.safe.PullCallback;
import org.awkward.safe.model.Repository;

import java.io.InputStream;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
public interface Server
{
    void start();

    Repository[] findAllRepositories();

    Repository createRepository(String name);

    void deleteRepository(String repositoryId);

    // file store
    void createDirectory(String repositoryId, String path);

    void createFile(String repositoryId, String parentPath, String filename, InputStream inputStream);

    void pull(PullCallback pullCallback);

    void obtainLock(String repositoryId);

    void releaseLock(String repositoryId);
}
