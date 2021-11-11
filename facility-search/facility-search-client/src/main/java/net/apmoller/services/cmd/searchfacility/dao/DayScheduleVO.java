package net.apmoller.services.cmd.searchfacility.dao;

import java.io.Serializable;

public class DayScheduleVO implements Serializable{

	private static final long serialVersionUID = 1L;
	   
	    protected String officeOpeningHours;
	    protected String officeClosingHours;
		
		public String getOfficeOpeningHours() {  
			return officeOpeningHours;
		}

		public void setOfficeOpeningHours(String officeOpeningHours) {
			this.officeOpeningHours = officeOpeningHours;
		}

		public String getOfficeClosingHours() {
			return officeClosingHours;
		}

		public void setOfficeClosingHours(String officeClosingHours) {
			this.officeClosingHours = officeClosingHours;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((officeClosingHours == null) ? 0 : officeClosingHours.hashCode());
			result = prime * result + ((officeOpeningHours == null) ? 0 : officeOpeningHours.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DayScheduleVO other = (DayScheduleVO) obj;
			if (officeClosingHours == null) {
				if (other.officeClosingHours != null)
					return false;
			} else if (!officeClosingHours.equals(other.officeClosingHours))
				return false;
			if (officeOpeningHours == null) {
				if (other.officeOpeningHours != null)
					return false;
			} else if (!officeOpeningHours.equals(other.officeOpeningHours))
				return false;
			return true;
		}

	    
}
