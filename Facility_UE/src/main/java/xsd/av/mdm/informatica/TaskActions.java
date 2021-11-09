
package xsd.av.mdm.informatica;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaskActions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaskActions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="INFATaskAction" type="{urn:informatica.mdm.av.xsd}INFATaskAction" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaskActions", propOrder = {
    "infaTaskAction"
})
public class TaskActions {

    @XmlElement(name = "INFATaskAction")
    protected List<INFATaskAction> infaTaskAction;

    /**
     * Gets the value of the infaTaskAction property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infaTaskAction property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getINFATaskAction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link INFATaskAction }
     * 
     * 
     */
    public List<INFATaskAction> getINFATaskAction() {
        if (infaTaskAction == null) {
            infaTaskAction = new ArrayList<INFATaskAction>();
        }
        return this.infaTaskAction;
    }

}
