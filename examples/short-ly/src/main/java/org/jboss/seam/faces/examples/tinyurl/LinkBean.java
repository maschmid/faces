package org.jboss.seam.faces.examples.tinyurl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import javax.inject.Inject;

import javax.ejb.Init;
import javax.ejb.Stateful;
import javax.ejb.PostActivate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named
@ConversationScoped
@Stateful
public class LinkBean implements Serializable
{
   private static final long serialVersionUID = -2209547152337410725L;

   @PersistenceContext
   private EntityManager em;

   private TinyLink link = new TinyLink();

   @Inject
   private Conversation conversation;

   @PostActivate
   public void onActivate() {
      System.out.println("LinkBean activating");
   }

   @PostConstruct
   public void onConstruct() {
      System.out.println("LinkBean construct");
   }

   @PreDestroy
   public void onDestroy() {
      System.out.println("LinkBean destroy");
   }


   @Begin
   @End
   public String createLink() throws SQLException
   {
      System.out.println("Created link: [ " + link.getName() + " => " + link.getTarget() + " ]");
      em.persist(link);
      //return "pretty:create";
      return "index";
   }

//   @End
   public String updateLink() throws SQLException
   {
      System.out.println("Updated link id " + link.getId() + ": [ " + link.getName() + " => " + link.getTarget() + " ]");
      em.persist(link);
      conversation.end();

      return "index";
      //return "pretty:create";
   }


   public void setName(String name)
   {
	   this.link.setName(name);
   }

   public String getName()
   {
      return this.link.getName();
   }

  // @Begin   
   public void editLink()
   {
      conversation.begin();
      System.out.println("editLink for " + link.getName());
	   setLink(getByKey(link.getName()));
   }

   @SuppressWarnings("unchecked")
   public TinyLink getByKey(final String key)
   {
      System.out.println("geByKey " + key);

      Query query = em.createQuery("from TinyLink t where t.name=:key", TinyLink.class);
      query.setParameter("key", key);
      List<TinyLink> resultList = query.getResultList();
      if (resultList.isEmpty())
      {
         TinyLink newLink = new TinyLink();
         newLink.setName(key);
         return newLink;
      }
      return resultList.get(0);
   }

   public String format(final String link)
   {
      if (link != null)
      {
         String result = link.trim();
         if (!result.matches("(http|ftp)://.*"))
         {
            result = "http://" + result;
         }
         return result;
      }
      return "";
   }

   public String deleteAll()
   {
      em.createQuery("delete from TinyLink").executeUpdate();
      return "pretty:";
   }

   public List<TinyLink> getLinks()
   {
      return em.createQuery("from TinyLink").getResultList();
   }

   public TinyLink getLink()
   {
      return link;
   }

   public void setLink(final TinyLink link)
   {
      System.out.println("set link id: " + link.getId());
      this.link = link;
   }
}
