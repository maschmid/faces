/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.faces.examples.tinyurl.ftest;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import com.thoughtworks.selenium.SeleniumException;
import java.net.URL;
import org.jboss.test.selenium.AbstractTestCase;
import org.jboss.test.selenium.locator.XpathLocator;
import static org.jboss.test.selenium.locator.LocatorFactory.*;
import static org.jboss.test.selenium.locator.Attribute.HREF;
import org.jboss.test.selenium.locator.AttributeLocator;
import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.*;

/**
 * A functional test for the short-ly example
 *
 * @author Marek Schmidt
 *
 */
public class ShortlyTest extends AbstractTestCase {

   protected final XpathLocator URL_TEXT = xp("//input[contains(@name,':url')]");
   protected final XpathLocator NAME_TEXT = xp("//input[contains(@name,':name')]");
   protected final XpathLocator CREATE_BUTTON = xp("//input[contains(@value,'Create')]");
   protected final XpathLocator DELETEALL_BUTTON = xp("//input[contains(@value,'deleteAll')]");

   protected final XpathLocator ROOT_LINK = xp("//a[text()=\"root\"]");
   protected final AttributeLocator ROOT_LINK_HREF = ROOT_LINK.getAttribute(HREF);

   @BeforeMethod
   public void openStartUrl() throws MalformedURLException
   {
      selenium.setSpeed(300);
      selenium.open(new URL(contextPath.toString()));
   }
 
   @Test
   public void testCreate() throws MalformedURLException
   {
      // deleteAll button is not displayed if there are no links
      assertEquals(selenium.isElementPresent(DELETEALL_BUTTON), false);

      // We can only test pages on the same domain, the only interesting page we can be quite sure to exist on the same domain is the context root
      selenium.type(URL_TEXT, contextRoot.toString());
      selenium.type(NAME_TEXT, "root");
      waitHttp(selenium).click(CREATE_BUTTON);

      assertEquals(selenium.getAttribute(ROOT_LINK_HREF), "/short.ly/root");

      waitHttp(selenium).click(ROOT_LINK);
      assertEquals(selenium.getLocation().toString(), contextRoot.toString());
   }

   @Test(dependsOnMethods={"testCreate"})
   public void testDeleteAll()
   {
      waitHttp(selenium).click(DELETEALL_BUTTON);
      assertEquals(selenium.isElementPresent(DELETEALL_BUTTON), false);
      assertEquals(selenium.isElementPresent(ROOT_LINK), false);
   }
}
