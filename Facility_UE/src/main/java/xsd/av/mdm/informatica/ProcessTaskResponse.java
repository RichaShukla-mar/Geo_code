
package xsd.av.mdm.informatica;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProcessTaskResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessTaskResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="INFATask" type="{urn:informatica.mdm.av.xsd}INFATask" minOccurs="0"/>
 *         &lt;element name="actionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessTaskResponse", propOrder = {
    "infaTaskAndActionName"
})
public class ProcessTaskResponse {

    @XmlElements({
        @XmlElement(name = "INFATask", type = INFATask.class),
        @XmlElement(name = "actionName", type = String.class)
    })
    protected List<Object> infaTaskAndActionName;

    /**
     * Gets the value of the infaTaskAndActionName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infaTaskAndActionName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getINFATaskAndActionName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link INFATask }
     * {@link String }
     * 
     * 
     */
    public List<Object> getINFATaskAndActionName() {
        if (infaTaskAndActionName == null) {
            infaTaskAndActionName = new ArrayList<Object>();
        }
        return this.infaTaskAndActionName;
    }

}
