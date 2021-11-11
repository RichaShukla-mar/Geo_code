package net.apmoller.services.cmd.searchfacility.dao;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface SearchDuplicateFacilityDAORemote {

	public List<SearchDuplicateFacilityVO> performSearchMatchRequest(
			SearchDuplicateFacilityVO parameters) throws SQLException;

}
