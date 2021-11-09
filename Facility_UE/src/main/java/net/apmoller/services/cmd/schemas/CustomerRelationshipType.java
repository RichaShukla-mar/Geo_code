
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CustomerRelationshipType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerRelationshipType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ParentCustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ParentSCVCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChildCustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChildSCVCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelationshipType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RelationshipTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ValidFromDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ValidThruDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="DeleteFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerRelationshipType", propOrder = {
    "parentCustomerCode",
    "parentSCVCode",
    "childCustomerCode",
    "childSCVCode",
    "relationshipType",
    "relationshipTypeName",
    "validFromDate",
    "validThruDate",
    "deleteFlag"
})
public class CustomerRelationshipType {

    @XmlElement(name = "ParentCustomerCode")
    protected String parentCustomerCode;
    @XmlElement(name = "ParentSCVCode")
    protected String parentSCVCode;
    @XmlElement(name = "ChildCustomerCode")
    protected String childCustomerCode;
    @XmlElement(name = "ChildSCVCode")
    protected String childSCVCode;
    @XmlElement(name = "RelationshipType", required = true)
    protected String relationshipType;
    @XmlElement(name = "RelationshipTypeName")
    protected String relationshipTypeName;
    @XmlElement(name = "ValidFromDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar validFromDate;
    @XmlElement(name = "ValidThruDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar validThruDate;
    @XmlElement(name = "DeleteFlag")
    protected Boolean deleteFlag;

    /**
     * Gets the value of the parentCustomerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentCustomerCode() {
        return parentCustomerCode;
    }

    /**
     * Sets the value of the parentCustomerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentCustomerCode(String value) {
        this.parentCustomerCode = value;
    }

    /**
     * Gets the value of the parentSCVCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentSCVCode() {
        return parentSCVCode;
    }

    /**
     * Sets the value of the parentSCVCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentSCVCode(String value) {
        this.parentSCVCode = value;
    }

    /**
     * Gets the value of the childCustomerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChildCustomerCode() {
        return childCustomerCode;
    }

    /**
     * Sets the value of the childCustomerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChildCustomerCode(String value) {
        this.childCustomerCode = value;
    }

    /**
     * Gets the value of the childSCVCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChildSCVCode() {
        return childSCVCode;
    }

    /**
     * Sets the value of the childSCVCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChildSCVCode(String value) {
        this.childSCVCode = value;
    }

    /**
     * Gets the value of the relationshipType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationshipType() {
        return relationshipType;
    }

    /**
     * Sets the value of the relationshipType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationshipType(String value) {
        this.relationshipType = value;
    }

    /**
     * Gets the value of the relationshipTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationshipTypeName() {
        return relationshipTypeName;
    }

    /**
     * Sets the value of the relationshipTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationshipTypeName(String value) {
        this.relationshipTypeName = value;
    }

    /**
     * Gets the value of the validFromDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidFromDate() {
        return validFromDate;
    }

    /**
     * Sets the value of the validFromDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidFromDate(XMLGregorianCalendar value) {
        this.validFromDate = value;
    }

    /**
     * Gets the value of the validThruDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidThruDate() {
        return validThruDate;
    }

    /**
     * Sets the value of the validThruDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidThruDate(XMLGregorianCalendar value) {
        this.validThruDate = value;
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

}
