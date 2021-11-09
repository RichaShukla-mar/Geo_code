
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 					Request to retrieve a facility with a given
 * 					facility code or a technical
 * 					identifier
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
 *         &lt;element name="FacilityID" type="{http://services.apmoller.net/cmd/schemas}FacilityIDsType"/>
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
@XmlType(name = "", propOrder = {
    "facilityID",
    "action"
})
@XmlRootElement(name = "PublishFacilityCode")
public class PublishFacilityCode {

    @XmlElement(name = "FacilityID", required = true)
    protected FacilityIDsType facilityID;
    @XmlElement(name = "Action", required = true)
    @XmlSchemaType(name = "string")
    protected UpsertEnum action;

    /**
     * Gets the value of the facilityID property.
     * 
     * @return
     *     possible object is
     *     {@link FacilityIDsType }
     *     
     */
    public FacilityIDsType getFacilityID() {
        return facilityID;
    }

    /**
     * Sets the value of the facilityID property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacilityIDsType }
     *     
     */
    public void setFacilityID(FacilityIDsType value) {
        this.facilityID = value;
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
