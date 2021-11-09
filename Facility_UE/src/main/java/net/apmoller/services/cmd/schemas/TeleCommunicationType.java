
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TeleCommunicationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TeleCommunicationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PhoneNumberType" type="{http://services.apmoller.net/cmd/schemas}PhoneNumberTypeEnum" minOccurs="0"/>
 *         &lt;element name="ISOCountryCode" type="{http://services.apmoller.net/cmd/schemas}ISOCountryCode" minOccurs="0"/>
 *         &lt;element name="InternationalDialingCode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[0-9]{1,4}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AreaCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Extension" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="10"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Number">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[0-9]{1,20}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TeleCommunicationType", propOrder = {
    "phoneNumberType",
    "isoCountryCode",
    "internationalDialingCode",
    "areaCode",
    "extension",
    "number"
})
public class TeleCommunicationType {

    @XmlElement(name = "PhoneNumberType", defaultValue = "TEL")
    @XmlSchemaType(name = "string")
    protected PhoneNumberTypeEnum phoneNumberType;
    @XmlElement(name = "ISOCountryCode")
    protected String isoCountryCode;
    @XmlElement(name = "InternationalDialingCode", required = true)
    protected String internationalDialingCode;
    @XmlElement(name = "AreaCode")
    protected String areaCode;
    @XmlElement(name = "Extension")
    protected String extension;
    @XmlElement(name = "Number", required = true)
    protected String number;

    /**
     * Gets the value of the phoneNumberType property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneNumberTypeEnum }
     *     
     */
    public PhoneNumberTypeEnum getPhoneNumberType() {
        return phoneNumberType;
    }

    /**
     * Sets the value of the phoneNumberType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneNumberTypeEnum }
     *     
     */
    public void setPhoneNumberType(PhoneNumberTypeEnum value) {
        this.phoneNumberType = value;
    }

    /**
     * Gets the value of the isoCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISOCountryCode() {
        return isoCountryCode;
    }

    /**
     * Sets the value of the isoCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISOCountryCode(String value) {
        this.isoCountryCode = value;
    }

    /**
     * Gets the value of the internationalDialingCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternationalDialingCode() {
        return internationalDialingCode;
    }

    /**
     * Sets the value of the internationalDialingCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternationalDialingCode(String value) {
        this.internationalDialingCode = value;
    }

    /**
     * Gets the value of the areaCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * Sets the value of the areaCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaCode(String value) {
        this.areaCode = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtension(String value) {
        this.extension = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

}
