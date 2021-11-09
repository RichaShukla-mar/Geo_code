
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
 * This is a single RecordKey object.
 * 			
 * 
 * <p>Java class for INFARecordKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="INFARecordKey">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="rowId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pkeySrcObject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="system" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rowidXref" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tableUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "INFARecordKey", propOrder = {
    "rowIdAndPkeySrcObjectAndSystem"
})
public class INFARecordKey {

    @XmlElementRefs({
        @XmlElementRef(name = "rowidXref", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class),
        @XmlElementRef(name = "pkeySrcObject", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class),
        @XmlElementRef(name = "tableUID", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class),
        @XmlElementRef(name = "rowId", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class),
        @XmlElementRef(name = "system", namespace = "urn:informatica.mdm.av.xsd", type = JAXBElement.class)
    })
    protected List<JAXBElement<String>> rowIdAndPkeySrcObjectAndSystem;

    /**
     * Gets the value of the rowIdAndPkeySrcObjectAndSystem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rowIdAndPkeySrcObjectAndSystem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRowIdAndPkeySrcObjectAndSystem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<String>> getRowIdAndPkeySrcObjectAndSystem() {
        if (rowIdAndPkeySrcObjectAndSystem == null) {
            rowIdAndPkeySrcObjectAndSystem = new ArrayList<JAXBElement<String>>();
        }
        return this.rowIdAndPkeySrcObjectAndSystem;
    }

}
