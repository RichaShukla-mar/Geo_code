
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Response of retrieve Relationship (identical to
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
 *         &lt;element name="ParentCustomer" type="{http://services.apmoller.net/cmd/schemas}Customer" minOccurs="0"/>
 *         &lt;element name="ChildCustomer" type="{http://services.apmoller.net/cmd/schemas}Customer" minOccurs="0"/>
 *         &lt;element name="RelationshipTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelationshipTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ValidFromDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ValidThruDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
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
    "parentCustomer",
    "childCustomer",
    "relationshipTypeCd",
    "relationshipTypeName",
    "validFromDate",
    "validThruDate"
})
@XmlRootElement(name = "RelationshipTypeInfo")
public class RelationshipTypeInfo {

    @XmlElement(name = "ParentCustomer")
    protected Customer parentCustomer;
    @XmlElement(name = "ChildCustomer")
    protected Customer childCustomer;
    @XmlElement(name = "RelationshipTypeCd")
    protected String relationshipTypeCd;
    @XmlElement(name = "RelationshipTypeName")
    protected String relationshipTypeName;
    @XmlElement(name = "ValidFromDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar validFromDate;
    @XmlElement(name = "ValidThruDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar validThruDate;

    /**
     * Gets the value of the parentCustomer property.
     * 
     * @return
     *     possible object is
     *     {@link Customer }
     *     
     */
    public Customer getParentCustomer() {
        return parentCustomer;
    }

    /**
     * Sets the value of the parentCustomer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Customer }
     *     
     */
    public void setParentCustomer(Customer value) {
        this.parentCustomer = value;
    }

    /**
     * Gets the value of the childCustomer property.
     * 
     * @return
     *     possible object is
     *     {@link Customer }
     *     
     */
    public Customer getChildCustomer() {
        return childCustomer;
    }

    /**
     * Sets the value of the childCustomer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Customer }
     *     
     */
    public void setChildCustomer(Customer value) {
        this.childCustomer = value;
    }

    /**
     * Gets the value of the relationshipTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationshipTypeCd() {
        return relationshipTypeCd;
    }

    /**
     * Sets the value of the relationshipTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationshipTypeCd(String value) {
        this.relationshipTypeCd = value;
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

}
