package net.apmoller.services.cmd.searchfacility.dao;

import java.io.Serializable;

public class FacilityTypeVO implements Serializable , Comparable<FacilityTypeVO>{

	private static final long serialVersionUID = 1L;
	   
	    protected String facilityType;
	    protected ValidDatePeriodVO validDatePeriodVO;
		public String getFacilityType() {
			return facilityType;
		}
		public void setFacilityType(String facilityType) {
			this.facilityType = facilityType;
		}
		public ValidDatePeriodVO getValidDatePeriodVO() {
			return validDatePeriodVO;
		}
		public void setValidDatePeriodVO(ValidDatePeriodVO validDatePeriodVO) {
			this.validDatePeriodVO = validDatePeriodVO;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((facilityType == null) ? 0 : facilityType.hashCode());
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
			FacilityTypeVO other = (FacilityTypeVO) obj;
			if (facilityType == null) {
				if (other.facilityType != null)
					return false;
			} else if (!facilityType.equals(other.facilityType))
				return false;
			return true;
		}
		@Override
		public int compareTo(FacilityTypeVO o) {
			return this.facilityType.compareTo(o.facilityType);
		}   
	    
}
