//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.16 at 03:43:06 PM CEST 
//


package org.xmlws.userservice.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;


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
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "role"
})
@XmlRootElement(name = "Authority")
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @XmlElement(required = true)
    protected AuthorityEnum role;

    /**
     * Gets the value of the role property.
     *
     * @return possible object is
     * {@link AuthorityEnum }
     */
    public AuthorityEnum getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     *
     * @param value allowed object is
     *              {@link AuthorityEnum }
     */
    public void setRole(AuthorityEnum value) {
        this.role = value;
    }

}
