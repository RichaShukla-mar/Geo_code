
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 *  Country specific tax number(s) per customer
 * 			
 * 
 * <p>Java class for TaxLocalNameType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxLocalNameType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TaxType" type="{http://services.apmoller.net/cmd/schemas}TaxNumberTypeEnum"/>
 *         &lt;element name="ISOCountryCode" type="{http://services.apmoller.net/cmd/schemas}ISOCountryCode"/>
 *         &lt;element name="TaxTypeLocalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}DeleteFlag" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxLocalNameType", propOrder = {
    "taxType",
    "isoCountryCode",
    "taxTypeLocalName",
    "deleteFlag"
})
public class TaxLocalNameType {

    @XmlElement(name = "TaxType", required = true)
    @XmlSchemaType(name = "string")
    protected TaxNumberTypeEnum taxType;
    @XmlElement(name = "ISOCountryCode", required = true)
    protected String isoCountryCode;
    @XmlElement(name = "TaxTypeLocalName")
    protected String taxTypeLocalName;
    @XmlElement(name = "DeleteFlag")
    protected Boolean deleteFlag;

    /**
     * Gets the value of the taxType property.
     * 
     * @return
     *     possible object is
     *     {@link TaxNumberTypeEnum }
     *     
     */
    public TaxNumberTypeEnum getTaxType() {
        return taxType;
    }

    /**
     * Sets the value of the taxType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxNumberTypeEnum }
     *     
     */
    public void setTaxType(TaxNumberTypeEnum value) {
        this.taxType = value;
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
     * Gets the value of the taxTypeLocalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxTypeLocalName() {
        return taxTypeLocalName;
    }

    /**
     * Sets the value of the taxTypeLocalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxTypeLocalName(String value) {
        this.taxTypeLocalName = value;
    }

    /**
     * Set to 1 to completly delete the tax number
     * 					
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Sets the value of the deleteFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeleteFlag(Boolean value) {
        this.deleteFlag = value;
    }

}
