
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactLifeCycleStatusEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ContactLifeCycleStatusEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="I"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ContactLifeCycleStatusEnum")
@XmlEnum
public enum ContactLifeCycleStatusEnum {

    A,
    I;

    public String value() {
        return name();
    }

    public static ContactLifeCycleStatusEnum fromValue(String v) {
        return valueOf(v);
    }

}
