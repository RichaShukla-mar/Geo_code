
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerRolesEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CustomerRolesEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ContractualOwner"/>
 *     &lt;enumeration value="AffiliateToContract"/>
 *     &lt;enumeration value="BookedByCustomer"/>
 *     &lt;enumeration value="InvoiceParty"/>
 *     &lt;enumeration value="DemurrageDealOwner"/>
 *     &lt;enumeration value="DetentionDealOwner"/>
 *     &lt;enumeration value="DemurrageInvoiceParty"/>
 *     &lt;enumeration value="DetentionInvoiceParty"/>
 *     &lt;enumeration value="NamedAccountCustomer"/>
 *     &lt;enumeration value="Shipper"/>
 *     &lt;enumeration value="Consignee"/>
 *     &lt;enumeration value="NotifyParty"/>
 *     &lt;enumeration value="AdditionalNotifyParty"/>
 *     &lt;enumeration value="CarrierHaulageSiteWarehouse"/>
 *     &lt;enumeration value="ReleaseToParty"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CustomerRolesEnum")
@XmlEnum
public enum CustomerRolesEnum {

    @XmlEnumValue("ContractualOwner")
    CONTRACTUAL_OWNER("ContractualOwner"),
    @XmlEnumValue("AffiliateToContract")
    AFFILIATE_TO_CONTRACT("AffiliateToContract"),
    @XmlEnumValue("BookedByCustomer")
    BOOKED_BY_CUSTOMER("BookedByCustomer"),
    @XmlEnumValue("InvoiceParty")
    INVOICE_PARTY("InvoiceParty"),
    @XmlEnumValue("DemurrageDealOwner")
    DEMURRAGE_DEAL_OWNER("DemurrageDealOwner"),
    @XmlEnumValue("DetentionDealOwner")
    DETENTION_DEAL_OWNER("DetentionDealOwner"),
    @XmlEnumValue("DemurrageInvoiceParty")
    DEMURRAGE_INVOICE_PARTY("DemurrageInvoiceParty"),
    @XmlEnumValue("DetentionInvoiceParty")
    DETENTION_INVOICE_PARTY("DetentionInvoiceParty"),
    @XmlEnumValue("NamedAccountCustomer")
    NAMED_ACCOUNT_CUSTOMER("NamedAccountCustomer"),
    @XmlEnumValue("Shipper")
    SHIPPER("Shipper"),
    @XmlEnumValue("Consignee")
    CONSIGNEE("Consignee"),
    @XmlEnumValue("NotifyParty")
    NOTIFY_PARTY("NotifyParty"),
    @XmlEnumValue("AdditionalNotifyParty")
    ADDITIONAL_NOTIFY_PARTY("AdditionalNotifyParty"),
    @XmlEnumValue("CarrierHaulageSiteWarehouse")
    CARRIER_HAULAGE_SITE_WAREHOUSE("CarrierHaulageSiteWarehouse"),
    @XmlEnumValue("ReleaseToParty")
    RELEASE_TO_PARTY("ReleaseToParty");
    private final String value;

    CustomerRolesEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CustomerRolesEnum fromValue(String v) {
        for (CustomerRolesEnum c: CustomerRolesEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
