package net.apmoller.maersk.cmd.geo.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.siperian.sif.message.Field;
import com.siperian.sif.message.Record;
import com.siperian.sif.message.mrm.CleansePutRequest;

public class GEOUtils {

	/** The Constant LOGGER. */
	private static final Logger		LOGGER				= org.apache.log4j.Logger.getLogger(GEOUtils.class.getName());

	/** The Constant serialVersionUID. */
	private static final long		serialVersionUID	= 1L;



	public GEOUtils() {

	}


	protected String getSequenceValue(String sequenceName, Connection con, String schemaName, String customerCodeCreateScenario) throws SQLException, IOException {
		String query = null;
		String nextVal = null;
		// Properties properties = initProperties();
		// String schemaName = properties.getProperty("SEQUENCE_SCHEMA_NAME");

		StringBuilder sb = null;
		try (Statement stmt = con.createStatement();) {
			// if (StringUtils.isNotBlank(customerCodeCreateScenario)) {
			// query = "select " + schemaName + "." + sequenceName + ".CURRVAL
			// from DUAL";
			// LOGGER.info("###Returning CURRVAL value from sequence###");
			// } else {
			query = "select " + schemaName + "." + sequenceName + ".NEXTVAL from DUAL";
			LOGGER.info("###Returning NEXT value from sequence###");
			// }

			try (ResultSet rs = stmt.executeQuery(query);) {

				while (rs.next()) {

					// if (StringUtils.isNotBlank(customerCodeCreateScenario)) {
					// nextVal = rs.getString("CURRVAL");
					// } else {
					nextVal = rs.getString("NEXTVAL");
					// }

				}
				sb = new StringBuilder();
				if (null != nextVal) {
					for (int toPrepend = 8 - nextVal.length(); toPrepend > 0; toPrepend--) {
						sb.append('0');
					}
					sb.append(nextVal);
					nextVal = sb.toString();
				}
			}

		} catch (Exception e) {
			LOGGER.fatal(e.getLocalizedMessage(), e);
			throw e;
		}
		return nextVal;

	}

	protected String getCurrSequenceValue(String sequenceName, Connection con, String schemaName, String customerCodeCreateScenario) throws SQLException, IOException {
		String query = null;
		String nextVal = null;
		// Properties properties = initProperties();
		// String schemaName = properties.getProperty("SEQUENCE_SCHEMA_NAME");

		StringBuilder sb = null;
		try (Statement stmt = con.createStatement();) {
			// if (StringUtils.isNotBlank(customerCodeCreateScenario)) {
			// query = "select " + schemaName + "." + sequenceName + ".CURRVAL
			// from DUAL";
			// LOGGER.info("###Returning CURRVAL value from sequence###");
			// } else {
			query = "select " + schemaName + "." + sequenceName + ".CURRVAL from DUAL";
			LOGGER.info("###Returning CURRVAL value from sequence###");
			// }

			try (ResultSet rs = stmt.executeQuery(query);) {

				while (rs.next()) {

					// if (StringUtils.isNotBlank(customerCodeCreateScenario)) {
					// nextVal = rs.getString("CURRVAL");
					// } else {
					nextVal = rs.getString("CURRVAL");
					// }

				}
				sb = new StringBuilder();
				if (null != nextVal) {
					for (int toPrepend = 8 - nextVal.length(); toPrepend > 0; toPrepend--) {
						sb.append('0');
					}
					sb.append(nextVal);
					nextVal = sb.toString();
				}
			}

		} catch (Exception e) {
			LOGGER.fatal(e.getLocalizedMessage(), e);
			throw e;
		}
		return nextVal;

	}


	/**
	 * Gets the two digit country code.
	 *
	 * @param countryName
	 *            the country name
	 * @return the two digit country code
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected String getTwoDigitCountryCode(String countryName, Connection con) throws SQLException {
		String query = null;
		String countryCode = null;

		try (Statement stmt = con.createStatement();) {

			query = "select x.code " + "from c_alt_code x,  c_gda_dfnd_area y, c_typ_type z " + "where x.GDA_DFND_AREA_ROWID = y.ROWID_OBJECT " + "and y.TYP_TYPE_CD = 'GDA.COUNTRY' " + "and z.code = 'ALT_CODE.RKST' "
			        + "and x.TYP_TYPE_ROWID = z.rowid_object " + "and lower(y.name) = lower('" + countryName + "')";
			try (ResultSet rs = stmt.executeQuery(query);) {
				while (rs.next()) {
					countryCode = rs.getString("CODE");
				}
			}
		} catch (SQLException e) {
			LOGGER.fatal(e.getLocalizedMessage(), e);
			throw e;
		}
		return countryCode;
	}

	/**
	 * Gets the three digit country code.
	 *
	 * @param country
	 *            the country
	 * @return the three digit country code
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected String getThreeDigitCountryCode(String country, Connection con) throws SQLException {
		ResultSet rs = null;
		String query = null;
		Statement stmt = null;
		String countryCode = null;

		try {

			stmt = con.createStatement();

			if (country.length() > 2) {
				query = "select x.code " + "from c_alt_code x,  c_gda_dfnd_area y, c_typ_type z " + "where x.GDA_DFND_AREA_ROWID = y.ROWID_OBJECT " + "and y.TYP_TYPE_CD = 'GDA.COUNTRY' " + "and z.code = 'ALT_CODE.RKTS' "
				        + "and x.TYP_TYPE_ROWID = z.rowid_object " + "and lower(y.name) = lower('" + country + "')";
			} else {
				query = "select x.code " + "from c_alt_code x,  c_gda_dfnd_area y, c_typ_type z " + "where x.GDA_DFND_AREA_ROWID = y.ROWID_OBJECT " + "and y.TYP_TYPE_CD = 'GDA.COUNTRY' " + "and z.code = 'ALT_CODE.RKTS' "
				        + "and x.TYP_TYPE_ROWID = z.rowid_object " + "and y.name = (select y.name " + "from c_alt_code x,  c_gda_dfnd_area y, c_typ_type z " + "where x.GDA_DFND_AREA_ROWID = y.ROWID_OBJECT " + "and y.TYP_TYPE_CD = 'GDA.COUNTRY' "
				        + "and z.code = 'ALT_CODE.RKST' " + "and x.TYP_TYPE_ROWID = z.rowid_object AND Y.ACTIVE_FLAG = 'Y'" + "and x.code = '" + country + "')";
			}
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				countryCode = rs.getString("CODE");
			}
		} catch (Exception e) {
			LOGGER.fatal(e.getLocalizedMessage(), e);
			throw e;
		} finally {

			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}

		}
		return countryCode;
	}

	/**
	 * Gets the database date.
	 *
	 * @return the database date
	 * @throws SQLException
	 *             the SQL exception
	 */
	public Date getDatabaseDate(Connection con) throws SQLException {
		Date currDate = null;
		String sqlQuery = "SELECT CURRENT_TIMESTAMP FROM DUAL";
		Long databaselongTime = null;
		Long systemTime = null;
		Long appserverNewDateTime = new java.util.Date().getTime();
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery);) {

			while (rs.next()) {
				databaselongTime = rs.getTimestamp("CURRENT_TIMESTAMP").getTime();
				// currDate = new Date(databaselongTime - futureTimeAdjust);
				currDate = new Date(databaselongTime);
				systemTime = System.currentTimeMillis();
			}
		}
		LOGGER.info("TIME LOG:: Database aboslute time ms::" + databaselongTime + " appserver time ::" + systemTime + " new java.util datetime::" + appserverNewDateTime + " time difference systime-dbtime::" + (systemTime - databaselongTime));

		return currDate;
	}

	public String getTypTypeRowID(Connection con) throws SQLException {
		String rowid = null;

		String sqlQuery = "select ROWID_OBJECT from MDM_INFM_SMDS.C_TYP_TYPE where TYP_MSTR_TYPE_CD='ALT_CODE' and CODE='ALT_CODE.POSTCODE' and name='POSTAL CODE'";
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery);) {

			while (rs.next()) {
				rowid = rs.getString("ROWID_OBJECT");
			}
		}

		return rowid;
	}

	public String getWrkArndTypTypeRowID(Connection con,String altCodeType) throws SQLException {
		String rowid = null;

		String sqlQuery = "select ROWID_OBJECT from MDM_INFM_SMDS.C_TYP_TYPE where TYP_MSTR_TYPE_CD='WRKRND_RSN' and CODE='"+altCodeType+"'";
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery);) {

			while (rs.next()) {
				rowid = rs.getString("ROWID_OBJECT");
			}
		}

		return rowid;
	}

	public String getTypTypeRowID(Connection con,String altCodeType,String altName) throws SQLException {
		String rowid = null;

		String sqlQuery = "select ROWID_OBJECT from MDM_INFM_SMDS.C_TYP_TYPE where TYP_MSTR_TYPE_CD='ALT_CODE' and CODE='"+altCodeType+"' and name='"+altName+"'";
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery);) {

			while (rs.next()) {
				rowid = rs.getString("ROWID_OBJECT");
			}
		}

		return rowid;
	}
	
	public String getTypTypeid(Connection connection) throws SQLException{
		String id = null;
		String sqlQuery = "SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE  HUB_STATE_IND = 1 AND TYP_MSTR_TYPE_CD = ('ALT_CODE') AND CODE = ('ALT_CODE.GEOID')";
		try(PreparedStatement stmt = connection.prepareStatement(sqlQuery);) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {   
				id = rs.getString("ROWID_OBJECT");      
			}
		}catch(Exception e)
		{
			//LOGGER.info(e);	
		}
			return id; 
		}

	public String getAltCodeRowID(String typerowid,String gdarowid,Connection con) throws SQLException {
		String rowid = null;

		String sqlQuery = "select ROWID_OBJECT from MDM_INFM_SMDS.C_ALT_CODE where GDA_DFND_AREA_ROWID='"+gdarowid+"' and TYP_TYPE_ROWID='"+typerowid+"'";
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery);) {

			while (rs.next()) {
				rowid = rs.getString("ROWID_OBJECT");
			}
		}

		return rowid;
	}

	
	public String getCountryRowID(Connection con,String name) throws SQLException {
		String rowid = null;
		
		String sqlQuery = "select ROWID_OBJECT from MDM_INFM_SMDS.C_GDA_DFND_AREA where TYP_TYPE_CD='GDA.COUNTRY' and NAME='"+name+"'";
		
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery);) {

			while (rs.next()) {
				rowid = rs.getString("ROWID_OBJECT");
			}
		}

		return rowid;
	}

	public String getPostalCodeRowID(Connection con,String name) throws SQLException {
		// Long futureTimeAdjust = 0l;
		// if (null != CreateCustomerEAO.environmentProperties && null !=
		// CreateCustomerEAO.environmentProperties.getProperty("future.time.adjust"))
		// {
		// futureTimeAdjust =
		// Long.valueOf(CreateCustomerEAO.environmentProperties.getProperty("future.time.adjust"));
		// }
		String rowid = null;
		String sqlQuery = "select ROWID_OBJECT from MDM_INFM_SMDS.C_GDA_DFND_AREA where TYP_TYPE_CD='GDA.POSTAL_CODE' and NAME='"+name+"'";
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery);) {

			while (rs.next()) {
				rowid = rs.getString("ROWID_OBJECT");
			}
		}

		return rowid;
	}

	public String getCityowID(Connection con,String GdaRowId) throws SQLException {
		// Long futureTimeAdjust = 0l;
		// if (null != CreateCustomerEAO.environmentProperties && null !=
		// CreateCustomerEAO.environmentProperties.getProperty("future.time.adjust"))
		// {
		// futureTimeAdjust =
		// Long.valueOf(CreateCustomerEAO.environmentProperties.getProperty("future.time.adjust"));
		// }
		String rowid = null;
		String sqlQuery = "select ROWID_OBJECT from MDM_INFM_SMDS.C_GDA_CITY where GDA_DFND_AREA_ROWID='"+GdaRowId+"'";
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery);) {

			while (rs.next()) {
				rowid = rs.getString("ROWID_OBJECT");
			}
		}

		return rowid;
	}
	
	public String getInteractionId(Connection conn) throws SQLException {
		String interactionId = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT C_REPOS_ID_SEQ.NEXTVAL INTERACTION_ID FROM DUAL");
		try (PreparedStatement stmt = conn.prepareStatement(sb.toString()); ResultSet rs = stmt.executeQuery();) {

			while (rs.next()) {
				interactionId = rs.getString("INTERACTION_ID");
			}
		}
		return interactionId;
	}

	public String getgeoRowid(String gdarowid,Connection con) throws SQLException {
		String rowid = null;
		String sqlQuery = "select ROWID_OBJECT from MDM_INFM_SMDS.C_GDA_DFND_AREA where LTRIM(RTRIM(ROWID_OBJECT))=LTRIM(RTRIM('"+gdarowid+"'))";
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery);) {

			while (rs.next()) {
				rowid = rs.getString("ROWID_OBJECT");
			}
		}catch(Exception exp){
			exp.printStackTrace();
		}

		return rowid;
	}

	public String getParentRowID(String gdarowid,Connection con){

		String rowid = null;
		String sqlQuery = "select ROWID_OBJECT from MDM_INFM_SMDS.C_GDA_DFND_AREA_REL where GDA_DFND_AREA_CHLD_ROWID='"+gdarowid+"' and HUB_STATE_IND=1";
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery);) {

			while (rs.next()) {
				rowid = rs.getString("ROWID_OBJECT");
			}
		}catch(Exception exp){
			exp.printStackTrace();
		}

		return rowid;
	}

	public boolean isParentChanged(String gdarowid,String parentrowid,Connection con){

		boolean isChanged = true;
		String sqlQuery = "select count(ROWID_OBJECT) from MDM_INFM_SMDS.C_GDA_DFND_AREA_REL where GDA_DFND_AREA_PRNT_ROWID='"+parentrowid+"' and GDA_DFND_AREA_CHLD_ROWID='"+gdarowid+"' and HUB_STATE_IND=1";
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery);) {

			rs.next(); 
			if(rs.getInt(1) == 0){
				isChanged = true;
			}else{
				isChanged = false;
			}
			
		}catch(Exception exp){
			exp.printStackTrace();
		}

		return isChanged;
	}



	protected void logRequest(CleansePutRequest req) {
		// TODO Auto-generated method stub
		Record rec = req.getRecord();
		for (Field field : (List<Field>) rec.getFields()) {
			LOGGER.info("Request element::" + field.getName() + "------" + field.getStringValue());
		}
	}

}
