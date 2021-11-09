
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CountryDetailsForSearch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CountryDetailsForSearch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ISOCountryCode" type="{http://services.apmoller.net/cmd/schemas}ISOCountryCode" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}CountryGeoID" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CountryDetailsForSearch", propOrder = {
    "isoCountryCode",
    "countryGeoID"
})
public class CountryDetailsForSearch {

    @XmlElement(name = "ISOCountryCode")
    protected String isoCountryCode;
    @XmlElement(name = "CountryGeoID")
    protected String countryGeoID;

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
     * Gets the value of the countryGeoID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryGeoID() {
        return countryGeoID;
    }

    /**
     * Sets the value of the countryGeoID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryGeoID(String value) {
        this.countryGeoID = value;
    }

}
