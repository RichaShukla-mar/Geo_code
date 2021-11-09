
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpsertEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UpsertEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CREATE"/>
 *     &lt;enumeration value="UPDATE"/>
 *     &lt;enumeration value="CREATE_APPROVE"/>
 *     &lt;enumeration value="UPDATE_APPROVE"/>
 *     &lt;enumeration value="INACTIVATE"/>
 *     &lt;enumeration value="TYPE_CHANGE"/>
 *     &lt;enumeration value="STATUS_CHANGE"/>
 *     &lt;enumeration value="DPL_APPROVE"/>
 *     &lt;enumeration value="DPL_REJECT"/>
 *     &lt;enumeration value="CREATE_REJECT"/>
 *     &lt;enumeration value="UPDATE_REJECT"/>
 *     &lt;enumeration value="ASSIGN_KEY_CLIENT"/>
 *     &lt;enumeration value="UNASSIGN_KEY_CLIENT"/>
 *     &lt;enumeration value="ASSIGN_SEGMENT"/>
 *     &lt;enumeration value="UNASSIGN_SEGMENT"/>
 *     &lt;enumeration value="RELATION_CREATE"/>
 *     &lt;enumeration value="RELATION_UPDATE"/>
 *     &lt;enumeration value="CONCERN_CREATE_UPDATE"/>
 *     &lt;enumeration value="CONCERN_INACTIVATE"/>
 *     &lt;enumeration value="REJECT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UpsertEnum")
@XmlEnum
public enum UpsertEnum {

    CREATE,
    UPDATE,
    CREATE_APPROVE,
    UPDATE_APPROVE,
    INACTIVATE,
    TYPE_CHANGE,
    STATUS_CHANGE,
    DPL_APPROVE,
    DPL_REJECT,
    CREATE_REJECT,
    UPDATE_REJECT,
    ASSIGN_KEY_CLIENT,
    UNASSIGN_KEY_CLIENT,
    ASSIGN_SEGMENT,
    UNASSIGN_SEGMENT,
    RELATION_CREATE,
    RELATION_UPDATE,
    CONCERN_CREATE_UPDATE,
    CONCERN_INACTIVATE,
    REJECT;

    public String value() {
        return name();
    }

    public static UpsertEnum fromValue(String v) {
        return valueOf(v);
    }

}
