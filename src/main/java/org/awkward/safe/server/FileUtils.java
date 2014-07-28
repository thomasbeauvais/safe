package org.awkward.safe.server;

import java.io.*;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
public class FileUtils extends org.apache.commons.io.FileUtils
{
    public static void writeInputStreamToFile(File file, InputStream inputStream)
    {
        try
        {
            final OutputStream outputStream = new FileOutputStream(file);

            final byte[] buffer = new byte[1024*1024];
            int read;
            while ((read = inputStream.read(buffer)) > -1)
            {
                outputStream.write(buffer, 0, read);
                outputStream.flush();
            }
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
