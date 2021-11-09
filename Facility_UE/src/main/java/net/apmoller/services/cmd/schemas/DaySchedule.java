
package net.apmoller.services.cmd.schemas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DaySchedule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DaySchedule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}OfficeWorkingDay"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}OfficeOpeningHours" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://services.apmoller.net/cmd/schemas}OfficeClosingHours" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DaySchedule", propOrder = {
    "officeWorkingDay",
    "officeOpeningHours",
    "officeClosingHours"
})
public class DaySchedule {

    @XmlElement(name = "OfficeWorkingDay", required = true)
    protected String officeWorkingDay;
    @XmlElement(name = "OfficeOpeningHours")
    protected List<XMLGregorianCalendar> officeOpeningHours;
    @XmlElement(name = "OfficeClosingHours")
    protected List<XMLGregorianCalendar> officeClosingHours;

    /**
     * Gets the value of the officeWorkingDay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfficeWorkingDay() {
        return officeWorkingDay;
    }

    /**
     * Sets the value of the officeWorkingDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfficeWorkingDay(String value) {
        this.officeWorkingDay = value;
    }

    /**
     * Gets the value of the officeOpeningHours property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the officeOpeningHours property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOfficeOpeningHours().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XMLGregorianCalendar }
     * 
     * 
     */
    public List<XMLGregorianCalendar> getOfficeOpeningHours() {
        if (officeOpeningHours == null) {
            officeOpeningHours = new ArrayList<XMLGregorianCalendar>();
        }
        return this.officeOpeningHours;
    }

    /**
     * Gets the value of the officeClosingHours property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the officeClosingHours property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOfficeClosingHours().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XMLGregorianCalendar }
     * 
     * 
     */
    public List<XMLGregorianCalendar> getOfficeClosingHours() {
        if (officeClosingHours == null) {
            officeClosingHours = new ArrayList<XMLGregorianCalendar>();
        }
        return this.officeClosingHours;
    }

}
