//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.06 at 04:47:22 PM CEST 
//


package https.github_com.mare11.xml_megatravel.reservation;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter5
    extends XmlAdapter<String, Boolean>
{


    public Boolean unmarshal(String value) {
        return (org.xmlws.util.TypeConverter.parseBoolean(value));
    }

    public String marshal(Boolean value) {
        if (value == null) {
            return null;
        }
        return (javax.xml.bind.DatatypeConverter.printBoolean((boolean)(boolean)value));
    }

}
