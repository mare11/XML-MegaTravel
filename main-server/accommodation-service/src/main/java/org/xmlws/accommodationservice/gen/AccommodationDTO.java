//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.13 at 09:21:00 PM CEST 
//


package org.xmlws.accommodationservice.gen;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.xmlws.accommodationservice.model.AccommodationType;
import org.xmlws.accommodationservice.model.AdditionalService;
import org.xmlws.accommodationservice.model.Location;
import org.xmlws.accommodationservice.model.PeriodPrice;
import org.xmlws.accommodationservice.model.Unavailability;
import org.xmlws.dataservice.adapter.BooleanAdapter;
import org.xmlws.dataservice.adapter.IntegerAdapter;
import org.xmlws.dataservice.adapter.LongAdapter;


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
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element ref="{https://github.com/mare11/XML_MegaTravel/accommodation}AccommodationType"/>
 *         &lt;element name="category">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element ref="{https://github.com/mare11/XML_MegaTravel/accommodation}AdditionalService" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeCancellation" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="cancellationDays">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;totalDigits value="2"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="cancellationPrice" type="{https://github.com/mare11/XML_MegaTravel/accommodation}TPrice"/>
 *         &lt;element name="description">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="200"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="images" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="numberOfPersons">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;totalDigits value="2"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="agentId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="defaultPrice" type="{https://github.com/mare11/XML_MegaTravel/accommodation}TPrice"/>
 *         &lt;element ref="{https://github.com/mare11/XML_MegaTravel/accommodation}PeriodPrice" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{https://github.com/mare11/XML_MegaTravel/accommodation}Location"/>
 *         &lt;element ref="{https://github.com/mare11/XML_MegaTravel/accommodation}Unavailability" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reservationIds" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
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
    "id",
    "accommodationType",
    "category",
    "additionalService",
    "freeCancellation",
    "cancellationDays",
    "cancellationPrice",
    "description",
    "images",
    "numberOfPersons",
    "agentId",
    "defaultPrice",
    "periodPrice",
    "location",
    "unavailability",
    "reservationIds"
})
@XmlRootElement(name = "AccommodationDTO")
public class AccommodationDTO {

    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(LongAdapter.class)
    @XmlSchemaType(name = "long")
    protected Long id;
    @XmlElement(name = "AccommodationType", required = true)
    protected AccommodationType accommodationType;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    protected Integer category;
    @XmlElement(name = "AdditionalService")
    protected List<AdditionalService> additionalService;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean freeCancellation;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    protected Integer cancellationDays;
    @XmlElement(required = true)
    protected BigDecimal cancellationPrice;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected List<String> images;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    protected Integer numberOfPersons;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(LongAdapter.class)
    @XmlSchemaType(name = "long")
    protected Long agentId;
    @XmlElement(required = true)
    protected BigDecimal defaultPrice;
    @XmlElement(name = "PeriodPrice")
    protected List<PeriodPrice> periodPrice;
    @XmlElement(name = "Location", required = true)
    protected Location location;
    @XmlElement(name = "Unavailability")
    protected List<Unavailability> unavailability;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(LongAdapter.class)
    @XmlSchemaType(name = "long")
    protected List<Long> reservationIds;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the accommodationType property.
     * 
     * @return
     *     possible object is
     *     {@link AccommodationType }
     *     
     */
    public AccommodationType getAccommodationType() {
        return accommodationType;
    }

    /**
     * Sets the value of the accommodationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccommodationType }
     *     
     */
    public void setAccommodationType(AccommodationType value) {
        this.accommodationType = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Integer getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategory(Integer value) {
        this.category = value;
    }

    /**
     * Gets the value of the additionalService property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the additionalService property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdditionalService }
     * 
     * 
     */
    public List<AdditionalService> getAdditionalService() {
        if (additionalService == null) {
            additionalService = new ArrayList<AdditionalService>();
        }
        return this.additionalService;
    }

    /**
     * Gets the value of the freeCancellation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean isFreeCancellation() {
        return freeCancellation;
    }

    /**
     * Sets the value of the freeCancellation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreeCancellation(Boolean value) {
        this.freeCancellation = value;
    }

    /**
     * Gets the value of the cancellationDays property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Integer getCancellationDays() {
        return cancellationDays;
    }

    /**
     * Sets the value of the cancellationDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCancellationDays(Integer value) {
        this.cancellationDays = value;
    }

    /**
     * Gets the value of the cancellationPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCancellationPrice() {
        return cancellationPrice;
    }

    /**
     * Sets the value of the cancellationPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCancellationPrice(BigDecimal value) {
        this.cancellationPrice = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the images property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the images property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getImages() {
        if (images == null) {
            images = new ArrayList<String>();
        }
        return this.images;
    }

    /**
     * Gets the value of the numberOfPersons property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Integer getNumberOfPersons() {
        return numberOfPersons;
    }

    /**
     * Sets the value of the numberOfPersons property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfPersons(Integer value) {
        this.numberOfPersons = value;
    }

    /**
     * Gets the value of the agentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Long getAgentId() {
        return agentId;
    }

    /**
     * Sets the value of the agentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentId(Long value) {
        this.agentId = value;
    }

    /**
     * Gets the value of the defaultPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDefaultPrice() {
        return defaultPrice;
    }

    /**
     * Sets the value of the defaultPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDefaultPrice(BigDecimal value) {
        this.defaultPrice = value;
    }

    /**
     * Gets the value of the periodPrice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the periodPrice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPeriodPrice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PeriodPrice }
     * 
     * 
     */
    public List<PeriodPrice> getPeriodPrice() {
        if (periodPrice == null) {
            periodPrice = new ArrayList<PeriodPrice>();
        }
        return this.periodPrice;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link Location }
     *     
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link Location }
     *     
     */
    public void setLocation(Location value) {
        this.location = value;
    }

    /**
     * Gets the value of the unavailability property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unavailability property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnavailability().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Unavailability }
     * 
     * 
     */
    public List<Unavailability> getUnavailability() {
        if (unavailability == null) {
            unavailability = new ArrayList<Unavailability>();
        }
        return this.unavailability;
    }

    /**
     * Gets the value of the reservationIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reservationIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReservationIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<Long> getReservationIds() {
        if (reservationIds == null) {
            reservationIds = new ArrayList<Long>();
        }
        return this.reservationIds;
    }

}
