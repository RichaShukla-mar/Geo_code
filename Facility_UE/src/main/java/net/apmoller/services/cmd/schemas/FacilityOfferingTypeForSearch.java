
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FacilityOfferingTypeForSearch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FacilityOfferingTypeForSearch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OfferingCode" type="{http://services.apmoller.net/cmd/schemas}OfferingCode" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}OfferingName"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacilityOfferingTypeForSearch", propOrder = {
    "offeringCode",
    "offeringName"
})
public class FacilityOfferingTypeForSearch {

    @XmlElement(name = "OfferingCode")
    protected String offeringCode;
    @XmlElement(name = "OfferingName", required = true)
    protected String offeringName;

    /**
     * Gets the value of the offeringCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferingCode() {
        return offeringCode;
    }

    /**
     * Sets the value of the offeringCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferingCode(String value) {
        this.offeringCode = value;
    }

    /**
     * Gets the value of the offeringName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferingName() {
        return offeringName;
    }

    /**
     * Sets the value of the offeringName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferingName(String value) {
        this.offeringName = value;
    }

}
