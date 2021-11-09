
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OperationalFacilityDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OperationalFacilityDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}WeightLimitOnCranes" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}VesselAgent" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}WeightLimitInYard" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}OceanFreightPricing" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}ExternallyExposed" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}ExternallyOwned" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}GPSFlag" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}GSMFlag" minOccurs="0"/>
 *         &lt;element name="OpeningHours" type="{http://services.apmoller.net/cmd/schemas}OpeningHoursType" minOccurs="0"/>
 *         &lt;element name="TransportModes" type="{http://services.apmoller.net/cmd/schemas}TransportModes" minOccurs="0"/>
 *         &lt;element name="FacilityOfferingGroups" type="{http://services.apmoller.net/cmd/schemas}FacilityOfferingGroups" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperationalFacilityDetailsType", propOrder = {
    "weightLimitOnCranes",
    "vesselAgent",
    "weightLimitInYard",
    "oceanFreightPricing",
    "externallyExposed",
    "externallyOwned",
    "gpsFlag",
    "gsmFlag",
    "openingHours",
    "transportModes",
    "facilityOfferingGroups"
})
public class OperationalFacilityDetailsType {

    @XmlElement(name = "WeightLimitOnCranes")
    protected String weightLimitOnCranes;
    @XmlElement(name = "VesselAgent")
    protected Boolean vesselAgent;
    @XmlElement(name = "WeightLimitInYard")
    protected String weightLimitInYard;
    @XmlElement(name = "OceanFreightPricing")
    protected Boolean oceanFreightPricing;
    @XmlElement(name = "ExternallyExposed")
    protected Boolean externallyExposed;
    @XmlElement(name = "ExternallyOwned")
    protected Boolean externallyOwned;
    @XmlElement(name = "GPSFlag")
    protected Boolean gpsFlag;
    @XmlElement(name = "GSMFlag")
    protected Boolean gsmFlag;
    @XmlElement(name = "OpeningHours")
    protected OpeningHoursType openingHours;
    @XmlElement(name = "TransportModes")
    protected TransportModes transportModes;
    @XmlElement(name = "FacilityOfferingGroups")
    protected FacilityOfferingGroups facilityOfferingGroups;

    /**
     * Gets the value of the weightLimitOnCranes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeightLimitOnCranes() {
        return weightLimitOnCranes;
    }

    /**
     * Sets the value of the weightLimitOnCranes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeightLimitOnCranes(String value) {
        this.weightLimitOnCranes = value;
    }

    /**
     * Gets the value of the vesselAgent property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVesselAgent() {
        return vesselAgent;
    }

    /**
     * Sets the value of the vesselAgent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVesselAgent(Boolean value) {
        this.vesselAgent = value;
    }

    /**
     * Gets the value of the weightLimitInYard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeightLimitInYard() {
        return weightLimitInYard;
    }

    /**
     * Sets the value of the weightLimitInYard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeightLimitInYard(String value) {
        this.weightLimitInYard = value;
    }

    /**
     * Gets the value of the oceanFreightPricing property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOceanFreightPricing() {
        return oceanFreightPricing;
    }

    /**
     * Sets the value of the oceanFreightPricing property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOceanFreightPricing(Boolean value) {
        this.oceanFreightPricing = value;
    }

    /**
     * Gets the value of the externallyExposed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExternallyExposed() {
        return externallyExposed;
    }

    /**
     * Sets the value of the externallyExposed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExternallyExposed(Boolean value) {
        this.externallyExposed = value;
    }

    /**
     * Gets the value of the externallyOwned property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExternallyOwned() {
        return externallyOwned;
    }

    /**
     * Sets the value of the externallyOwned property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExternallyOwned(Boolean value) {
        this.externallyOwned = value;
    }

    /**
     * Gets the value of the gpsFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGPSFlag() {
        return gpsFlag;
    }

    /**
     * Sets the value of the gpsFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGPSFlag(Boolean value) {
        this.gpsFlag = value;
    }

    /**
     * Gets the value of the gsmFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGSMFlag() {
        return gsmFlag;
    }

    /**
     * Sets the value of the gsmFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGSMFlag(Boolean value) {
        this.gsmFlag = value;
    }

    /**
     * Gets the value of the openingHours property.
     * 
     * @return
     *     possible object is
     *     {@link OpeningHoursType }
     *     
     */
    public OpeningHoursType getOpeningHours() {
        return openingHours;
    }

    /**
     * Sets the value of the openingHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link OpeningHoursType }
     *     
     */
    public void setOpeningHours(OpeningHoursType value) {
        this.openingHours = value;
    }

    /**
     * Gets the value of the transportModes property.
     * 
     * @return
     *     possible object is
     *     {@link TransportModes }
     *     
     */
    public TransportModes getTransportModes() {
        return transportModes;
    }

    /**
     * Sets the value of the transportModes property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportModes }
     *     
     */
    public void setTransportModes(TransportModes value) {
        this.transportModes = value;
    }

    /**
     * Gets the value of the facilityOfferingGroups property.
     * 
     * @return
     *     possible object is
     *     {@link FacilityOfferingGroups }
     *     
     */
    public FacilityOfferingGroups getFacilityOfferingGroups() {
        return facilityOfferingGroups;
    }

    /**
     * Sets the value of the facilityOfferingGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacilityOfferingGroups }
     *     
     */
    public void setFacilityOfferingGroups(FacilityOfferingGroups value) {
        this.facilityOfferingGroups = value;
    }

}
