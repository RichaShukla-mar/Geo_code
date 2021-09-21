package net.apmoller.maersk.services.fct.geowrite;

import java.net.MalformedURLException;
import java.sql.SQLException;

import javax.ejb.Remote;


import net.apmoller.services.cmd.definitions.RetrieveFacilityFault;
import java.text.ParseException;

@Remote
public interface GeoWriteBackDaoRemote {
	public Boolean geoWriteBack(String geoid,Boolean isGeoCode) throws MalformedURLException, RetrieveFacilityFault,SQLException,ParseException;
}
