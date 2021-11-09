
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReferenceTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReferenceTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="EOR"/>
 *     &lt;enumeration value="DOD"/>
 *     &lt;enumeration value="SAN"/>
 *     &lt;enumeration value="COR"/>
 *     &lt;enumeration value="FOF"/>
 *     &lt;enumeration value="SIR"/>
 *     &lt;enumeration value="APM"/>
 *     &lt;enumeration value="BVD"/>
 *     &lt;enumeration value="DUN"/>
 *     &lt;enumeration value="BRN"/>
 *     &lt;enumeration value="TCO"/>
 *     &lt;enumeration value="CUS"/>
 *     &lt;enumeration value="BVD_GUO"/>
 *     &lt;enumeration value="BVD_HQ"/>
 *     &lt;enumeration value="ED1"/>
 *     &lt;enumeration value="ED2"/>
 *     &lt;enumeration value="ED3"/>
 *     &lt;enumeration value="SFDC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReferenceTypeEnum")
@XmlEnum
public enum ReferenceTypeEnum {

    EOR("EOR"),
    DOD("DOD"),
    SAN("SAN"),
    COR("COR"),
    FOF("FOF"),
    SIR("SIR"),
    APM("APM"),
    BVD("BVD"),
    DUN("DUN"),
    BRN("BRN"),
    TCO("TCO"),
    CUS("CUS"),
    BVD_GUO("BVD_GUO"),
    BVD_HQ("BVD_HQ"),
    @XmlEnumValue("ED1")
    ED_1("ED1"),
    @XmlEnumValue("ED2")
    ED_2("ED2"),
    @XmlEnumValue("ED3")
    ED_3("ED3"),
    SFDC("SFDC");
    private final String value;

    ReferenceTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReferenceTypeEnum fromValue(String v) {
        for (ReferenceTypeEnum c: ReferenceTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
