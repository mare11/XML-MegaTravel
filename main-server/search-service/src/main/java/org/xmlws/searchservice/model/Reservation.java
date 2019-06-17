//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.10 at 06:00:00 PM CEST 
//


package org.xmlws.searchservice.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.xmlws.dataservice.adapter.BooleanAdapter;
import org.xmlws.dataservice.adapter.LocalDateAdapter;
import org.xmlws.dataservice.adapter.LongAdapter;
import org.xmlws.dataservice.entity.Entity;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="realized" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element ref="{https://github.com/mare11/XML_MegaTravel/reservation}ReservationRating"/>
 *         &lt;element ref="{https://github.com/mare11/XML_MegaTravel/reservation}Message" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "accommodationId",
        "userId",
        "startDate",
        "endDate",
        "realized",
        "reservationRating",
        "message"
})
@XmlRootElement(name = "Reservation")
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends Entity {

    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(LongAdapter.class)
    @XmlSchemaType(name = "long")
    protected Long accommodationId;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(LongAdapter.class)
    @XmlSchemaType(name = "long")
    protected Long userId;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlSchemaType(name = "date")
    protected LocalDate startDate;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlSchemaType(name = "date")
    protected LocalDate endDate;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean realized;
    @XmlElement(name = "ReservationRating", required = true)
    protected ReservationRating reservationRating;
    @XmlElement(name = "Message")
    protected List<Message> message;

    /**
     * Gets the value of the accommodationId property.
     *
     * @return possible object is
     * {@link String }
     */
    public Long getAccommodationId() {
        return accommodationId;
    }

    /**
     * Sets the value of the accommodationId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAccommodationId(Long value) {
        this.accommodationId = value;
    }

    /**
     * Gets the value of the userId property.
     *
     * @return possible object is
     * {@link String }
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setUserId(Long value) {
        this.userId = value;
    }

    /**
     * Gets the value of the startDate property.
     *
     * @return possible object is
     * {@link String }
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStartDate(LocalDate value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     *
     * @return possible object is
     * {@link String }
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEndDate(LocalDate value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the realized property.
     *
     * @return possible object is
     * {@link String }
     */
    public Boolean isRealized() {
        return realized;
    }

    /**
     * Sets the value of the realized property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRealized(Boolean value) {
        this.realized = value;
    }

    /**
     * Gets the value of the reservationRating property.
     *
     * @return possible object is
     * {@link ReservationRating }
     */
    public ReservationRating getReservationRating() {
        return reservationRating;
    }

    /**
     * Sets the value of the reservationRating property.
     *
     * @param value allowed object is
     *              {@link ReservationRating }
     */
    public void setReservationRating(ReservationRating value) {
        this.reservationRating = value;
    }

    /**
     * Gets the value of the message property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the message property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessage().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Message }
     */
    public List<Message> getMessage() {
        if (message == null) {
            message = new ArrayList<Message>();
        }
        return this.message;
    }

}
