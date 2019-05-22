//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.21 at 12:07:23 PM CEST 
//

package org.xmlws.userservice.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.boot.jaxb.hbm.spi.Adapter1;
import org.hibernate.boot.jaxb.hbm.spi.Adapter2;
import org.hibernate.boot.jaxb.hbm.spi.Adapter3;
import org.hibernate.boot.jaxb.hbm.spi.Adapter4;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
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
 *         &lt;element name="images" type="{http://www.w3.org/2001/XMLSchema}base64Binary" maxOccurs="unbounded"/>
 *         &lt;element name="numberOfPersons">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;totalDigits value="2"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element ref="{https://github.com/mare11/XML_MegaTravel/user}Agent"/>
 *         &lt;element name="defaultPrice" type="{https://github.com/mare11/XML_MegaTravel/accommodation}TPrice"/>
 *         &lt;element ref="{https://github.com/mare11/XML_MegaTravel/accommodation}PeriodPrice" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{https://github.com/mare11/XML_MegaTravel/accommodation}Location"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "accommodationType", "category", "additionalService", "freeCancellation",
		"cancellationDays", "cancellationPrice", "description", "images", "numberOfPersons", "agent", "defaultPrice",
		"periodPrice", "location" })
@XmlRootElement(name = "Accommodation")
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Accommodation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@XmlElement(name = "AccommodationType", required = true)
	@OneToOne(fetch = FetchType.EAGER)
	protected AccommodationType accommodationType;

	@XmlElement(required = true, type = String.class)
	@XmlJavaTypeAdapter(Adapter1.class)
	@Column(name = "category", nullable = false)
	protected Integer category;

	@XmlElement(name = "AdditionalService")
	@ManyToMany
	@JoinTable(name = "accommodation_services", joinColumns = @JoinColumn(name = "accommodation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
	protected Set<AdditionalService> additionalServices = new HashSet<>();

	@XmlElement(required = true, type = String.class)
	@XmlJavaTypeAdapter(Adapter2.class)
	@XmlSchemaType(name = "boolean")
	@Column(name = "free_cancellation", nullable = false)
	protected Boolean freeCancellation;

	@XmlElement(required = true, type = String.class)
	@XmlJavaTypeAdapter(Adapter3.class)
	@Column(name = "cancellation_days", nullable = false)
	protected Integer cancellationDays;

	@XmlElement(required = true)
	@Column(name = "cancellation_price", nullable = false)
	protected BigDecimal cancellationPrice;

	@XmlElement(required = true)
	@Column(name = "description")
	protected String description;

	@XmlElement(required = true)
	@Lob
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_images")
	@Column(name = "image")
	protected Set<byte[]> images = new HashSet<>();

	@XmlElement(required = true, type = String.class)
	@XmlJavaTypeAdapter(Adapter4.class)
	@Column(name = "number_of_persons", nullable = false)
	protected Integer numberOfPersons;

	@XmlElement(name = "Agent", namespace = "https://github.com/mare11/XML_MegaTravel/user", required = true)
	@ManyToOne(fetch = FetchType.EAGER)
	protected Agent agent;

	@XmlElement(required = true)
	@Column(name = "default_price", nullable = false)
	protected BigDecimal defaultPrice;

	@XmlElement(name = "PeriodPrice")
	@OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<PeriodPrice> periodPrices = new HashSet<>();

	@XmlElement(name = "Location", required = true)
	@OneToOne(fetch = FetchType.EAGER)
	protected Location location;

	@XmlElement(name = "Reservation", namespace = "https://github.com/mare11/XML_MegaTravel/reservation")
	@OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<Reservation> reservations = new HashSet<>();

	@XmlElement(name = "Unavailability")
	@OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<Unavailability> unavailabilities = new HashSet<>();

	@Version
	private Long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the value of the accommodationType property.
	 * 
	 * @return possible object is {@link AccommodationType }
	 * 
	 */
	public AccommodationType getAccommodationType() {
		return accommodationType;
	}

	/**
	 * Sets the value of the accommodationType property.
	 * 
	 * @param value allowed object is {@link AccommodationType }
	 * 
	 */
	public void setAccommodationType(AccommodationType value) {
		this.accommodationType = value;
	}

	/**
	 * Gets the value of the category property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public Integer getCategory() {
		return category;
	}

	/**
	 * Sets the value of the category property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCategory(Integer value) {
		this.category = value;
	}

	/**
	 * Gets the value of the additionalService property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present
	 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
	 * for the additionalService property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAdditionalService().add(newItem);
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
		if (additionalServices == null) {
			additionalServices = new HashSet<AdditionalService>();
		}
		return new ArrayList<>(this.additionalServices);
	}

	/**
	 * Gets the value of the freeCancellation property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public Boolean isFreeCancellation() {
		return freeCancellation;
	}

	/**
	 * Sets the value of the freeCancellation property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setFreeCancellation(Boolean value) {
		this.freeCancellation = value;
	}

	/**
	 * Gets the value of the cancellationDays property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public Integer getCancellationDays() {
		return cancellationDays;
	}

	/**
	 * Sets the value of the cancellationDays property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCancellationDays(Integer value) {
		this.cancellationDays = value;
	}

	/**
	 * Gets the value of the cancellationPrice property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getCancellationPrice() {
		return cancellationPrice;
	}

	/**
	 * Sets the value of the cancellationPrice property.
	 * 
	 * @param value allowed object is {@link BigDecimal }
	 * 
	 */
	public void setCancellationPrice(BigDecimal value) {
		this.cancellationPrice = value;
	}

	/**
	 * Gets the value of the description property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the description property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
	 * Gets the value of the images property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present
	 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
	 * for the images property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getImages().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list byte[]
	 * 
	 */
	public List<byte[]> getImages() {
		if (images == null) {
			images = new HashSet<byte[]>();
		}
		return new ArrayList<>(this.images);
	}

	/**
	 * Gets the value of the numberOfPersons property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public Integer getNumberOfPersons() {
		return numberOfPersons;
	}

	/**
	 * Sets the value of the numberOfPersons property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setNumberOfPersons(Integer value) {
		this.numberOfPersons = value;
	}

	/**
	 * Gets the value of the agent property.
	 * 
	 * @return possible object is {@link Agent }
	 * 
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * Sets the value of the agent property.
	 * 
	 * @param value allowed object is {@link Agent }
	 * 
	 */
	public void setAgent(Agent value) {
		this.agent = value;
	}

	/**
	 * Gets the value of the defaultPrice property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getDefaultPrice() {
		return defaultPrice;
	}

	/**
	 * Sets the value of the defaultPrice property.
	 * 
	 * @param value allowed object is {@link BigDecimal }
	 * 
	 */
	public void setDefaultPrice(BigDecimal value) {
		this.defaultPrice = value;
	}

	/**
	 * Gets the value of the periodPrice property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present
	 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
	 * for the periodPrice property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getPeriodPrice().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link PeriodPrice }
	 * 
	 * 
	 */
	public List<PeriodPrice> getPeriodPrices() {
		if (periodPrices == null) {
			periodPrices = new HashSet<PeriodPrice>();
		}
		return new ArrayList<>(this.periodPrices);
	}

	/**
	 * Gets the value of the location property.
	 * 
	 * @return possible object is {@link Location }
	 * 
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Sets the value of the location property.
	 * 
	 * @param value allowed object is {@link Location }
	 * 
	 */
	public void setLocation(Location value) {
		this.location = value;
	}

	
    /**
     * Gets the value of the reservation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reservation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReservation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Reservation }
     * 
     * 
     */
	public List<Reservation> getReservations() {
		if (reservations == null) {
			reservations = new HashSet<Reservation>();
		}
		return new ArrayList<>(this.reservations);
	}

	/**
	 * Gets the value of the unavailability property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present
	 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
	 * for the unavailability property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getUnavailability().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link Unavailability }
	 * 
	 * 
	 */
	public List<Unavailability> getUnavailabilities() {
		if (unavailabilities == null) {
			unavailabilities = new HashSet<Unavailability>();
		}
		return new ArrayList<>(this.unavailabilities);
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
