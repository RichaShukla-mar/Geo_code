
package net.apmoller.services.cmd.schemas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 					Return message for all asynchronous upsert
 * 					operations, containing the
 * 					code
 * 				
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="FacilityID" type="{http://services.apmoller.net/cmd/schemas}FacilityIDsType" minOccurs="0"/>
 *         &lt;element name="UpsertFacilityValidationStatus" type="{http://services.apmoller.net/cmd/schemas}ValidationResponseType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="UpsertFacilityStatus" type="{http://services.apmoller.net/cmd/schemas}StatusResponseType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "facilityIDAndUpsertFacilityValidationStatusAndUpsertFacilityStatus"
})
@XmlRootElement(name = "UpsertFacilityResponse")
public class UpsertFacilityResponse {

    @XmlElements({
        @XmlElement(name = "FacilityID", required = true, type = FacilityIDsType.class),
        @XmlElement(name = "UpsertFacilityValidationStatus", required = true, type = ValidationResponseType.class),
        @XmlElement(name = "UpsertFacilityStatus", required = true, type = StatusResponseType.class)
    })
    protected List<Object> facilityIDAndUpsertFacilityValidationStatusAndUpsertFacilityStatus;

    /**
     * Gets the value of the facilityIDAndUpsertFacilityValidationStatusAndUpsertFacilityStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the facilityIDAndUpsertFacilityValidationStatusAndUpsertFacilityStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFacilityIDAndUpsertFacilityValidationStatusAndUpsertFacilityStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FacilityIDsType }
     * {@link ValidationResponseType }
     * {@link StatusResponseType }
     * 
     * 
     */
    public List<Object> getFacilityIDAndUpsertFacilityValidationStatusAndUpsertFacilityStatus() {
        if (facilityIDAndUpsertFacilityValidationStatusAndUpsertFacilityStatus == null) {
            facilityIDAndUpsertFacilityValidationStatusAndUpsertFacilityStatus = new ArrayList<Object>();
        }
        return this.facilityIDAndUpsertFacilityValidationStatusAndUpsertFacilityStatus;
    }

}
