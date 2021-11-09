
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Standard Status Response with code and description
 * 			
 * 
 * <p>Java class for TaxInputType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxInputType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VatNumber" type="{http://services.apmoller.net/cmd/schemas}TaxInformationType"/>
 *         &lt;element name="TraderName" type="{http://services.apmoller.net/cmd/schemas}CustomerTradingNameString" minOccurs="0"/>
 *         &lt;element name="TradeCompanyType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}StreetName" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}PostalCode" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}City" minOccurs="0"/>
 *         &lt;element name="SoleProprietor" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxInputType", propOrder = {
    "vatNumber",
    "traderName",
    "tradeCompanyType",
    "streetName",
    "postalCode",
    "city",
    "soleProprietor"
})
public class TaxInputType {

    @XmlElement(name = "VatNumber", required = true)
    protected TaxInformationType vatNumber;
    @XmlElement(name = "TraderName")
    protected String traderName;
    @XmlElement(name = "TradeCompanyType")
    protected String tradeCompanyType;
    @XmlElement(name = "StreetName")
    protected String streetName;
    @XmlElement(name = "PostalCode")
    protected String postalCode;
    @XmlElement(name = "City")
    protected String city;
    @XmlElement(name = "SoleProprietor", defaultValue = "false")
    protected Boolean soleProprietor;

    /**
     * Gets the value of the vatNumber property.
     * 
     * @return
     *     possible object is
     *     {@link TaxInformationType }
     *     
     */
    public TaxInformationType getVatNumber() {
        return vatNumber;
    }

    /**
     * Sets the value of the vatNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxInformationType }
     *     
     */
    public void setVatNumber(TaxInformationType value) {
        this.vatNumber = value;
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
     * Gets the value of the tradeCompanyType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeCompanyType() {
        return tradeCompanyType;
    }

    /**
     * Sets the value of the tradeCompanyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeCompanyType(String value) {
        this.tradeCompanyType = value;
    }

    /**
     * Gets the value of the streetName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets the value of the streetName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetName(String value) {
        this.streetName = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

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
     * Gets the value of the soleProprietor property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSoleProprietor() {
        return soleProprietor;
    }

    /**
     * Sets the value of the soleProprietor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSoleProprietor(Boolean value) {
        this.soleProprietor = value;
    }

}
