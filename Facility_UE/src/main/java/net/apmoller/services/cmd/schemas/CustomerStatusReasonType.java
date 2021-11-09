
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 *  Description for the reason of a status change
 * 			
 * 
 * <p>Java class for CustomerStatusReasonType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerStatusReasonType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustomerStatusReasonCode" type="{http://services.apmoller.net/cmd/schemas}CustomerStatusReasonEnum"/>
 *         &lt;element name="CustomerStatusReasonName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerStatusReasonDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerStatusReasonType", propOrder = {
    "customerStatusReasonCode",
    "customerStatusReasonName",
    "customerStatusReasonDescription"
})
public class CustomerStatusReasonType {

    @XmlElement(name = "CustomerStatusReasonCode")
    protected int customerStatusReasonCode;
    @XmlElement(name = "CustomerStatusReasonName")
    protected String customerStatusReasonName;
    @XmlElement(name = "CustomerStatusReasonDescription")
    protected String customerStatusReasonDescription;

    /**
     * Gets the value of the customerStatusReasonCode property.
     * 
     */
    public int getCustomerStatusReasonCode() {
        return customerStatusReasonCode;
    }

    /**
     * Sets the value of the customerStatusReasonCode property.
     * 
     */
    public void setCustomerStatusReasonCode(int value) {
        this.customerStatusReasonCode = value;
    }

    /**
     * Gets the value of the customerStatusReasonName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerStatusReasonName() {
        return customerStatusReasonName;
    }

    /**
     * Sets the value of the customerStatusReasonName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerStatusReasonName(String value) {
        this.customerStatusReasonName = value;
    }

    /**
     * Gets the value of the customerStatusReasonDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerStatusReasonDescription() {
        return customerStatusReasonDescription;
    }

    /**
     * Sets the value of the customerStatusReasonDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerStatusReasonDescription(String value) {
        this.customerStatusReasonDescription = value;
    }

}
