
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
 *         &lt;element name="ApplicationResponder">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="StatusDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "applicationResponder"
})
@XmlRootElement(name = "CommonIdentifierResponse")
public class CommonIdentifierResponse {

    @XmlElement(name = "ApplicationResponder", required = true)
    protected CommonIdentifierResponse.ApplicationResponder applicationResponder;

    /**
     * Gets the value of the applicationResponder property.
     * 
     * @return
     *     possible object is
     *     {@link CommonIdentifierResponse.ApplicationResponder }
     *     
     */
    public CommonIdentifierResponse.ApplicationResponder getApplicationResponder() {
        return applicationResponder;
    }

    /**
     * Sets the value of the applicationResponder property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommonIdentifierResponse.ApplicationResponder }
     *     
     */
    public void setApplicationResponder(CommonIdentifierResponse.ApplicationResponder value) {
        this.applicationResponder = value;
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
     *         &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="StatusDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "transactionID",
        "statusCode",
        "statusDescription"
    })
    public static class ApplicationResponder {

        @XmlElement(name = "TransactionID", required = true)
        protected String transactionID;
        @XmlElement(name = "StatusCode")
        protected String statusCode;
        @XmlElement(name = "StatusDescription")
        protected String statusDescription;

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

        /**
         * Gets the value of the statusCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatusCode() {
            return statusCode;
        }

        /**
         * Sets the value of the statusCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatusCode(String value) {
            this.statusCode = value;
        }

        /**
         * Gets the value of the statusDescription property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatusDescription() {
            return statusDescription;
        }

        /**
         * Sets the value of the statusDescription property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatusDescription(String value) {
            this.statusDescription = value;
        }

    }

}
