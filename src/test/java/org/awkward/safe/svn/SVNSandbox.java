package org.awkward.safe.svn;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc2.ISvnObjectReceiver;
import org.tmatesoft.svn.core.wc2.SvnList;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnTarget;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 01.07.14
 */
public class SVNSandbox
{
    public static final String username = "tbeauvais";
    public static final String password = "Donkeyman1";

    private SvnOperationFactory operationFactory;

    public static final SVNClientManager clientManager = SVNClientManager.newInstance(new DefaultSVNOptions(), username, password);

    public static final String path = "https://silbury.svn.beanstalkapp.com/tbeauvais/";

    private static SVNURL url;

    @BeforeClass
    public static void beforeClass() throws SVNException
    {
        url = SVNURL.parseURIEncoded(path);
    }

//    private ExecutorService executorService;

    @Test
    public void login() throws SVNException, InterruptedException
    {
        final File file = new File("/safe/Code/silbury/tbeauvais");

        final SVNRepository repository = clientManager.createRepository(url, true);

        operationFactory = new SvnOperationFactory();
        operationFactory.setAuthenticationManager(new BasicAuthenticationManager(username, password));
        operationFactory.setOptions(new DefaultSVNOptions());

//        final Collection<SVNEntry> entries = repository.getDir("", -1, null, (Collection) null);

        System.out.println();

        final SVNURL repositoryRoot = repository.getRepositoryRoot(true);

//        final DirectoryNode root = new SVNDirectoryNode(repository, operationFactory, null, repositoryRoot);

//        listEntries(repository, "");

//        executorService = Executors.newFixedThreadPool(8);

//          listContents(url);

//        executorService.awaitTermination(10, TimeUnit.MINUTES);

/*
        final SvnRepositoryGetTree tree = operationFactory.createRepositoryGetTree();
        tree.addTarget(SvnTarget.fromURL(repo.getLocation()));
        tree.setRecursive(true);
        tree.setRevision(SVNRevision.HEAD);
        tree.setReceiver(new ISvnObjectReceiver<SVNAdminPath>()
        {
            @Override
            public void receive(SvnTarget target, SVNAdminPath object) throws SVNException
            {
                System.out.println(object.getPath());
            }
        });
        tree.run();
        */

//        final SVNRepository repository = clientManager.createRepository(url, true);
//        repository.getFile("/trunk/.svnignore", -1, null, System.out );
    }

    public static void listEntries(SVNRepository repository, String path) throws SVNException
    {
        Collection entries = repository.getDir(path, -1, null, (Collection) null);
        Iterator iterator = entries.iterator();
        while (iterator.hasNext())
        {
            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            System.out.println("/" + (path.equals("") ? "" : path + "/") + entry.getName() +
                                       " ( author: '" + entry.getAuthor() + "'; revision: " + entry.getRevision() +
                                       "; date: " + entry.getDate() + ")");
            if (entry.getKind() == SVNNodeKind.DIR)
            {
                listEntries(repository, (path.equals("")) ? entry.getName() : path + "/" + entry.getName());

            }
        }
    }

    private void listContents(SVNURL url) throws SVNException
    {
        final SvnList list = operationFactory.createList();
        list.addTarget(SvnTarget.fromURL(url));
        list.setReceiver(new ISvnObjectReceiver<SVNDirEntry>()
        {
            public void receive(SvnTarget target, final SVNDirEntry object) throws SVNException
            {
                if (StringUtils.isEmpty(object.getName()))
                {
                    // skip?
                    return;
                }

                System.out.println(object.getURL().toDecodedString());

                if (object.getKind().equals(SVNNodeKind.DIR))
                {
                    listContents(object.getURL());
                }
            }
        });

        list.run();
    }
}
