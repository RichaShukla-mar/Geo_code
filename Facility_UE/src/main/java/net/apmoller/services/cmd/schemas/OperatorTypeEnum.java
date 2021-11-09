
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OperatorTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OperatorTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MAEU"/>
 *     &lt;enumeration value="SAFM"/>
 *     &lt;enumeration value="SEJJ"/>
 *     &lt;enumeration value="SEAU"/>
 *     &lt;enumeration value="MCCQ"/>
 *     &lt;enumeration value="MCPU"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OperatorTypeEnum")
@XmlEnum
public enum OperatorTypeEnum {

    MAEU,
    SAFM,
    SEJJ,
    SEAU,
    MCCQ,
    MCPU;

    public String value() {
        return name();
    }

    public static OperatorTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
