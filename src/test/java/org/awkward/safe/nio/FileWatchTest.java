package org.awkward.safe.nio;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 19.07.14
 */
public class FileWatchTest
{
    @Test
    public void file_watch() throws IOException, InterruptedException, NoSuchAlgorithmException
    {
        final WatchService watcher = FileSystems.getDefault().newWatchService();

        final SimpleFileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>()
        {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
            {
                dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

                return FileVisitResult.CONTINUE;
            }
        };

        final Path base = new File("data").toPath();

        Files.walkFileTree(base, fileVisitor);

        for (; ; )
        {
            System.out.println("polling..");
            WatchKey key = watcher.take();

            for (WatchEvent<?> event : key.pollEvents())
            {
                WatchEvent.Kind<?> kind = event.kind();

                // This key is registered only
                // for ENTRY_CREATE events,
                // but an OVERFLOW event can
                // occur regardless if events
                // are lost or discarded.
                if (kind == OVERFLOW)
                {
                    continue;
                }

                // The filename is the
                // context of the event.
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path context = ev.context();

                final Path watchable = (Path) key.watchable();
                final Path absolute = watchable.resolve(context).toAbsolutePath();
                final File file = absolute.toFile();
                if (file.exists())
                {
//                    final byte[] md5 = MessageDigest.getInstance("MD5").digest(IOUtils.toByteArray(new FileInputStream(file)));

                    final MessageDigest md5 = MessageDigest.getInstance("MD5");

                    String hex = (new HexBinaryAdapter()).marshal(md5.digest(IOUtils.toByteArray(new FileInputStream(file))));

                    System.out.format("File %s %s(%s)%n", ev.kind().name(), absolute, hex);
                }
            }

            // Reset the key -- this step is critical if you want to
            // receive further watch events.  If the key is no longer valid,
            // the directory is inaccessible so exit the loop.
            boolean valid = key.reset();
            if (!valid)
            {
                break;
            }
        }

    }
}
