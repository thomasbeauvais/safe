package org.awkward.safe.data;

import com.sun.jna.platform.win32.VerRsrc;
import org.awkward.DefaultUnitTest;
import org.awkward.safe.model.DirectoryNode;
import org.awkward.safe.model.FileNode;
import org.awkward.safe.model.Repository;
import org.awkward.safe.model.Version;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
@ContextConfiguration(locations = "classpath:spring.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class NodeTest extends DefaultUnitTest
{
    @Autowired
    FileNodeRepository nodeRepository;

    @Autowired
    DirectoryNodeRepository directoryNodeRepository;

    @Autowired
    VersionRepository versionRepository;

    @Autowired
    RepositoryRepository repositoryRepository;

    @Test
    public void path()
    {
        final Version version = versionRepository.findByPathOrderByLastModifiedAsc("/safe/Code/personal/safe/data/local");

        System.out.println(version);
    }

    @Test
    public void test()
    {
        assertNotNull(nodeRepository);
        assertNotNull(directoryNodeRepository);
        assertNotNull(repositoryRepository);

        repositoryRepository.save(local);
    }

    @Test
    public void retrieve()
    {
        final List<DirectoryNode> all = directoryNodeRepository.findAll();
        System.out.println(all);
    }
}
