
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SegmentClassificationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SegmentClassificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SegmentClassificationCode" type="{http://services.apmoller.net/cmd/schemas}SegmentClassificationString"/>
 *         &lt;element name="SegmentClassificationValue" type="{http://services.apmoller.net/cmd/schemas}SegmentClassificationValueString"/>
 *         &lt;element name="SegmentClassificationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SegmentClassificationDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SegmentClassificationType", propOrder = {
    "segmentClassificationCode",
    "segmentClassificationValue",
    "segmentClassificationName",
    "segmentClassificationDescription"
})
public class SegmentClassificationType {

    @XmlElement(name = "SegmentClassificationCode", required = true)
    protected String segmentClassificationCode;
    @XmlElement(name = "SegmentClassificationValue", required = true)
    protected String segmentClassificationValue;
    @XmlElement(name = "SegmentClassificationName")
    protected String segmentClassificationName;
    @XmlElement(name = "SegmentClassificationDescription")
    protected String segmentClassificationDescription;

    /**
     * Gets the value of the segmentClassificationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentClassificationCode() {
        return segmentClassificationCode;
    }

    /**
     * Sets the value of the segmentClassificationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentClassificationCode(String value) {
        this.segmentClassificationCode = value;
    }

    /**
     * Gets the value of the segmentClassificationValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentClassificationValue() {
        return segmentClassificationValue;
    }

    /**
     * Sets the value of the segmentClassificationValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentClassificationValue(String value) {
        this.segmentClassificationValue = value;
    }

    /**
     * Gets the value of the segmentClassificationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentClassificationName() {
        return segmentClassificationName;
    }

    /**
     * Sets the value of the segmentClassificationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentClassificationName(String value) {
        this.segmentClassificationName = value;
    }

    /**
     * Gets the value of the segmentClassificationDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentClassificationDescription() {
        return segmentClassificationDescription;
    }

    /**
     * Sets the value of the segmentClassificationDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentClassificationDescription(String value) {
        this.segmentClassificationDescription = value;
    }

}
