
package net.apmoller.services.cmd.schemas;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 *  Postal Address. the language attribute specifies any local language and
 *         defaults to EN if omitted 
 * 
 * <p>Java class for FCTAddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FCTAddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}HouseNo" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}StreetName" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}Building" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}Suburb" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}District" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}PostalCode" minOccurs="0"/>
 *         &lt;element name="CityDetails" type="{http://services.apmoller.net/cmd/schemas}CityDetails"/>
 *         &lt;element name="RegionDetails" type="{http://services.apmoller.net/cmd/schemas}RegionDetails" minOccurs="0"/>
 *         &lt;element name="CountryDetails" type="{http://services.apmoller.net/cmd/schemas}CountryDetails"/>
 *         &lt;element name="Latitude" type="{http://services.apmoller.net/cmd/schemas}latitudeType" minOccurs="0"/>
 *         &lt;element name="Longitude" type="{http://services.apmoller.net/cmd/schemas}longitudeType" minOccurs="0"/>
 *         &lt;element name="ValidDatePeriod" type="{http://services.apmoller.net/cmd/schemas}ValidDatePeriodType" minOccurs="0"/>
 *         &lt;element name="AddressAuditData" type="{http://services.apmoller.net/cmd/schemas}AuditType" minOccurs="0"/>
 *         &lt;element name="AddressWorkflowIndicator" type="{http://services.apmoller.net/cmd/schemas}WorkflowEnum" minOccurs="0"/>
 *         &lt;element name="AddressMailabilityScore" type="{http://services.apmoller.net/cmd/schemas}AddressMailabilityScore" minOccurs="0"/>
 *         &lt;element name="AddressMatchScore" type="{http://services.apmoller.net/cmd/schemas}AddressMatchScore" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FCTAddressType", propOrder = {
    "houseNo",
    "streetName",
    "building",
    "suburb",
    "district",
    "postalCode",
    "cityDetails",
    "regionDetails",
    "countryDetails",
    "latitude",
    "longitude",
    "validDatePeriod",
    "addressAuditData",
    "addressWorkflowIndicator",
    "addressMailabilityScore",
    "addressMatchScore"
})
public class FCTAddressType {

    @XmlElement(name = "HouseNo")
    protected String houseNo;
    @XmlElement(name = "StreetName")
    protected String streetName;
    @XmlElement(name = "Building")
    protected String building;
    @XmlElement(name = "Suburb")
    protected String suburb;
    @XmlElement(name = "District")
    protected String district;
    @XmlElement(name = "PostalCode")
    protected String postalCode;
    @XmlElement(name = "CityDetails", required = true)
    protected CityDetails cityDetails;
    @XmlElement(name = "RegionDetails")
    protected RegionDetails regionDetails;
    @XmlElement(name = "CountryDetails", required = true)
    protected CountryDetails countryDetails;
    @XmlElement(name = "Latitude")
    protected BigDecimal latitude;
    @XmlElement(name = "Longitude")
    protected BigDecimal longitude;
    @XmlElement(name = "ValidDatePeriod")
    protected ValidDatePeriodType validDatePeriod;
    @XmlElement(name = "AddressAuditData")
    protected AuditType addressAuditData;
    @XmlElement(name = "AddressWorkflowIndicator")
    @XmlSchemaType(name = "string")
    protected WorkflowEnum addressWorkflowIndicator;
    @XmlElement(name = "AddressMailabilityScore")
    protected Integer addressMailabilityScore;
    @XmlElement(name = "AddressMatchScore")
    protected String addressMatchScore;

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
     * Gets the value of the cityDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CityDetails }
     *     
     */
    public CityDetails getCityDetails() {
        return cityDetails;
    }

    /**
     * Sets the value of the cityDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CityDetails }
     *     
     */
    public void setCityDetails(CityDetails value) {
        this.cityDetails = value;
    }

    /**
     * Gets the value of the regionDetails property.
     * 
     * @return
     *     possible object is
     *     {@link RegionDetails }
     *     
     */
    public RegionDetails getRegionDetails() {
        return regionDetails;
    }

    /**
     * Sets the value of the regionDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegionDetails }
     *     
     */
    public void setRegionDetails(RegionDetails value) {
        this.regionDetails = value;
    }

    /**
     * Gets the value of the countryDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CountryDetails }
     *     
     */
    public CountryDetails getCountryDetails() {
        return countryDetails;
    }

    /**
     * Sets the value of the countryDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryDetails }
     *     
     */
    public void setCountryDetails(CountryDetails value) {
        this.countryDetails = value;
    }

    /**
     * Gets the value of the latitude property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLatitude(BigDecimal value) {
        this.latitude = value;
    }

    /**
     * Gets the value of the longitude property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLongitude(BigDecimal value) {
        this.longitude = value;
    }

    /**
     * Gets the value of the validDatePeriod property.
     * 
     * @return
     *     possible object is
     *     {@link ValidDatePeriodType }
     *     
     */
    public ValidDatePeriodType getValidDatePeriod() {
        return validDatePeriod;
    }

    /**
     * Sets the value of the validDatePeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidDatePeriodType }
     *     
     */
    public void setValidDatePeriod(ValidDatePeriodType value) {
        this.validDatePeriod = value;
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

}
