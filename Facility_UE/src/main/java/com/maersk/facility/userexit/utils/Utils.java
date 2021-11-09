package com.maersk.facility.userexit.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.maersk.facility.userexit.util.PublishFacilityViaMQ;
import com.maersk.facility.userexit.util.PublishService;
import com.siperian.bdd.userexits.datamodel.BDDObject;
import com.siperian.bdd.userexits.operations.OperationContext;

public class Utils {

	private static Logger log = Logger.getLogger(Utils.class);

	public static String changeDateFormat(String date) {
		try {
			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			SimpleDateFormat inputFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
	
	public static Object validate(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj;
	}

	public static String getCurrentDateInFormat() {
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date now = new Date();
		String strDate = outputFormat.format(now);
		return strDate;
	}
	
	public static String getRelationShipDate(String tableName,String rowidObject,String returnColumn, String filterColumn) {
		String returnValue = "SELECT "+returnColumn+" FROM "+tableName+"  WHERE "+filterColumn +"='"+rowidObject+"'";
		log.info("SQL QUERY IS ::"+returnValue);
		return returnValue;
	}
	public static String getDatabaseLookupValue(String tableName,String rowidObject,String returnColumn, String filterColumn) {
		String returnValue = "SELECT "+returnColumn+" FROM "+tableName+"  WHERE "+filterColumn +"='"+rowidObject+"'";
		log.info("SQL QUERY IS ::"+returnValue);
		return returnValue;
	}
	public static String getUScountryrowid(String tableName,String rowidObject,String returnColumn, String filterColumn) {
		String returnValue1 = "select NAME as CountryName from C_GDA_DFND_AREA where ROWID_OBJECT in (select CTRY_ROWID from C_CTM_PSTL_ADDR where CTRY_ROWID ='1501366') and C_GDA_DFND_AREA.TYP_TYPE_CD ='GDA.COUNTRY'";
		log.info("SQL QUERY IS ::"+returnValue1);
		return returnValue1;
	}
	
	private static Connection getConnection(String orsId) {
		try {
			String dataSource = "jdbc/siperian-" + orsId + "-ds";
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(dataSource);
			Connection connection = ds.getConnection();
			return connection;
		} catch (Exception e) {
			return null;
		}
	}
		
		public static String getValueFromDB(OperationContext operationContext, String query) {

			String orsId = (String) operationContext.getValue(OperationContext.ORS_ID);
			ResultSet rs=null;
			Statement stmt=null;
			String obj="";
			Connection con = getConnection(orsId.toLowerCase());
			try {
				 stmt = con.createStatement();
				 log.info("Statement to run is ::" +stmt);
				 rs = stmt.executeQuery(query);
				if (rs.next()) {
					 obj = rs.getString(1);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					rs.close();
					stmt.close();
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info("Exception in running query ::" +query);
				}
			}
			return obj;

		}
		
		public static String getValueFromDB1(String orsId, String query) {

			log.info("Inside getValueFromDB");
			ResultSet rs=null;
			Statement stmt=null;
			String obj="";
			Connection con = getConnection(orsId.toLowerCase());
			log.info("con:" + con );;
			try {
				 stmt = con.createStatement();
				 log.info("Statement to run is ::");
				 rs = stmt.executeQuery(query);
				if (rs.next()) {
					 obj = rs.getString(1);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					rs.close();
					stmt.close();
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info("Exception in running query ::" +query);
				}
			}
			return obj;

		}
		



		public static String getRelDateFromTable(BDDObject bddObject, String TableName) throws ParseException {
			String validThruDate="";
			if (bddObject != null) {
				Object getValidThruDt=bddObject.getValue(TableName+"|VALID_THRU_DT");
				if(getValidThruDt==null || getValidThruDt=="")
					return validThruDate;
				else
				 validThruDate=Utils.validate(getValidThruDt).toString();
				
				return validThruDate;
			}
			return validThruDate;
		}
		
		public static PublishService getMQInterface() {
		    PublishService mqInterface;
		    mqInterface = new PublishFacilityViaMQ();
		    return mqInterface;
		  }
		public static String changeInputCreateDate(String createDate) throws ParseException {
			try{
	     	SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		      SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		      Date parsein = inputFormat.parse(createDate);
		      String formattedDate = outputFormat.format(parsein);
		      return formattedDate;
	    }
		catch(Exception ex) {
			ex.printStackTrace();
			log.info(ex.getCause());
			return createDate;
			}
}
}
