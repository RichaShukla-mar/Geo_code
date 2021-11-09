
package net.apmoller.services.cmd.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import xsd.av.mdm.informatica.INFATask;


/**
 *  The StartProcessRequest for ActiveVOS to trigger
 * 					a workflow
 * 					extended by required customer attributes
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
 *         &lt;element name="INFATask" type="{urn:informatica.mdm.av.xsd}INFATask"/>
 *         &lt;element name="CmdFctProcess" type="{http://services.apmoller.net/cmd/schemas}CmdFctProcessType"/>
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
    "infaTask",
    "cmdFctProcess"
})
@XmlRootElement(name = "StartCustomerProcessRequest")
public class StartCustomerProcessRequest {

    @XmlElement(name = "INFATask", required = true)
    protected INFATask infaTask;
    @XmlElement(name = "CmdFctProcess", required = true)
    protected CmdFctProcessType cmdFctProcess;

    /**
     * Gets the value of the infaTask property.
     * 
     * @return
     *     possible object is
     *     {@link INFATask }
     *     
     */
    public INFATask getINFATask() {
        return infaTask;
    }

    /**
     * Sets the value of the infaTask property.
     * 
     * @param value
     *     allowed object is
     *     {@link INFATask }
     *     
     */
    public void setINFATask(INFATask value) {
        this.infaTask = value;
    }

    /**
     * Gets the value of the cmdFctProcess property.
     * 
     * @return
     *     possible object is
     *     {@link CmdFctProcessType }
     *     
     */
    public CmdFctProcessType getCmdFctProcess() {
        return cmdFctProcess;
    }

    /**
     * Sets the value of the cmdFctProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link CmdFctProcessType }
     *     
     */
    public void setCmdFctProcess(CmdFctProcessType value) {
        this.cmdFctProcess = value;
    }

}
