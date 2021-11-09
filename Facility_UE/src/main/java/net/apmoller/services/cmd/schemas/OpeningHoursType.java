
package net.apmoller.services.cmd.schemas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OpeningHoursType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OpeningHoursType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DaySchedule" type="{http://services.apmoller.net/cmd/schemas}DaySchedule" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpeningHoursType", propOrder = {
    "daySchedule"
})
public class OpeningHoursType {

    @XmlElement(name = "DaySchedule")
    protected List<DaySchedule> daySchedule;

    /**
     * Gets the value of the daySchedule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the daySchedule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDaySchedule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DaySchedule }
     * 
     * 
     */
    public List<DaySchedule> getDaySchedule() {
        if (daySchedule == null) {
            daySchedule = new ArrayList<DaySchedule>();
        }
        return this.daySchedule;
    }

}
