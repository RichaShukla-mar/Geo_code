
package net.apmoller.services.cmd.schemas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *  Response of retrieve concern codes
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
 *         &lt;element name="ConcernCode" type="{http://services.apmoller.net/cmd/schemas}ConcernCodeType" maxOccurs="unbounded" minOccurs="0"/>
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
    "concernCode"
})
@XmlRootElement(name = "ConcernCodes")
public class ConcernCodes {

    @XmlElement(name = "ConcernCode")
    protected List<ConcernCodeType> concernCode;

    /**
     * Gets the value of the concernCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the concernCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConcernCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConcernCodeType }
     * 
     * 
     */
    public List<ConcernCodeType> getConcernCode() {
        if (concernCode == null) {
            concernCode = new ArrayList<ConcernCodeType>();
        }
        return this.concernCode;
    }

}
