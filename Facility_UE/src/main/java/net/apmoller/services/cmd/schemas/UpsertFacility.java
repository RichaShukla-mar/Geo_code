
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Facility" type="{http://services.apmoller.net/cmd/schemas}UpsertFacilityDetailsType" minOccurs="0"/>
 *         &lt;element name="FacilityAddress" type="{http://services.apmoller.net/cmd/schemas}FCTAddressType"/>
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
    "facility",
    "facilityAddress"
})
@XmlRootElement(name = "UpsertFacility")
public class UpsertFacility {

    @XmlElement(name = "Facility")
    protected UpsertFacilityDetailsType facility;
    @XmlElement(name = "FacilityAddress", required = true)
    protected FCTAddressType facilityAddress;

    /**
     * Gets the value of the facility property.
     * 
     * @return
     *     possible object is
     *     {@link UpsertFacilityDetailsType }
     *     
     */
    public UpsertFacilityDetailsType getFacility() {
        return facility;
    }

    /**
     * Sets the value of the facility property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpsertFacilityDetailsType }
     *     
     */
    public void setFacility(UpsertFacilityDetailsType value) {
        this.facility = value;
    }

    /**
     * Gets the value of the facilityAddress property.
     * 
     * @return
     *     possible object is
     *     {@link FCTAddressType }
     *     
     */
    public FCTAddressType getFacilityAddress() {
        return facilityAddress;
    }

    /**
     * Sets the value of the facilityAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link FCTAddressType }
     *     
     */
    public void setFacilityAddress(FCTAddressType value) {
        this.facilityAddress = value;
    }

}
