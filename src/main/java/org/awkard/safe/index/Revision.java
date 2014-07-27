package org.awkard.safe.index;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
public class Revision
{
    private final int sequence;

    private final long lastModified;

    private final String checksum;

    public Revision(int sequence, long lastModified, String checksum)
    {
        this.sequence = sequence;
        this.lastModified = lastModified;
        this.checksum = checksum;
    }

    public int getSequence()
    {
        return sequence;
    }

    public long getLastModified()
    {
        return lastModified;
    }

    public String getChecksum()
    {
        return checksum;
    }
}
