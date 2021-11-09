
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WorkflowReferenceNumberElement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WorkflowReferenceNumberElement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WorkflowReferenceTypeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="WorkflowReferenceTypeNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkflowReferenceNumberElement", propOrder = {
    "workflowReferenceTypeCode",
    "workflowReferenceTypeNumber"
})
public class WorkflowReferenceNumberElement {

    @XmlElement(name = "WorkflowReferenceTypeCode", required = true)
    protected String workflowReferenceTypeCode;
    @XmlElement(name = "WorkflowReferenceTypeNumber", required = true)
    protected String workflowReferenceTypeNumber;

    /**
     * Gets the value of the workflowReferenceTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkflowReferenceTypeCode() {
        return workflowReferenceTypeCode;
    }

    /**
     * Sets the value of the workflowReferenceTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkflowReferenceTypeCode(String value) {
        this.workflowReferenceTypeCode = value;
    }

    /**
     * Gets the value of the workflowReferenceTypeNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkflowReferenceTypeNumber() {
        return workflowReferenceTypeNumber;
    }

    /**
     * Sets the value of the workflowReferenceTypeNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkflowReferenceTypeNumber(String value) {
        this.workflowReferenceTypeNumber = value;
    }

}
