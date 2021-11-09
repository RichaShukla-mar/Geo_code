
package net.apmoller.services.cmd.schemas;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for BVDInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BVDInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MarketCap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MajorSector" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OperatingRevenueTurnOver" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateOfAccount" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ProfitMarginPercentage" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="CreditRating" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RecommendedCreditLimit" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="BVDGUOCustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BVDGUOLegalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BVDHQCustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BVDHQLegalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BVDInformationType", propOrder = {
    "marketCap",
    "majorSector",
    "operatingRevenueTurnOver",
    "dateOfAccount",
    "profitMarginPercentage",
    "creditRating",
    "recommendedCreditLimit",
    "bvdguoCustomerCode",
    "bvdguoLegalName",
    "bvdhqCustomerCode",
    "bvdhqLegalName"
})
public class BVDInformationType {

    @XmlElement(name = "MarketCap")
    protected String marketCap;
    @XmlElement(name = "MajorSector")
    protected String majorSector;
    @XmlElement(name = "OperatingRevenueTurnOver")
    protected String operatingRevenueTurnOver;
    @XmlElement(name = "DateOfAccount")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateOfAccount;
    @XmlElement(name = "ProfitMarginPercentage")
    protected BigDecimal profitMarginPercentage;
    @XmlElement(name = "CreditRating")
    protected String creditRating;
    @XmlElement(name = "RecommendedCreditLimit")
    protected BigDecimal recommendedCreditLimit;
    @XmlElement(name = "BVDGUOCustomerCode")
    protected String bvdguoCustomerCode;
    @XmlElement(name = "BVDGUOLegalName")
    protected String bvdguoLegalName;
    @XmlElement(name = "BVDHQCustomerCode")
    protected String bvdhqCustomerCode;
    @XmlElement(name = "BVDHQLegalName")
    protected String bvdhqLegalName;

    /**
     * Gets the value of the marketCap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarketCap() {
        return marketCap;
    }

    /**
     * Sets the value of the marketCap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarketCap(String value) {
        this.marketCap = value;
    }

    /**
     * Gets the value of the majorSector property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajorSector() {
        return majorSector;
    }

    /**
     * Sets the value of the majorSector property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajorSector(String value) {
        this.majorSector = value;
    }

    /**
     * Gets the value of the operatingRevenueTurnOver property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatingRevenueTurnOver() {
        return operatingRevenueTurnOver;
    }

    /**
     * Sets the value of the operatingRevenueTurnOver property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatingRevenueTurnOver(String value) {
        this.operatingRevenueTurnOver = value;
    }

    /**
     * Gets the value of the dateOfAccount property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfAccount() {
        return dateOfAccount;
    }

    /**
     * Sets the value of the dateOfAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfAccount(XMLGregorianCalendar value) {
        this.dateOfAccount = value;
    }

    /**
     * Gets the value of the profitMarginPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getProfitMarginPercentage() {
        return profitMarginPercentage;
    }

    /**
     * Sets the value of the profitMarginPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setProfitMarginPercentage(BigDecimal value) {
        this.profitMarginPercentage = value;
    }

    /**
     * Gets the value of the creditRating property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditRating() {
        return creditRating;
    }

    /**
     * Sets the value of the creditRating property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditRating(String value) {
        this.creditRating = value;
    }

    /**
     * Gets the value of the recommendedCreditLimit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRecommendedCreditLimit() {
        return recommendedCreditLimit;
    }

    /**
     * Sets the value of the recommendedCreditLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRecommendedCreditLimit(BigDecimal value) {
        this.recommendedCreditLimit = value;
    }

    /**
     * Gets the value of the bvdguoCustomerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBVDGUOCustomerCode() {
        return bvdguoCustomerCode;
    }

    /**
     * Sets the value of the bvdguoCustomerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBVDGUOCustomerCode(String value) {
        this.bvdguoCustomerCode = value;
    }

    /**
     * Gets the value of the bvdguoLegalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBVDGUOLegalName() {
        return bvdguoLegalName;
    }

    /**
     * Sets the value of the bvdguoLegalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBVDGUOLegalName(String value) {
        this.bvdguoLegalName = value;
    }

    /**
     * Gets the value of the bvdhqCustomerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBVDHQCustomerCode() {
        return bvdhqCustomerCode;
    }

    /**
     * Sets the value of the bvdhqCustomerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBVDHQCustomerCode(String value) {
        this.bvdhqCustomerCode = value;
    }

    /**
     * Gets the value of the bvdhqLegalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBVDHQLegalName() {
        return bvdhqLegalName;
    }

    /**
     * Sets the value of the bvdhqLegalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBVDHQLegalName(String value) {
        this.bvdhqLegalName = value;
    }

}
