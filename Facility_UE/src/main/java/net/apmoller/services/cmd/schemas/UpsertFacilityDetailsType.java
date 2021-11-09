
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpsertFacilityDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpsertFacilityDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FacilityIDs" type="{http://services.apmoller.net/cmd/schemas}FacilityIDsType" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}FacilityName"/>
 *         &lt;element name="FacilityCategory" type="{http://services.apmoller.net/cmd/schemas}FacilityCategoryEnum"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}FacilityLifecycleStatus" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}URL" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}DODAAC" minOccurs="0"/>
 *         &lt;element name="FacilityWorkflowIndicator" type="{http://services.apmoller.net/cmd/schemas}WorkflowEnum" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpsertFacilityDetailsType", propOrder = {
    "facilityIDs",
    "facilityName",
    "facilityCategory",
    "facilityLifecycleStatus",
    "url",
    "dodaac",
    "facilityWorkflowIndicator"
})
public class UpsertFacilityDetailsType {

    @XmlElement(name = "FacilityIDs")
    protected FacilityIDsType facilityIDs;
    @XmlElement(name = "FacilityName", required = true)
    protected String facilityName;
    @XmlElement(name = "FacilityCategory", required = true)
    @XmlSchemaType(name = "string")
    protected FacilityCategoryEnum facilityCategory;
    @XmlElement(name = "FacilityLifecycleStatus", defaultValue = "A")
    @XmlSchemaType(name = "string")
    protected FacilityLifeCycleStatusEnum facilityLifecycleStatus;
    @XmlElement(name = "URL")
    protected String url;
    @XmlElement(name = "DODAAC")
    protected String dodaac;
    @XmlElement(name = "FacilityWorkflowIndicator")
    @XmlSchemaType(name = "string")
    protected WorkflowEnum facilityWorkflowIndicator;

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
     * Gets the value of the facilityWorkflowIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link WorkflowEnum }
     *     
     */
    public WorkflowEnum getFacilityWorkflowIndicator() {
        return facilityWorkflowIndicator;
    }

    /**
     * Sets the value of the facilityWorkflowIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkflowEnum }
     *     
     */
    public void setFacilityWorkflowIndicator(WorkflowEnum value) {
        this.facilityWorkflowIndicator = value;
    }

}
