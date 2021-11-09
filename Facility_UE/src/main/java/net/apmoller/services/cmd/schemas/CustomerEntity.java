
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *  Response of retrieve customer (identical to
 * 					create/update) for a
 * 					given customer code(s)
 * 				
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Customer" type="{http://services.apmoller.net/cmd/schemas}CustomerCompleteType"/>
 *         &lt;element name="CustomerAddress" type="{http://services.apmoller.net/cmd/schemas}AddressType"/>
 *         &lt;element name="IsWorkflowPending" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="WorkflowInformation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerTaxNumbers" type="{http://services.apmoller.net/cmd/schemas}CustomerTaxNumberType" minOccurs="0"/>
 *         &lt;element name="CustomerReferenceNumbers" type="{http://services.apmoller.net/cmd/schemas}CustomerReferenceNumberType" minOccurs="0"/>
 *         &lt;element name="CustomerBVDInformation" type="{http://services.apmoller.net/cmd/schemas}BVDInformationType" minOccurs="0"/>
 *         &lt;element name="CustomerSegments" type="{http://services.apmoller.net/cmd/schemas}SegmentationsType" minOccurs="0"/>
 *         &lt;element name="RelationShips" type="{http://services.apmoller.net/cmd/schemas}CustomerRelationshipsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "customer",
    "customerAddress",
    "isWorkflowPending",
    "workflowInformation",
    "customerTaxNumbers",
    "customerReferenceNumbers",
    "customerBVDInformation",
    "customerSegments",
    "relationShips"
})
@XmlRootElement(name = "CustomerEntity")
public class CustomerEntity {

    @XmlElement(name = "Customer", required = true)
    protected CustomerCompleteType customer;
    @XmlElement(name = "CustomerAddress", required = true)
    protected AddressType customerAddress;
    @XmlElement(name = "IsWorkflowPending")
    protected Boolean isWorkflowPending;
    @XmlElement(name = "WorkflowInformation")
    protected String workflowInformation;
    @XmlElement(name = "CustomerTaxNumbers")
    protected CustomerTaxNumberType customerTaxNumbers;
    @XmlElement(name = "CustomerReferenceNumbers")
    protected CustomerReferenceNumberType customerReferenceNumbers;
    @XmlElement(name = "CustomerBVDInformation")
    protected BVDInformationType customerBVDInformation;
    @XmlElement(name = "CustomerSegments")
    protected SegmentationsType customerSegments;
    @XmlElement(name = "RelationShips")
    protected CustomerRelationshipsType relationShips;

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerCompleteType }
     *     
     */
    public CustomerCompleteType getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerCompleteType }
     *     
     */
    public void setCustomer(CustomerCompleteType value) {
        this.customer = value;
    }

    /**
     * Gets the value of the customerAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Sets the value of the customerAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setCustomerAddress(AddressType value) {
        this.customerAddress = value;
    }

    /**
     * Gets the value of the isWorkflowPending property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsWorkflowPending() {
        return isWorkflowPending;
    }

    /**
     * Sets the value of the isWorkflowPending property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsWorkflowPending(Boolean value) {
        this.isWorkflowPending = value;
    }

    /**
     * Gets the value of the workflowInformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkflowInformation() {
        return workflowInformation;
    }

    /**
     * Sets the value of the workflowInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkflowInformation(String value) {
        this.workflowInformation = value;
    }

    /**
     * Gets the value of the customerTaxNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerTaxNumberType }
     *     
     */
    public CustomerTaxNumberType getCustomerTaxNumbers() {
        return customerTaxNumbers;
    }

    /**
     * Sets the value of the customerTaxNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerTaxNumberType }
     *     
     */
    public void setCustomerTaxNumbers(CustomerTaxNumberType value) {
        this.customerTaxNumbers = value;
    }

    /**
     * Gets the value of the customerReferenceNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerReferenceNumberType }
     *     
     */
    public CustomerReferenceNumberType getCustomerReferenceNumbers() {
        return customerReferenceNumbers;
    }

    /**
     * Sets the value of the customerReferenceNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerReferenceNumberType }
     *     
     */
    public void setCustomerReferenceNumbers(CustomerReferenceNumberType value) {
        this.customerReferenceNumbers = value;
    }

    /**
     * Gets the value of the customerBVDInformation property.
     * 
     * @return
     *     possible object is
     *     {@link BVDInformationType }
     *     
     */
    public BVDInformationType getCustomerBVDInformation() {
        return customerBVDInformation;
    }

    /**
     * Sets the value of the customerBVDInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link BVDInformationType }
     *     
     */
    public void setCustomerBVDInformation(BVDInformationType value) {
        this.customerBVDInformation = value;
    }

    /**
     * Gets the value of the customerSegments property.
     * 
     * @return
     *     possible object is
     *     {@link SegmentationsType }
     *     
     */
    public SegmentationsType getCustomerSegments() {
        return customerSegments;
    }

    /**
     * Sets the value of the customerSegments property.
     * 
     * @param value
     *     allowed object is
     *     {@link SegmentationsType }
     *     
     */
    public void setCustomerSegments(SegmentationsType value) {
        this.customerSegments = value;
    }

    /**
     * Gets the value of the relationShips property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerRelationshipsType }
     *     
     */
    public CustomerRelationshipsType getRelationShips() {
        return relationShips;
    }

    /**
     * Sets the value of the relationShips property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerRelationshipsType }
     *     
     */
    public void setRelationShips(CustomerRelationshipsType value) {
        this.relationShips = value;
    }

}
