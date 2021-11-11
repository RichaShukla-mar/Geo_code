package net.apmoller.services.cmd.definitions;

import javax.resource.spi.IllegalStateException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sun.xml.ws.developer.ValidationErrorHandler;

public class ErrorHandler extends ValidationErrorHandler {

	private static Logger			LOGGER	= Logger.getLogger(ErrorHandler.class.getName());

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		// TODO Auto-generated method stub
		throw exception;
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		// TODO Auto-generated method stub
		LOGGER.fatal("Caught sax exception for data::["+exception.toString()+"]");
//		throw exception;
		if (exception.toString().contains("FacilityName'")) {
			return;
		} else {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		// TODO Auto-generated method stub
		throw new RuntimeException(exception);

	}

}
