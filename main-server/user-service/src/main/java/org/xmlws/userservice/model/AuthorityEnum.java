//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.16 at 03:43:06 PM CEST 
//


package org.xmlws.userservice.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthorityEnum.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AuthorityEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ROLE_USER"/>
 *     &lt;enumeration value="ROLE_ADMIN"/>
 *     &lt;enumeration value="ROLE_AGENT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "AuthorityEnum")
@XmlEnum
public enum AuthorityEnum {

    ROLE_USER,
    ROLE_ADMIN,
    ROLE_AGENT;

    public String value() {
        return name();
    }

    public static AuthorityEnum fromValue(String v) {
        return valueOf(v);
    }

}
