
package xsd.av.mdm.informatica;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * This is a single TaskData object.
 * 			
 * 
 * <p>Java class for TaskData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaskData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="taskId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ownerUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dueDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="subjectAreaUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interactionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="updatedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastUpdateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="taskRecords" type="{urn:informatica.mdm.av.xsd}TaskRecords" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaskData", propOrder = {
    "taskIdAndOwnerUIDAndDueDate"
})
public class TaskData {

    @XmlElementRefs({
        @XmlElementRef(name = "taskId", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "createDate", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "status", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "interactionId", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "taskRecords", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "priority", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "lastUpdateDate", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "subjectAreaUID", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "title", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "dueDate", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "comments", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ownerUID", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "creator", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "updatedBy", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> taskIdAndOwnerUIDAndDueDate;

    /**
     * Gets the value of the taskIdAndOwnerUIDAndDueDate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taskIdAndOwnerUIDAndDueDate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaskIdAndOwnerUIDAndDueDate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link TaskRecords }{@code >}
     * {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getTaskIdAndOwnerUIDAndDueDate() {
        if (taskIdAndOwnerUIDAndDueDate == null) {
            taskIdAndOwnerUIDAndDueDate = new ArrayList<JAXBElement<?>>();
        }
        return this.taskIdAndOwnerUIDAndDueDate;
    }

}
