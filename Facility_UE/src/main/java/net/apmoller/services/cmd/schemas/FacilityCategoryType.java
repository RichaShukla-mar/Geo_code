
package net.apmoller.services.cmd.schemas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FacilityCategoryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FacilityCategoryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FacilityTypeDetails" type="{http://services.apmoller.net/cmd/schemas}FacilityCategoryOperationalType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacilityCategoryType", propOrder = {
    "facilityTypeDetails"
})
public class FacilityCategoryType {

    @XmlElement(name = "FacilityTypeDetails")
    protected List<FacilityCategoryOperationalType> facilityTypeDetails;

    /**
     * Gets the value of the facilityTypeDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the facilityTypeDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFacilityTypeDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FacilityCategoryOperationalType }
     * 
     * 
     */
    public List<FacilityCategoryOperationalType> getFacilityTypeDetails() {
        if (facilityTypeDetails == null) {
            facilityTypeDetails = new ArrayList<FacilityCategoryOperationalType>();
        }
        return this.facilityTypeDetails;
    }

}
