
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommercialFacilityDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommercialFacilityDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FacilityType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Brand" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Function" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ImportMail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExportMail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TelephoneDetails" type="{http://services.apmoller.net/cmd/schemas}TeleCommunicationType" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}ExternallyExposed" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}ExternallyOwned" minOccurs="0"/>
 *         &lt;element name="OpeningHours" type="{http://services.apmoller.net/cmd/schemas}OpeningHoursType" minOccurs="0"/>
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
@XmlType(name = "CommercialFacilityDetailsType", propOrder = {
    "facilityType",
    "brand",
    "function",
    "importMail",
    "exportMail",
    "telephoneDetails",
    "externallyExposed",
    "externallyOwned",
    "openingHours",
    "facilityOfferingGroups"
})
public class CommercialFacilityDetailsType {

    @XmlElement(name = "FacilityType", required = true)
    protected String facilityType;
    @XmlElement(name = "Brand")
    protected String brand;
    @XmlElement(name = "Function", required = true)
    protected String function;
    @XmlElement(name = "ImportMail")
    protected String importMail;
    @XmlElement(name = "ExportMail")
    protected String exportMail;
    @XmlElement(name = "TelephoneDetails")
    protected TeleCommunicationType telephoneDetails;
    @XmlElement(name = "ExternallyExposed")
    protected Boolean externallyExposed;
    @XmlElement(name = "ExternallyOwned")
    protected Boolean externallyOwned;
    @XmlElement(name = "OpeningHours")
    protected OpeningHoursType openingHours;
    @XmlElement(name = "FacilityOfferingGroups")
    protected FacilityOfferingGroups facilityOfferingGroups;

    /**
     * Gets the value of the facilityType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityType() {
        return facilityType;
    }

    /**
     * Sets the value of the facilityType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityType(String value) {
        this.facilityType = value;
    }

    /**
     * Gets the value of the brand property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the value of the brand property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrand(String value) {
        this.brand = value;
    }

    /**
     * Gets the value of the function property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunction() {
        return function;
    }

    /**
     * Sets the value of the function property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunction(String value) {
        this.function = value;
    }

    /**
     * Gets the value of the importMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImportMail() {
        return importMail;
    }

    /**
     * Sets the value of the importMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImportMail(String value) {
        this.importMail = value;
    }

    /**
     * Gets the value of the exportMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportMail() {
        return exportMail;
    }

    /**
     * Sets the value of the exportMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportMail(String value) {
        this.exportMail = value;
    }

    /**
     * Gets the value of the telephoneDetails property.
     * 
     * @return
     *     possible object is
     *     {@link TeleCommunicationType }
     *     
     */
    public TeleCommunicationType getTelephoneDetails() {
        return telephoneDetails;
    }

    /**
     * Sets the value of the telephoneDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link TeleCommunicationType }
     *     
     */
    public void setTelephoneDetails(TeleCommunicationType value) {
        this.telephoneDetails = value;
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
