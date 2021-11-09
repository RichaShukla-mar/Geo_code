
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerLifeCycleStatusEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CustomerLifeCycleStatusEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="I"/>
 *     &lt;enumeration value="S"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CustomerLifeCycleStatusEnum")
@XmlEnum
public enum CustomerLifeCycleStatusEnum {

    A,
    I,
    S;

    public String value() {
        return name();
    }

    public static CustomerLifeCycleStatusEnum fromValue(String v) {
        return valueOf(v);
    }

}
