
package net.apmoller.services.cmd.definitions;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "FacilityService", targetNamespace = "http://services.apmoller.net/cmd/definitions", wsdlLocation = "http://smdsws.apmoller.net/FacilityManagement/FacilityService?wsdl")
public class FacilityService
    extends Service
{

    private final static URL FACILITYSERVICE_WSDL_LOCATION;
    private final static WebServiceException FACILITYSERVICE_EXCEPTION;
    private final static QName FACILITYSERVICE_QNAME = new QName("http://services.apmoller.net/cmd/definitions", "FacilityService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://smdsws.apmoller.net/FacilityManagement/FacilityService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        FACILITYSERVICE_WSDL_LOCATION = url;
        FACILITYSERVICE_EXCEPTION = e;
    }

    public FacilityService() {
        super(__getWsdlLocation(), FACILITYSERVICE_QNAME);
    }

    public FacilityService(WebServiceFeature... features) {
        super(__getWsdlLocation(), FACILITYSERVICE_QNAME, features);
    }

    public FacilityService(URL wsdlLocation) {
        super(wsdlLocation, FACILITYSERVICE_QNAME);
    }

    public FacilityService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, FACILITYSERVICE_QNAME, features);
    }

    public FacilityService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public FacilityService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns UpsertFacilityService
     */
    @WebEndpoint(name = "FacilityServicePort")
    public UpsertFacilityService getFacilityServicePort() {
        return super.getPort(new QName("http://services.apmoller.net/cmd/definitions", "FacilityServicePort"), UpsertFacilityService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns UpsertFacilityService
     */
    @WebEndpoint(name = "FacilityServicePort")
    public UpsertFacilityService getFacilityServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://services.apmoller.net/cmd/definitions", "FacilityServicePort"), UpsertFacilityService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (FACILITYSERVICE_EXCEPTION!= null) {
            throw FACILITYSERVICE_EXCEPTION;
        }
        return FACILITYSERVICE_WSDL_LOCATION;
    }

}
