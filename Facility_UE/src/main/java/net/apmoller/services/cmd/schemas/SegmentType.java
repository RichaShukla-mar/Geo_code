
package net.apmoller.services.cmd.schemas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SegmentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SegmentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="Brand" type="{http://services.apmoller.net/cmd/schemas}OperatorTypeEnum"/>
 *         &lt;element name="SegmentClassification" type="{http://services.apmoller.net/cmd/schemas}SegmentClassificationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="UnassignSegmentFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SegmentType", propOrder = {
    "brandAndSegmentClassificationAndUnassignSegmentFlag"
})
public class SegmentType {

    @XmlElements({
        @XmlElement(name = "Brand", required = true, type = OperatorTypeEnum.class),
        @XmlElement(name = "SegmentClassification", required = true, type = SegmentClassificationType.class),
        @XmlElement(name = "UnassignSegmentFlag", required = true, type = Boolean.class, defaultValue = "false")
    })
    protected List<Object> brandAndSegmentClassificationAndUnassignSegmentFlag;

    /**
     * Gets the value of the brandAndSegmentClassificationAndUnassignSegmentFlag property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the brandAndSegmentClassificationAndUnassignSegmentFlag property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBrandAndSegmentClassificationAndUnassignSegmentFlag().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OperatorTypeEnum }
     * {@link SegmentClassificationType }
     * {@link Boolean }
     * 
     * 
     */
    public List<Object> getBrandAndSegmentClassificationAndUnassignSegmentFlag() {
        if (brandAndSegmentClassificationAndUnassignSegmentFlag == null) {
            brandAndSegmentClassificationAndUnassignSegmentFlag = new ArrayList<Object>();
        }
        return this.brandAndSegmentClassificationAndUnassignSegmentFlag;
    }

}
