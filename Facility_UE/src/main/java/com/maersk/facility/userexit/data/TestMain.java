package com.maersk.facility.userexit.data;

import java.sql.Connection;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.InitialDirContext;
import javax.sql.DataSource;


public class TestMain {

	public static void main(String agrs[]) {/*
											 * 
											 * 
											 * String password = "Weblogic2018int"; Hashtable<String, String> env = new
											 * Hashtable<String, String>(); env.put(Context.INITIAL_CONTEXT_FACTORY,
											 * "weblogic.jndi.WLInitialContextFactory"); env.put(Context.PROVIDER_URL,
											 * "t3://scrbsmddk002093:7001/"); env.put(Context.SECURITY_AUTHENTICATION,
											 * "simple"); env.put(Context.SECURITY_PRINCIPAL, "weblogic"); // replace
											 * with user DN env.put(Context.SECURITY_CREDENTIALS, password);
											 * 
											 * 
											 * String Region_Code="TAMIL NADU"; String CTRY_ROWID="145668"; Connection
											 * conn = getConnection("SMDSINT-MDM_INFM_SMDS",env); String region=null;
											 * String countryRowid=null; String TRTY_CD=null; if(Region_Code!=null &&
											 * CTRY_ROWID!=null) { region=Region_Code.trim();
											 * countryRowid=CTRY_ROWID.trim(); String
											 * regionQuery="SELECT TRTY.ROWID_OBJECT AS TRTY_ROWID, TRTY.NAME AS TRTY_NAME,\r\n"
											 * +
											 * "     ALT_CODE.CODE AS TRTY_CODE, CTRY.ROWID_OBJECT AS CTRY_ROWID, TYP_TYPE.CODE AS TYP_TYPE_CODE,\r\n"
											 * +
											 * "     REL.HUB_STATE_IND FROM C_GDA_DFND_AREA TRTY INNER JOIN C_GDA_DFND_AREA_REL\r\n"
											 * +
											 * "     REL ON TRTY.ROWID_OBJECT = REL.GDA_DFND_AREA_CHLD_ROWID INNER JOIN\r\n"
											 * +
											 * "     C_GDA_DFND_AREA CTRY ON CTRY.ROWID_OBJECT = REL.GDA_DFND_AREA_PRNT_ROWID\r\n"
											 * +
											 * "     INNER JOIN C_ALT_CODE ALT_CODE ON TRTY.ROWID_OBJECT = ALT_CODE.GDA_DFND_AREA_ROWID\r\n"
											 * +
											 * "     INNER JOIN C_TYP_TYPE TYP_TYPE ON TYP_TYPE.ROWID_OBJECT = ALT_CODE.TYP_TYPE_ROWID\r\n"
											 * + "     WHERE REL.TYP_TYPE_CD = 'GDA_REL.TRTY_IN_CTRY'\r\n" +
											 * "     AND TRTY.TYP_TYPE_CD = 'GDA.STATE/PROV'\r\n" +
											 * "     AND TRTY.ACTIVE_FLAG = 'Y' and TYP_TYPE.CODE='ALT_CODE.ISO_TRTY' "
											 * + "AND CTRY.ROWID_OBJECT='"
											 * +countryRowid+"' AND UPPER(TRTY.NAME) LIKE UPPER('%"+region+"%');";
											 * 
											 * 
											 * try { PreparedStatement stmt = null; stmt =
											 * conn.prepareStatement(regionQuery); ResultSet rs = stmt.executeQuery();
											 * while (rs.next()) { TRTY_CD = rs.getString("TRTY_CD"); }
											 * 
											 * } catch (Exception e) { e.printStackTrace(); } }
											 */
			String isMaersk="Y";
			if(isMaersk.equalsIgnoreCase("N"))
			{
				System.out.println("ERROR : IsMaersk Value is "+isMaersk);
			}
	}
	private static Connection getConnection(String orsId, Hashtable<String, String> env) {
		 
		try {
			String dataSource = "jdbc/siperian-" + orsId + "-ds";
			Context ctx = new InitialDirContext(env);

		
			DataSource ds = (DataSource) ctx.lookup(dataSource);
			Connection connection = ds.getConnection();
			return connection;
		} catch (Exception e) {
			return null;
		}
	}	
	
}
