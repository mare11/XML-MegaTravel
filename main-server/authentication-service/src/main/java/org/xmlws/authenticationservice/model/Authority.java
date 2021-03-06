//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.16 at 03:43:06 PM CEST 
//


package org.xmlws.authenticationservice.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="role" type="{https://github.com/mare11/XML_MegaTravel/user}AuthorityEnum"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "role"
})
@XmlRootElement(name = "Authority")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = -4808358912150973864L;
	
	@XmlElement(required = true)
    protected AuthorityEnum role;

    /**
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link AuthorityEnum }
     *     
     */
    public AuthorityEnum getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthorityEnum }
     *     
     */
    public void setRole(AuthorityEnum value) {
        this.role = value;
    }

	@Override
	public String getAuthority() {
		return this.getRole().name();
	}
}