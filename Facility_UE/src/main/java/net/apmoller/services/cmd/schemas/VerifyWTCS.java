
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *  Match given customer (given by trading name)
 * 					against the
 * 					WTCS-Lists (OFAC/EEAS). Currently only an input is required, as a workflow is
 * 					started in ActiveVOS from the service This also includes the
 * 					customer header
 * 					information.
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
 *         &lt;element name="SdnHeader" type="{http://services.apmoller.net/cmd/schemas}SdnHeaderType"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}TechnicalIdentifier"/>
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SCVCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WTCSInformation" type="{http://services.apmoller.net/cmd/schemas}WTCSInformationType"/>
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
    "sdnHeader",
    "technicalIdentifier",
    "code",
    "scvCode",
    "wtcsInformation"
})
@XmlRootElement(name = "VerifyWTCS")
public class VerifyWTCS {

    @XmlElement(name = "SdnHeader", required = true)
    protected SdnHeaderType sdnHeader;
    @XmlElement(name = "TechnicalIdentifier", required = true)
    protected String technicalIdentifier;
    @XmlElement(name = "Code", required = true)
    protected String code;
    @XmlElement(name = "SCVCode")
    protected String scvCode;
    @XmlElement(name = "WTCSInformation", required = true)
    protected WTCSInformationType wtcsInformation;

    /**
     * Gets the value of the sdnHeader property.
     * 
     * @return
     *     possible object is
     *     {@link SdnHeaderType }
     *     
     */
    public SdnHeaderType getSdnHeader() {
        return sdnHeader;
    }

    /**
     * Sets the value of the sdnHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link SdnHeaderType }
     *     
     */
    public void setSdnHeader(SdnHeaderType value) {
        this.sdnHeader = value;
    }

    /**
     *  RowID of the customer or contact, who is to be
     * 							verified
     * 							against the SDN List
     * 						
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTechnicalIdentifier() {
        return technicalIdentifier;
    }

    /**
     * Sets the value of the technicalIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTechnicalIdentifier(String value) {
        this.technicalIdentifier = value;
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

}
