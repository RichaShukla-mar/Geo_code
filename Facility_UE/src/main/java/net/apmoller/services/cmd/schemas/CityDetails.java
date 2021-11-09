
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CityDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CityDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}City"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}CityGeoID" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CityDetails", propOrder = {
    "city",
    "cityGeoID"
})
public class CityDetails {

    @XmlElement(name = "City", required = true)
    protected String city;
    @XmlElement(name = "CityGeoID")
    protected String cityGeoID;

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
     * Gets the value of the cityGeoID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityGeoID() {
        return cityGeoID;
    }

    /**
     * Sets the value of the cityGeoID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityGeoID(String value) {
        this.cityGeoID = value;
    }

}
