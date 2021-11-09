
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Request to retrieve a customer with a given
 * 				customer code or a technical
 * 				identifier
 * 			
 * 
 * <p>Java class for InactivateFacilityRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InactivateFacilityRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}TechnicalIdentifier"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InactivateFacilityRequest", propOrder = {
    "technicalIdentifier"
})
public class InactivateFacilityRequest {

    @XmlElement(name = "TechnicalIdentifier", required = true)
    protected String technicalIdentifier;

    /**
     * Gets the value of the technicalIdentifier property.
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

}
