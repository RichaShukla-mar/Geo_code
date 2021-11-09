package com.maersk.mdm.taskdata.util;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

//import org.apache.openjpa.lib.log.Log;

import com.maersk.mdm.taskdata.data.BindBDAData;
import com.maersk.mdm.taskdata.data.BindCityData;
import com.maersk.mdm.taskdata.data.BindContinentData;
import com.maersk.mdm.taskdata.data.BindCountryData;
import com.maersk.mdm.taskdata.data.BindPostalCodeData;
import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.bdd.userexits.datamodel.CustomizableBDDObject;
import com.siperian.bdd.userexits.operations.OperationContext;
import com.siperian.dsapp.common.util.Logger;


public class Utility {
	private static final Logger log = Logger.getLogger(Utility.class);
	static Connection connection = null;
	String getcountryname;
	  public static Object validate(Object obj) {
	    if (obj == null) {
	      return "";
	    }
	    return obj;
	  }

	  public static Long validateLong(Object obj) {
	    if (obj == null || obj.toString().length() <= 0) {
	      return null;
	    }
	    String value = obj.toString();
	    Double val = Double.parseDouble(value);
	    return new Long(val.longValue());
	  }

	  public static Integer validateInteger(Object obj) {
	    if (obj == null || obj.toString().length() <= 0) {
	      return null;
	    }
	    String value = obj.toString();
	    Double val = Double.parseDouble(value);
	    return new Integer(val.intValue());
	  }

	  public static Double validateDouble(Object obj) {
	    if (obj == null || obj.toString().length() <= 0) {
	      return null;
	    }
	    return Double.parseDouble(obj.toString());
	  }

	  public static String getValueFromDB(OperationContext operationContext, String query) {

	    String orsId = (String) operationContext.getValue(OperationContext.ORS_ID);
	    ResultSet rs = null;
	    Statement stmt = null;
	    String obj = "";
	    connection = getDatabaseConnection(orsId.toLowerCase());
	    try {
	      stmt = connection.createStatement();
	      rs = stmt.executeQuery(query);
	      if (rs.next()) {
	        obj = rs.getString(1);
	      }

	    } catch (SQLException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    } finally {
	      try {
	        rs.close();
	        stmt.close();
	        connection.close();
	      } catch (SQLException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        log.info("Exception caught in method getValueFromDB ::"+e.getMessage());
	      }
	    }
	    return obj;

	  }
	  
	  
	  public static String changeDateFormat(String date) {

	    try {
	      SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	      SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      Date parsein = inputFormat.parse(date);
	      String formattedDate = outputFormat.format(parsein);
	      log.info("Formatted date " + formattedDate + " Actual date " + date);
	      return formattedDate;
	    } catch (ParseException e1) {
	      log.info("Exception is change date format " + e1.getMessage());
	    } catch (Exception e1) {
	      log.info("Exception is change date format " + e1.getMessage());
	    }
	    return date;
	  }

	  public static String replaceDigit(String imo) {
	    imo = imo.replace(imo.charAt(0), '.');
	    return imo;
	  }

	  public static String getCurrentDateInFormat() {
	    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	    Date now = new Date();
	    String strDate = outputFormat.format(now);
	    return strDate;
	  }

	  public static Date parseStringToDate(Object dateValue, int value) {
	    log.info(" Parse value" + value);
	    if (dateValue != null && dateValue.toString() != null
	        && dateValue.toString().trim().length() > 0) {

	      String date = (String) dateValue;
	      try {
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        log.info(" Parse value finish" + value);// 8951200785
	        return format.parse(date);

	      } catch (ParseException ex) {
	        log.error("Enter validate in SpecialValidations---parseStringToDate" + ex.getMessage());
	        log.info(" Parse value exception " + value);
	      } catch (Exception ex) {
	        log.error("Enter validate in SpecialValidations---parseStringToDate" + ex.getMessage());
	        log.info(" Parse valueexception" + value);
	      }
	    }
	    return null;
	  }



	  
	public static Connection getDatabaseConnection(String mdmORSid) {

		try {
			String dataSourceName = "jdbc/siperian-" + mdmORSid.toLowerCase() + "-ds";
			log.info((Object) ("The MDM DataSource name is ::" + dataSourceName));
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(dataSourceName);
			connection = ds.getConnection();
			log.info((Object) "getConnection end - success");
			return connection;
		} catch (Exception e) {
			log.info((Object) ("getConnection Exception ::" + e.getLocalizedMessage()));
			return null;
		}

	}
	
	public static PublishService getMQInterface() {
	    PublishService mqInterface;
	    mqInterface = new PublishGeographyViaMQ();
	    return mqInterface;
	  }
	public static String getDatabaseLookupValue(String tableName,String rowidObject,String returnColumn, String filterColumn) {
		String returnValue = "SELECT "+returnColumn+" FROM "+tableName+"  WHERE "+filterColumn +"='"+rowidObject+"'";
		log.info("SQL QUERY IS ::"+returnValue);
		return returnValue;
	}
	

	
	public static String getCitySubAreaParent(String rowidObject){
		String returnValue = "select GDA_DFND_AREA_PRNT_ROWID from C_GDA_DFND_AREA_REL where  HUB_STATE_IND ='1' and TYP_TYPE_CD in ('GDA_REL.TRTY_IN_CTRY','GDA_REL.CITY_IN_CTRY') and GDA_DFND_AREA_CHLD_ROWID ='"+rowidObject+"'";
		log.info("SQL QUERY IS getCitySubAreaParent ::"+returnValue);
		return returnValue;
	}
	
	//Richa Query to get Country for state
	
	public String getStateParentname(BDDObject DFNDAREA,String orsid){
		List<BDDObject> hierarchyfield = DFNDAREA.getChildren("Hierarchy");
		String Rowid_State = ((String) DFNDAREA.getValue("C_GDA_DFND_AREA|ROWID_OBJECT")).trim();
		Statement oprstmt = null;
		 ResultSet oprrs= null;
		 
		String Query_Ex = "select C_GDA_DFND_AREA.NAME  from C_GDA_DFND_AREA_REL , C_GDA_DFND_AREA  where C_GDA_DFND_AREA_REL.GDA_DFND_AREA_CHLD_ROWID in (select rowid_object from C_GDA_DFND_AREA where rowid_Object= '"+ Rowid_State +"' ) and C_GDA_DFND_AREA_REL.TYP_TYPE_CD='GDA_REL.TRTY_IN_CTRY' and"
				+ " C_GDA_DFND_AREA_REL.GDA_DFND_AREA_PRNT_ROWID = C_GDA_DFND_AREA.ROWID_OBJECT and "
				+ "C_GDA_DFND_AREA.TYP_TYPE_CD ='GDA.COUNTRY'";
		log.info("checkoutfleetdate :: sql query result "  + Query_Ex);
		
		Connection connection = getDatabaseConnection(orsid.toLowerCase());
		log.info("getStateParentname :: "  + connection);
		   try  {
			   oprstmt = connection.createStatement();           
			   oprrs = oprstmt.executeQuery(Query_Ex); 
			   log.info("getStateParentname :: get query result"  + oprrs);
			   if (oprrs.next()) 
				{
					getcountryname = oprrs.getString(1);
				}
			   log.info("getStateParentname ::value returned: " + getcountryname );
		   }catch (Exception e) {
			   log.info("getStateParentname :: get into catch if any failure : " +  getcountryname );
			   e.printStackTrace();
			   
		   }
		   finally {
				try {
					oprrs.close();
					oprstmt.close();
					connection.close();
				}catch (SQLException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			      }return getcountryname;
	}
	}
	
	public String getCountryParentname(BDDObject DFNDAREA,String orsid){
		List<BDDObject> hierarchyfield = DFNDAREA.getChildren("Hierarchy");
		String Rowid_State = ((String) DFNDAREA.getValue("C_GDA_DFND_AREA|ROWID_OBJECT")).trim();
		Statement oprstmt = null;
		 ResultSet oprrs= null;
		 
		String Query_Ex = "select NAME from C_GDA_DFND_AREA where TYP_TYPE_CD ='GDA.COUNTRY' and  rowid_Object= '"+ Rowid_State +"' ";
		log.info("checkoutfleetdate :: sql query result "  + Query_Ex);
		
		Connection connection = getDatabaseConnection(orsid.toLowerCase());
		log.info("getStateParentname :: "  + connection);
		   try  {
			   oprstmt = connection.createStatement();           
			   oprrs = oprstmt.executeQuery(Query_Ex); 
			   log.info("getStateParentname :: get query result"  + oprrs);
			   if (oprrs.next()) 
				{
					getcountryname = oprrs.getString(1);
				}
			   log.info("getStateParentname ::value returned: " + getcountryname );
		   }catch (Exception e) {
			   log.info("getStateParentname :: get into catch if any failure : " +  getcountryname );
			   e.printStackTrace();
			   
		   }
		   finally {
				try {
					oprrs.close();
					oprstmt.close();
					connection.close();
				}catch (SQLException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			      }return getcountryname;
	}
	}
	
	
	/******************Start Get geoID FCT Rowid*********************/
    public static String getFCTRowid(String geoRowid,String orsid){	
 	  
  	    String fctRowid = null;
	  		ResultSet rs =null;
	  		
	  		  String sql = "select FCT_ROWID from C_FCT_ALT_CODES where CODE ='"+geoRowid+"'";
	  	      Connection con = getDatabaseConnection(orsid.toLowerCase());
	  	      log.info("con : "  + con);
	  	      try {
	  	      Statement stmt = con.createStatement();
	  	      rs = stmt.executeQuery(sql);
	  	    if (rs.next()) {		
	  	    	fctRowid  = rs.getString("FCT_ROWID");
	  	    }
	  	      rs.close();
	  	      stmt.close();
	  	       con.close();
	  	      }catch (Exception e) {
	  			// TODO: handle exception
	  	    	  log.info("Inside getStatefromCity exception : "  + geoRowid);
	  	    	  e.printStackTrace();
	  		}
	  	   return fctRowid;
  	  
    }
    
    /*******************End Get geoID FCT Rowid*********************/
	/******************    200293: Add the Alternate Code to the parent start ***********************/
	public static Object getParentAltCodes(String parentRowidObject,OperationContext operationContext,String bindElement){
		ResultSet rs =null;
		String orsid = (String) operationContext.getValue(operationContext.ORS_ID);
		log.info("orsid : "  + orsid);
		  String sql = "SELECT  code,typ_type_rowid FROM MDM_INFM_SMDS.c_alt_code WHERE hub_state_ind = 1 AND gda_dfnd_area_rowid='"+parentRowidObject+"'";
	      Connection con = getDatabaseConnection(orsid.toLowerCase());
	      log.info("con : "  + con);
	      try {
	      Statement stmt = con.createStatement();
	      rs = stmt.executeQuery(sql);
	      switch(bindElement) {
			case "COUNTRY" :
				List<com.maersk.mdm.taskdata.jaxb.Geography.Country.Parent.AlternateCodes.AlternateCode> countryParentAltCode= new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.Country.Parent.AlternateCodes.AlternateCode>();
				  if (rs!=null){
						while(rs.next()) {
							com.maersk.mdm.taskdata.jaxb.Geography.Country.Parent.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.Country.Parent.AlternateCodes.AlternateCode();
							altCode.setCode(rs.getString("code"));
							String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
							log.info("typeTypeRowid is ::" + typeTypeRowid);
							String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
							String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
							altCode.setCodeType(altCodeValue);
							log.info("altCode.code" + altCode.getCode());
							log.info("altCode.CodeType" + altCode.getCodeType());
							countryParentAltCode.add(altCode);
						}
						return countryParentAltCode;
				  }
				break;
				
			case "CITY" :
				List<com.maersk.mdm.taskdata.jaxb.Geography.City.Parent.AlternateCodes.AlternateCode> cityParentAltCode= new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.City.Parent.AlternateCodes.AlternateCode>();
				if (rs!=null){
					while(rs.next()) {
						com.maersk.mdm.taskdata.jaxb.Geography.City.Parent.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.City.Parent.AlternateCodes.AlternateCode();
						altCode.setCode(rs.getString("code"));
						String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
						log.info("typeTypeRowid is ::" + typeTypeRowid);
						String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
						String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
						altCode.setCodeType(altCodeValue);
						log.info("altCode.code" + altCode.getCode());
						log.info("altCode.CodeType" + altCode.getCodeType());
						cityParentAltCode.add(altCode);
					}	
					return cityParentAltCode;
				}
				break;
			
			case "SUBCITY" :
				List<com.maersk.mdm.taskdata.jaxb.Geography.City.SubCityParent.AlternateCodes.AlternateCode> subCityParentAltCode= new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.City.SubCityParent.AlternateCodes.AlternateCode>();
				if (rs!=null){
					while(rs.next()) {
						com.maersk.mdm.taskdata.jaxb.Geography.City.SubCityParent.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.City.SubCityParent.AlternateCodes.AlternateCode();
						altCode.setCode(rs.getString("code"));
						String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
						log.info("typeTypeRowid is ::" + typeTypeRowid);
						String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
						String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
						altCode.setCodeType(altCodeValue);
						log.info("altCode.code" + altCode.getCode());
						log.info("altCode.CodeType" + altCode.getCodeType());
						subCityParentAltCode.add(altCode);
					}	
					return subCityParentAltCode;
				}
				break;
				
			case "BUSINESSDEFINEDAREA" :
				List<com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.ParentBDA.AlternateCodes.AlternateCode> BDAParentAltCode= new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.ParentBDA.AlternateCodes.AlternateCode>();
				if (rs!=null){
					while(rs.next()) {
						com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.ParentBDA.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.ParentBDA.AlternateCodes.AlternateCode();
						altCode.setCode(rs.getString("code"));
						String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
						log.info("typeTypeRowid is ::" + typeTypeRowid);
						String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
						String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
						altCode.setCodeType(altCodeValue);
						log.info("altCode.code" + altCode.getCode());
						log.info("altCode.CodeType" + altCode.getCodeType());
						BDAParentAltCode.add(altCode);
					}	
					return BDAParentAltCode;
				}
				break;
				
				case "POSTALCODE" :
					List<com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Parent.AlternateCodes.AlternateCode> PostalCDParentAltCode= new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Parent.AlternateCodes.AlternateCode>();
					if (rs!=null){
						while(rs.next()) {
							com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Parent.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Parent.AlternateCodes.AlternateCode();
							altCode.setCode(rs.getString("code"));
							String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
							log.info("typeTypeRowid is ::" + typeTypeRowid);
							String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
							String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
							altCode.setCodeType(altCodeValue);
							log.info("altCode.code" + altCode.getCode());
							log.info("altCode.CodeType" + altCode.getCodeType());
							PostalCDParentAltCode.add(altCode);
						}	
						return PostalCDParentAltCode;
					}
					break;
					
				case "SITE" :
					List<com.maersk.mdm.taskdata.jaxb.Geography.Site.Parent.AlternateCodes.AlternateCode> siteParentAltCode= new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.Site.Parent.AlternateCodes.AlternateCode>();
					if (rs!=null){
						while(rs.next()) {
							com.maersk.mdm.taskdata.jaxb.Geography.Site.Parent.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.Site.Parent.AlternateCodes.AlternateCode();
							altCode.setCode(rs.getString("code"));
							String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
							log.info("typeTypeRowid is ::" + typeTypeRowid);
							String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
							String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
							altCode.setCodeType(altCodeValue);
							log.info("altCode.code" + altCode.getCode());
							log.info("altCode.CodeType" + altCode.getCodeType());
							siteParentAltCode.add(altCode);
						}	
						return siteParentAltCode;
					}
					break;
					
				case "SITECOUNTRY" :
					List<com.maersk.mdm.taskdata.jaxb.Geography.Site.Country.AlternateCodes.AlternateCode> siteCountryAltCode = new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.Site.Country.AlternateCodes.AlternateCode>();
					if (rs!=null){
						while(rs.next()) {
							com.maersk.mdm.taskdata.jaxb.Geography.Site.Country.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.Site.Country.AlternateCodes.AlternateCode();
							altCode.setCode(rs.getString("code"));
							String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
							log.info("typeTypeRowid is ::" + typeTypeRowid);
							String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
							String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
							altCode.setCodeType(altCodeValue);
							log.info("altCode.code" + altCode.getCode());
							log.info("altCode.CodeType" + altCode.getCodeType());
							siteCountryAltCode.add(altCode);
						}	
						return siteCountryAltCode;
					}
					break;
					
				case "STATEPROV" :
					List<com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.Parent.AlternateCodes.AlternateCode> stateParentAltCode= new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.Parent.AlternateCodes.AlternateCode>();
					if (rs!=null){
						while(rs.next()) {
							com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.Parent.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.Parent.AlternateCodes.AlternateCode();
							altCode.setCode(rs.getString("code"));
							String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
							log.info("typeTypeRowid is ::" + typeTypeRowid);
							String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
							String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
							altCode.setCodeType(altCodeValue);
							log.info("altCode.code" + altCode.getCode());
							log.info("altCode.CodeType" + altCode.getCodeType());
							stateParentAltCode.add(altCode);
						}	
						return stateParentAltCode;
					}
					break;
					
				case "POSTALCOUNTRY" :
					List<com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Country.AlternateCodes.AlternateCode> PSCountryAltCode = new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Country.AlternateCodes.AlternateCode>();
					if (rs!=null){
						while(rs.next()) {
							com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Country.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.Country.AlternateCodes.AlternateCode();
							altCode.setCode(rs.getString("code"));
							String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
							log.info("typeTypeRowid is ::" + typeTypeRowid);
							String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
							String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
							altCode.setCodeType(altCodeValue);
							log.info("altCode.code" + altCode.getCode());
							log.info("altCode.CodeType" + altCode.getCodeType());
							PSCountryAltCode.add(altCode);
						}	
						return PSCountryAltCode;
					}
					break;
					
				case "CITYCOUNTRT" :
					List<com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes.AlternateCode> cityCountryAltCode = new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes.AlternateCode>();
					if (rs!=null){
						while(rs.next()) {
							com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.City.Country.AlternateCodes.AlternateCode();
							altCode.setCode(rs.getString("code"));
							String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
							log.info("typeTypeRowid is ::" + typeTypeRowid);
							String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
							String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
							altCode.setCodeType(altCodeValue);
							log.info("altCode.code" + altCode.getCode());
							log.info("altCode.CodeType" + altCode.getCodeType());
							cityCountryAltCode.add(altCode);
						}	
						return cityCountryAltCode;
					}
					break;
					
				/*case "SUBCITYCOUNTRY" :
				List<com.maersk.mdm.taskdata.jaxb.Geography.CitySubArea.Country.AlternateCodes.AlternateCode> subCityCountryAltCode = new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.CitySubArea.Country.AlternateCodes.AlternateCode>();
					if (rs!=null){
						while(rs.next()) {
							com.maersk.mdm.taskdata.jaxb.Geography.CitySubArea.Country.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.CitySubArea.Country.AlternateCodes.AlternateCode();
							altCode.setCode(rs.getString("code"));
							String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
							log.info("typeTypeRowid is ::" + typeTypeRowid);
							String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
							String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
							altCode.setCodeType(altCodeValue);
							log.info("altCode.code" + altCode.getCode());
							log.info("altCode.CodeType" + altCode.getCodeType());
							subCityCountryAltCode.add(altCode);
						}	
						return subCityCountryAltCode;
					}
					break;*/
				}
	     
	    	
	      }catch (Exception e) {
			// TODO: handle exception
	    	  log.info("Inside getParentAltCodes exception : "  + parentRowidObject);
	    	  e.printStackTrace();
		}
	      return rs;
		
	}
	
	      public static String getCountryfromState(String parentRowidObject,OperationContext operationContext){
	    	 
	        String CountryRowid = null;
	  		ResultSet rs =null;
	  		String orsid = (String) operationContext.getValue(operationContext.ORS_ID);
	  		log.info("orsid : "  + orsid);
	  		  String sql = "SELECT GDA_DFND_AREA_PRNT_ROWID	FROM MDM_INFM_SMDS.C_GDA_DFND_AREA_REL WHERE TYP_TYPE_CD = 'GDA_REL.TRTY_IN_CTRY' AND hub_state_ind = 1 AND GDA_DFND_AREA_CHLD_ROWID='"+parentRowidObject+"'";
	  	      Connection con = getDatabaseConnection(orsid.toLowerCase());
	  	      log.info("con : "  + con);
	  	      log.info("sql : "  + sql);
	  	      try {
	  	      Statement stmt = con.createStatement();
	  	      rs = stmt.executeQuery(sql);
	  	  if (rs.next()) {
	  	      CountryRowid = rs.getString("GDA_DFND_AREA_PRNT_ROWID");
	  	  }
	  	      rs.close();
	  	      stmt.close();
	  	       con.close();
	  	      }catch (Exception e) {
	  			// TODO: handle exception
	  	    	  log.info("Inside getCountryfromState exception : "  + parentRowidObject);
	  	    	  e.printStackTrace();
	  		}
	  	   return CountryRowid;
	  		
	  	}
	      
	      
	      public static String getStatefromCity(String parentRowidObject,OperationContext operationContext){
		    	 
		        String StateRowid = null;
		  		ResultSet rs =null;
		  		String orsid = (String) operationContext.getValue(operationContext.ORS_ID);
		  		log.info("orsid : "  + orsid);
		  		  String sql = "SELECT GDA_DFND_AREA_PRNT_ROWID	FROM MDM_INFM_SMDS.C_GDA_DFND_AREA_REL WHERE TYP_TYPE_CD = 'GDA_REL.CITY_IN_TRTY' AND hub_state_ind = 1 AND GDA_DFND_AREA_CHLD_ROWID='"+parentRowidObject+"'";
		  	      Connection con = getDatabaseConnection(orsid.toLowerCase());
		  	      log.info("con : "  + con);
		  	      try {
		  	      Statement stmt = con.createStatement();
		  	      rs = stmt.executeQuery(sql);
		  	    if (rs.next()) {
		  	    StateRowid = rs.getString("GDA_DFND_AREA_PRNT_ROWID");
		  	    }
		  	      rs.close();
		  	      stmt.close();
		  	       con.close();
		  	      }catch (Exception e) {
		  			// TODO: handle exception
		  	    	  log.info("Inside getStatefromCity exception : "  + parentRowidObject);
		  	    	  e.printStackTrace();
		  		}
		  	   return StateRowid;
		  		
		  	}
	
	      /*********************Richa Query to get City parent for Subcity**********/
	      public static String getParentfromCity(String parentRowidObject,OperationContext operationContext){
		    	 
		        String StateRowid = null;
		  		ResultSet rs =null;
		  		String orsid = (String) operationContext.getValue(operationContext.ORS_ID);
		  		log.info("orsid : "  + orsid);
		  		  String sql = "SELECT GDA_DFND_AREA_PRNT_ROWID	FROM MDM_INFM_SMDS.C_GDA_DFND_AREA_REL WHERE TYP_TYPE_CD in ('GDA_REL.CITY_IN_TRTY','GDA_REL.CITY_IN_CTRY') AND hub_state_ind = 1 AND GDA_DFND_AREA_CHLD_ROWID='"+parentRowidObject+"'";
		  	      Connection con = getDatabaseConnection(orsid.toLowerCase());
		  	      log.info("con : "  + con);
		  	      try {
		  	      Statement stmt = con.createStatement();
		  	      rs = stmt.executeQuery(sql);
		  	    if (rs.next()) {
		  	    StateRowid = rs.getString("GDA_DFND_AREA_PRNT_ROWID");
		  	    }
		  	      rs.close();
		  	      stmt.close();
		  	       con.close();
		  	      }catch (Exception e) {
		  			// TODO: handle exception
		  	    	  log.info("Inside getStatefromCity exception : "  + parentRowidObject);
		  	    	  e.printStackTrace();
		  		}
		  	   return StateRowid;
		  		
		  	}
	      /*********************Richa Query to get City parent for Subcity**********/
	      public static String getCountryfromCity(String parentRowidObject,OperationContext operationContext){
		    	 
		        String CountryRowid = null;
		  		ResultSet rs =null;
		  		String orsid = (String) operationContext.getValue(operationContext.ORS_ID);
		  		log.info("orsid : "  + orsid);
		  		  String sql = "SELECT GDA_DFND_AREA_PRNT_ROWID	FROM MDM_INFM_SMDS.C_GDA_DFND_AREA_REL WHERE TYP_TYPE_CD = 'GDA_REL.CITY_IN_CTRY' AND hub_state_ind = 1 AND GDA_DFND_AREA_CHLD_ROWID='"+parentRowidObject+"' and rownum = 1";
		  	      Connection con = getDatabaseConnection(orsid.toLowerCase());
		  	      log.info("con : "  + con);
		  	      try {
		  	      Statement stmt = con.createStatement();
		  	      rs = stmt.executeQuery(sql);
		  	    if (rs.next()) {
		  	    	CountryRowid = rs.getString("GDA_DFND_AREA_PRNT_ROWID");
		  	    }
		  	      rs.close();
		  	      stmt.close();
		  	       con.close();
		  	      }catch (Exception e) {
		  			// TODO: handle exception
		  	    	  log.info("Inside getStatefromCity exception : "  + parentRowidObject);
		  	    	  e.printStackTrace();
		  		}
		  	   return CountryRowid;
		  		
		  	}
	      
	      public static int IsCityROWIDRelCountry(String parentRowidObject,OperationContext operationContext){	    	  
	    	  
	    	    int cnt = 0;
		  		ResultSet rs =null;
		  		String orsid = (String) operationContext.getValue(operationContext.ORS_ID);
		  		log.info("orsid : "  + orsid);
		  		  String sql = "SELECT count(*)	as c1 FROM MDM_INFM_SMDS.C_GDA_DFND_AREA_REL WHERE TYP_TYPE_CD = 'GDA_REL.CITY_IN_CTRY'  AND hub_state_ind = 1 AND GDA_DFND_AREA_CHLD_ROWID='"+parentRowidObject+"'";
		  	      Connection con = getDatabaseConnection(orsid.toLowerCase());
		  	      log.info("con : "  + con);
		  	      try {
		  	      Statement stmt = con.createStatement();
		  	      rs = stmt.executeQuery(sql);
		  	    if (rs.next()) {		
		  	      cnt  = rs.getInt("c1");
		  	    }
		  	      rs.close();
		  	      stmt.close();
		  	       con.close();
		  	      }catch (Exception e) {
		  			// TODO: handle exception
		  	    	  log.info("Inside getStatefromCity exception : "  + parentRowidObject);
		  	    	  e.printStackTrace();
		  		}
		  	   return cnt;
	    	  
	      }
	/******************    200293: Add the Alternate Code to the parent start 
	 * @param conn 
	 * @param mdmORSid ***********************/

		public static boolean isBDAPoolBDA(Object bdaName, String mdmORSid) {	    	  
	    	  log.info("Inside isBDAPoolBDA method");
    	    String bdaType="";
	  		ResultSet rs =null;
	  		log.info("orsid : "  + mdmORSid);
	  		  String sql = "SELECT BDA_TYPE_CD FROM C_GDA_BDA WHERE GDA_DFND_AREA_ROWID IN(SELECT ROWID_OBJECT FROM C_GDA_DFND_AREA WHERE ACTIVE_FLAG ='Y' AND  NAME='"+bdaName+"')";
	  		  log.info("SQL Statement to check POOL BDA is :: "+sql);
	  	      Connection con = getDatabaseConnection(mdmORSid.toLowerCase());
	  	      log.info("con : "  + con);
	  	      try {
	  	      Statement stmt = con.createStatement();
	  	      rs = stmt.executeQuery(sql);
	  	    if (rs.next()) {		
	  	    	bdaType  = rs.getString("BDA_TYPE_CD").trim();
	  	    }
	  	      rs.close();
	  	      stmt.close();
	  	       con.close();
	  	      }catch (Exception e) {
	  	    	  log.info("Inside isBDAPoolBDA exception : "  + bdaName);
	  	    	  e.printStackTrace();
	  		}
if(bdaType.equalsIgnoreCase("POOL")) {
	return true;
}
	else {
		return false;
		}
}

		public static Object getBDAAltCodes(String parentRowidObject,OperationContext operationContext,String GeoEntityType) {
			ResultSet rs =null;
			String orsid = (String) operationContext.getValue(operationContext.ORS_ID);
			log.info("orsid : "  + orsid);
			  String sql = "SELECT  code,typ_type_rowid FROM MDM_INFM_SMDS.c_alt_code WHERE hub_state_ind = 1 AND gda_dfnd_area_rowid='"+parentRowidObject+"'";
		      Connection con = getDatabaseConnection(orsid.toLowerCase());
		      log.info("con : "  + con);
		      try {
		      Statement stmt = con.createStatement();
		      rs = stmt.executeQuery(sql);
		      switch(GeoEntityType) {
				case "COUNTRY" :
					List<com.maersk.mdm.taskdata.jaxb.Geography.Country.BDA.BDAType.AlternateCodes.AlternateCode> countryParentAltCode= new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.Country.BDA.BDAType.AlternateCodes.AlternateCode>();
					  if (rs!=null){
							while(rs.next()) {
								com.maersk.mdm.taskdata.jaxb.Geography.Country.BDA.BDAType.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.Country.BDA.BDAType.AlternateCodes.AlternateCode();
								altCode.setCode(rs.getString("CODE"));
								String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
								log.info("typeTypeRowid is ::" + typeTypeRowid);
								String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
								String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
								log.info("altCodeValue is ::"+altCodeValue);
								altCode.setCodeType(altCodeValue);
								log.info("altCode.code" + altCode.getCode());
								log.info("altCode.CodeType" + altCode.getCodeType());
								countryParentAltCode.add(altCode);	
							}
							return countryParentAltCode;
					  }
					break;
					
				case "CITY" :
					List<com.maersk.mdm.taskdata.jaxb.Geography.City.BDA.BDAType.AlternateCodes.AlternateCode> cityParentAltCode= new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.City.BDA.BDAType.AlternateCodes.AlternateCode>();
					if (rs!=null){
						while(rs.next()) {
							com.maersk.mdm.taskdata.jaxb.Geography.City.BDA.BDAType.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.City.BDA.BDAType.AlternateCodes.AlternateCode();
							altCode.setCode(rs.getString("CODE"));
							String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
							log.info("typeTypeRowid is ::" + typeTypeRowid);
							String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
							String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
							altCode.setCodeType(altCodeValue);
							log.info("altCode.code" + altCode.getCode());
							log.info("altCode.CodeType" + altCode.getCodeType());
							cityParentAltCode.add(altCode);
						}	
						return cityParentAltCode;
					}
					break;

				case "BUSINESSDEFINEDAREA" :
					List<com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.Locations.Location.AlternateCodes.AlternateCode> BDAParentAltCode= new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.Locations.Location.AlternateCodes.AlternateCode>();
					if (rs!=null){
						while(rs.next()) {
							com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.Locations.Location.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.BusinessDefinedArea.Locations.Location.AlternateCodes.AlternateCode();
							altCode.setCode(rs.getString("CODE"));
							String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
							log.info("typeTypeRowid is ::" + typeTypeRowid);
							String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
							String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
							altCode.setCodeType(altCodeValue);
							log.info("altCode.code" + altCode.getCode());
							log.info("altCode.CodeType" + altCode.getCodeType());
							BDAParentAltCode.add(altCode);
						}	
						return BDAParentAltCode;
					}
					break;
					
					case "POSTALCODE" :
						List<com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.BDA.BDAType.AlternateCodes.AlternateCode> PostalCDParentAltCode= new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.BDA.BDAType.AlternateCodes.AlternateCode>();
						if (rs!=null){
							while(rs.next()) {
								com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.BDA.BDAType.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.PostalCode.BDA.BDAType.AlternateCodes.AlternateCode();
								altCode.setCode(rs.getString("CODE"));
								String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
								log.info("typeTypeRowid is ::" + typeTypeRowid);
								String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
								String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
								altCode.setCodeType(altCodeValue);
								log.info("altCode.code" + altCode.getCode());
								log.info("altCode.CodeType" + altCode.getCodeType());
								PostalCDParentAltCode.add(altCode);
							}	
							return PostalCDParentAltCode;
						}
						break;
						
					case "SITE" :
						List<com.maersk.mdm.taskdata.jaxb.Geography.Site.BDA.BDAType.AlternateCodes.AlternateCode> siteParentAltCode= new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.Site.BDA.BDAType.AlternateCodes.AlternateCode>();
						if (rs!=null){
							while(rs.next()) {
								com.maersk.mdm.taskdata.jaxb.Geography.Site.BDA.BDAType.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.Site.BDA.BDAType.AlternateCodes.AlternateCode();
								altCode.setCode(rs.getString("CODE"));
								String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
								log.info("typeTypeRowid is ::" + typeTypeRowid);
								String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
								String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
								altCode.setCodeType(altCodeValue);
								log.info("altCode.code" + altCode.getCode());
								log.info("altCode.CodeType" + altCode.getCodeType());
								siteParentAltCode.add(altCode);
							}	
							return siteParentAltCode;
						}
						break;
						
					case "STATEPROV" :
						List<com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.BDA.BDAType.AlternateCodes.AlternateCode> stateParentAltCode= new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.BDA.BDAType.AlternateCodes.AlternateCode>();
						if (rs!=null){
							while(rs.next()) {
								com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.BDA.BDAType.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.StateProvince.BDA.BDAType.AlternateCodes.AlternateCode();
								altCode.setCode(rs.getString("CODE"));
								String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
								log.info("typeTypeRowid is ::" + typeTypeRowid);
								String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
								String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
								altCode.setCodeType(altCodeValue);
								log.info("altCode.code" + altCode.getCode());
								log.info("altCode.CodeType" + altCode.getCodeType());
								stateParentAltCode.add(altCode);
							}	
							return stateParentAltCode;
						}
						break;
					/*case "SUBCITY" :
						List<com.maersk.mdm.taskdata.jaxb.Geography.CitySubArea.BDA.BDAType.AlternateCodes.AlternateCode> subCityAltCode= new ArrayList<com.maersk.mdm.taskdata.jaxb.Geography.CitySubArea.BDA.BDAType.AlternateCodes.AlternateCode>();
						if (rs!=null){
							while(rs.next()) {
								com.maersk.mdm.taskdata.jaxb.Geography.CitySubArea.BDA.BDAType.AlternateCodes.AlternateCode altCode= new com.maersk.mdm.taskdata.jaxb.Geography.CitySubArea.BDA.BDAType.AlternateCodes.AlternateCode();
								altCode.setCode(rs.getString("CODE"));
								String typeTypeRowid = Utility.validate(rs.getString("typ_type_rowid")).toString();
								log.info("typeTypeRowid is ::" + typeTypeRowid);
								String getAltCodeQuery = Utility.getDatabaseLookupValue("C_TYP_TYPE", typeTypeRowid, "NAME", "ROWID_OBJECT");
								String altCodeValue = Utility.getValueFromDB(operationContext, getAltCodeQuery);
								altCode.setCodeType(altCodeValue);
								log.info("altCode.code" + altCode.getCode());
								log.info("altCode.CodeType" + altCode.getCodeType());
								subCityAltCode.add(altCode);
							}	
							return subCityAltCode;
						}
						break;*/
					}
		     
		    	
		      }catch (Exception e) {
				// TODO: handle exception
		    	  log.info("Inside getParentAltCodes exception : "  + parentRowidObject);
		    	  e.printStackTrace();
			}
		      return rs;
			
		}

		public static String getMaxPeriodDate(OperationContext operationContext, String dayLightSavingTime, String columnName) {
			
			String query="SELECT MAX ("+columnName+") AS \"Max Date\" \n" + 
					"FROM C_TDS_DSTD WHERE TDS_DST_ROWID='"+dayLightSavingTime+"' ";
		    String orsId = (String) operationContext.getValue(OperationContext.ORS_ID);
		    ResultSet rs = null;
		    Statement stmt = null;
		    String obj = "";
		    connection = getDatabaseConnection(orsId.toLowerCase());
		    try {
		      stmt = connection.createStatement();
		      rs = stmt.executeQuery(query);
		      if (rs.next()) {
		        obj = rs.getString(1);
		      }
		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      try {
		        rs.close();
		        stmt.close();
		        connection.close();
		      } catch (SQLException e) {
		        e.printStackTrace();
		        log.info("Exception caught in method getMaxPeriodDate ::"+e.getMessage());
		      }
		    }
		    return obj;

		  } 
		
		 public static String changeInputCreateDate(String createDate) throws ParseException {
		     	SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			      SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			      Date parsein = inputFormat.parse(createDate);
			      String formattedDate = outputFormat.format(parsein);
			      return formattedDate;
		    }
}
