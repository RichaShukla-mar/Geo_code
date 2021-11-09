
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxNumberTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TaxNumberTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="TAXNO1"/>
 *     &lt;enumeration value="TAXNO2"/>
 *     &lt;enumeration value="TAXNO3"/>
 *     &lt;enumeration value="TAXNO4"/>
 *     &lt;enumeration value="VATALT"/>
 *     &lt;enumeration value="VATREG"/>
 *     &lt;enumeration value="ALTREG"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TaxNumberTypeEnum")
@XmlEnum
public enum TaxNumberTypeEnum {

    @XmlEnumValue("TAXNO1")
    TAXNO_1("TAXNO1"),
    @XmlEnumValue("TAXNO2")
    TAXNO_2("TAXNO2"),
    @XmlEnumValue("TAXNO3")
    TAXNO_3("TAXNO3"),
    @XmlEnumValue("TAXNO4")
    TAXNO_4("TAXNO4"),
    VATALT("VATALT"),
    VATREG("VATREG"),
    ALTREG("ALTREG");
    private final String value;

    TaxNumberTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TaxNumberTypeEnum fromValue(String v) {
        for (TaxNumberTypeEnum c: TaxNumberTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
