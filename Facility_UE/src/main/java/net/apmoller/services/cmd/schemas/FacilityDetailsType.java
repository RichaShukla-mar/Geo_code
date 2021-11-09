
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FacilityDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FacilityDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FacilityIDs" type="{http://services.apmoller.net/cmd/schemas}FacilityIDsType"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}FacilityName"/>
 *         &lt;element name="FacilityCategory" type="{http://services.apmoller.net/cmd/schemas}FacilityCategoryEnum"/>
 *         &lt;element name="FacilityTypes" type="{http://services.apmoller.net/cmd/schemas}FacilityCategoryType" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}FacilityLifecycleStatus" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}URL" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}DODAAC" minOccurs="0"/>
 *         &lt;element name="FacilityAuditData" type="{http://services.apmoller.net/cmd/schemas}AuditType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacilityDetailsType", propOrder = {
    "facilityIDs",
    "facilityName",
    "facilityCategory",
    "facilityTypes",
    "facilityLifecycleStatus",
    "url",
    "dodaac",
    "facilityAuditData"
})
public class FacilityDetailsType {

    @XmlElement(name = "FacilityIDs", required = true)
    protected FacilityIDsType facilityIDs;
    @XmlElement(name = "FacilityName", required = true)
    protected String facilityName;
    @XmlElement(name = "FacilityCategory", required = true)
    @XmlSchemaType(name = "string")
    protected FacilityCategoryEnum facilityCategory;
    @XmlElement(name = "FacilityTypes")
    protected FacilityCategoryType facilityTypes;
    @XmlElement(name = "FacilityLifecycleStatus", defaultValue = "A")
    @XmlSchemaType(name = "string")
    protected FacilityLifeCycleStatusEnum facilityLifecycleStatus;
    @XmlElement(name = "URL")
    protected String url;
    @XmlElement(name = "DODAAC")
    protected String dodaac;
    @XmlElement(name = "FacilityAuditData")
    protected AuditType facilityAuditData;

    /**
     * Gets the value of the facilityIDs property.
     * 
     * @return
     *     possible object is
     *     {@link FacilityIDsType }
     *     
     */
    public FacilityIDsType getFacilityIDs() {
        return facilityIDs;
    }

    /**
     * Sets the value of the facilityIDs property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacilityIDsType }
     *     
     */
    public void setFacilityIDs(FacilityIDsType value) {
        this.facilityIDs = value;
    }

    /**
     * Gets the value of the facilityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityName() {
        return facilityName;
    }

    /**
     * Sets the value of the facilityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityName(String value) {
        this.facilityName = value;
    }

    /**
     * Gets the value of the facilityCategory property.
     * 
     * @return
     *     possible object is
     *     {@link FacilityCategoryEnum }
     *     
     */
    public FacilityCategoryEnum getFacilityCategory() {
        return facilityCategory;
    }

    /**
     * Sets the value of the facilityCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacilityCategoryEnum }
     *     
     */
    public void setFacilityCategory(FacilityCategoryEnum value) {
        this.facilityCategory = value;
    }

    /**
     * Gets the value of the facilityTypes property.
     * 
     * @return
     *     possible object is
     *     {@link FacilityCategoryType }
     *     
     */
    public FacilityCategoryType getFacilityTypes() {
        return facilityTypes;
    }

    /**
     * Sets the value of the facilityTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacilityCategoryType }
     *     
     */
    public void setFacilityTypes(FacilityCategoryType value) {
        this.facilityTypes = value;
    }

    /**
     * Gets the value of the facilityLifecycleStatus property.
     * 
     * @return
     *     possible object is
     *     {@link FacilityLifeCycleStatusEnum }
     *     
     */
    public FacilityLifeCycleStatusEnum getFacilityLifecycleStatus() {
        return facilityLifecycleStatus;
    }

    /**
     * Sets the value of the facilityLifecycleStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacilityLifeCycleStatusEnum }
     *     
     */
    public void setFacilityLifecycleStatus(FacilityLifeCycleStatusEnum value) {
        this.facilityLifecycleStatus = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURL() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURL(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the dodaac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDODAAC() {
        return dodaac;
    }

    /**
     * Sets the value of the dodaac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDODAAC(String value) {
        this.dodaac = value;
    }

    /**
     * Gets the value of the facilityAuditData property.
     * 
     * @return
     *     possible object is
     *     {@link AuditType }
     *     
     */
    public AuditType getFacilityAuditData() {
        return facilityAuditData;
    }

    /**
     * Sets the value of the facilityAuditData property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuditType }
     *     
     */
    public void setFacilityAuditData(AuditType value) {
        this.facilityAuditData = value;
    }

}
