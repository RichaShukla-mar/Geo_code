
package xsd.av.mdm.informatica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProcessTaskRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessTaskRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="INFATask" type="{urn:informatica.mdm.av.xsd}INFATask"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessTaskRequest", propOrder = {
    "infaTask"
})
public class ProcessTaskRequest {

    @XmlElement(name = "INFATask", required = true)
    protected INFATask infaTask;

    /**
     * Gets the value of the infaTask property.
     * 
     * @return
     *     possible object is
     *     {@link INFATask }
     *     
     */
    public INFATask getINFATask() {
        return infaTask;
    }

    /**
     * Sets the value of the infaTask property.
     * 
     * @param value
     *     allowed object is
     *     {@link INFATask }
     *     
     */
    public void setINFATask(INFATask value) {
        this.infaTask = value;
    }

}
