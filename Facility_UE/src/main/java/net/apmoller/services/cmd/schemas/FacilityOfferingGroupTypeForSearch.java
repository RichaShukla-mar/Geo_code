
package net.apmoller.services.cmd.schemas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FacilityOfferingGroupTypeForSearch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FacilityOfferingGroupTypeForSearch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}FacilityOfferingGroupCode"/>
 *         &lt;element name="FacilityOfferings" type="{http://services.apmoller.net/cmd/schemas}FacilityOfferingTypeForSearch" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacilityOfferingGroupTypeForSearch", propOrder = {
    "facilityOfferingGroupCode",
    "facilityOfferings"
})
public class FacilityOfferingGroupTypeForSearch {

    @XmlElement(name = "FacilityOfferingGroupCode", required = true)
    protected String facilityOfferingGroupCode;
    @XmlElement(name = "FacilityOfferings")
    protected List<FacilityOfferingTypeForSearch> facilityOfferings;

    /**
     * Gets the value of the facilityOfferingGroupCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityOfferingGroupCode() {
        return facilityOfferingGroupCode;
    }

    /**
     * Sets the value of the facilityOfferingGroupCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityOfferingGroupCode(String value) {
        this.facilityOfferingGroupCode = value;
    }

    /**
     * Gets the value of the facilityOfferings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the facilityOfferings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFacilityOfferings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FacilityOfferingTypeForSearch }
     * 
     * 
     */
    public List<FacilityOfferingTypeForSearch> getFacilityOfferings() {
        if (facilityOfferings == null) {
            facilityOfferings = new ArrayList<FacilityOfferingTypeForSearch>();
        }
        return this.facilityOfferings;
    }

}
