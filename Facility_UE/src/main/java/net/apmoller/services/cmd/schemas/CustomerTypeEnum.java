
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CustomerTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ZEXC"/>
 *     &lt;enumeration value="ZICC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CustomerTypeEnum")
@XmlEnum
public enum CustomerTypeEnum {

    ZEXC,
    ZICC;

    public String value() {
        return name();
    }

    public static CustomerTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
