
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransportModeDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransportModeDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransportMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "TransportModeDetails", propOrder = {
    "transportMode",
    "validDatePeriod"
})
public class TransportModeDetails {

    @XmlElement(name = "TransportMode")
    protected String transportMode;
    @XmlElement(name = "ValidDatePeriod")
    protected ValidDatePeriodType validDatePeriod;

    /**
     * Gets the value of the transportMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransportMode() {
        return transportMode;
    }

    /**
     * Sets the value of the transportMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransportMode(String value) {
        this.transportMode = value;
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
