package net.apmoller.services.cmd.searchfacility.dao;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class TransportModesVO implements Serializable , Comparable<TransportModesVO>{

	private static final long serialVersionUID = 1L;

	    protected String transportMode;
	    protected ValidDatePeriodVO validDatePeriodVO;
		public String getTransportMode() {
			return transportMode;
		}
		public void setTransportMode(String transportMode) {
			this.transportMode = transportMode;
		}
		public ValidDatePeriodVO getValidDatePeriodVO() {
			return validDatePeriodVO;
		}
		public void setValidDatePeriodVO(ValidDatePeriodVO validDatePeriodVO) {
			this.validDatePeriodVO = validDatePeriodVO;
		}
//		@Override
//		public int hashCode() {
//			final int prime = 31;
//			int result = 1;
//			result = prime * result + ((transportMode == null) ? 0 : transportMode.hashCode());
//			return result;
//		}
//		@Override
//		public boolean equals(Object obj) {
//			if (this == obj)
//				return true;
//			if (obj == null)
//				return false;
//			if (getClass() != obj.getClass())
//				return false;
//			TransportModesVO other = (TransportModesVO) obj;
//			if (transportMode == null) {
//				if (other.transportMode != null)
//					return false;
//			} else if (!transportMode.equals(other.transportMode))
//				return false;
//			return true;
//		}
		@Override
		public int compareTo(TransportModesVO o) {
			return this.transportMode.compareTo(o.transportMode);
		}


		@Override
		public int hashCode() {
			return new HashCodeBuilder(17, 19).append(transportMode).toHashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof TransportModesVO))
				return false;
			if (obj == this)
				return true;

			TransportModesVO rhs = (TransportModesVO) obj;
			return new EqualsBuilder().append(transportMode, rhs.transportMode).isEquals();
		}
}
