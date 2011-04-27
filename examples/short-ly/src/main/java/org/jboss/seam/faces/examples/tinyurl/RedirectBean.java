package org.jboss.seam.faces.examples.tinyurl;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named
@RequestScoped
public class RedirectBean
{
   @Inject
   FacesContext context;

   @Inject
   LinkBean linkBean;

   private String name;

   public String send() throws IOException
   {
	   
	   System.out.println("send!");
	   
      String url = linkBean.getByKey(name).getTarget();
      if (url == null)
      {
         return "index";
      }

      System.out.println("Sent redirect for key: " + name + " => " + url);
      HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
      response.sendRedirect(linkBean.format(url));
      context.responseComplete();
      
      return null;
   }

   public String getName()
   {
      return name;
   }

   public void setName(final String key)
   {
      this.name = key;
   }
}
