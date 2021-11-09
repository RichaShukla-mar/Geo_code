
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FacilityIDsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FacilityIDsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}FacilityGEOId" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}FacilityRKSTCode" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}FacilityBusinessUnitId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacilityIDsType", propOrder = {
    "facilityGEOId",
    "facilityRKSTCode",
    "facilityBusinessUnitId"
})
public class FacilityIDsType {

    @XmlElement(name = "FacilityGEOId")
    protected String facilityGEOId;
    @XmlElement(name = "FacilityRKSTCode")
    protected String facilityRKSTCode;
    @XmlElement(name = "FacilityBusinessUnitId")
    protected String facilityBusinessUnitId;

    /**
     * Gets the value of the facilityGEOId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityGEOId() {
        return facilityGEOId;
    }

    /**
     * Sets the value of the facilityGEOId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityGEOId(String value) {
        this.facilityGEOId = value;
    }

    /**
     * Gets the value of the facilityRKSTCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityRKSTCode() {
        return facilityRKSTCode;
    }

    /**
     * Sets the value of the facilityRKSTCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityRKSTCode(String value) {
        this.facilityRKSTCode = value;
    }

    /**
     * Gets the value of the facilityBusinessUnitId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityBusinessUnitId() {
        return facilityBusinessUnitId;
    }

    /**
     * Sets the value of the facilityBusinessUnitId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityBusinessUnitId(String value) {
        this.facilityBusinessUnitId = value;
    }

}
