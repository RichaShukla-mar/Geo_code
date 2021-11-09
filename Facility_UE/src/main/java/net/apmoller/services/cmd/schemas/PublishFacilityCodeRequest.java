
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Request to retrieve a customer with a given
 * 				customer code or a technical
 * 				identifier
 * 			
 * 
 * <p>Java class for PublishFacilityCodeRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PublishFacilityCodeRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FacilityCode" type="{http://services.apmoller.net/cmd/schemas}FacilityIDsType"/>
 *         &lt;element name="Action" type="{http://services.apmoller.net/cmd/schemas}UpsertEnum"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PublishFacilityCodeRequest", propOrder = {
    "facilityCode",
    "action"
})
public class PublishFacilityCodeRequest {

    @XmlElement(name = "FacilityCode", required = true)
    protected FacilityIDsType facilityCode;
    @XmlElement(name = "Action", required = true)
    @XmlSchemaType(name = "string")
    protected UpsertEnum action;

    /**
     * Gets the value of the facilityCode property.
     * 
     * @return
     *     possible object is
     *     {@link FacilityIDsType }
     *     
     */
    public FacilityIDsType getFacilityCode() {
        return facilityCode;
    }

    /**
     * Sets the value of the facilityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacilityIDsType }
     *     
     */
    public void setFacilityCode(FacilityIDsType value) {
        this.facilityCode = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link UpsertEnum }
     *     
     */
    public UpsertEnum getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpsertEnum }
     *     
     */
    public void setAction(UpsertEnum value) {
        this.action = value;
    }

}
