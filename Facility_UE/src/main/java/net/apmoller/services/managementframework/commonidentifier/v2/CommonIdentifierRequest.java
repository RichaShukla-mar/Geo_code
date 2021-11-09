
package net.apmoller.services.managementframework.commonidentifier.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ApplicationRequester">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ApplicationID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ChannelID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="OperatorID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ApplicationUserID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="UserRequester">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RequesterID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CustomerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "", propOrder = {
    "applicationRequester",
    "userRequester"
})
@XmlRootElement(name = "CommonIdentifierRequest")
public class CommonIdentifierRequest {

    @XmlElement(name = "ApplicationRequester", required = true)
    protected CommonIdentifierRequest.ApplicationRequester applicationRequester;
    @XmlElement(name = "UserRequester", required = true)
    protected CommonIdentifierRequest.UserRequester userRequester;

    /**
     * Gets the value of the applicationRequester property.
     * 
     * @return
     *     possible object is
     *     {@link CommonIdentifierRequest.ApplicationRequester }
     *     
     */
    public CommonIdentifierRequest.ApplicationRequester getApplicationRequester() {
        return applicationRequester;
    }

    /**
     * Sets the value of the applicationRequester property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommonIdentifierRequest.ApplicationRequester }
     *     
     */
    public void setApplicationRequester(CommonIdentifierRequest.ApplicationRequester value) {
        this.applicationRequester = value;
    }

    /**
     * Gets the value of the userRequester property.
     * 
     * @return
     *     possible object is
     *     {@link CommonIdentifierRequest.UserRequester }
     *     
     */
    public CommonIdentifierRequest.UserRequester getUserRequester() {
        return userRequester;
    }

    /**
     * Sets the value of the userRequester property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommonIdentifierRequest.UserRequester }
     *     
     */
    public void setUserRequester(CommonIdentifierRequest.UserRequester value) {
        this.userRequester = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ApplicationID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ChannelID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="OperatorID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ApplicationUserID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "applicationID",
        "channelID",
        "operatorID",
        "applicationUserID",
        "transactionID"
    })
    public static class ApplicationRequester {

        @XmlElement(name = "ApplicationID", required = true)
        protected String applicationID;
        @XmlElement(name = "ChannelID", required = true)
        protected String channelID;
        @XmlElement(name = "OperatorID", required = true)
        protected String operatorID;
        @XmlElement(name = "ApplicationUserID")
        protected String applicationUserID;
        @XmlElement(name = "TransactionID")
        protected String transactionID;

        /**
         * Gets the value of the applicationID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getApplicationID() {
            return applicationID;
        }

        /**
         * Sets the value of the applicationID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setApplicationID(String value) {
            this.applicationID = value;
        }

        /**
         * Gets the value of the channelID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChannelID() {
            return channelID;
        }

        /**
         * Sets the value of the channelID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChannelID(String value) {
            this.channelID = value;
        }

        /**
         * Gets the value of the operatorID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOperatorID() {
            return operatorID;
        }

        /**
         * Sets the value of the operatorID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOperatorID(String value) {
            this.operatorID = value;
        }

        /**
         * Gets the value of the applicationUserID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getApplicationUserID() {
            return applicationUserID;
        }

        /**
         * Sets the value of the applicationUserID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setApplicationUserID(String value) {
            this.applicationUserID = value;
        }

        /**
         * Gets the value of the transactionID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTransactionID() {
            return transactionID;
        }

        /**
         * Sets the value of the transactionID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTransactionID(String value) {
            this.transactionID = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="RequesterID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="CustomerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "requesterID",
        "customerCode",
        "customerName"
    })
    public static class UserRequester {

        @XmlElement(name = "RequesterID")
        protected String requesterID;
        @XmlElement(name = "CustomerCode")
        protected String customerCode;
        @XmlElement(name = "CustomerName")
        protected String customerName;

        /**
         * Gets the value of the requesterID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRequesterID() {
            return requesterID;
        }

        /**
         * Sets the value of the requesterID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRequesterID(String value) {
            this.requesterID = value;
        }

        /**
         * Gets the value of the customerCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerCode() {
            return customerCode;
        }

        /**
         * Sets the value of the customerCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerCode(String value) {
            this.customerCode = value;
        }

        /**
         * Gets the value of the customerName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerName() {
            return customerName;
        }

        /**
         * Sets the value of the customerName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerName(String value) {
            this.customerName = value;
        }

    }

}
