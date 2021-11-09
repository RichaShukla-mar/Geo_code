
package net.apmoller.services.cmd.definitions;

import javax.xml.ws.WebFault;
import net.apmoller.services.cmd.schemas.ServiceFaultType;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "SdnServiceFault", targetNamespace = "http://services.apmoller.net/cmd/schemas")
public class DeletePartyMatchFault
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ServiceFaultType faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public DeletePartyMatchFault(String message, ServiceFaultType faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public DeletePartyMatchFault(String message, ServiceFaultType faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: net.apmoller.services.cmd.schemas.ServiceFaultType
     */
    public ServiceFaultType getFaultInfo() {
        return faultInfo;
    }

}