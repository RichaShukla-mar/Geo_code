package net.apmoller.services.cmd.searchfacility.dao;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class OfferingVO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	protected String			offCode;
	protected String			offName;
	protected ValidDatePeriodVO	validDatePeriodVO;

	public String getOffCode() {
		return offCode;
	}

	public void setOffCode(String offCode) {
		this.offCode = offCode;
	}

	public String getOffName() {
		return offName;
	}

	public void setOffName(String offName) {
		this.offName = offName;
	}

	public ValidDatePeriodVO getValidDatePeriodVO() {
		return validDatePeriodVO;
	}

	public void setValidDatePeriodVO(ValidDatePeriodVO validDatePeriodVO) {
		this.validDatePeriodVO = validDatePeriodVO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 19). // two randomly chosen prime numbers
		                                    // if deriving:
		                                    // appendSuper(super.hashCode()).
		        append(offName).append(offName).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof OfferingVO))
			return false;
		if (obj == this)
			return true;

		OfferingVO rhs = (OfferingVO) obj;
		return new EqualsBuilder().append(offName, rhs.offName).isEquals();
	}

}
