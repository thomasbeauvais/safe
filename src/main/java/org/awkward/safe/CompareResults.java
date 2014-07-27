package org.awkward.safe;

import org.awkward.safe.model.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
public class CompareResults
{
    public List<Node> missing = new ArrayList<Node>();

    public List<Node> modified = new ArrayList<Node>();

    @Override
    public String toString()
    {
        return "NodeCompare{" +
                "missing=" + missing +
                ", modified=" + modified +
                '}';
    }
}
