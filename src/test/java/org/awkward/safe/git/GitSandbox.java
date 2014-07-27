package org.awkward.safe.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.Repository;
import org.junit.Test;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 01.07.14
 */
public class GitSandbox
{
    @Test
    public void aklsdjflksdj() throws Exception
    {
        final String path = "https://github.com/thomasbeauvais/silbury-test.git";

        final DfsRepositoryDescription sdfalsdkjf = new DfsRepositoryDescription("sdfalsdkjf");
        final InMemoryRepository inMemoryRepository = new InMemoryRepository(sdfalsdkjf);
        final Git git = new Git(inMemoryRepository);
        final Repository repository = git.cloneRepository().setURI(path).call().getRepository();

        System.out.println(repository.getDirectory());
    }

}
