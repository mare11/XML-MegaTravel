//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.11 at 11:35:53 AM CEST 
//


package org.xmlws.reservationservice.gen;

import org.xmlws.dataservice.adapter.LongAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element name="accommodationId" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "accommodationId"
})
@XmlRootElement(name = "getAccReservationRequest")
public class GetAccReservationRequest {

    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(LongAdapter.class)
    @XmlSchemaType(name = "long")
    protected Long accommodationId;

    /**
     * Gets the value of the accommodationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Long getAccommodationId() {
        return accommodationId;
    }

    /**
     * Sets the value of the accommodationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccommodationId(Long value) {
        this.accommodationId = value;
    }

}
