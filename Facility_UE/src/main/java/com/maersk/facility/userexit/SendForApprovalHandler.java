//=====================================================================
// project:   Informatica MDM
//---------------------------------------------------------------------
// copyright: Informatica Corp. (c) 2003-2010.  All Rights Reserved.
//=====================================================================

package com.maersk.facility.userexit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.validator.UrlValidator;
import org.apache.log4j.Logger;

import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.bdd.userexits.operations.ISendForApproveOperationPlugin;
import com.siperian.bdd.userexits.operations.OperationContext;
import com.siperian.bdd.userexits.operations.OperationExecutionError;
import com.siperian.bdd.userexits.operations.OperationResult;
import com.siperian.bdd.userexits.operations.OperationType;
import com.siperian.sif.client.SiperianClient;
import com.siperian.sif.message.Field;
import com.siperian.sif.message.Password;
import com.siperian.sif.message.Record;
import com.siperian.sif.message.SiperianRequest;
import com.siperian.sif.message.mrm.CleanseRequest;
import com.siperian.sif.message.mrm.CleanseResponse;

public class SendForApprovalHandler extends SaveHandler implements ISendForApproveOperationPlugin {

	private static final Logger log = Logger.getLogger(SendForApprovalHandler.class.getName());
	Connection conn4 = null;
	String test = "Inititated";

	public OperationType getOperationType() {

		return OperationType.SEND_FOR_APPROVAL_OPERATION;

	}

	public OperationResult beforeEverything(BDDObject arg0) {

		log.info("Inside BeforeEverything of SendForApproval ::");

		String mdmORSid = getOperationContext().getValue(OperationContext.ORS_ID).toString();
		Connection conn2 = getDatabaseConnection(mdmORSid);

		try {

			if ("Services".equalsIgnoreCase(arg0.getObjectName().toString())
					|| "Services_Group".equalsIgnoreCase(arg0.getObjectName().toString())) {
				return new OperationResult(new OperationExecutionError("SIP-500158", getLocalizationGate()));
			} else if (arg0.getObjectName().equals("Operational")
					&& !"P".equalsIgnoreCase(arg0.getValue("C_FCT_FACILITY|STATUS_CD").toString())) {
				return new OperationResult(new OperationExecutionError("SIP-500159", getLocalizationGate()));
			} else if (arg0.getObjectName().equals("Operational")
					&& "P".equalsIgnoreCase(arg0.getValue("C_FCT_FACILITY|STATUS_CD").toString())) {
				String OldStatus = (String) arg0.getOldValue("C_FCT_FACILITY|STATUS_CD");
				log.info("OldStatus is ::" + OldStatus);
				if (OldStatus != null && !"P".equalsIgnoreCase(OldStatus)) {
					log.info("Throwingg error");
					return new OperationResult(new OperationExecutionError("SIP-500160", getLocalizationGate()));
				}

				else {

					List<BDDObject> facilityDetails = arg0.getChildren("Facility_Details");
					log.info("Inside facility details count");
					if (getObjectCount(facilityDetails) > 1 || facilityDetails.size() > 1) {
						log.info("facility details more than one SendForApproval");
						return new OperationResult(
								new OperationExecutionError("SIP-500114", this.getLocalizationGate()));
					}
					List<BDDObject> facilityAddress = arg0.getChildren("Facility_Address");
					log.info("Inside facility address count");
					if (getObjectCount(facilityAddress) > 1 || facilityAddress.size() > 1) {
						log.info("facility address more than one");
						return new OperationResult(
								new OperationExecutionError("SIP-500115", this.getLocalizationGate()));
					}

					List<BDDObject> AlternateCodes = arg0.getChildren("Alternate_Codes");
					log.info((Object) "Inside Alternate Codes count");

					if ("Operational".equalsIgnoreCase(arg0.getObjectName().toString())) {
						


						List<BDDObject> facilityAddres = arg0.getChildren("Facility_Address");
						log.info("Inside AdressDoctorCall");

						if (this.getObjectCount(facilityAddres) > 0 && facilityAddres.size() > 0) {
							for (BDDObject facility_Address : facilityAddres) {
								String ADDR_DCTR_IND = (String) facility_Address
										.getValue("C_FCT_ADDR_REL|ADDR_DCTR_IND");
								log.info("ADDR_DCTR_IND IS::" + ADDR_DCTR_IND);
								String AddressDctorCheck_Output = null;

								if ("Do not Check".equalsIgnoreCase(ADDR_DCTR_IND)) {
									log.info("Do not Check");
									return new OperationResult(
											new OperationExecutionError("SIP-500154", this.getLocalizationGate()));
								}

								else {

									log.info("Check");
									String STREET = (String) facility_Address.getValue("C_CTM_PSTL_ADDR|STREET");
									log.info("Inside AdressDoctorCall 1");
									String HOUSE_NUM = (String) facility_Address.getValue("C_CTM_PSTL_ADDR|HOUSE_NUM");
									log.info("Inside AdressDoctorCall 2");
									String CITY = facility_Address.getValue("C_CTM_PSTL_ADDR|CITY_ROWID").toString();
									log.info("Inside AdressDoctorCall 3");
									String PSTCD = (String) facility_Address.getValue("C_CTM_PSTL_ADDR|PSTCD");
									log.info("Inside AdressDoctorCall 4");
									String TRTY_ROWID = (String) facility_Address
											.getValue("C_CTM_PSTL_ADDR|TRTY_ROWID");
									log.info("Inside AdressDoctorCall 5");
									String CTRY_ROWID = facility_Address.getValue("C_CTM_PSTL_ADDR|CTRY_ROWID")
											.toString();
									log.info("Inside AdressDoctorCall 6");
									String ADDR_LN_2 = (String) facility_Address.getValue("C_CTM_PSTL_ADDR|ADDR_LN_2");
									log.info("Inside AdressDoctorCall 7");
									String ADDR_LN_3 = (String) facility_Address.getValue("C_CTM_PSTL_ADDR|ADDR_LN_3");
									log.info("Inside AdressDoctorCall 8");
									String LAT_GEOSPTL = (String) facility_Address
											.getValue("C_CTM_PSTL_ADDR|LAT_GEOSPTL");
									log.info("Inside AdressDoctorCall 9");
									String LNG_GEOSPTL = (String) facility_Address
											.getValue("C_CTM_PSTL_ADDR|LNG_GEOSPTL");
									log.info("Inside AdressDoctorCall 10");

									AddressDctorCheck_Output = AddressDctorCheck(STREET, HOUSE_NUM, CITY, PSTCD,
											TRTY_ROWID, CTRY_ROWID, ADDR_LN_2, ADDR_LN_3, LAT_GEOSPTL, LNG_GEOSPTL,
											FetchCountryCode(conn2, CTRY_ROWID));
									log.info("AddressDctorCheck_Output isssss" + AddressDctorCheck_Output);

									if ("Country Not Available".equalsIgnoreCase(AddressDctorCheck_Output)) {
										return new OperationResult(
												new OperationExecutionError("SIP-500168", this.getLocalizationGate()));

									}

									if ("Region Not Available".equalsIgnoreCase(AddressDctorCheck_Output)) {
										return new OperationResult(
												new OperationExecutionError("SIP-500169", this.getLocalizationGate()));

									}

									if ("Check City_country".equalsIgnoreCase(AddressDctorCheck_Output)) {
										return new OperationResult(
												new OperationExecutionError("SIP-500151", this.getLocalizationGate()));

									}
									if ("BadAddress".equalsIgnoreCase(AddressDctorCheck_Output)) {
										return new OperationResult(
												new OperationExecutionError("SIP-500150", this.getLocalizationGate()));
									}
									if (AddressDctorCheck_Output.contains("CLEANSE_0")) {

										log.info("modified");
										test = AddressDctorCheck_Output;
										return new OperationResult(
												new OperationExecutionError("SIP-500152", this.getLocalizationGate()));
									} else {
										String STREET_OUTPUT = AddressDctorCheck_Output.substring(0,
												AddressDctorCheck_Output.indexOf("CLEANSE_1"));
										String HOUSE_NUM_OUTPUT = AddressDctorCheck_Output.substring(
												AddressDctorCheck_Output.indexOf("CLEANSE_1") + "CLEANSE_1".length(),
												AddressDctorCheck_Output.indexOf("CLEANSE_2"));
										String PSTCD_OUTPUT = AddressDctorCheck_Output.substring(
												AddressDctorCheck_Output.indexOf("CLEANSE_2") + "CLEANSE_2".length(),
												AddressDctorCheck_Output.indexOf("CLEANSE_3"));
										String ADDR_LN_2_OUTPUT = AddressDctorCheck_Output.substring(
												AddressDctorCheck_Output.indexOf("CLEANSE_3") + "CLEANSE_3".length(),
												AddressDctorCheck_Output.indexOf("CLEANSE_4"));
										String ADDR_LN_3_OUTPUT = AddressDctorCheck_Output.substring(
												AddressDctorCheck_Output.indexOf("CLEANSE_4") + "CLEANSE_4".length(),
												AddressDctorCheck_Output.indexOf("CLEANSE_5"));
										String LAT_GEOSPTL_OUTPUT = AddressDctorCheck_Output.substring(
												AddressDctorCheck_Output.indexOf("CLEANSE_5") + "CLEANSE_5".length(),
												AddressDctorCheck_Output.indexOf("CLEANSE_6"));
										String LNG_GEOSPTL_OUTPUT = AddressDctorCheck_Output.substring(
												AddressDctorCheck_Output.indexOf("CLEANSE_6") + "CLEANSE_6".length(),
												AddressDctorCheck_Output.indexOf("CLEANSE_7"));
										String Country_Rowid = AddressDctorCheck_Output.substring(
												AddressDctorCheck_Output.indexOf("CLEANSE_7") + "CLEANSE_7".length(),
												AddressDctorCheck_Output.indexOf("CLEANSE_8"));
										String Region_Rowid = AddressDctorCheck_Output.substring(
												AddressDctorCheck_Output.indexOf("CLEANSE_8") + "CLEANSE_8".length(),
												AddressDctorCheck_Output.indexOf("CLEANSE_9"));
										log.info("STREET_OUTPUT::" + STREET_OUTPUT);
										facility_Address.setValue("C_CTM_PSTL_ADDR|STREET", STREET_OUTPUT);
										log.info("HOUSE_NUM_OUTPUT::" + HOUSE_NUM_OUTPUT);
										facility_Address.setValue("C_CTM_PSTL_ADDR|HOUSE_NUM", HOUSE_NUM_OUTPUT);
										log.info("PSTCD_OUTPUT::" + PSTCD_OUTPUT);
										facility_Address.setValue("C_CTM_PSTL_ADDR|PSTCD", PSTCD_OUTPUT);
										log.info("ADDR_LN_2_OUTPUT::" + ADDR_LN_2_OUTPUT);
										facility_Address.setValue("C_CTM_PSTL_ADDR|ADDR_LN_2", ADDR_LN_2_OUTPUT);
										log.info("ADDR_LN_3_OUTPUT::" + ADDR_LN_3_OUTPUT);
										facility_Address.setValue("C_CTM_PSTL_ADDR|ADDR_LN_3", ADDR_LN_3_OUTPUT);
										log.info("LAT_GEOSPTL_OUTPUT::" + LAT_GEOSPTL_OUTPUT);
										facility_Address.setValue("C_CTM_PSTL_ADDR|LAT_GEOSPTL", LAT_GEOSPTL_OUTPUT);
										log.info("LNG_GEOSPTL_OUTPUT::" + LNG_GEOSPTL_OUTPUT);
										facility_Address.setValue("C_CTM_PSTL_ADDR|LNG_GEOSPTL", LNG_GEOSPTL_OUTPUT);
										log.info("Country::" + Country_Rowid);
										facility_Address.setValue("C_CTM_PSTL_ADDR|CTRY_ROWID", Country_Rowid);
										log.info("Region ::" + Region_Rowid);
										facility_Address.setValue("C_CTM_PSTL_ADDR|TRTY_ROWID", Region_Rowid);
										log.info("Setting has completed");

									}

								}

							}
						}

						String Fct_Rowid = arg0.getSystemValue("C_FCT_FACILITY|ROWID_OBJECT").toString();
						log.info("Facility Rowid Is ::" + Fct_Rowid);

						log.info("Count is " + getOffDetails(conn2, Fct_Rowid));

						if (getOffDetails(conn2, Fct_Rowid) > 0) {
							log.info("inside getOffDetails ::");

							return new OperationResult(
									new OperationExecutionError("SIP-500125", this.getLocalizationGate()));

						}

					}
					if ("Services".equalsIgnoreCase(arg0.getObjectName().toString())) {

						// String isActiveBefore = arg0.getOldValue("C_FCT_OFF|IS_ACTIVE_IND");
						// log.info("Is Active Ind before::" +isActiveBefore);

						String isActive = arg0.getValue("C_FCT_OFF|IS_ACTIVE_IND").toString();
						log.info("Is Active Ind ::" + isActive);

						if (null == arg0.getOldValue("C_FCT_OFF|IS_ACTIVE_IND") && "N".equalsIgnoreCase(isActive)) {
							log.info("Is Active Ind is being created inactive");
							return new OperationResult(
									new OperationExecutionError("SIP-500116", this.getLocalizationGate()));

						}

						if (null != arg0.getOldValue("C_FCT_OFF|IS_ACTIVE_IND") && "N".equalsIgnoreCase(isActive)) {
							log.info("Is Active Ind is being updated to inactive");
							return new OperationResult(
									new OperationExecutionError("SIP-500105", this.getLocalizationGate()));
						}

						/*
						 * if( "Y".equalsIgnoreCase(isActiveBefore) && "N".equalsIgnoreCase(isActive)){
						 * log.info("Is Active Ind is being updated inactive" ); return new
						 * OperationResult(new OperationExecutionError("SIP-500117",
						 * this.getLocalizationGate()));
						 * 
						 * }
						 */
					}

					log.info("Everything is ok");
				}

			}

		} catch (Exception e) {
			log.info(e);
		} finally {
			log.info("We are inside finally method");
			try {
				conn2.close();
				log.info((Object) "--------Connection closed BeforeEverything");
			} catch (Exception e) {
			}
			{

			}
		}
		return OperationResult.OK;

	}

	public Connection getDatabaseConnection(String mdmORSid) {

		try {
			String dataSourceName = "jdbc/siperian-" + mdmORSid.toLowerCase() + "-ds";
			log.info("The MDM DataSource name is ::" + dataSourceName);
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(dataSourceName);
			conn4 = ds.getConnection();
			log.info("getConnection end - success");
			return conn4;

		} catch (Exception e) {
			log.info("getConnection Exception ::" + e.getLocalizedMessage());
			return null;
		}

	}

	public boolean GetCountOff(List<BDDObject> Service_Details) {

		if (getObjectCount(Service_Details) > 0 && Service_Details.size() > 0) {

			List<String> NewList = new ArrayList<String>();

			for (BDDObject Service_Detail : Service_Details) {
				String Off_Name = Service_Detail.getValue("C_FCT_OFF|OFF_NAME").toString();
				NewList.add(Off_Name);
			}
			Set<String> set = new HashSet<String>(NewList);
			if (set.size() < NewList.size())
				return true;
		}
		return false;
	}

	public boolean GetCountTrnsp(List<BDDObject> Trnsp_Details) {

		if (getObjectCount(Trnsp_Details) > 0 && Trnsp_Details.size() > 0) {

			List<String> NewList = new ArrayList<String>();

			for (BDDObject Trnsp_Detail : Trnsp_Details) {
				String Trnsp_Name = Trnsp_Detail.getValue("C_FCT_TRNSP_MODE|TRNSP_NAME").toString();

				NewList.add(Trnsp_Name);
			}
			Set<String> set = new HashSet<String>(NewList);
			log.info("NewList.size()" + set.size());
			log.info("set.size()" + Trnsp_Details.size());
			if (set.size() < NewList.size())
				return true;
		}
		return false;
	}

	public boolean GetCountType(List<BDDObject> Facility_Details) {

		if (getObjectCount(Facility_Details) > 0 && Facility_Details.size() > 0) {
			for (BDDObject Facility_Detail : Facility_Details) {

				List<BDDObject> Facility_types = Facility_Detail.getChildren("Facility_Type");

				if (getObjectCount(Facility_types) > 0 && Facility_types.size() > 0) {

					List<String> NewList = new ArrayList<String>();

					for (BDDObject Facility_type : Facility_types) {

						String Facility_type_name = (String) Facility_type.getValue("C_TYP_TYPE|CODE");
						NewList.add(Facility_type_name);

					}
					Set<String> set = new HashSet<String>(NewList);
					log.info("NewList.size()" + set.size());
					log.info("set.size()" + NewList.size());
					if (set.size() < NewList.size())
						return true;

				}

			}
		}
		return false;
	}

	public String getRKSTRowid(Connection conn) {

		String partyRoleName = null;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.RKST' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");

		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT");
			}

			return partyRoleName;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("getTypTypeRowid Exception ::" + e.getLocalizedMessage());
			return "NOT AVAILABLE";
		}

	}

	public String getRKTSRowid(Connection conn) {

		String partyRoleName = null;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.RKTS' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");

		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT");
			}

			return partyRoleName;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("getTypTypeRowid Exception ::" + e.getLocalizedMessage());
			return "NOT AVAILABLE";
		}

	}

	//Richa Change Start
	
		public String getBICRowid(Connection conn) {
			String partyRoleName = null;
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
			sb.append("FROM ");
			sb.append("( ");
			sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
			sb.append("FROM ");
			sb.append("( ");
			sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
			sb.append("FROM    C_TYP_TYPE ");
			sb.append("WHERE   HUB_STATE_IND = 1 ");
			sb.append("AND     CODE = 'ALT_CODE.BIC' ");
			sb.append("UNION ALL ");
			sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
			sb.append(") ");
			sb.append(") ");
			sb.append("WHERE D_RANK = 1");
			try {
				PreparedStatement stmt = null;
				stmt = conn.prepareStatement(sb.toString());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					partyRoleName = rs.getString("ROWID_OBJECT");
					log.info("Get BIC rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
				}
				return partyRoleName;
			} catch (Exception e) {
				log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
				return "NOT AVAILABLE";
			}
		}
		
		public String getSMDGRowid(Connection conn) {
			String partyRoleName = null;
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
			sb.append("FROM ");
			sb.append("( ");
			sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
			sb.append("FROM ");
			sb.append("( ");
			sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
			sb.append("FROM    C_TYP_TYPE ");
			sb.append("WHERE   HUB_STATE_IND = 1 ");
			sb.append("AND     CODE = 'ALT_CODE.SMDG' ");
			sb.append("UNION ALL ");
			sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
			sb.append(") ");
			sb.append(") ");
			sb.append("WHERE D_RANK = 1");
			try {
				PreparedStatement stmt = null;
				stmt = conn.prepareStatement(sb.toString());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					partyRoleName = rs.getString("ROWID_OBJECT");
				}
				return partyRoleName;
			} catch (Exception e) {
				log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
				return "NOT AVAILABLE";
			}
		}
		
		
		private int getUncodeCount(List<BDDObject> objects, Connection conn) {
			int count = 0;
			if (objects != null) {
				for (BDDObject object : objects) {
					String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
					log.info("getUncodeCount loop");
					if (AltCodeValue.equalsIgnoreCase(this.getUncodeRowid(conn))){
					count++;
					}
					log.info("getUncodeCount after count loop1 " + AltCodeValue.equalsIgnoreCase(this.getUncodeRowid(conn)) +" " +  count );
				}
			}
			return count;
		}
	    
		private int getUncodeLookupCount(List<BDDObject> objects, Connection conn) {
			int count = 0;
			if (objects != null) {
				for (BDDObject object : objects) {
					String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
					log.info("getUncodeCount1 loop");
					if (AltCodeValue.equalsIgnoreCase(getUncodeLookupRowid(conn)))
					{
					++count;
					}
					log.info("getUncodeLookupCount after count loop1 " + AltCodeValue.equalsIgnoreCase(this.getUncodeLookupRowid(conn)) +" " +  count );
				}
			}
			return count;
		}
	    
		
		private int getUncodeReturnCount(List<BDDObject> objects, Connection conn) {
			int count = 0;
			if (objects != null) {
				for (BDDObject object : objects) {
					String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
					log.info("getUncodeCount2 loop");
					if (AltCodeValue.equalsIgnoreCase(getUncodeReturnRowid(conn))){
						
					count++;
					}
					log.info("getUncodeCount after count loop2 " + AltCodeValue.equalsIgnoreCase(this.getUncodeReturnRowid(conn)) +" " + count );
				}
			}
			return count;
		}
	    
		private int getScheduleKCount(List<BDDObject> objects, Connection conn) {
			int count = 0;
			if (objects != null) {
				for (BDDObject object : objects) {
					String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
					log.info("getScheduleDCount2 loop");
					if (AltCodeValue.equalsIgnoreCase(getScheduleKRowid(conn))){
						
					count++;
					}
					log.info("getUncodeCount after count loop2 " + AltCodeValue.equalsIgnoreCase(this.getUncodeReturnRowid(conn)) +" " + count );
				}
			}
			return count;
		}
		
		private int getScheduleDCount(List<BDDObject> objects, Connection conn) {
			int count = 0;
			if (objects != null) {
				for (BDDObject object : objects) {
					String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
					log.info("getSchedeuleKCount2 loop");
					if (AltCodeValue.equalsIgnoreCase(getScheduleDRowid(conn))){
						
					count++;
					}
					log.info("getUncodeCount after count loop2 " + AltCodeValue.equalsIgnoreCase(this.getUncodeReturnRowid(conn)) +" " + count );
				}
			}
			return count;
		}
	    
	    
	    //########
	    
	    public String getUncodeRowid(Connection conn) {
			String partyRoleName = null;
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
			sb.append("FROM ");
			sb.append("( ");
			sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
			sb.append("FROM ");
			sb.append("( ");
			sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
			sb.append("FROM    C_TYP_TYPE ");
			sb.append("WHERE   HUB_STATE_IND = 1 ");
			sb.append("AND     CODE = 'ALT_CODE.UN_CODE' ");
			sb.append("UNION ALL ");
			sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
			sb.append(") ");
			sb.append(") ");
			sb.append("WHERE D_RANK = 1");
			try {
				PreparedStatement stmt = null;
				stmt = conn.prepareStatement(sb.toString());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					partyRoleName = rs.getString("ROWID_OBJECT").trim();
					log.info("Get UNCODE rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
				}
				return partyRoleName;
			} catch (Exception e) {
				log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
				return "NOT AVAILABLE";
			}
		}
	    
	    public String getUncodeLookupRowid(Connection conn) {
			String partyRoleName = null;
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
			sb.append("FROM ");
			sb.append("( ");
			sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
			sb.append("FROM ");
			sb.append("( ");
			sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
			sb.append("FROM    C_TYP_TYPE ");
			sb.append("WHERE   HUB_STATE_IND = 1 ");
			sb.append("AND     CODE = 'ALT_CODE.UN_CODE1' ");
			sb.append("UNION ALL ");
			sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
			sb.append(") ");
			sb.append(") ");
			sb.append("WHERE D_RANK = 1");
			try {
				PreparedStatement stmt = null;
				stmt = conn.prepareStatement(sb.toString());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					partyRoleName = rs.getString("ROWID_OBJECT").trim();
					log.info("Get UNCODELOOKUP rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
				}
				return partyRoleName;
			} catch (Exception e) {
				log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
				return "NOT AVAILABLE";
			}
		}
	    
	    public String getUncodeReturnRowid(Connection conn) {
			String partyRoleName = null;
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
			sb.append("FROM ");
			sb.append("( ");
			sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
			sb.append("FROM ");
			sb.append("( ");
			sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
			sb.append("FROM    C_TYP_TYPE ");
			sb.append("WHERE   HUB_STATE_IND = 1 ");
			sb.append("AND     CODE = 'ALT_CODE.UN_CODE2' ");
			sb.append("UNION ALL ");
			sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
			sb.append(") ");
			sb.append(") ");
			sb.append("WHERE D_RANK = 1");
			try {
				PreparedStatement stmt = null;
				stmt = conn.prepareStatement(sb.toString());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					partyRoleName = rs.getString("ROWID_OBJECT").trim();
					log.info("Get UNCODERETURN rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
				}
				return partyRoleName;
			} catch (Exception e) {
				log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
				return "NOT AVAILABLE";
			}
		}
	    
	    public String getScheduleKRowid(Connection conn) {
			String partyRoleName = null;
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
			sb.append("FROM ");
			sb.append("( ");
			sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
			sb.append("FROM ");
			sb.append("( ");
			sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
			sb.append("FROM    C_TYP_TYPE ");
			sb.append("WHERE   HUB_STATE_IND = 1 ");
			sb.append("AND     CODE = 'ALT_CODE.Schedule_K' ");
			sb.append("UNION ALL ");
			sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
			sb.append(") ");
			sb.append(") ");
			sb.append("WHERE D_RANK = 1");
			try {
				PreparedStatement stmt = null;
				stmt = conn.prepareStatement(sb.toString());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					partyRoleName = rs.getString("ROWID_OBJECT").trim();
					log.info("Get ALT_CODE.Schedule_K rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
				}
				return partyRoleName;
			} catch (Exception e) {
				log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
				return "NOT AVAILABLE";
			}
		}
	    
	    public String getScheduleDRowid(Connection conn) {
			String partyRoleName = null;
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
			sb.append("FROM ");
			sb.append("( ");
			sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
			sb.append("FROM ");
			sb.append("( ");
			sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
			sb.append("FROM    C_TYP_TYPE ");
			sb.append("WHERE   HUB_STATE_IND = 1 ");
			sb.append("AND     CODE = 'ALT_CODE.Schedule_D' ");
			sb.append("UNION ALL ");
			sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
			sb.append(") ");
			sb.append(") ");
			sb.append("WHERE D_RANK = 1");
			try {
				PreparedStatement stmt = null;
				stmt = conn.prepareStatement(sb.toString());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					partyRoleName = rs.getString("ROWID_OBJECT").trim();
					log.info("Get ALT_CODE.Schedule_D rowid # " + partyRoleName+ " & " + ROWID_OBJECT );
				}
				return partyRoleName;
			} catch (Exception e) {
				log.info((Object) ("getTypTypeRowid Exception ::" + e.getLocalizedMessage()));
				return "NOT AVAILABLE";
			}
		}

	    
		//Richa code end


	
	
	public String getTypTypeRowid(Connection conn) {

		String partyRoleName = null;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  DECODE(ROWID_OBJECT,LOWER('ZZZZZZZZZZ'),'NOT AVAILABLE',ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  ROWID_OBJECT, DENSE_RANK() OVER ( ORDER BY ROWID_OBJECT) D_RANK ");
		sb.append("FROM ");
		sb.append("( ");
		sb.append("SELECT  LOWER(ROWID_OBJECT) ROWID_OBJECT ");
		sb.append("FROM    C_TYP_TYPE ");
		sb.append("WHERE   HUB_STATE_IND = 1 ");
		sb.append("AND     CODE = 'ALT_CODE.GEOID' ");
		sb.append("UNION ALL ");
		sb.append("SELECT  LOWER('ZZZZZZZZZZ') FROM DUAL ");
		sb.append(") ");
		sb.append(") ");
		sb.append("WHERE D_RANK = 1");
		
		log.info("query to run for getTypTypeRowid is ::" +sb.toString());
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				partyRoleName = rs.getString("ROWID_OBJECT");
			}

			return partyRoleName;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("getTypTypeRowid Exception ::" + e.getLocalizedMessage());
			return "NOT AVAILABLE";
		}

	}

	public Integer getOffDetails(Connection conn, String Facility_Rowid) {

		Integer Off_Rowid = 0;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(1) AS CNT FROM C_FCT_OFF_REL ");
		sb.append("P WHERE P.FCT_ROWID= ");
		sb.append("'" + Facility_Rowid + "'");
		sb.append(" AND EXISTS (SELECT 1 FROM C_FCT_OFF WHERE IS_ACTIVE_IND='N' AND ROWID_OBJECT=P.OFFERING_ROWID)");

		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Off_Rowid = rs.getInt("CNT");
				log.info("Count is ::" + Off_Rowid);
			}

			return Off_Rowid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("getOffDetails Exception ::" + e.getLocalizedMessage());
			return 0;
		}

	}

	public String FetchCountryCode(Connection conn, String CNTRY_ROWID) {

		String CNTRY_CD = "";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT SUBSTR(CODE,0,2) AS CNTRY_CD FROM C_ALT_CODE WHERE TYP_TYPE_ROWID= ");
		sb.append("(SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE='ALT_CODE.RKST') ");
		sb.append("AND GDA_DFND_AREA_ROWID= ");
		sb.append("'" + CNTRY_ROWID + "'");
		log.info(sb);

		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				CNTRY_CD = rs.getString("CNTRY_CD");
				log.info("CNTRY_CD is ::" + CNTRY_CD);
			}

			return CNTRY_CD;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("FetchCountryCode Exception ::" + e.getLocalizedMessage());
			return "Error in Fetching the Code";
		}

	}

	/*
	 * public void setVal(BDDObject arg0){
	 * 
	 * if ("Operational".equalsIgnoreCase(arg0.getObjectName().toString())){
	 * 
	 * String mdmORSid =
	 * getOperationContext().getValue(OperationContext.ORS_ID).toString();
	 * log.info("The MDM ORS is::" + mdmORSid); Connection conn =
	 * getDatabaseConnection(mdmORSid); String typTypeRowid = getTypTypeRowid(conn);
	 * log.info("The typTypeRowid is::" + typTypeRowid); List<BDDObject>
	 * Alternate_Codes=arg0.getChildren("Alternate_Codes");
	 * 
	 * 
	 * 
	 * String geoID = generateId(); log.info("The GEOID is::" + geoID);
	 * if(getAltCodeCount(Alternate_Codes,typTypeRowid)==0){ Map<String,Object>
	 * altCodeRecord = new HashMap<String,Object>();
	 * altCodeRecord.put("C_FCT_ALT_CODES|TYP_TYPE_ROWID", new String(typTypeRowid)
	 * ); altCodeRecord.put("C_FCT_ALT_CODES|CODE", new String(geoID) );
	 * 
	 * //arg0.createChild("Alternate_Codes", altCodeRecord);
	 * //Alternate_Code.setValue("C_FCT_ALT_CODES|CODE", geoID);
	 * //Alternate_Code.setValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID", typTypeRowid);
	 * 
	 * } }
	 */

	/*
	 * public boolean makeInactiveOff(BDDObject arg0){
	 * 
	 * if("Services".equalsIgnoreCase(arg0.getObjectName().toString())) { String
	 * Status=arg0.getValue("C_FCT_OFF|IS_ACTIVE_IND").toString();
	 * 
	 * if("N".equalsIgnoreCase(Status)){ return true; } } return false; }
	 */

	public boolean TagInactiveOff(BDDObject arg0) {
		List<BDDObject> Service_Details = arg0.getChildren("Facility_Services");

		if (getObjectCount(Service_Details) > 0 && Service_Details.size() > 0) {
			for (BDDObject Service_Detail : Service_Details) {
				String ServiceOldValue = (String) Service_Detail.getOldValue("C_FCT_OFF|IS_ACTIVE_IND");
				if (ServiceOldValue == null) {
					if ("N".equalsIgnoreCase(Service_Detail.getValue("C_FCT_OFF|IS_ACTIVE_IND").toString())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean ExistInactiveOff(BDDObject arg0) {
		List<BDDObject> Service_Details = arg0.getChildren("Facility_Services");

		if (getObjectCount(Service_Details) > 0 && Service_Details.size() > 0) {
			for (BDDObject Service_Detail : Service_Details) {
				String ServiceOldValue = (String) Service_Detail.getOldValue("C_FCT_OFF|IS_ACTIVE_IND");
				log.info("ServiceOldValue=" + ServiceOldValue);
				if (ServiceOldValue != null) {
					if ("N".equalsIgnoreCase(Service_Detail.getValue("C_FCT_OFF|IS_ACTIVE_IND").toString())) {
						log.info("ServiceValue=" + Service_Detail.getValue("C_FCT_OFF|IS_ACTIVE_IND").toString());
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean URLCheck(BDDObject arg0) {
		org.apache.commons.validator.UrlValidator urlValidator = new UrlValidator();
		log.info("1");
		String URL = (String) arg0.getValue("C_FCT_FACILITY|URL");
		log.info("2:= " + URL);

		if (URL != null) {
			log.info("3");
			if (!urlValidator.isValid(URL)) {
				log.info("4");
				return true;
			}
		}
		return false;
	}

	public boolean emailCheck(BDDObject arg0) {

		Pattern pattern;
		Matcher matcher;

		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);

		List<BDDObject> Contact_Details = arg0.getChildren("Facility_Contact_Details");

		if (getObjectCount(Contact_Details) > 0 && Contact_Details.size() > 0)
			for (BDDObject Contact_Detail : Contact_Details) {

				String Email = (String) Contact_Detail.getValue("C_FCT_CONT|EMAIL");

				if (Email != null) {
					log.info("3");
					matcher = pattern.matcher(Email);
					return matcher.matches();
				}

			}
		return true;
	}

	/*
	 * public boolean CheckCustoff(BDDObject arg0){
	 * 
	 * List<BDDObject> Ops_Details=arg0.getChildren("Facility_Details");
	 * 
	 * 
	 * if(getObjectCount(Ops_Details) >0 && Ops_Details.size() >0) { for (BDDObject
	 * Ops_Detail : Ops_Details){
	 * 
	 * List<BDDObject> Ops_Type_Details=Ops_Detail.getChildren("Facility_Type");
	 * 
	 * if(getObjectCount(Ops_Type_Details) >0 && Ops_Type_Details.size() >0) { for
	 * (BDDObject Ops_Type_Detail : Ops_Type_Details){
	 * if("FCT_OPS_TYPE.CUST".equalsIgnoreCase(Ops_Type_Detail.getValue(
	 * "C_TYP_TYPE|CODE").toString()))
	 * if(getObjectCount(arg0.getChildren("Facility_Services"))>0){ return true; }
	 * 
	 * } }
	 * 
	 * } } return false;
	 * 
	 * }
	 */

	private int getRKSTCount(List<BDDObject> objects, Connection conn) {

		int count = 0;
		if (objects != null) {

			for (BDDObject object : objects) {

				/*
				 * Date VALID_FROM_DT =(Date)
				 * formatter.parseObject(object.getValue("C_FCT_CONT_REL|VALID_FROM_DT").
				 * toString());
				 */
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();

				if (AltCodeValue.equalsIgnoreCase(getRKSTRowid(conn))) {

					if (object.isRemoved())
						continue;
					++count;

				}

			}
		}
		return count;
	}

	private boolean getRKSTLength(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|CODE").toString();
				if (AltCodeName.equalsIgnoreCase(this.getRKSTRowid(conn)) && AltCodeValue.length() > 8) {
					return true;

				}

			}
		}
		return false;
	}

	private boolean getRKTSLength(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|CODE").toString();
				if (AltCodeName.equalsIgnoreCase(this.getRKTSRowid(conn)) && AltCodeValue.length() > 5) {
					return true;

				}

			}
		}
		return false;
	}

	private int getRKTSCount(List<BDDObject> objects, Connection conn) {

		int count = 0;
		if (objects != null) {

			for (BDDObject object : objects) {

				/*
				 * Date VALID_FROM_DT =(Date)
				 * formatter.parseObject(object.getValue("C_FCT_CONT_REL|VALID_FROM_DT").
				 * toString());
				 */
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();

				if (AltCodeValue.equalsIgnoreCase(getRKTSRowid(conn))) {

					if (object.isRemoved())
						continue;
					++count;

				}

			}
		}
		return count;
	}

	/*-----------------------Start of (A Contact can have only one active relation)----------------------------*/
	private int getContactCount(List<BDDObject> objects) throws java.text.ParseException {

		Format formatter;
		Date dNow = new Date();
		formatter = new SimpleDateFormat("yyyy-MM-dd");

		int count = 0;
		if (objects != null) {

			for (BDDObject object : objects) {

				/*
				 * Date VALID_FROM_DT =(Date)
				 * formatter.parseObject(object.getValue("C_FCT_CONT_REL|VALID_FROM_DT").
				 * toString());
				 */
				Date Vlid_Thru_Dt = (Date) formatter
						.parseObject(object.getValue("C_FCT_CONT_REL|VALID_THRU_DT").toString());

				if (dNow.compareTo(Vlid_Thru_Dt) <= 0) {

					if (object.isRemoved())
						continue;
					++count;

				}

			}
		}
		return count;
	}

	/*-----------------------End of (A Contact can have only one active relation)----------------------------*/

	private boolean getAltCodeCount(List<BDDObject> objects, String typTypeRowid) {

		log.info("getAltCodeCount called 1");
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltcodeBeforeSave = (String) object.getOldValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID");

				log.info("getAltCodeCount AltcodeBeforeSave" + AltcodeBeforeSave);
				String AltcodeAfterSave = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();

				log.info("getAltCodeCount AltcodeAfterSave" + AltcodeAfterSave);

				if (AltcodeBeforeSave != null) {

					if (AltcodeBeforeSave.equalsIgnoreCase(typTypeRowid) && AltcodeBeforeSave != AltcodeAfterSave) {
						log.info("getAltCodeCount called 2");
						return true;

					}

				}
			}
		}
		return false;
	}

	public int getGeoTypTypeRowidCount(Connection conn, String typTypeRowid, String fctRowid) {

		int geoTyptypeRowidCount = 0;

		StringBuilder sb = new StringBuilder();

		sb.append(" select count ");
		sb.append(" ( ");
		sb.append(" * ");
		sb.append(" ) ");
		sb.append(" from C_FCT_ALT_CODES ");
		sb.append(" where ");
		sb.append(" TYP_TYPE_ROWID = (?) ");
		sb.append(" and ");
		sb.append(" FCT_ROWID = (?) ");

		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, typTypeRowid);
			stmt.setString(2, fctRowid);

			log.info("Query ::" + sb);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				geoTyptypeRowidCount = rs.getInt(1);

			}
			return geoTyptypeRowidCount;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("getGeoTypTypeRowidCount Exception ::" + e.getLocalizedMessage());
			return 0;
		}

	}

	/*
	 * public boolean ClssCdChk(BDDObject SavingObject){
	 * 
	 * if ("Operational".equalsIgnoreCase(SavingObject.getObjectName().toString())){
	 * String Clss_CD=(String)SavingObject.getValue("C_FCT_FACILITY|CLASS_CD");
	 * 
	 * if("COMM".equalsIgnoreCase(Clss_CD)){
	 * 
	 * return true; } else
	 * 
	 * return false; }
	 * 
	 * else
	 * 
	 * return false;
	 * 
	 * }
	 */
	private int getObjectCount(List<BDDObject> objects) {
		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				if (object.isRemoved())
					continue;
				++count;
				log.info("Count::" + count);
			}
		}
		return count;
	}

	/*
	 * public boolean CheckOFF(BDDObject Facility){
	 * 
	 * List<BDDObject> Facility_Details=Facility.getChildren("Facility_Details");
	 * 
	 * if(getObjectCount(Facility_Details) >0 && Facility_Details.size()>0){
	 * for(BDDObject Facility_Detail:Facility_Details){
	 * 
	 * List<BDDObject> Types=Facility_Detail.getChildren("Facility_Type");
	 * 
	 * if(getObjectCount(Types) >1 && Types.size()>1){ for(BDDObject Type:Types){
	 * if("FCT_OPS_TYPE.CUST".equalsIgnoreCase(Type.getValue("C_TYP_TYPE|CODE").
	 * toString())){ return true; }
	 * 
	 * } } } }
	 * 
	 * 
	 * 
	 * return false; }
	 */

	public String AddressDctorCheck(String Street, String House_Num, String City, String Pstl_Cd, String Trty_Rowid,
			String Cntry_Rowid, String Addr_Ln_2, String Addr_Ln_3, String LAT_GEOSPTL, String LNG_GEOSPTL,
			String CNTRY_CD) {
		String mdmORSid = (String) this.getOperationContext().getValue("ors id");
		Connection conn6 = getDatabaseConnection(mdmORSid);

		log.info((Object) "Inside AddressDctorCheck::");
		this.sipClient = (SiperianClient) this.getOperationContext().getValue("sifClient");
		log.info((Object) ("The Siperian Client is::" + (Object) this.sipClient));
		CleanseRequest request = new CleanseRequest();
		request.setCleanseFunctionName("Facility Cleanse Function|FacilityAdressIDD");
		Record record = new Record();
		Field field = new Field();
		field.setName("STREET");
		field.setValue((Object) Street);
		record.setField(field);
		field = new Field();
		field.setName("HOUSE_NUM");
		field.setValue((Object) House_Num);
		record.setField(field);
		field = new Field();
		field.setName("CITY");
		field.setValue((Object) City);
		record.setField(field);
		field = new Field();
		field.setName("PSTCD");
		field.setValue((Object) Pstl_Cd);
		record.setField(field);
		field = new Field();
		field.setName("TRTY_ROWID");
		field.setValue((Object) Trty_Rowid);
		record.setField(field);
		field = new Field();
		field.setName("CTRY_ROWID");
		field.setValue((Object) Cntry_Rowid);
		record.setField(field);
		field = new Field();
		field.setName("ADDR_LN_2");
		field.setValue((Object) Addr_Ln_2);
		record.setField(field);
		field = new Field();
		field.setName("ADDR_LN_3");
		field.setValue((Object) Addr_Ln_3);
		record.setField(field);
		field = new Field();
		field.setName("LAT_GEOSPTL");
		field.setValue((Object) LAT_GEOSPTL);
		record.setField(field);
		field = new Field();
		field.setName("LNG_GEOSPTL");
		field.setValue((Object) LNG_GEOSPTL);
		record.setField(field);
		field = new Field();
		field.setName("CNTRY_CD");
		field.setValue((Object) CNTRY_CD);
		record.setField(field);
		log.info("input completed");

		try {
			request.setUsername((String) this.getOperationContext().getValue(OperationContext.USERNAME));
			log.info((Object) ("The IDD  is(Adress)::"
					+ this.getOperationContext().getValue(OperationContext.USERNAME).toString()));
			request.setPassword(new Password((String) this.getOperationContext().getValue(OperationContext.PASSWORD)));
			log.info((Object) ("The MDM password is(Adress)::"
					+ this.getOperationContext().getValue(OperationContext.PASSWORD).toString()));
			log.info("getOperationContext has been sent for AddressDctorCheck");

		} catch (Exception e) {
			log.info("Security Payload has been started AddressDctorCheck");
			byte[] securityPayload = (byte[]) getOperationContext().getValue("security payload");
			request.setSecurityPayload(securityPayload);
			log.info("Security Payload has been sent AddressDctorCheck" + e);
		}

		/*
		 * log.info("Security Payload has been started AddressDctorCheck"); byte[]
		 * securityPayload = (byte[]
		 * )getOperationContext().getValue("security payload");
		 * request.setSecurityPayload(securityPayload);
		 * log.info("Security Payload has been sent AddressDctorCheck");
		 */

		request.setOrsId((String) this.getOperationContext().getValue("ors id"));
		log.info((Object) ("The MDM ORS is(Adress)::" + this.getOperationContext().getValue("ors id").toString()));
		request.setRecord(record);
		CleanseResponse response = (CleanseResponse) this.sipClient.process((SiperianRequest) request);
		log.info((Object) "before getting response(Adress)");
		log.info((Object) ("StatusInfoMatchCode is ::"
				+ response.getRecord().getField("StatusInfoMatchCode").getStringValue()));
		String StatusInfoMatchCode = response.getRecord().getField("StatusInfoMatchCode").getStringValue();
		try {

			if (StatusInfoMatchCode.contains("V")) {

				log.info("V_ADDR_0");
				String Country_Name = response.getRecord().getField("Country").getStringValue();
				log.info("V_ADDR_1 Modified");

				log.info("V_Territory is ::" + response.getRecord().getField("Territory"));
				String Region_Code = null;
				String Get_Region = null;
				log.info("AV_DDR_3");
				String Get_country = GetCountry(conn6, Country_Name);
				log.info("V_Country Rowid is::" + Get_country);
				if (response.getRecord().getField("ADDR_LN_2") == null) {

					if (response.getRecord().getField("Territory") == null) {

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8"
										+ "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("V_No222222");
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							} else
								return null;

						}
					} else {
						Region_Code = response.getRecord().getField("Territory").getStringValue();
						Get_Region = GetRegion(conn6, Region_Code, Country_Name);
						log.info("V_Region Rowid is::" + Get_Region);

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else if (!"Country Not Available".equalsIgnoreCase(Get_country)
								&& "Region Not Available".equalsIgnoreCase(Get_Region)) {
							return "Region Not Available";
						} else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8"
										+ Get_Region + "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region
										+ "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("V_No222222_1");
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3" + ""
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							} else
								return null;

						}
					}
				} else {
					if (response.getRecord().getField("Territory") == null) {

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("V_No222222_2");
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							} else
								return null;

						}
					} else {
						Region_Code = response.getRecord().getField("Territory").getStringValue();
						Get_Region = GetRegion(conn6, Region_Code, Country_Name);
						log.info("V_Region Rowid is::" + Get_Region);

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else if (!"Country Not Available".equalsIgnoreCase(Get_country)
								&& "Region Not Available".equalsIgnoreCase(Get_Region)) {
							return "Region Not Available";
						} else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region
										+ "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("V_No222222_3");
								return response.getRecord().getField("STREET").getStringValue() + "CLEANSE_1"
										+ response.getRecord().getField("HOUSE_NUM").getStringValue() + "CLEANSE_2"
										+ response.getRecord().getField("PSTCD").getStringValue() + "CLEANSE_3"
										+ response.getRecord().getField("ADDR_LN_2").getStringValue() + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							} else
								return null;

						}
					}
				}

			} else if (StatusInfoMatchCode.contains("C")) {

				log.info("ADDR_0");
				String Country_Name = response.getRecord().getField("Country").getStringValue();
				log.info("ADDR_1 Modified");

				log.info("Territory is ::" + response.getRecord().getField("Territory"));
				String Region_Code = null;
				String Get_Region = null;
				log.info("ADDR_3");
				String Get_country = GetCountry(conn6, Country_Name);
				log.info("Country Rowid is::" + Get_country);
				if (response.getRecord().getField("ADDR_LN_2") == null) {

					if (response.getRecord().getField("Territory") == null) {

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("No222222");
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							} else
								return null;

						}
					} else {
						Region_Code = response.getRecord().getField("Territory").getStringValue();
						Get_Region = GetRegion(conn6, Region_Code, Country_Name);
						log.info("Region Rowid is::" + Get_Region);

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else if (!"Country Not Available".equalsIgnoreCase(Get_country)
								&& "Region Not Available".equalsIgnoreCase(Get_Region)) {
							return "Region Not Available";
						} else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region
										+ "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5" + ""
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("No222222_1");
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + "" + "CLEANSE_4"
										+ response.getRecord().getField("ADDR_LN_3").getStringValue() + "CLEANSE_5"
										+ response.getRecord().getField("LAT_GEOSPTL").getStringValue() + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							} else
								return null;

						}
					}
				} else {
					if (response.getRecord().getField("Territory") == null) {

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8"
										+ "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("No222222_2");
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + "" + "CLEANSE_9";
							} else
								return null;

						}
					} else {
						Region_Code = response.getRecord().getField("Territory").getStringValue();
						Get_Region = GetRegion(conn6, Region_Code, Country_Name);
						log.info("Region Rowid is::" + Get_Region);

						if ("Country Not Available".equalsIgnoreCase(Get_country)) {
							return "Country Not Available";
						}

						else if (!"Country Not Available".equalsIgnoreCase(Get_country)
								&& "Region Not Available".equalsIgnoreCase(Get_Region)) {
							return "Region Not Available";
						} else {

							if (LAT_GEOSPTL == null && LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8"
										+ Get_Region + "CLEANSE_9";
							}
							if (LAT_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + "" + "CLEANSE_6"
										+ response.getRecord().getField("LNG_GEOSPTL").getStringValue() + "CLEANSE_7"
										+ Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							}
							if (LNG_GEOSPTL == null) {
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + "" + "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region
										+ "CLEANSE_9";
							}
							if (LAT_GEOSPTL != null && LNG_GEOSPTL != null) {
								log.info("No222222_3");
								return "CLEANSE_0" + response.getRecord().getField("STREET").getStringValue()
										+ "CLEANSE_1" + response.getRecord().getField("HOUSE_NUM").getStringValue()
										+ "CLEANSE_2" + response.getRecord().getField("PSTCD").getStringValue()
										+ "CLEANSE_3" + response.getRecord().getField("ADDR_LN_2").getStringValue()
										+ "CLEANSE_4" + response.getRecord().getField("ADDR_LN_3").getStringValue()
										+ "CLEANSE_5" + response.getRecord().getField("LAT_GEOSPTL").getStringValue()
										+ "CLEANSE_6" + response.getRecord().getField("LNG_GEOSPTL").getStringValue()
										+ "CLEANSE_7" + Get_country + "CLEANSE_8" + Get_Region + "CLEANSE_9";
							} else
								return null;

						}
					}
				}

			} else

				return "BadAddress";

		} catch (NumberFormatException e) {
			return "Check City_country";
		}

		finally {
			try {
				conn6.close();
				log.info((Object) "--------Connection closed");
			} catch (Exception e) {
			}
		}
	}

	public OperationResult afterEverything(BDDObject bddObject) {

		return OperationResult.OK;
	}

	public OperationResult afterSave(BDDObject savedObject) {

		return OperationResult.OK;
	}

	public OperationResult beforeSave(BDDObject SavingObject) {

		String mdmORSid = getOperationContext().getValue(OperationContext.ORS_ID).toString();
		Connection conn = getDatabaseConnection(mdmORSid);
		String Var = "No";

		try {

			if ("Operational".equalsIgnoreCase(SavingObject.getObjectName().toString())) {

				log.info("Inside SendforApproval BeforeSave");
				/*
				 * if (CheckCustoff(SavingObject)){ return new OperationResult(new
				 * OperationExecutionError("SIP-500104", this.getLocalizationGate())); }
				 */

			   	
				//Richa Added code in Beforeeverthing for SMDG & BIC
				int count = 0;
									

				List<BDDObject> facilityBICOperationaltcode = SavingObject.getChildren("Alternate_Codes");
				log.info("value of child adding altcode object r : " + facilityBICOperationaltcode);
				if (facilityBICOperationaltcode != null && facilityBICOperationaltcode.size() > 0) {
					log.info("size of child adding altcode object r : " + facilityBICOperationaltcode.size());
					for (BDDObject alttypeobject : facilityBICOperationaltcode) {
					String AltCodeValue = alttypeobject.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
				log.info("AltCodeValue in BIC CODE  " + AltCodeValue);
				log.info("get BIC Rowid from code  " + getBICRowid(conn).trim() );
				String comaprerowid = getBICRowid(conn).trim();
				
					if (AltCodeValue.equalsIgnoreCase(comaprerowid)){
					count++;					
					log.info("Inside BIC count1 '" + AltCodeValue +"' = " + count );
					
					}
				}
					if (count>1){
						log.info("get count BIC Code check" + count );
						return new OperationResult(new OperationExecutionError("SIP-5566998", getLocalizationGate()));
					}
				}
				
				List<BDDObject> facilitySMDGOperationaltcode = SavingObject.getChildren("Alternate_Codes");
				log.info("value of child adding altcode object r : " + facilitySMDGOperationaltcode);
				if (facilitySMDGOperationaltcode != null && facilitySMDGOperationaltcode.size() > 0) {
					log.info("size of child adding altcode object r : " + facilitySMDGOperationaltcode.size());
					for (BDDObject alttypeobject : facilitySMDGOperationaltcode) {
					String AltCodeValue = alttypeobject.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
				log.info("AltCodeValue in SMDG CODE  " + AltCodeValue );
				log.info("get SMDG Rowid from code  " + getSMDGRowid(conn).trim() );
				String comaprerowid = getBICRowid(conn).trim();
					if (AltCodeValue.equalsIgnoreCase(comaprerowid)){
					count++;					
					log.info("Inside SMDG count1 '" + AltCodeValue +"' = " + count );
					
					}
				}
					if (count>1){
						log.info("get count SMDG Code check" + count );
						return new OperationResult(new OperationExecutionError("SIP-5566999", getLocalizationGate()));
					}
				}

				 List<BDDObject> Uncodesite = SavingObject.getChildren("Alternate_Codes");
	   				log.info("value of child adding both cityaltcode1 object r : " + Uncodesite);
	   				if (Uncodesite != null && Uncodesite.size() > 0) {
	   					log.info("getinto city adding both cityaltcode2 object r : " + Uncodesite);
	   		  if ((getUncodeCount(Uncodesite,conn)>0) && (getUncodeReturnCount(Uncodesite,conn)>0)){
	   			log.info("getinto city adding both altcode check object Uncodesite : " + Uncodesite);
	   			return new OperationResult(new OperationExecutionError("SIP-500290", this.getLocalizationGate()));
	   				} }
	   				
	   			
	            	   List<BDDObject> Uncodesite1 = SavingObject.getChildren("Alternate_Codes");
	   				log.info("value of child adding both cityaltcode1 object r : " + Uncodesite1);
	   				if (Uncodesite1 != null && Uncodesite1.size() > 0) {
	   					log.info("getinto city adding both cityaltcode2 object r : " + Uncodesite1);
	   		     if ((getUncodeLookupCount(Uncodesite1,conn)>0) && (getUncodeReturnCount(Uncodesite1,conn)>0)){
	   			log.info("getinto city adding both altcode check object Uncodesite1 : " + Uncodesite1);
	   			return new OperationResult(new OperationExecutionError("SIP-500291", this.getLocalizationGate()));
	   				} }
	               
	   			  List<BDDObject> Uncodesite2 = SavingObject.getChildren("Alternate_Codes");
	   				log.info("value of child adding both cityaltcode1 object r : " + Uncodesite2);
	   				if (Uncodesite2 != null && Uncodesite2.size() > 0) {
	   					log.info("getinto city adding both cityaltcode2 object r : " + Uncodesite2);
	   		     if (getUncodeReturnCount(Uncodesite2,conn)>1){
	   			log.info("getinto city adding both altcode check object Uncodesite2 : " + Uncodesite2);
	   			return new OperationResult(new OperationExecutionError("SIP-500292", this.getLocalizationGate()));
	   				} }
	   				
	   			 List<BDDObject> Uncodesite3 = SavingObject.getChildren("Alternate_Codes");
	   				log.info("value of child adding both cityaltcode1 object r : " + Uncodesite3);
	   				if (Uncodesite3 != null && Uncodesite3.size() > 0) {
	   					log.info("getinto city adding both cityaltcode2 object r : " + Uncodesite3);
	   		     if (getUncodeCount(Uncodesite3,conn)>1){
	   			log.info("getinto city adding both altcode check object Uncodesite3 : " + Uncodesite3);
	   			return new OperationResult(new OperationExecutionError("SIP-500293", this.getLocalizationGate()));
	   				} }
	   				
	   				List<BDDObject> Uncodesite4 = SavingObject.getChildren("Alternate_Codes");
	   				log.info("value of child adding both cityaltcode1 object r : " + Uncodesite4);
	   				if (Uncodesite4 != null && Uncodesite4.size() > 0) {
	   					log.info("getinto city adding both cityaltcode2 object r : " + Uncodesite4);
	   		  if ((getUncodeLookupCount(Uncodesite4,conn)>0) && (getUncodeCount(Uncodesite4,conn)==0)){
	   			log.info("getinto city adding both altcode check object Uncodesite : " + Uncodesite4);
	   			return new OperationResult(new OperationExecutionError("SIP-500294", this.getLocalizationGate()));
	   				} }
	   				
	   				//schduleD Check
	   				
	   				/*List<BDDObject> schduleD = SavingObject.getChildren("Alternate_Codes");
	   				List<BDDObject> getCountry = SavingObject.getChildren("Facility_Address");
	   			    log.info("value of child adding both schduleD object r : " + schduleD);
		   			log.info("value of child adding both schduleD object r : " + getCountry);
		   			if (schduleD != null && schduleD.size() > 0) {
		   				for (BDDObject schduleDcheck : schduleD){
		   				String AltCodeValue = schduleDcheck.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
		   				String tocomapreDKrowid = getScheduleDRowid(conn).trim();
		   			 if(AltCodeValue.equalsIgnoreCase(tocomapreDKrowid)) {
		   			 for (BDDObject facility_Country : getCountry) {
		   			  String ADDR_CNTRYROWID = facility_Country.getValue("C_CTM_PSTL_ADDR|CTRY_ROWID").toString().trim();
		   			  String US_CNTRYROWID = getUScountryrowid(conn).trim();
   					  log.info("get Value from IDD ADDR_CNTRYROWID # " + ADDR_CNTRYROWID);
   					  log.info("get Value from IDD US_CNTRYROWID # " + US_CNTRYROWID);
   					  log.info("Get All If Values # " + (getScheduleDCount(schduleD,conn)>1) + " Get another Values # " + !ADDR_CNTRYROWID.equalsIgnoreCase(US_CNTRYROWID)  + "Check final" + ((getScheduleDCount(schduleD,conn)>1) && !ADDR_CNTRYROWID.equalsIgnoreCase(US_CNTRYROWID)) );
   					  if (!ADDR_CNTRYROWID.equalsIgnoreCase(US_CNTRYROWID)){
   						log.info("Get All If Values Inside If # " + (getScheduleDCount(schduleD,conn)>1) + " Get another Values # " + ADDR_CNTRYROWID.equalsIgnoreCase(US_CNTRYROWID) +
   	   							"Final check # " + ((getScheduleDCount(schduleD,conn)>1) && !ADDR_CNTRYROWID.equalsIgnoreCase(US_CNTRYROWID)));
   						return new OperationResult(new OperationExecutionError("SIP-500295", this.getLocalizationGate()));   						
   					}
   					if (getScheduleDCount(schduleD,conn)>1){
   						log.info(" get count for Schdeule D # " + getScheduleDCount(schduleD,conn));
   						return new OperationResult(new OperationExecutionError("SIP-500298", this.getLocalizationGate()));   						
   					}
		   			  }
		   			}
			}
		   			}*/
		   			
		   			
                  //schduleK Check Richa
	   				/*List<BDDObject> schduleK = SavingObject.getChildren("Alternate_Codes");
	   				List<BDDObject> get_Country = SavingObject.getChildren("Facility_Address");
	   			    log.info("value of child adding both schduleK object r : " + schduleD);
		   			log.info("value of child adding both schduleK object r : " + getCountry);
		   			if (schduleK != null && schduleK.size() > 0) {
		   				for (BDDObject schduleKcheck : schduleK){
		   				String AltCodeValue = schduleKcheck.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString().trim();
		   				String tocomapreKrowid = getScheduleKRowid(conn).trim();
		   			 if(AltCodeValue.equalsIgnoreCase(tocomapreKrowid)) {
		   			 for (BDDObject facility_CountryK : get_Country) {
		   			  String ADDR_CNTRYROWIK = facility_CountryK.getValue("C_CTM_PSTL_ADDR|CTRY_ROWID").toString().trim();
		   			  String US_CNTRYROWIDK = getUScountryrowid(conn).trim();
   					  log.info("get Value from IDD ADDR_CNTRYROWID for K # " + ADDR_CNTRYROWIK);
   					  log.info("get Value from IDD US_CNTRYROWID  for K # " + US_CNTRYROWIDK);
   					 log.info("Get All If Values K # " + (getScheduleKCount(schduleK,conn)>1) + " Get another Values # " + ADDR_CNTRYROWIK.equalsIgnoreCase(US_CNTRYROWIDK)  + "Check final" + 
   					  ((getScheduleKCount(schduleK,conn)>1) || ADDR_CNTRYROWIK.equalsIgnoreCase(US_CNTRYROWIDK)) );
   					   if (ADDR_CNTRYROWIK.equalsIgnoreCase(US_CNTRYROWIDK)){
   						 log.info("Get All If Values K Inside IF # " + (getScheduleDCount(schduleD,conn)>1) + " Get another Values # " + ADDR_CNTRYROWIK.equalsIgnoreCase(US_CNTRYROWIDK)  + "Check final" +
   								((getScheduleKCount(schduleK,conn)>1) || ADDR_CNTRYROWIK.equalsIgnoreCase(US_CNTRYROWIDK)) );
   						   return new OperationResult(new OperationExecutionError("SIP-500296", this.getLocalizationGate()));   						
   					}
   					   if (getScheduleKCount(schduleK,conn)>1){
     						 log.info(" get count for Schdeule K # " + getScheduleKCount(schduleK,conn));
        						   return new OperationResult(new OperationExecutionError("SIP-500297", this.getLocalizationGate()));
   					   }
   					   
		   			  }
		   			}
			}
		   			}	*/		   				
				
//Richa End Code
				
				String Fct_Rowid = SavingObject.getSystemValue("C_FCT_FACILITY|ROWID_OBJECT").toString();
				log.info("Facility Rowid Is ::" + Fct_Rowid);
				int geoTypTypeRowidCount = 0;
				List<BDDObject> Service_Details = SavingObject.getChildren("Facility_Services");
				List<BDDObject> Trnsp_Details = SavingObject.getChildren("Transport_Details");
				List<BDDObject> Facility_Details = SavingObject.getChildren("Facility_Details");
				List<BDDObject> Alternate_Codes = SavingObject.getChildren("Alternate_Codes");
				String typTypeRowid = this.getTypTypeRowid(conn);
				geoTypTypeRowidCount = this.getGeoTypTypeRowidCount(conn, typTypeRowid, Fct_Rowid);

				if (getRKSTCount(Alternate_Codes, conn) > 1) {
					Var = "1";
					log.info("getRKSTCount being called");
					return new OperationResult(new OperationExecutionError("SIP-500156", this.getLocalizationGate()));
				}

				if (getRKTSCount(Alternate_Codes, conn) > 1) {
					log.info("getRKTSCount being called");
					Var = "2";
					return new OperationResult(new OperationExecutionError("SIP-500157", this.getLocalizationGate()));
				}

				if (getAltCodeCount(Alternate_Codes, getTypTypeRowid(conn))) {
					log.info("getAltCodeCount being called");
					Var = "3";
					return new OperationResult(new OperationExecutionError("SIP-500155", this.getLocalizationGate()));
				}

				if (GetCountOff(Service_Details)) {
					Var = "4";
					return new OperationResult(new OperationExecutionError("SIP-500153", this.getLocalizationGate()));
				}

				if (GetCountTrnsp(Trnsp_Details)) {
					Var = "5";
					return new OperationResult(new OperationExecutionError("SIP-500153", this.getLocalizationGate()));
				}

				if (GetCountType(Facility_Details)) {
					Var = "6";
					return new OperationResult(new OperationExecutionError("SIP-500153", this.getLocalizationGate()));
				}

				try {

					if ((Float.valueOf(FacltyLatitudeCheck(SavingObject)) > 90
							|| Float.valueOf(FacltyLatitudeCheck(SavingObject)) < -90)
							&& (Float.valueOf(FacltyLongitudeCheck(SavingObject)) > 180
									|| Float.valueOf(FacltyLongitudeCheck(SavingObject)) < -180)) {
						log.info("SendforApproval");
						Var = "7";
						return new OperationResult(
								new OperationExecutionError("SIP-500122", this.getLocalizationGate()));
					}

					if (Float.valueOf(FacltyLatitudeCheck(SavingObject)) > 90
							|| Float.valueOf(FacltyLatitudeCheck(SavingObject)) < -90) {
						Var = "8";
						return new OperationResult(
								new OperationExecutionError("SIP-500123", this.getLocalizationGate()));
					}

					if (Float.valueOf(FacltyLongitudeCheck(SavingObject)) > 180
							|| Float.valueOf(FacltyLongitudeCheck(SavingObject)) < -180) {
						Var = "9";
						return new OperationResult(
								new OperationExecutionError("SIP-500124", this.getLocalizationGate()));
					}
				} catch (NumberFormatException e) {

					Var = "56";
					return new OperationResult(new OperationExecutionError("SIP-500415", this.getLocalizationGate()));
				}

				if (TagInactiveOff(SavingObject)) {
					Var = "10";
					return new OperationResult(new OperationExecutionError("SIP-500106", this.getLocalizationGate()));
				}

				/*-----------------------Start of (A Contact can have only one active relation)----------------------------*/
				try {
					if (getContactCount(SavingObject.getChildren("Facility_Contact_Details")) > 1) {

						Var = "11";
						return new OperationResult(
								new OperationExecutionError("SIP-500107", this.getLocalizationGate()));

					}
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					Var = "12";
					e.printStackTrace();
				}

				/*-----------------------End of (A Contact can have only one active relation)----------------------------*/

				/*
				 * if(ClssCdChk(SavingObject)){ return new OperationResult(new
				 * OperationExecutionError("SIP-500108", this.getLocalizationGate())); }
				 */

				/*
				 * if(CheckOFF(SavingObject)){ return new OperationResult(new
				 * OperationExecutionError("SIP-500109", this.getLocalizationGate())); }
				 */

				if (URLCheck(SavingObject)) {
					Var = "13";
					return new OperationResult(new OperationExecutionError("SIP-500110", this.getLocalizationGate()));
				}
				if (!emailCheck(SavingObject)) {
					Var = "14";
					return new OperationResult(new OperationExecutionError("SIP-500163", this.getLocalizationGate()));
				}

				/*
				 * if(FacltyOpsCheck(SavingObject) && FacltyAddressCheck(SavingObject)){
				 * 
				 * log.info("Facility Details,Facility Type and Facility Address not given");
				 * return new OperationResult(new OperationExecutionError("SIP-500117",
				 * this.getLocalizationGate())); }
				 * 
				 * if( FacltyTypeCheck(SavingObject) && FacltyAddressCheck(SavingObject)){
				 * 
				 * log.info("Facility Type and Facility Address not given"); return new
				 * OperationResult(new OperationExecutionError("SIP-500118",
				 * this.getLocalizationGate())); }
				 * 
				 * if( FacltyOpsCheck(SavingObject)){
				 * 
				 * log.info("Facility Details,Facility Type not given"); return new
				 * OperationResult(new OperationExecutionError("SIP-500119",
				 * this.getLocalizationGate())); }
				 * 
				 * if(FacltyTypeCheck(SavingObject)){
				 * 
				 * log.info("Facility Type and Facility Address"); return new
				 * OperationResult(new OperationExecutionError("SIP-500120",
				 * this.getLocalizationGate())); }
				 */

				if (FacltyAddressCheck(SavingObject)) {

					log.info("Facility Address not given");
					Var = "15";
					return new OperationResult(new OperationExecutionError("SIP-500177", this.getLocalizationGate()));

				}

				if (this.getRKSTCount(Alternate_Codes,
						conn) == 0 /* || this.getRKTSCount(Alternate_Codes, conn) == 0 */) {
					log.info((Object) "getRKSTCount and getRKSTCount being called for exist or not");
					Var = "16";
					return new OperationResult(new OperationExecutionError("SIP-500176", this.getLocalizationGate()));
				}

				if (this.getRKSTCount(Alternate_Codes, conn) == 0) {
					log.info((Object) "getRKSTCount being called for exist or not");
					Var = "17";
					return new OperationResult(new OperationExecutionError("SIP-500174", this.getLocalizationGate()));
				}
				if (this.getRKTSCount(Alternate_Codes, conn) == 0) {
					log.info((Object) "getRKTSCount being called for exist or not");
					Var = "18";
				//	return new OperationResult(new OperationExecutionError("SIP-500175", this.getLocalizationGate()));
				}

				if (getMINRKSTLength(Alternate_Codes, conn)) {
					log.info((Object) "RKST can not be less  than 7");
					Var = "19";
					return new OperationResult(new OperationExecutionError("SIP-500188", this.getLocalizationGate()));

				}

				if (getMINRKTSLength(Alternate_Codes, conn)) {
					log.info((Object) "RKTS can not be less  than 5");
					Var = "20";
					return new OperationResult(new OperationExecutionError("SIP-500189", this.getLocalizationGate()));

				}

				if (getRKSTLength(Alternate_Codes, conn)) {
					log.info((Object) "RKST can not be more than 8");
					Var = "21";
					return new OperationResult(new OperationExecutionError("SIP-500165", this.getLocalizationGate()));

				}

				if (getRKTSLength(Alternate_Codes, conn)) {
					log.info((Object) "RKTS can not be more than 5");
					Var = "22";
					return new OperationResult(new OperationExecutionError("SIP-500166", this.getLocalizationGate()));

				}

				/*************** This is pending ************/

				/*
				 * if(getAltDetails(getDatabaseConnection(mdmORSid),Fct_Rowid,AlternateCodes)>0)
				 * { log.info((Object)"RKST or RKTS is not unique"); return new
				 * OperationResult(new OperationExecutionError("SIP-500167",
				 * this.getLocalizationGate())); }
				 */

				/*************** This is pending ************/
				List<BDDObject> facilityAddres = SavingObject.getChildren("Facility_Address");
				String FacilityRKST = getFacilityRKST(Alternate_Codes, conn);
				log.info(FacilityRKST);
				String FacilityRKTS = getFacilityRKTS(Alternate_Codes, conn);
				log.info(FacilityRKTS);

				String FacilityRKST_Cntry = FacilityRKST.substring(0, 2);
				log.info(FacilityRKST_Cntry);
				String FacilityRKST_City = FacilityRKST.substring(0, 5);
				log.info(FacilityRKST_City);
				String FacilityRKTS_City = FacilityRKTS.substring(0, 3);
				log.info(FacilityRKTS_City);

				String CntryRKSTVal = getCntryRKSTVal(facilityAddres, conn);
				log.info(CntryRKSTVal);
				String CityRKSTVal = getCityRKSTVal(facilityAddres, conn);
				log.info(CityRKSTVal);
				String CityRKTSVal = getCityRKTSVal(facilityAddres, conn);
				log.info(CityRKTSVal);

				/*
				 * if(FacilityRKST_Cntry.equalsIgnoreCase(CntryRKSTVal) &&
				 * FacilityRKST_City.equalsIgnoreCase(CityRKSTVal) &&
				 * FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)){
				 * log.info((Object)"RKST and RKTS is not sync with Geo"); return new
				 * OperationResult(new OperationExecutionError("SIP-500185",
				 * this.getLocalizationGate())); }
				 */

				if (!FacilityRKST_Cntry.equalsIgnoreCase(CntryRKSTVal)) {
					log.info((Object) "RKST and RKTS is not sync with Geo 1");
					if (!FacilityRKST_City.equalsIgnoreCase(CityRKSTVal)) {
						log.info((Object) "RKST and RKTS is not sync with Geo 2");
						if (!FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)) {
							log.info((Object) "RKST and RKTS is not sync with Geo 3");
							Var = "23";
							return new OperationResult(
									new OperationExecutionError("SIP-500185", this.getLocalizationGate()));
						} else if (FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)) {
							log.info((Object) "RKST and RKTS is not sync with Geo 3");
							Var = "24";
							return new OperationResult(
									new OperationExecutionError("SIP-500191", this.getLocalizationGate()));
						}
					} else if (FacilityRKST_City.equalsIgnoreCase(CityRKSTVal)) {
						log.info((Object) "RKST and RKTS is not sync with Geo 8");
						if (!FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)) {
							log.info((Object) "RKST and RKTS is not sync with Geo 9");
							Var = "25";
							return new OperationResult(
									new OperationExecutionError("SIP-500413", this.getLocalizationGate()));
						} else if (FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)) {
							log.info((Object) "RKST and RKTS is not sync with Geo 10");
							Var = "26";
							return new OperationResult(
									new OperationExecutionError("SIP-500414", this.getLocalizationGate()));
						}
					}
				} else if (!FacilityRKST_City.equalsIgnoreCase(CityRKSTVal)) {
					log.info((Object) "RKST and RKTS is not sync with Geo 4");
					if (!FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)) {
						log.info((Object) "RKST and RKTS is not sync with Geo 5");
						Var = "27";
						return new OperationResult(
								new OperationExecutionError("SIP-500186", this.getLocalizationGate()));
					} else if (FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)) {
						log.info((Object) "RKST and RKTS is not sync with Geo 7");
						Var = "28";
						return new OperationResult(
								new OperationExecutionError("SIP-500190", this.getLocalizationGate()));
					}

				} else if (!FacilityRKTS_City.equalsIgnoreCase(CityRKTSVal)) {
					log.info((Object) "RKST and RKTS is not sync with Geo 6");
					Var = "29";
					return new OperationResult(new OperationExecutionError("SIP-500187", this.getLocalizationGate()));
				}

				/*
				 * else if(!FacilityRKST_City.equalsIgnoreCase(CityRKSTVal)){
				 * log.info((Object)"RKST and RKTS is not sync with Geo 7"); return new
				 * OperationResult(new OperationExecutionError("SIP-500190",
				 * this.getLocalizationGate())); }
				 */

				if (geoTypTypeRowidCount == 0) {
					if (("YES".equalsIgnoreCase(getFacilityRKSTCount(Alternate_Codes, conn)))
							&& ("YES".equalsIgnoreCase(getFacilityRKTSCount(Alternate_Codes, conn)))) {
						log.info((Object) "RKST and RKTS Duplicate");
						Var = "30";
						return new OperationResult(
								new OperationExecutionError("SIP-500213", this.getLocalizationGate()));
					}

					if ("YES".equalsIgnoreCase(getFacilityRKSTCount(Alternate_Codes, conn))) {
						log.info((Object) "RKST Duplicate");
						Var = "31";
						return new OperationResult(
								new OperationExecutionError("SIP-500211", this.getLocalizationGate()));
					}

					if ("YES".equalsIgnoreCase(getFacilityRKTSCount(Alternate_Codes, conn))) {
						log.info((Object) "RKTS Duplicate");
						Var = "32";
						return new OperationResult(
								new OperationExecutionError("SIP-500212", this.getLocalizationGate()));
					}
				}

				else {
					log.info((Object) "Ok");
				}

			}

		}

		catch (Exception e) {
			log.info(e);
		} finally {
			log.info("We are inside finally method");
			try {

				if ("No".equalsIgnoreCase(Var)) {
					if ("Operational".equalsIgnoreCase(SavingObject.getObjectName().toString())) {

						/*---------------------------------------------------*/

						log.info("in AftreSave");
						log.info(test);
						List<BDDObject> facilityAddress = SavingObject.getChildren("Facility_Address");

						if (this.getObjectCount(facilityAddress) > 0 && facilityAddress.size() > 0) {
							for (BDDObject facility_Address : facilityAddress) {

								log.info("test 1");

								if (test.contains("CLEANSE_0")) {

									String STREET_OUTPUT = test.substring(
											test.indexOf("CLEANSE_0") + "CLEANSE_0".length(),
											test.indexOf("CLEANSE_1"));
									String HOUSE_NUM_OUTPUT = test.substring(
											test.indexOf("CLEANSE_1") + "CLEANSE_1".length(),
											test.indexOf("CLEANSE_2"));
									String PSTCD_OUTPUT = test.substring(
											test.indexOf("CLEANSE_2") + "CLEANSE_2".length(),
											test.indexOf("CLEANSE_3"));
									String ADDR_LN_2_OUTPUT = test.substring(
											test.indexOf("CLEANSE_3") + "CLEANSE_3".length(),
											test.indexOf("CLEANSE_4"));
									String ADDR_LN_3_OUTPUT = test.substring(
											test.indexOf("CLEANSE_4") + "CLEANSE_4".length(),
											test.indexOf("CLEANSE_5"));
									String LAT_GEOSPTL_OUTPUT = test.substring(
											test.indexOf("CLEANSE_5") + "CLEANSE_5".length(),
											test.indexOf("CLEANSE_6"));
									String LNG_GEOSPTL_OUTPUT = test.substring(
											test.indexOf("CLEANSE_6") + "CLEANSE_6".length(),
											test.indexOf("CLEANSE_7"));
									String Country_Rowid = test.substring(
											test.indexOf("CLEANSE_7") + "CLEANSE_7".length(),
											test.indexOf("CLEANSE_8"));
									String Region_Rowid = test.substring(
											test.indexOf("CLEANSE_8") + "CLEANSE_8".length(),
											test.indexOf("CLEANSE_9"));
									log.info("STREET_OUTPUT::" + STREET_OUTPUT);
									facility_Address.setValue("C_CTM_PSTL_ADDR|STREET", STREET_OUTPUT);
									log.info("HOUSE_NUM_OUTPUT::" + HOUSE_NUM_OUTPUT);
									facility_Address.setValue("C_CTM_PSTL_ADDR|HOUSE_NUM", HOUSE_NUM_OUTPUT);
									log.info("PSTCD_OUTPUT::" + PSTCD_OUTPUT);
									facility_Address.setValue("C_CTM_PSTL_ADDR|PSTCD", PSTCD_OUTPUT);
									log.info("ADDR_LN_2_OUTPUT::" + ADDR_LN_2_OUTPUT);
									facility_Address.setValue("C_CTM_PSTL_ADDR|ADDR_LN_2", ADDR_LN_2_OUTPUT);
									log.info("ADDR_LN_3_OUTPUT::" + ADDR_LN_3_OUTPUT);
									facility_Address.setValue("C_CTM_PSTL_ADDR|ADDR_LN_3", ADDR_LN_3_OUTPUT);
									log.info("LAT_GEOSPTL_OUTPUT::" + LAT_GEOSPTL_OUTPUT);
									facility_Address.setValue("C_CTM_PSTL_ADDR|LAT_GEOSPTL", LAT_GEOSPTL_OUTPUT);
									log.info("LNG_GEOSPTL_OUTPUT::" + LNG_GEOSPTL_OUTPUT);
									facility_Address.setValue("C_CTM_PSTL_ADDR|LNG_GEOSPTL", LNG_GEOSPTL_OUTPUT);
									log.info("Country::" + Country_Rowid);
									facility_Address.setValue("C_CTM_PSTL_ADDR|CTRY_ROWID", Country_Rowid);
									log.info("Region ::" + Region_Rowid);
									facility_Address.setValue("C_CTM_PSTL_ADDR|TRTY_ROWID", Region_Rowid);
									log.info("Setting has completed");

								}

							}
						}
					}
				}
				/*------------------------------------------------------*/
				conn.close();
				log.info((Object) "--------Connection closed AfterSave");
			} catch (Exception e) {
			}
			{

			}
		}

		return OperationResult.OK;
	}

	public boolean FacltyOpsCheck(BDDObject SavingObject) {

		List<BDDObject> Ops_Details = SavingObject.getChildren("Facility_Details");

		if (getObjectCount(Ops_Details) == 0) {
			return true;
		}

		return false;

	}

	public boolean FacltyTypeCheck(BDDObject SavingObject) {

		List<BDDObject> Ops_Details = SavingObject.getChildren("Facility_Details");

		if (getObjectCount(Ops_Details) > 0 && Ops_Details.size() > 0) {
			for (BDDObject Ops_Detail : Ops_Details) {

				if (getObjectCount(Ops_Detail.getChildren("Facility_Type")) == 0) {
					return true;
				}
			}

		}

		return false;

	}

	public boolean FacltyAddressCheck(BDDObject SavingObject) {

		List<BDDObject> Address_Details = SavingObject.getChildren("Facility_Address");

		if (getObjectCount(Address_Details) == 0) {
			return true;
		}

		return false;

	}

	public String FacltyLongitudeCheck(BDDObject SavingObject) {

		List<BDDObject> Address_Details = SavingObject.getChildren("Facility_Address");

		if (getObjectCount(Address_Details) > 0 && Address_Details.size() > 0) {
			for (BDDObject Address_Detail : Address_Details) {

				String Longitude = (String) Address_Detail.getValue("C_CTM_PSTL_ADDR|LNG_GEOSPTL");

				if (Longitude != null && !Longitude.isEmpty()) {

					return Longitude;
				}

			}
		}

		return "0";

	}

	public String FacltyLatitudeCheck(BDDObject SavingObject) {

		List<BDDObject> Address_Details = SavingObject.getChildren("Facility_Address");

		if (getObjectCount(Address_Details) > 0 && Address_Details.size() > 0) {
			for (BDDObject Address_Detail : Address_Details) {

				String Latitude = (String) Address_Detail.getValue("C_CTM_PSTL_ADDR|LAT_GEOSPTL");

				if (Latitude != null && !Latitude.isEmpty()) {

					return Latitude;
				}

			}
		}

		return "0";

	}

	private boolean getMINRKSTLength(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|CODE").toString();
				if (AltCodeName.equalsIgnoreCase(this.getRKSTRowid(conn)) && AltCodeValue.length() < 7) {
					return true;

				}

			}
		}
		return false;
	}

	private boolean getMINRKTSLength(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCodeValue = object.getValue("C_FCT_ALT_CODES|CODE").toString();
				if (AltCodeName.equalsIgnoreCase(this.getRKTSRowid(conn)) && AltCodeValue.length() < 5) {
					return true;

				}

			}
		}
		return false;
	}

	private String getFacilityRKST(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCode = object.getValue("C_FCT_ALT_CODES|CODE").toString();

				if (AltCodeName.equalsIgnoreCase(this.getRKSTRowid(conn))) {
					return AltCode;

				}

			}
		}
		return null;
	}

	private String getFacilityRKTS(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCode = object.getValue("C_FCT_ALT_CODES|CODE").toString();

				if (AltCodeName.equalsIgnoreCase(this.getRKTSRowid(conn))) {
					return AltCode;

				}

			}
		}
		return null;
	}

	private String getCntryRKSTVal(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String CTRY_ROWID = object.getValue("C_CTM_PSTL_ADDR|CTRY_ROWID").toString();

				return getCntryRKST(conn, CTRY_ROWID);

			}
		}
		return null;
	}

	private String getFacilityRKSTCount(List<BDDObject> objects, Connection conn) {

		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {

				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCode = object.getValue("C_FCT_ALT_CODES|CODE").toString();

				if (AltCodeName.equalsIgnoreCase(this.getRKSTRowid(conn))) {
					count = getFCTRKSTCount(conn, AltCode);
					if (count > 0) {
						return "YES";
					}
				}
			}
		}
		return null;
	}

	private String getFacilityRKTSCount(List<BDDObject> objects, Connection conn) {

		int count = 0;
		if (objects != null) {
			for (BDDObject object : objects) {

				String AltCodeName = object.getValue("C_FCT_ALT_CODES|TYP_TYPE_ROWID").toString();
				String AltCode = object.getValue("C_FCT_ALT_CODES|CODE").toString();

				if (AltCodeName.equalsIgnoreCase(this.getRKTSRowid(conn))) {
					count = getFCTRKTSCount(conn, AltCode);
					if (count > 0) {
						return "YES";
					}
				}
			}
		}
		return null;
	}

	private String getCityRKSTVal(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String CITY_ROWID = object.getValue("C_CTM_PSTL_ADDR|CITY_ROWID").toString();

				return getCityRKST(conn, CITY_ROWID);

			}
		}
		return null;
	}

	private String getCityRKTSVal(List<BDDObject> objects, Connection conn) {
		int Lnth = 0;
		if (objects != null) {
			for (BDDObject object : objects) {
				String CITY_ROWID = object.getValue("C_CTM_PSTL_ADDR|CITY_ROWID").toString();

				return getCityRKTS(conn, CITY_ROWID);

			}
		}
		return null;
	}

	public String GetCountry(Connection conn, String Country_Name) {
		String Cntry_Rowid = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" with tab as (SELECT DISTINCT C_GDA_DFND_AREA.ROWID_OBJECT COUNTRY_MDM_ID  ");
		sb.append(" ,TRIM(C_ALT_CODE.CODE) CNTRY_CD,C_GDA_DFND_AREA.NAME CTRY_NAME FROM C_ALT_CODE ,C_GDA_DFND_AREA ");
		sb.append(" WHERE C_ALT_CODE.GDA_DFND_AREA_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT ");
		sb.append(" AND   C_GDA_DFND_AREA.TYP_TYPE_CD    = 'GDA.COUNTRY' ");
		sb.append(" AND   C_GDA_DFND_AREA.ACTIVE_FLAG    = 'Y' AND   C_ALT_CODE.TYP_TYPE_ROWID      =( ");
		sb.append(" SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.GEOID') ");
		sb.append(" AND LOWER(TRIM(C_GDA_DFND_AREA.NAME))    = LOWER('" + Country_Name + "') ");
		sb.append(" union all select 'No','','' from dual) ");
		sb.append(
				" select decode (COUNTRY_MDM_ID ,'No','Country Not Available',COUNTRY_MDM_ID)COUNTRY_MDM_ID from tab WHERE rownum<2 ");
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			log.info((Object) ("Country Query ::" + sb));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Cntry_Rowid = rs.getString("COUNTRY_MDM_ID");
			}
			return Cntry_Rowid;
		} catch (Exception e) {
			log.info((Object) ("COUNTRY_MDM_ID Exception ::" + e.getLocalizedMessage()));
			return null;
		}
	}

	public String GetRegion(Connection conn, String Region_Code, String Country_Name) {
		String Region_Rowid = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" WITH CNTRY_CD_DETAILS AS (  SELECT DISTINCT Y.ROWID_OBJECT COUNTRY_MDM_ID,   ");
		sb.append(
				" TRIM(X.CODE) CNTRY_CD, Y.NAME CTRY_NAME FROM C_ALT_CODE X, C_GDA_DFND_AREA Y WHERE X.GDA_DFND_AREA_ROWID = Y.ROWID_OBJECT ");
		sb.append(" AND Y.TYP_TYPE_CD           = 'GDA.COUNTRY'   AND Y.ACTIVE_FLAG           = 'Y'  ");
		sb.append(
				" AND X.TYP_TYPE_ROWID        = ( SELECT ROWID_OBJECT FROM C_TYP_TYPE WHERE CODE = 'ALT_CODE.RKST' ) ");
		sb.append("  AND  LOWER(TRIM(Y.NAME))    = LOWER('" + Country_Name + "')) ,  ");
		sb.append(" CTRY_REGION_REL AS  ( SELECT DISTINCT  K1.ROWID_OBJECT REGION_MDM_ID,K1.NAME REGION_NAME, ");
		sb.append(" K3.CNTRY_CD FROM C_GDA_DFND_AREA K1,C_GDA_DFND_AREA_REL K2,CNTRY_CD_DETAILS K3  ");
		sb.append(
				" WHERE K2.TYP_TYPE_CD = 'GDA_REL.TRTY_IN_CTRY'  AND K2.GDA_DFND_AREA_PRNT_ROWID = K3.COUNTRY_MDM_ID ");
		sb.append(" AND K2.GDA_DFND_AREA_CHLD_ROWID = K1.ROWID_OBJECT ),  ");
		sb.append(
				" REGION_INFO AS  ( SELECT DISTINCT C_ALT_CODE.CODE, REGION.NAME REGION_NAME,REGION.ROWID_OBJECT REGION_MDM_ID ");
		sb.append(
				"  FROM C_ALT_CODE, C_TYP_TYPE,  C_GDA_DFND_AREA REGION  WHERE C_ALT_CODE.TYP_TYPE_ROWID = C_TYP_TYPE.ROWID_OBJECT  ");
		sb.append(
				" AND C_ALT_CODE.GDA_DFND_AREA_ROWID = REGION.ROWID_OBJECT AND C_TYP_TYPE.CODE IN ('ALT_CODE.ISO_TRTY')  AND C_ALT_CODE.HUB_STATE_IND = 1  ");
		sb.append("  AND REGION.ACTIVE_FLAG  = 'Y'  AND ( LOWER ( C_ALT_CODE.CODE )    = LOWER('" + Region_Code
				+ "'))  ");
		sb.append(" AND REGION.NAME IN  ( SELECT REGION_NAME FROM CTRY_REGION_REL)) ");
		sb.append(" SELECT DECODE ( REGION_MDM_ID,'ZZZZZZZ', 'Region Not Available',REGION_MDM_ID ) REGION_MDM_ID  ");
		sb.append(" FROM  ( SELECT DISTINCT REGION_MDM_ID, DENSE_RANK() OVER (ORDER BY REGION_MDM_ID) D_RANK FROM  ");
		sb.append(" ( SELECT REGION_MDM_ID FROM REGION_INFO WHERE REGION_MDM_ID <> 'XXX' AND ROWNUM  < 2 UNION ALL  ");
		sb.append(" SELECT 'ZZZZZZZ' FROM DUAL )) WHERE D_RANK = 1 ");

		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sb.toString());
			log.info((Object) ("Region Query ::" + sb));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Region_Rowid = rs.getString("REGION_MDM_ID");
			}
			return Region_Rowid;
		} catch (Exception e) {
			log.info((Object) ("REGION_MDM_ID Exception ::" + e.getLocalizedMessage()));
			return null;
		}
	}

	/*
	 * public static OperationType valueOf(String name){ if
	 * (name.equals("SEND_FOR_APPROVAL_OPERATION")) { log.debug("Inside IF #####");
	 * return new OperationType(new OperationExecutionError("SIP-60014",
	 * getLocalizationGate()); }else log.debug("not a Send For approval");
	 * 
	 * }
	 */

}
