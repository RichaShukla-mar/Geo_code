
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 *  Additional information required to start any of
 * 				the customer
 * 				workflows WorkflowIndicator - Indicate which kind of human task activity is
 * 				shown in
 * 				IDD CustomerCreateIndicator - Indicates if the activateCustomer
 * 				Operation needs to
 * 				be invoked once the record is accepted (only for new records)
 * 				WTCSInformation -
 * 				Information required to call the SDN-Service in case record is accepted and
 * 				customer
 * 				name or address changed
 * 			
 * 
 * <p>Java class for CmdFctProcessType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CmdFctProcessType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WorkflowIndicator" type="{http://services.apmoller.net/cmd/schemas}WorkflowEnum"/>
 *         &lt;element name="CreateIndicator" type="{http://services.apmoller.net/cmd/schemas}UpsertEnum"/>
 *         &lt;element name="AdditionalOwnerUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SCVCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WTCSInformation" type="{http://services.apmoller.net/cmd/schemas}WTCSInformationType"/>
 *         &lt;element name="CreationUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdateUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CallPublish" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="WorkflowReferenceNumbers" type="{http://services.apmoller.net/cmd/schemas}WorkflowReferenceNumberType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CmdFctProcessType", propOrder = {
    "workflowIndicator",
    "createIndicator",
    "additionalOwnerUID",
    "code",
    "scvCode",
    "wtcsInformation",
    "creationUser",
    "lastUpdateUser",
    "callPublish",
    "workflowReferenceNumbers"
})
public class CmdFctProcessType {

    @XmlElement(name = "WorkflowIndicator", required = true)
    @XmlSchemaType(name = "string")
    protected WorkflowEnum workflowIndicator;
    @XmlElement(name = "CreateIndicator", required = true)
    @XmlSchemaType(name = "string")
    protected UpsertEnum createIndicator;
    @XmlElement(name = "AdditionalOwnerUID")
    protected String additionalOwnerUID;
    @XmlElement(name = "Code")
    protected String code;
    @XmlElement(name = "SCVCode")
    protected String scvCode;
    @XmlElement(name = "WTCSInformation", required = true)
    protected WTCSInformationType wtcsInformation;
    @XmlElement(name = "CreationUser")
    protected String creationUser;
    @XmlElement(name = "LastUpdateUser")
    protected String lastUpdateUser;
    @XmlElement(name = "CallPublish")
    protected Boolean callPublish;
    @XmlElement(name = "WorkflowReferenceNumbers")
    protected WorkflowReferenceNumberType workflowReferenceNumbers;

    /**
     * Gets the value of the workflowIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link WorkflowEnum }
     *     
     */
    public WorkflowEnum getWorkflowIndicator() {
        return workflowIndicator;
    }

    /**
     * Sets the value of the workflowIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkflowEnum }
     *     
     */
    public void setWorkflowIndicator(WorkflowEnum value) {
        this.workflowIndicator = value;
    }

    /**
     * Gets the value of the createIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link UpsertEnum }
     *     
     */
    public UpsertEnum getCreateIndicator() {
        return createIndicator;
    }

    /**
     * Sets the value of the createIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpsertEnum }
     *     
     */
    public void setCreateIndicator(UpsertEnum value) {
        this.createIndicator = value;
    }

    /**
     * Gets the value of the additionalOwnerUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalOwnerUID() {
        return additionalOwnerUID;
    }

    /**
     * Sets the value of the additionalOwnerUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalOwnerUID(String value) {
        this.additionalOwnerUID = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
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
     * Gets the value of the wtcsInformation property.
     * 
     * @return
     *     possible object is
     *     {@link WTCSInformationType }
     *     
     */
    public WTCSInformationType getWTCSInformation() {
        return wtcsInformation;
    }

    /**
     * Sets the value of the wtcsInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link WTCSInformationType }
     *     
     */
    public void setWTCSInformation(WTCSInformationType value) {
        this.wtcsInformation = value;
    }

    /**
     * Gets the value of the creationUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * Sets the value of the creationUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreationUser(String value) {
        this.creationUser = value;
    }

    /**
     * Gets the value of the lastUpdateUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * Sets the value of the lastUpdateUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdateUser(String value) {
        this.lastUpdateUser = value;
    }

    /**
     * Gets the value of the callPublish property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCallPublish() {
        return callPublish;
    }

    /**
     * Sets the value of the callPublish property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCallPublish(Boolean value) {
        this.callPublish = value;
    }

    /**
     * Gets the value of the workflowReferenceNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link WorkflowReferenceNumberType }
     *     
     */
    public WorkflowReferenceNumberType getWorkflowReferenceNumbers() {
        return workflowReferenceNumbers;
    }

    /**
     * Sets the value of the workflowReferenceNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkflowReferenceNumberType }
     *     
     */
    public void setWorkflowReferenceNumbers(WorkflowReferenceNumberType value) {
        this.workflowReferenceNumbers = value;
    }

}
