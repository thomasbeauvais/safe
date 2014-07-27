package org.awkard.safe;

import org.apache.commons.io.IOUtils;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 21.07.14
 */
public class MD5Utils
{
    public static String getChecksum(File file)
    {
        if (file.isDirectory())
        {
            return null;
        }

        InputStream inputStream = null;
        try
        {
//            final MessageDigest md5 = MessageDigest.getInstance("MD5");
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");

            inputStream = new FileInputStream(file);

            final byte[] buffer = new byte[Byte.SIZE*1024*1024*16];
            int read;
            while ((read = inputStream.read(buffer)) > 0)
            {
                digest.update(buffer, 0, read);
            }

            return new HexBinaryAdapter().marshal(digest.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
