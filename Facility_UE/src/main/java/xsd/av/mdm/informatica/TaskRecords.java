
package xsd.av.mdm.informatica;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaskRecords complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaskRecords">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="INFARecordKey" type="{urn:informatica.mdm.av.xsd}INFARecordKey" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaskRecords", propOrder = {
    "infaRecordKey"
})
public class TaskRecords {

    @XmlElement(name = "INFARecordKey")
    protected List<INFARecordKey> infaRecordKey;

    /**
     * Gets the value of the infaRecordKey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infaRecordKey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getINFARecordKey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link INFARecordKey }
     * 
     * 
     */
    public List<INFARecordKey> getINFARecordKey() {
        if (infaRecordKey == null) {
            infaRecordKey = new ArrayList<INFARecordKey>();
        }
        return this.infaRecordKey;
    }

}
