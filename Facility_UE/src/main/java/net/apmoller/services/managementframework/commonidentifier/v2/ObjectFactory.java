
package net.apmoller.services.managementframework.commonidentifier.v2;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.apmoller.services.managementframework.commonidentifier.v2 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.apmoller.services.managementframework.commonidentifier.v2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CommonIdentifierRequest }
     * 
     */
    public CommonIdentifierRequest createCommonIdentifierRequest() {
        return new CommonIdentifierRequest();
    }

    /**
     * Create an instance of {@link CommonIdentifierResponse }
     * 
     */
    public CommonIdentifierResponse createCommonIdentifierResponse() {
        return new CommonIdentifierResponse();
    }

    /**
     * Create an instance of {@link CommonIdentifierRequest.ApplicationRequester }
     * 
     */
    public CommonIdentifierRequest.ApplicationRequester createCommonIdentifierRequestApplicationRequester() {
        return new CommonIdentifierRequest.ApplicationRequester();
    }

    /**
     * Create an instance of {@link CommonIdentifierRequest.UserRequester }
     * 
     */
    public CommonIdentifierRequest.UserRequester createCommonIdentifierRequestUserRequester() {
        return new CommonIdentifierRequest.UserRequester();
    }

    /**
     * Create an instance of {@link CommonIdentifierResponse.ApplicationResponder }
     * 
     */
    public CommonIdentifierResponse.ApplicationResponder createCommonIdentifierResponseApplicationResponder() {
        return new CommonIdentifierResponse.ApplicationResponder();
    }

}
