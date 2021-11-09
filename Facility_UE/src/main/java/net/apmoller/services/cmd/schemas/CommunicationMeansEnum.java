
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommunicationMeansEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CommunicationMeansEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Telephone"/>
 *     &lt;enumeration value="Mobile phone"/>
 *     &lt;enumeration value="Email"/>
 *     &lt;enumeration value="Fax"/>
 *     &lt;enumeration value="Post"/>
 *     &lt;enumeration value="Internet Alert"/>
 *     &lt;enumeration value="Portal"/>
 *     &lt;enumeration value="Do not send"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CommunicationMeansEnum")
@XmlEnum
public enum CommunicationMeansEnum {

    @XmlEnumValue("Telephone")
    TELEPHONE("Telephone"),
    @XmlEnumValue("Mobile phone")
    MOBILE_PHONE("Mobile phone"),
    @XmlEnumValue("Email")
    EMAIL("Email"),
    @XmlEnumValue("Fax")
    FAX("Fax"),
    @XmlEnumValue("Post")
    POST("Post"),
    @XmlEnumValue("Internet Alert")
    INTERNET_ALERT("Internet Alert"),
    @XmlEnumValue("Portal")
    PORTAL("Portal"),
    @XmlEnumValue("Do not send")
    DO_NOT_SEND("Do not send");
    private final String value;

    CommunicationMeansEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CommunicationMeansEnum fromValue(String v) {
        for (CommunicationMeansEnum c: CommunicationMeansEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
