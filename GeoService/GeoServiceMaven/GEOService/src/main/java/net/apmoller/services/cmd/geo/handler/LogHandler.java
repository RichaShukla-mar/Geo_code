package net.apmoller.services.cmd.geo.handler;

import java.io.IOException;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHandler
		implements SOAPHandler<SOAPMessageContext>
{
	private static final Logger logger = LogManager.getLogger(LogHandler.class);

	public boolean handleMessage(SOAPMessageContext context)
	{
		Boolean output = (Boolean)context.get("javax.xml.ws.handler.message.outbound");
		if (!output.booleanValue()) {
			try
			{
				SOAPMessage message = context.getMessage();
				message.writeTo(System.out);
				logger.info(" Geo Mu Input Message : " + message.getSOAPBody().getTextContent());
			}
			catch (SOAPException|IOException e)
			{
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean handleFault(SOAPMessageContext context)
	{
		return false;
	}

	public void close(MessageContext context) {}

	public Set<QName> getHeaders()
	{
		return null;
	}
}
