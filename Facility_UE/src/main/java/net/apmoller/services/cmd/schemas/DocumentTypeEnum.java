
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DocumentTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Booking confirmation"/>
 *     &lt;enumeration value="Quotation"/>
 *     &lt;enumeration value="Billing of lading"/>
 *     &lt;enumeration value="Waybill"/>
 *     &lt;enumeration value="Invoice"/>
 *     &lt;enumeration value="Dunning letter"/>
 *     &lt;enumeration value="Financial statement"/>
 *     &lt;enumeration value="Status notification"/>
 *     &lt;enumeration value="Arrival Notice"/>
 *     &lt;enumeration value="Status change alert"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DocumentTypeEnum")
@XmlEnum
public enum DocumentTypeEnum {

    @XmlEnumValue("Booking confirmation")
    BOOKING_CONFIRMATION("Booking confirmation"),
    @XmlEnumValue("Quotation")
    QUOTATION("Quotation"),
    @XmlEnumValue("Billing of lading")
    BILLING_OF_LADING("Billing of lading"),
    @XmlEnumValue("Waybill")
    WAYBILL("Waybill"),
    @XmlEnumValue("Invoice")
    INVOICE("Invoice"),
    @XmlEnumValue("Dunning letter")
    DUNNING_LETTER("Dunning letter"),
    @XmlEnumValue("Financial statement")
    FINANCIAL_STATEMENT("Financial statement"),
    @XmlEnumValue("Status notification")
    STATUS_NOTIFICATION("Status notification"),
    @XmlEnumValue("Arrival Notice")
    ARRIVAL_NOTICE("Arrival Notice"),
    @XmlEnumValue("Status change alert")
    STATUS_CHANGE_ALERT("Status change alert");
    private final String value;

    DocumentTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DocumentTypeEnum fromValue(String v) {
        for (DocumentTypeEnum c: DocumentTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
