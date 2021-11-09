
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 *  Complete Customer information to be used upon
 * 				create/update or
 * 				retrieval of customer data
 * 			
 * 
 * <p>Java class for CustomerCompleteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerCompleteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}CustomerCode" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}SCVCode" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}CustomerLegalName" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}CustomerTradingName"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}CustomerTypes" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}CustomerLifecycleStatus" minOccurs="0"/>
 *         &lt;element name="CustomerLifecycleStatusReason" type="{http://services.apmoller.net/cmd/schemas}CustomerStatusReasonType" minOccurs="0"/>
 *         &lt;element name="CustomerURL" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="300"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CustomerSwitchboardNumber" type="{http://services.apmoller.net/cmd/schemas}TeleCommunicationType" minOccurs="0"/>
 *         &lt;element name="InvoiceLanguagePreference" type="{http://services.apmoller.net/cmd/schemas}ISOLanguageCode" minOccurs="0"/>
 *         &lt;element name="Brokerage" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ForwardersCompensation" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CustomerWorkflowIndicator" type="{http://services.apmoller.net/cmd/schemas}WorkflowEnum" minOccurs="0"/>
 *         &lt;element name="SoleProprietor" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CustomerAuditData" type="{http://services.apmoller.net/cmd/schemas}AuditType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerCompleteType", propOrder = {
    "customerCode",
    "scvCode",
    "customerLegalName",
    "customerTradingName",
    "customerTypes",
    "customerLifecycleStatus",
    "customerLifecycleStatusReason",
    "customerURL",
    "customerSwitchboardNumber",
    "invoiceLanguagePreference",
    "brokerage",
    "forwardersCompensation",
    "customerWorkflowIndicator",
    "soleProprietor",
    "customerAuditData"
})
public class CustomerCompleteType {

    @XmlElement(name = "CustomerCode")
    protected String customerCode;
    @XmlElement(name = "SCVCode")
    protected String scvCode;
    @XmlElement(name = "CustomerLegalName")
    protected String customerLegalName;
    @XmlElement(name = "CustomerTradingName", required = true)
    protected String customerTradingName;
    @XmlElement(name = "CustomerTypes")
    @XmlSchemaType(name = "string")
    protected CustomerTypeEnum customerTypes;
    @XmlElement(name = "CustomerLifecycleStatus", defaultValue = "A")
    @XmlSchemaType(name = "string")
    protected CustomerLifeCycleStatusEnum customerLifecycleStatus;
    @XmlElement(name = "CustomerLifecycleStatusReason")
    protected CustomerStatusReasonType customerLifecycleStatusReason;
    @XmlElement(name = "CustomerURL")
    protected String customerURL;
    @XmlElement(name = "CustomerSwitchboardNumber")
    protected TeleCommunicationType customerSwitchboardNumber;
    @XmlElement(name = "InvoiceLanguagePreference")
    protected String invoiceLanguagePreference;
    @XmlElement(name = "Brokerage")
    protected Boolean brokerage;
    @XmlElement(name = "ForwardersCompensation")
    protected Boolean forwardersCompensation;
    @XmlElement(name = "CustomerWorkflowIndicator")
    @XmlSchemaType(name = "string")
    protected WorkflowEnum customerWorkflowIndicator;
    @XmlElement(name = "SoleProprietor", defaultValue = "false")
    protected Boolean soleProprietor;
    @XmlElement(name = "CustomerAuditData")
    protected AuditType customerAuditData;

    /**
     *  Unique identification of a customer can be empty
     * 						if a new
     * 						customer is created from portal, otherwise needs always to
     * 						be included
     * 					
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
     *  Unique identification of a customer can be empty
     * 						if a new
     * 						customer is created from portal, otherwise needs always to
     * 						be included
     * 					
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
     *  Legal name and trading name is mandatory
     * 					
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerLegalName() {
        return customerLegalName;
    }

    /**
     * Sets the value of the customerLegalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerLegalName(String value) {
        this.customerLegalName = value;
    }

    /**
     *  Trading name is one type of customer name alias
     * 						(will be
     * 						internally translated to a name alias type)
     * 					
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerTradingName() {
        return customerTradingName;
    }

    /**
     * Sets the value of the customerTradingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerTradingName(String value) {
        this.customerTradingName = value;
    }

    /**
     *  ZEXC External Customer ZICC APM Owned
     * 					
     * 
     * @return
     *     possible object is
     *     {@link CustomerTypeEnum }
     *     
     */
    public CustomerTypeEnum getCustomerTypes() {
        return customerTypes;
    }

    /**
     * Sets the value of the customerTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerTypeEnum }
     *     
     */
    public void setCustomerTypes(CustomerTypeEnum value) {
        this.customerTypes = value;
    }

    /**
     *  Status of the customer (e.g. Active, Pending,
     * 						Inactive).
     * 						Status is only used for display in portal, therefore
     * 						optional
     * 					
     * 
     * @return
     *     possible object is
     *     {@link CustomerLifeCycleStatusEnum }
     *     
     */
    public CustomerLifeCycleStatusEnum getCustomerLifecycleStatus() {
        return customerLifecycleStatus;
    }

    /**
     * Sets the value of the customerLifecycleStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerLifeCycleStatusEnum }
     *     
     */
    public void setCustomerLifecycleStatus(CustomerLifeCycleStatusEnum value) {
        this.customerLifecycleStatus = value;
    }

    /**
     * Gets the value of the customerLifecycleStatusReason property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerStatusReasonType }
     *     
     */
    public CustomerStatusReasonType getCustomerLifecycleStatusReason() {
        return customerLifecycleStatusReason;
    }

    /**
     * Sets the value of the customerLifecycleStatusReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerStatusReasonType }
     *     
     */
    public void setCustomerLifecycleStatusReason(CustomerStatusReasonType value) {
        this.customerLifecycleStatusReason = value;
    }

    /**
     * Gets the value of the customerURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerURL() {
        return customerURL;
    }

    /**
     * Sets the value of the customerURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerURL(String value) {
        this.customerURL = value;
    }

    /**
     * Gets the value of the customerSwitchboardNumber property.
     * 
     * @return
     *     possible object is
     *     {@link TeleCommunicationType }
     *     
     */
    public TeleCommunicationType getCustomerSwitchboardNumber() {
        return customerSwitchboardNumber;
    }

    /**
     * Sets the value of the customerSwitchboardNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link TeleCommunicationType }
     *     
     */
    public void setCustomerSwitchboardNumber(TeleCommunicationType value) {
        this.customerSwitchboardNumber = value;
    }

    /**
     * Gets the value of the invoiceLanguagePreference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceLanguagePreference() {
        return invoiceLanguagePreference;
    }

    /**
     * Sets the value of the invoiceLanguagePreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceLanguagePreference(String value) {
        this.invoiceLanguagePreference = value;
    }

    /**
     * Gets the value of the brokerage property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBrokerage() {
        return brokerage;
    }

    /**
     * Sets the value of the brokerage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBrokerage(Boolean value) {
        this.brokerage = value;
    }

    /**
     * Gets the value of the forwardersCompensation property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isForwardersCompensation() {
        return forwardersCompensation;
    }

    /**
     * Sets the value of the forwardersCompensation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setForwardersCompensation(Boolean value) {
        this.forwardersCompensation = value;
    }

    /**
     * Gets the value of the customerWorkflowIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link WorkflowEnum }
     *     
     */
    public WorkflowEnum getCustomerWorkflowIndicator() {
        return customerWorkflowIndicator;
    }

    /**
     * Sets the value of the customerWorkflowIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkflowEnum }
     *     
     */
    public void setCustomerWorkflowIndicator(WorkflowEnum value) {
        this.customerWorkflowIndicator = value;
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

    /**
     * Gets the value of the customerAuditData property.
     * 
     * @return
     *     possible object is
     *     {@link AuditType }
     *     
     */
    public AuditType getCustomerAuditData() {
        return customerAuditData;
    }

    /**
     * Sets the value of the customerAuditData property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuditType }
     *     
     */
    public void setCustomerAuditData(AuditType value) {
        this.customerAuditData = value;
    }

}
