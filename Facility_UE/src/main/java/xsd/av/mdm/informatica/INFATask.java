
package xsd.av.mdm.informatica;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for INFATask complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="INFATask">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="hubUsername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hubPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="securityPayload" type="{http://www.w3.org/2001/XMLSchema}hexBinary" minOccurs="0"/>
 *         &lt;element name="orsId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="taskType" type="{urn:informatica.mdm.av.xsd}TaskType" minOccurs="0"/>
 *         &lt;element name="taskData" type="{urn:informatica.mdm.av.xsd}TaskData" minOccurs="0"/>
 *         &lt;element name="actions" type="{urn:informatica.mdm.av.xsd}TaskActions" minOccurs="0"/>
 *         &lt;element name="workflowVersion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "INFATask", propOrder = {
    "hubUsernameAndHubPasswordAndSecurityPayload"
})
public class INFATask {

    @XmlElementRefs({
        @XmlElementRef(name = "orsId", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "hubPassword", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "hubUsername", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "securityPayload", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "taskType", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "taskData", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "workflowVersion", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "actions", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> hubUsernameAndHubPasswordAndSecurityPayload;

    /**
     * Gets the value of the hubUsernameAndHubPasswordAndSecurityPayload property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hubUsernameAndHubPasswordAndSecurityPayload property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHubUsernameAndHubPasswordAndSecurityPayload().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * {@link JAXBElement }{@code <}{@link TaskType }{@code >}
     * {@link JAXBElement }{@code <}{@link TaskData }{@code >}
     * {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * {@link JAXBElement }{@code <}{@link TaskActions }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getHubUsernameAndHubPasswordAndSecurityPayload() {
        if (hubUsernameAndHubPasswordAndSecurityPayload == null) {
            hubUsernameAndHubPasswordAndSecurityPayload = new ArrayList<JAXBElement<?>>();
        }
        return this.hubUsernameAndHubPasswordAndSecurityPayload;
    }

}
