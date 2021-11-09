
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FacilityCategoryEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FacilityCategoryEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OPS"/>
 *     &lt;enumeration value="COMM"/>
 *     &lt;enumeration value="CUST"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FacilityCategoryEnum")
@XmlEnum
public enum FacilityCategoryEnum {

    OPS,
    COMM,
    CUST;

    public String value() {
        return name();
    }

    public static FacilityCategoryEnum fromValue(String v) {
        return valueOf(v);
    }

}
