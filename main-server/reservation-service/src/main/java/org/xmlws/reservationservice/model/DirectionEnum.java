//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.07 at 12:55:23 PM CEST 
//


package org.xmlws.reservationservice.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DirectionEnum.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType firstName="DirectionEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AGENT_TO_USER"/>
 *     &lt;enumeration value="USER_TO_AGENT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "DirectionEnum")
@XmlEnum
public enum DirectionEnum {

    AGENT_TO_USER,
    USER_TO_AGENT;

    public String value() {
        return name();
    }

    public static DirectionEnum fromValue(String v) {
        return valueOf(v);
    }

}
