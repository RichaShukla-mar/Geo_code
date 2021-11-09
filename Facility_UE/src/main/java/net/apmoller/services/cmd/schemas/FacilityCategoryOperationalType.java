
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FacilityCategoryOperationalType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FacilityCategoryOperationalType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FacilityType" type="{http://services.apmoller.net/cmd/schemas}FacilityOperationalType" minOccurs="0"/>
 *         &lt;element name="ValidDatePeriod" type="{http://services.apmoller.net/cmd/schemas}ValidDatePeriodType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacilityCategoryOperationalType", propOrder = {
    "facilityType",
    "validDatePeriod"
})
public class FacilityCategoryOperationalType {

    @XmlElement(name = "FacilityType")
    protected String facilityType;
    @XmlElement(name = "ValidDatePeriod")
    protected ValidDatePeriodType validDatePeriod;

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

}
