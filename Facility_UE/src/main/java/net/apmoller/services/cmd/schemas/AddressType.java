
package net.apmoller.services.cmd.schemas;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 *  Postal Address. the language attribute specifies any local language and
 *         defaults to EN if omitted 
 * 
 * <p>Java class for AddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}HouseNo" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}StreetName" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}PoBox" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}Building" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}Suburb" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}District" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}City"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}Region" minOccurs="0"/>
 *         &lt;element name="ISOCountryCode" type="{http://services.apmoller.net/cmd/schemas}ISOCountryCode"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}PostalCode" minOccurs="0"/>
 *         &lt;element name="TaxJurisdictionCode" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="AddressAuditData" type="{http://services.apmoller.net/cmd/schemas}AuditType" minOccurs="0"/>
 *         &lt;element name="AddressWorkflowIndicator" type="{http://services.apmoller.net/cmd/schemas}WorkflowEnum" minOccurs="0"/>
 *         &lt;element name="AddressMailabilityScore" type="{http://services.apmoller.net/cmd/schemas}AddressMailabilityScore" minOccurs="0"/>
 *         &lt;element name="AddressMatchScore" type="{http://services.apmoller.net/cmd/schemas}AddressMatchScore" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="lang" type="{http://services.apmoller.net/cmd/schemas}ISOLanguageCode" default="en" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressType", propOrder = {
    "houseNo",
    "streetName",
    "poBox",
    "building",
    "suburb",
    "district",
    "city",
    "region",
    "isoCountryCode",
    "postalCode",
    "taxJurisdictionCode",
    "addressAuditData",
    "addressWorkflowIndicator",
    "addressMailabilityScore",
    "addressMatchScore"
})
public class AddressType {

    @XmlElement(name = "HouseNo")
    protected String houseNo;
    @XmlElement(name = "StreetName")
    protected String streetName;
    @XmlElement(name = "PoBox")
    protected String poBox;
    @XmlElement(name = "Building")
    protected String building;
    @XmlElement(name = "Suburb")
    protected String suburb;
    @XmlElement(name = "District")
    protected String district;
    @XmlElement(name = "City", required = true)
    protected String city;
    @XmlElement(name = "Region")
    protected String region;
    @XmlElement(name = "ISOCountryCode", required = true)
    protected String isoCountryCode;
    @XmlElement(name = "PostalCode")
    protected String postalCode;
    @XmlElement(name = "TaxJurisdictionCode")
    protected BigInteger taxJurisdictionCode;
    @XmlElement(name = "AddressAuditData")
    protected AuditType addressAuditData;
    @XmlElement(name = "AddressWorkflowIndicator")
    @XmlSchemaType(name = "string")
    protected WorkflowEnum addressWorkflowIndicator;
    @XmlElement(name = "AddressMailabilityScore")
    protected Integer addressMailabilityScore;
    @XmlElement(name = "AddressMatchScore")
    protected String addressMatchScore;
    @XmlAttribute(name = "lang")
    protected String lang;

    /**
     * Gets the value of the houseNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouseNo() {
        return houseNo;
    }

    /**
     * Sets the value of the houseNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHouseNo(String value) {
        this.houseNo = value;
    }

    /**
     * Gets the value of the streetName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets the value of the streetName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetName(String value) {
        this.streetName = value;
    }

    /**
     * Gets the value of the poBox property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoBox() {
        return poBox;
    }

    /**
     * Sets the value of the poBox property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoBox(String value) {
        this.poBox = value;
    }

    /**
     * Gets the value of the building property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuilding() {
        return building;
    }

    /**
     * Sets the value of the building property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuilding(String value) {
        this.building = value;
    }

    /**
     * Gets the value of the suburb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuburb() {
        return suburb;
    }

    /**
     * Sets the value of the suburb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuburb(String value) {
        this.suburb = value;
    }

    /**
     * Gets the value of the district property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Sets the value of the district property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrict(String value) {
        this.district = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegion(String value) {
        this.region = value;
    }

    /**
     * Gets the value of the isoCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISOCountryCode() {
        return isoCountryCode;
    }

    /**
     * Sets the value of the isoCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISOCountryCode(String value) {
        this.isoCountryCode = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the taxJurisdictionCode property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTaxJurisdictionCode() {
        return taxJurisdictionCode;
    }

    /**
     * Sets the value of the taxJurisdictionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTaxJurisdictionCode(BigInteger value) {
        this.taxJurisdictionCode = value;
    }

    /**
     * Gets the value of the addressAuditData property.
     * 
     * @return
     *     possible object is
     *     {@link AuditType }
     *     
     */
    public AuditType getAddressAuditData() {
        return addressAuditData;
    }

    /**
     * Sets the value of the addressAuditData property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuditType }
     *     
     */
    public void setAddressAuditData(AuditType value) {
        this.addressAuditData = value;
    }

    /**
     * Gets the value of the addressWorkflowIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link WorkflowEnum }
     *     
     */
    public WorkflowEnum getAddressWorkflowIndicator() {
        return addressWorkflowIndicator;
    }

    /**
     * Sets the value of the addressWorkflowIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkflowEnum }
     *     
     */
    public void setAddressWorkflowIndicator(WorkflowEnum value) {
        this.addressWorkflowIndicator = value;
    }

    /**
     * Gets the value of the addressMailabilityScore property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAddressMailabilityScore() {
        return addressMailabilityScore;
    }

    /**
     * Sets the value of the addressMailabilityScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAddressMailabilityScore(Integer value) {
        this.addressMailabilityScore = value;
    }

    /**
     * Gets the value of the addressMatchScore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressMatchScore() {
        return addressMatchScore;
    }

    /**
     * Sets the value of the addressMatchScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressMatchScore(String value) {
        this.addressMatchScore = value;
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        if (lang == null) {
            return "en";
        } else {
            return lang;
        }
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

}
