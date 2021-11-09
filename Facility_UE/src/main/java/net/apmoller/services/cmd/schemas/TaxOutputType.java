
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Standard Status Response with code and description
 * 			
 * 
 * <p>Java class for TaxOutputType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxOutputType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ValidationStatus" type="{http://services.apmoller.net/cmd/schemas}StatusResponseType"/>
 *         &lt;element name="TraderName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TraderCompanyType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TraderAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestTs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ISOCountryCode" type="{http://services.apmoller.net/cmd/schemas}ISOCountryCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxOutputType", propOrder = {
    "validationStatus",
    "traderName",
    "traderCompanyType",
    "traderAddress",
    "requestId",
    "requestTs",
    "isoCountryCode"
})
public class TaxOutputType {

    @XmlElement(name = "ValidationStatus", required = true)
    protected StatusResponseType validationStatus;
    @XmlElement(name = "TraderName")
    protected String traderName;
    @XmlElement(name = "TraderCompanyType")
    protected String traderCompanyType;
    @XmlElement(name = "TraderAddress")
    protected String traderAddress;
    @XmlElement(name = "RequestId")
    protected String requestId;
    @XmlElement(name = "RequestTs")
    protected String requestTs;
    @XmlElement(name = "ISOCountryCode")
    protected String isoCountryCode;

    /**
     * Gets the value of the validationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link StatusResponseType }
     *     
     */
    public StatusResponseType getValidationStatus() {
        return validationStatus;
    }

    /**
     * Sets the value of the validationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusResponseType }
     *     
     */
    public void setValidationStatus(StatusResponseType value) {
        this.validationStatus = value;
    }

    /**
     * Gets the value of the traderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTraderName() {
        return traderName;
    }

    /**
     * Sets the value of the traderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTraderName(String value) {
        this.traderName = value;
    }

    /**
     * Gets the value of the traderCompanyType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTraderCompanyType() {
        return traderCompanyType;
    }

    /**
     * Sets the value of the traderCompanyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTraderCompanyType(String value) {
        this.traderCompanyType = value;
    }

    /**
     * Gets the value of the traderAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTraderAddress() {
        return traderAddress;
    }

    /**
     * Sets the value of the traderAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTraderAddress(String value) {
        this.traderAddress = value;
    }

    /**
     * Gets the value of the requestId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestId(String value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the requestTs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestTs() {
        return requestTs;
    }

    /**
     * Sets the value of the requestTs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestTs(String value) {
        this.requestTs = value;
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

}
