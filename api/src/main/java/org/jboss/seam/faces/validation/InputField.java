package org.jboss.seam.faces.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.inject.Qualifier;

/**
 * To be used in conjunction with <code>&lt;s:validateForm /&gt;</code> in
 * Validators that should have their values fetched from a JSF form field.
 * <p>
 * Example:
 * <p>
 * <code>
 * 
 * public @{@link FacesValidator}(&quot;locationValidator&quot;) class
 *         LocationValidator implements {@link Validator} {<p>
 * 
 *         &nbsp;&nbsp;&nbsp; public @Field String city;<br/> &nbsp;&nbsp;&nbsp;
 *         public @Field String state;<br/> &nbsp;&nbsp;&nbsp; public
 * 
 * @Field("zip") String zipcode;<br/> <p> &nbsp;&nbsp;&nbsp; public void
 *               validate(FacesContext context, UIComponent comp, Object
 *               componentMap <br/> &nbsp;&nbsp;&nbsp; {<br/>
 *               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 *               //validate like usual. <br/> &nbsp;&nbsp;&nbsp; } <p> }</code>
 *               <p>
 *               <b>Note:</b> The annotation value @Field("id") Specifies the
 *               default clientId alias for which values will be bound to the
 *               field annotated by this annotation.
 * 
 *               &lt;h:form id=&quot;form&quot;&gt;<br/>
 *               &nbsp;&nbsp;&nbsp; &lt;h:inputText id=&quot;cityId&quot;
 *               value=&quot;#{bean.city}&quot; /&gt;<br/>
 *               &nbsp;&nbsp;&nbsp; &lt;h:inputText id=&quot;state&quot;
 *               value=&quot;#{bean.state}&quot; /&gt;<br/>
 *               &nbsp;&nbsp;&nbsp; &lt;h:inputText id=&quot;zip&quot;
 *               value=&quot;#{bean.zip}&quot; /&gt;<br/>
 *               &nbsp;&nbsp;&nbsp; &lt;h:commandButton id=&quot;submit&quot;
 *               value=&quot;Submit&quot; action=&quot;#{bean.submit}&quot;
 *               /&gt;
 *               <p>
 *               &nbsp;&nbsp;&nbsp; &lt;s:validateForm
 *               fields=&quot;city=cityId&quot;
 *               validatorId=&quot;<b>locationValidator</b>&quot; /&gt;<br/>
 *               &lt;/h:form&gt; </code>
 *               <p>
 *               Notice in the above example, that not all fields must be
 *               specified in the validator tag. If the Facelet field IDs match
 *               the validator fields, the values will automatically be mapped
 *               to the validator.
 *               <p>
 *               Fields can also be mapped to the validator through a simple
 *               alias: "validatorFieldId=componentClientId", where
 *               validatorFieldId is the name of the annotated @Field in the
 *               Validator, and componentClientId is the ID of the input
 *               component relative to the form in which it resides.
 *               <p>
 *               When writing your <b>public void validate(FacesContext context,
 *               UIComponent comp, Object componentMap)</b> method, keep in mind
 *               the following differences from a normal validator:
 *               <ul>
 *               <li>"comp" is the parent UIForm that contains this
 *               &lt;s:validateForm /&gt; tag.</li>
 *               <li>"componentMap" is a map of the requested input field names,
 *               and their corresponding UIInput component objects. This allows
 *               programmatic access to each of the components being validated.</li>
 *               </ul>
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com>Lincoln Baxter, III</a>
 */
@Qualifier
@Retention(RUNTIME)
@Target( { FIELD, METHOD })
public @interface InputField
{
   @Nonbinding
   public String value() default "";
}