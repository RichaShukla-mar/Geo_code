
package net.apmoller.services.cmd.schemas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Concern code type to be retrieved in retrieve
 * 				concerncode operation
 * 			
 * 
 * <p>Java class for ConcernCodeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConcernCodeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Country" type="{http://services.apmoller.net/cmd/schemas}ISOCountryCode"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}CustomerEntity" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}ConcernMembers" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConcernCodeType", propOrder = {
    "code",
    "name",
    "status",
    "country",
    "customerEntity",
    "concernMembers"
})
public class ConcernCodeType {

    @XmlElement(name = "Code", required = true)
    protected String code;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "Country", required = true)
    protected String country;
    @XmlElement(name = "CustomerEntity")
    protected CustomerEntity customerEntity;
    @XmlElement(name = "ConcernMembers")
    protected List<ConcernMembers> concernMembers;

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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the customerEntity property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerEntity }
     *     
     */
    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    /**
     * Sets the value of the customerEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerEntity }
     *     
     */
    public void setCustomerEntity(CustomerEntity value) {
        this.customerEntity = value;
    }

    /**
     * Gets the value of the concernMembers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the concernMembers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConcernMembers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConcernMembers }
     * 
     * 
     */
    public List<ConcernMembers> getConcernMembers() {
        if (concernMembers == null) {
            concernMembers = new ArrayList<ConcernMembers>();
        }
        return this.concernMembers;
    }

}
