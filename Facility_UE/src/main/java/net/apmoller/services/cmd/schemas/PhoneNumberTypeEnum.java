
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PhoneNumberTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PhoneNumberTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MOB"/>
 *     &lt;enumeration value="TEL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PhoneNumberTypeEnum")
@XmlEnum
public enum PhoneNumberTypeEnum {

    MOB,
    TEL;

    public String value() {
        return name();
    }

    public static PhoneNumberTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
