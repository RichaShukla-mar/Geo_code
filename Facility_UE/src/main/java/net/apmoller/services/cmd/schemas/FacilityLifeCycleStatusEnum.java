
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FacilityLifeCycleStatusEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FacilityLifeCycleStatusEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="I"/>
 *     &lt;enumeration value="P"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FacilityLifeCycleStatusEnum")
@XmlEnum
public enum FacilityLifeCycleStatusEnum {

    A,
    I,
    P;

    public String value() {
        return name();
    }

    public static FacilityLifeCycleStatusEnum fromValue(String v) {
        return valueOf(v);
    }

}
