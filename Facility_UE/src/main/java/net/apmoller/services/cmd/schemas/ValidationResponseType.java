
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Validation Status Response with the field name and
 * 				detail validation message
 * 			
 * 
 * <p>Java class for ValidationResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidationResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FieldName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ValidationErrorDetail" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidationResponseType", propOrder = {
    "fieldName",
    "validationErrorDetail"
})
public class ValidationResponseType {

    @XmlElement(name = "FieldName", required = true)
    protected String fieldName;
    @XmlElement(name = "ValidationErrorDetail", required = true)
    protected String validationErrorDetail;

    /**
     * Gets the value of the fieldName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the value of the fieldName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldName(String value) {
        this.fieldName = value;
    }

    /**
     * Gets the value of the validationErrorDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidationErrorDetail() {
        return validationErrorDetail;
    }

    /**
     * Sets the value of the validationErrorDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidationErrorDetail(String value) {
        this.validationErrorDetail = value;
    }

}
