//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.07 at 12:55:23 PM CEST 
//


package org.xmlws.accommodationservice.model;

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
 *         &lt;element firstName="additionalServiceName">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="40"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "additionalServiceName"
})
@XmlRootElement(name = "AdditionalService")
public class AdditionalService {

    @XmlElement(required = true)
    protected String additionalServiceName;

    /**
     * Gets the value of the additionalServiceName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAdditionalServiceName() {
        return additionalServiceName;
    }

    /**
     * Sets the value of the additionalServiceName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAdditionalServiceName(String value) {
        this.additionalServiceName = value;
    }

}
