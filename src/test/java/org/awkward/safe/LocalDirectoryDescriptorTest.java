//package org.awkward.safe;
//
//import org.awkard.safe.model.local.LocalDescriptor;
//import org.awkard.safe.model.local.LocalDirectoryDescriptor;
//import org.junit.Test;
//
//import java.io.File;
//
//import static org.junit.Assert.*;
//
///**
// * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
// * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
// *
// * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
// * @since 30.06.14
// */
//public class LocalDirectoryDescriptorTest
//{
//    final LocalDirectoryDescriptor localDescriptor = new LocalDirectoryDescriptor(new File("data/local")).index();
//    final LocalDirectoryDescriptor serverDescriptor = new LocalDirectoryDescriptor(new File("data/server")).setLocalSeed(localDescriptor).index();
//
//    final String path = "/chrome-extension/kitties/myPage.html";
//
//    @Test
//    public void server_count()
//    {
//        assertEquals(2, serverDescriptor.getCount());
//        assertEquals(58, serverDescriptor.getCount(true));
//    }
//
//    @Test
//    public void server_find() throws Exception
//    {
//        final LocalDescriptor descriptor = serverDescriptor.getRootParent().find(path);
//
//        assertNotNull(descriptor);
//    }
//
//    @Test
//    public void server_path()
//    {
//        final LocalDescriptor descriptor = serverDescriptor.getRootParent().find(path);
//
//        assertEquals(path, descriptor.getPath());
//    }
//
//    @Test
//    public void local_find_true()
//    {
//        assertNotNull(localDescriptor.find(path));
//    }
//
//    @Test
//    public void local_find_false()
//    {
//        assertNull(localDescriptor.find("/asdf"));
//    }
//
//    @Test
//    public void local_exists_true()
//    {
//        final LocalDescriptor descriptor = serverDescriptor.getRootParent().find(path);
//
//        assertTrue(descriptor.getLocalSeed().exists(descriptor));
//    }
//
//    @Test
//    public void local_exists_false()
//    {
//        assertFalse(serverDescriptor.getLocalSeed().exists("/asdf"));
//    }
//}
