package net.apmoller.services.cmd.searchfacility.dao;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;

@Local
public interface SearchDuplicateFacilityDAOLocal {

	public List<SearchDuplicateFacilityVO> performSearchMatchRequest(
			SearchDuplicateFacilityVO parameters) throws SQLException;

}
