
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Reduced Eldm type to be used by publish customer
 * 			
 * 
 * <p>Java class for PartyCustomUpsertRequestStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartyCustomUpsertRequestStructure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SCVCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://services.apmoller.net/cmd/schemas}UpsertEnum" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyCustomUpsertRequestStructure", propOrder = {
    "customerCode",
    "scvCode",
    "action"
})
public class PartyCustomUpsertRequestStructure {

    @XmlElement(name = "CustomerCode")
    protected String customerCode;
    @XmlElement(name = "SCVCode")
    protected String scvCode;
    @XmlElement(name = "Action")
    @XmlSchemaType(name = "string")
    protected UpsertEnum action;

    /**
     * Gets the value of the customerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * Sets the value of the customerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerCode(String value) {
        this.customerCode = value;
    }

    /**
     * Gets the value of the scvCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSCVCode() {
        return scvCode;
    }

    /**
     * Sets the value of the scvCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSCVCode(String value) {
        this.scvCode = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link UpsertEnum }
     *     
     */
    public UpsertEnum getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpsertEnum }
     *     
     */
    public void setAction(UpsertEnum value) {
        this.action = value;
    }

}
