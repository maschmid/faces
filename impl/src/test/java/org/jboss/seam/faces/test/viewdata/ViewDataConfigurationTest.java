package org.jboss.seam.faces.test.viewdata;

import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.faces.viewdata.ViewDataStore;
import org.jboss.seam.faces.viewdata.ViewDataStoreImpl;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ViewDataConfigurationTest
{

   @Deployment
   public static Archive<?> createTestArchive()
   {
      JavaArchive archive = ShrinkWrap.create(JavaArchive.class)
            .addClass(ViewDataStoreImpl.class)
            .addPackage(ViewDataConfigurationTest.class.getPackage())
            .addManifestResource(new ByteArrayAsset(new byte[0]), ArchivePaths.create("beans.xml"));
      return archive;
   }

   @Inject
   ViewDataStore store;

   @Test
   public void testViewDataStore()
   {

      store.addData("/*", new IconLiteral("default.gif"));
      store.addData("/sad/*", new IconLiteral("sad.gif"));
      store.addData("/happy/*", new IconLiteral("happy.gif"));
      store.addData("/happy/done.xhtml", new IconLiteral("finished.gif"));

      Icon data;
      data = store.getData("/happy/done.xhtml", Icon.class);
      Assert.assertEquals("finished.gif", data.value());
      data = store.getData("/happy/other.xhtml", Icon.class);
      Assert.assertEquals("happy.gif", data.value());
      data = store.getData("/default/news.xhtml", Icon.class);
      Assert.assertEquals("default.gif", data.value());

      List<Icon> dlist;
      dlist = store.getAllData("/happy/done.xhtml", Icon.class);
      Assert.assertEquals(3, dlist.size());
      Assert.assertEquals("finished.gif", dlist.get(0).value());
      Assert.assertEquals("happy.gif", dlist.get(1).value());
      Assert.assertEquals("default.gif", dlist.get(2).value());
      dlist = store.getAllData("/happy/other.xhtml", Icon.class);
      Assert.assertEquals(2, dlist.size());
      Assert.assertEquals("happy.gif", dlist.get(0).value());
      Assert.assertEquals("default.gif", dlist.get(1).value());
      dlist = store.getAllData("/default/news.xhtml", Icon.class);
      Assert.assertEquals(1, dlist.size());
      Assert.assertEquals("default.gif", dlist.get(0).value());

   }
}
