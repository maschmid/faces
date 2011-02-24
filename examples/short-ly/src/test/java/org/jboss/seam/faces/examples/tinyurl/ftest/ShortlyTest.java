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
import java.net.URL;
import org.jboss.test.selenium.AbstractTestCase;
import org.jboss.test.selenium.locator.XpathLocator;
import static org.jboss.test.selenium.locator.LocatorFactory.*;
import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.*;

/**
 * A functional test for the short-ly example
 *
 * @author Marek Schmidt
 *
 */
class ShortlyTest extends AbstractTestCase {

   protected XpathLocator URL_TEXT = xp("//input[ends-with(@name,':url')]");
   protected XpathLocator NAME_TEXT = xp("//input[ends-with(@name,':name')]");
   protected XpathLocator CREATE_BUTTON = xp("//input[contains(@value,'Create')]");

   protected XpathLocator LINK1 = xp("//a[1]");

   @BeforeMethod
   public void openStartUrl() throws MalformedURLException
   {
      selenium.setSpeed(300);
      selenium.open(new URL(contextPath.toString()));
   }
 
   @Test
   public void testCreate()
   {
      selenium.type(URL_TEXT, "http://www.seamframework.org/Seam3/FacesModule");
      selenium.type(NAME_TEXT, "faces");
      waitHttp(selenium).click(CREATE_BUTTON);
      assertEquals(selenium.getText(LINK1), "faces");
   }
}
