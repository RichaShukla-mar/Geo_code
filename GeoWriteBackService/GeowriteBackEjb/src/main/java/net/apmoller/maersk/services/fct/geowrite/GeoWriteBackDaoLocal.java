package net.apmoller.maersk.services.fct.geowrite;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.ejb.Local;
import javax.xml.datatype.DatatypeConfigurationException;

import com.siperian.sif.message.mrm.PutResponse;

import net.apmoller.services.cmd.definitions.RetrieveFacilityFault;


@Local
public interface GeoWriteBackDaoLocal {
	public Boolean geoWriteBack(String geoid,Boolean isGeoCode)throws MalformedURLException, RetrieveFacilityFault,SQLException,ParseException;
}
