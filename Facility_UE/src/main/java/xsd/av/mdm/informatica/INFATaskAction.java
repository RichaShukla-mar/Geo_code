
package xsd.av.mdm.informatica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for INFATaskAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="INFATaskAction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cancelTask" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="closeTaskView" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="manualReassign" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "INFATaskAction", propOrder = {
    "nameAndDisplayNameAndDescription"
})
public class INFATaskAction {

    @XmlElementRefs({
        @XmlElementRef(name = "displayName", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "closeTaskView", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "description", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "manualReassign", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "cancelTask", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "name", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<? extends Serializable>> nameAndDisplayNameAndDescription;

    /**
     * Gets the value of the nameAndDisplayNameAndDescription property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nameAndDisplayNameAndDescription property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNameAndDisplayNameAndDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends Serializable>> getNameAndDisplayNameAndDescription() {
        if (nameAndDisplayNameAndDescription == null) {
            nameAndDisplayNameAndDescription = new ArrayList<JAXBElement<? extends Serializable>>();
        }
        return this.nameAndDisplayNameAndDescription;
    }

}
