//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.07 at 12:55:23 PM CEST 
//

package org.xmlws.accommodationservice.model;

import javax.xml.bind.annotation.*;

import org.xmlws.dataservice.entity.Entity;

import java.math.BigDecimal;

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
 *         &lt;element firstName="country" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element firstName="city" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element firstName="address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element firstName="latitude">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="-90"/>
 *               &lt;maxInclusive value="90"/>
 *               &lt;totalDigits value="8"/>
 *               &lt;fractionDigits value="6"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element firstName="longitude">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="-180"/>
 *               &lt;maxInclusive value="180"/>
 *               &lt;totalDigits value="9"/>
 *               &lt;fractionDigits value="6"/>
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
@XmlType(name = "", propOrder = { "country", "city", "address", "latitude", "longitude" })
@XmlRootElement(name = "Location")
public class Location extends Entity {

	@XmlElement(required = true)
	protected String country;
	@XmlElement(required = true)
	protected String city;
	@XmlElement(required = true)
	protected String address;
	@XmlElement(required = true, defaultValue = "0.0")
	protected BigDecimal latitude;
	@XmlElement(required = true, defaultValue = "0.0")
	protected BigDecimal longitude;

	/**
	 * Gets the value of the country property.
	 *
	 * @return possible object is {@link String }
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the value of the country property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setCountry(String value) {
		this.country = value;
	}

	/**
	 * Gets the value of the city property.
	 *
	 * @return possible object is {@link String }
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the value of the city property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setCity(String value) {
		this.city = value;
	}

	/**
	 * Gets the value of the address property.
	 *
	 * @return possible object is {@link String }
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the value of the address property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setAddress(String value) {
		this.address = value;
	}

	/**
	 * Gets the value of the latitude property.
	 *
	 * @return possible object is {@link BigDecimal }
	 */
	public BigDecimal getLatitude() {
		return latitude;
	}

	/**
	 * Sets the value of the latitude property.
	 *
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 */
	public void setLatitude(BigDecimal value) {
		this.latitude = value;
	}

	/**
	 * Gets the value of the longitude property.
	 *
	 * @return possible object is {@link BigDecimal }
	 */
	public BigDecimal getLongitude() {
		return longitude;
	}

	/**
	 * Sets the value of the longitude property.
	 *
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 */
	public void setLongitude(BigDecimal value) {
		this.longitude = value;
	}

}
