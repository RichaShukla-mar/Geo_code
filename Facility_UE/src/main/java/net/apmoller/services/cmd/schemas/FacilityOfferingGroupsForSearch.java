
package net.apmoller.services.cmd.schemas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FacilityOfferingGroupsForSearch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FacilityOfferingGroupsForSearch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FacilityOfferingGroup" type="{http://services.apmoller.net/cmd/schemas}FacilityOfferingGroupTypeForSearch" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacilityOfferingGroupsForSearch", propOrder = {
    "facilityOfferingGroup"
})
public class FacilityOfferingGroupsForSearch {

    @XmlElement(name = "FacilityOfferingGroup")
    protected List<FacilityOfferingGroupTypeForSearch> facilityOfferingGroup;

    /**
     * Gets the value of the facilityOfferingGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the facilityOfferingGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFacilityOfferingGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FacilityOfferingGroupTypeForSearch }
     * 
     * 
     */
    public List<FacilityOfferingGroupTypeForSearch> getFacilityOfferingGroup() {
        if (facilityOfferingGroup == null) {
            facilityOfferingGroup = new ArrayList<FacilityOfferingGroupTypeForSearch>();
        }
        return this.facilityOfferingGroup;
    }

}
