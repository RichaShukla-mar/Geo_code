package net.apmoller.services.cmd.searchfacility.dao;

import java.io.Serializable;

import javax.xml.datatype.XMLGregorianCalendar;

public class ValidDatePeriodVO implements Serializable{

		private static final long serialVersionUID = 1L;
	 	protected XMLGregorianCalendar validFrom;
	    protected XMLGregorianCalendar validTo;
	    
		public XMLGregorianCalendar getValidFrom() {
			return validFrom;
		}
		public void setValidFrom(XMLGregorianCalendar validFrom) {
			this.validFrom = validFrom;
		}
		public XMLGregorianCalendar getValidTo() {
			return validTo;
		}
		public void setValidTo(XMLGregorianCalendar validTo) {
			this.validTo = validTo;
		}
}
