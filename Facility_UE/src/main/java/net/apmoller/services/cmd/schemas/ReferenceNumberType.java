
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReferenceNumberType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReferenceNumberType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReferenceTypeCode" type="{http://services.apmoller.net/cmd/schemas}ReferenceTypeEnum"/>
 *         &lt;element name="ReferenceTypeNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}DeleteFlag" minOccurs="0"/>
 *         &lt;element name="ReferenceActiveIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferenceNumberType", propOrder = {
    "referenceTypeCode",
    "referenceTypeNumber",
    "deleteFlag",
    "referenceActiveIndicator"
})
public class ReferenceNumberType {

    @XmlElement(name = "ReferenceTypeCode", required = true)
    @XmlSchemaType(name = "string")
    protected ReferenceTypeEnum referenceTypeCode;
    @XmlElement(name = "ReferenceTypeNumber", required = true)
    protected String referenceTypeNumber;
    @XmlElement(name = "DeleteFlag")
    protected Boolean deleteFlag;
    @XmlElement(name = "ReferenceActiveIndicator")
    protected Boolean referenceActiveIndicator;

    /**
     * Gets the value of the referenceTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceTypeEnum }
     *     
     */
    public ReferenceTypeEnum getReferenceTypeCode() {
        return referenceTypeCode;
    }

    /**
     * Sets the value of the referenceTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceTypeEnum }
     *     
     */
    public void setReferenceTypeCode(ReferenceTypeEnum value) {
        this.referenceTypeCode = value;
    }

    /**
     * Gets the value of the referenceTypeNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceTypeNumber() {
        return referenceTypeNumber;
    }

    /**
     * Sets the value of the referenceTypeNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceTypeNumber(String value) {
        this.referenceTypeNumber = value;
    }

    /**
     * Gets the value of the deleteFlag property.
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

    /**
     * Gets the value of the referenceActiveIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReferenceActiveIndicator() {
        return referenceActiveIndicator;
    }

    /**
     * Sets the value of the referenceActiveIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReferenceActiveIndicator(Boolean value) {
        this.referenceActiveIndicator = value;
    }

}
