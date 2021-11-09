
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommPreferenceTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CommPreferenceTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Telephone"/>
 *     &lt;enumeration value="Email"/>
 *     &lt;enumeration value="Fax"/>
 *     &lt;enumeration value="Do not send"/>
 *     &lt;enumeration value="Print"/>
 *     &lt;enumeration value="Web"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CommPreferenceTypeEnum")
@XmlEnum
public enum CommPreferenceTypeEnum {

    @XmlEnumValue("Telephone")
    TELEPHONE("Telephone"),
    @XmlEnumValue("Email")
    EMAIL("Email"),
    @XmlEnumValue("Fax")
    FAX("Fax"),
    @XmlEnumValue("Do not send")
    DO_NOT_SEND("Do not send"),
    @XmlEnumValue("Print")
    PRINT("Print"),
    @XmlEnumValue("Web")
    WEB("Web");
    private final String value;

    CommPreferenceTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CommPreferenceTypeEnum fromValue(String v) {
        for (CommPreferenceTypeEnum c: CommPreferenceTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
