
package net.apmoller.services.cmd.schemas;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FacilityAddressForSearchType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FacilityAddressForSearchType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CountryDetails" type="{http://services.apmoller.net/cmd/schemas}CountryDetails" minOccurs="0"/>
 *         &lt;element name="CityDetails" type="{http://services.apmoller.net/cmd/schemas}CityDetails" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}HouseNo" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}StreetName" minOccurs="0"/>
 *         &lt;element name="RegionDetails" type="{http://services.apmoller.net/cmd/schemas}RegionDetails" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}PostalCode" minOccurs="0"/>
 *         &lt;element name="Latitude" type="{http://services.apmoller.net/cmd/schemas}latitudeType" minOccurs="0"/>
 *         &lt;element name="Longitude" type="{http://services.apmoller.net/cmd/schemas}longitudeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacilityAddressForSearchType", propOrder = {
    "countryDetails",
    "cityDetails",
    "houseNo",
    "streetName",
    "regionDetails",
    "postalCode",
    "latitude",
    "longitude"
})
public class FacilityAddressForSearchType {

    @XmlElement(name = "CountryDetails")
    protected CountryDetails countryDetails;
    @XmlElement(name = "CityDetails")
    protected CityDetails cityDetails;
    @XmlElement(name = "HouseNo")
    protected String houseNo;
    @XmlElement(name = "StreetName")
    protected String streetName;
    @XmlElement(name = "RegionDetails")
    protected RegionDetails regionDetails;
    @XmlElement(name = "PostalCode")
    protected String postalCode;
    @XmlElement(name = "Latitude")
    protected BigDecimal latitude;
    @XmlElement(name = "Longitude")
    protected BigDecimal longitude;

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

}
