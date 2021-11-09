
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WorkflowEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="WorkflowEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="ADDRESS-OVERRIDE"/>
 *     &lt;enumeration value="ADDRESS-QUALITY"/>
 *     &lt;enumeration value="SUSPENDTOACTIVE"/>
 *     &lt;enumeration value="REACTIVATE"/>
 *     &lt;enumeration value="DUPLICATE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "WorkflowEnum")
@XmlEnum
public enum WorkflowEnum {

    NONE("NONE"),
    @XmlEnumValue("ADDRESS-OVERRIDE")
    ADDRESS_OVERRIDE("ADDRESS-OVERRIDE"),
    @XmlEnumValue("ADDRESS-QUALITY")
    ADDRESS_QUALITY("ADDRESS-QUALITY"),
    SUSPENDTOACTIVE("SUSPENDTOACTIVE"),
    REACTIVATE("REACTIVATE"),
    DUPLICATE("DUPLICATE");
    private final String value;

    WorkflowEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WorkflowEnum fromValue(String v) {
        for (WorkflowEnum c: WorkflowEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
