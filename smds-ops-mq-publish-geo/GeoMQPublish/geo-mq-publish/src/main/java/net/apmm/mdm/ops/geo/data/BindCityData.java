package net.apmm.mdm.ops.geo.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.config.DataBaseQueries;
import net.apmm.mdm.ops.geo.jaxb.Geography;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import net.apmm.mdm.ops.geo.jaxb.Geography.City;
import net.apmm.mdm.ops.geo.jaxb.Geography.City.AlternateCodes;
import net.apmm.mdm.ops.geo.jaxb.Geography.City.AlternateCodes.AlternateCode;
import net.apmm.mdm.ops.geo.jaxb.Geography.City.AlternateNames;
import net.apmm.mdm.ops.geo.jaxb.Geography.City.AlternateNames.AlternateName;
import net.apmm.mdm.ops.geo.jaxb.Geography.City.BDA;
import net.apmm.mdm.ops.geo.jaxb.Geography.City.BDA.BDAType;
import net.apmm.mdm.ops.geo.jaxb.Geography.City.Country;
import net.apmm.mdm.ops.geo.jaxb.Geography.City.Parent;
import net.apmm.mdm.ops.geo.jaxb.Geography.City.SubCityParent;

//import maersk.GeoServiceTask.Util.Utility;
@Slf4j
public class BindCityData {
    private static Geography geographyEvent;


    @Autowired
    public BindCityData(JdbcTemplate smdsJdbcTemplate) {
        this.smdsJdbcTemplate = smdsJdbcTemplate;
    }

    @Autowired
    private final JdbcTemplate smdsJdbcTemplate;

    public Geography bindAllCityDetails(String rowid) {

        if(rowid!=null) {

            String cityQuery = DataBaseQueries.cityQuery.replace("GEOROWNUMBER",rowid);
            City cityEvent = new City();

            try {

                smdsJdbcTemplate.query(cityQuery, new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        try {

                            String name = rs.getString("NAME");
                            if (name != null) {
                                cityEvent.setName(name);   //Name
                            } else {
                                cityEvent.setName("");
                            }

                            String status = rs.getString("STATUS");
                            if (status != null) {
                                cityEvent.setStatus(status);
                            }//Status
                            else
                            {
                                cityEvent.setStatus("");
                            }


                            String validFrom = rs.getString("VALIDFROM");
                            if (validFrom != null) {
                                cityEvent.setValidFrom(validFrom);
                            }//Status
                            else
                            {
                                cityEvent.setValidFrom("");
                            }

                            String validTo = rs.getString("VALIDTO");
                            if (validTo != null) {
                                cityEvent.setValidTo(validTo);
                            }//Status
                            else
                            {
                                cityEvent.setValidTo("");
                            }

                            String latitude = rs.getString("LATITUDE");
                            if (latitude != null) {
                                cityEvent.setLatitude(latitude);
                            }//Status
                            else
                            {
                                cityEvent.setLatitude("");
                            }


                            String longitude = rs.getString("LONGITUDE");
                            if (longitude != null) {
                                cityEvent.setLongitude(longitude);
                            }//Status
                            else
                            {
                                cityEvent.setLongitude("");
                            }


                            String timezone = rs.getString("TIMEZONE");
                            if (timezone != null) {
                                cityEvent.setTimeZone(timezone);
                            }//Status
                            else
                            {
                                cityEvent.setTimeZone("");
                            }

                            String daylightSavingTime = rs.getString("DAYLIGHTSAVINGTIME");
                            if(daylightSavingTime!=null) {
                                cityEvent.setDaylightSavingTime(daylightSavingTime);
                            }
                            else
                            {
                                cityEvent.setDaylightSavingTime("");/// Daylight savings
                            }

                            String daylightSavingStart = rs.getString("DAYLIGHTSAVINGSTART");
                            if (daylightSavingStart!=null) {
                                cityEvent.setDaylightSavingStart(daylightSavingStart);
                            }
                            else
                            {
                                cityEvent.setDaylightSavingStart("");
                            }

                            String DaylightSavingEnd = rs.getString("DAYLIGHTSAVINGEND");
                            if(DaylightSavingEnd!=null) {
                                cityEvent.setDaylightSavingEnd(DaylightSavingEnd);
                            }
                            else
                            {
                                cityEvent.setDaylightSavingEnd("");
                            }

                            String daylightSavingShiftMinutes = rs.getString("DAYLIGHTSAVINGSHIFTMINUTES");
                            if(daylightSavingShiftMinutes!=null) {
                                cityEvent.setDaylightSavingShiftMinutes(daylightSavingShiftMinutes);
                            }
                            else
                            {
                                cityEvent.setDaylightSavingShiftMinutes("");
                            }


                            String utOffSetMinutes = rs.getString("UTCOFFSETMINUTES");
                            if(utOffSetMinutes!=null)
                            {
                                cityEvent.setUtOffSetMinutes(utOffSetMinutes);
                            }
                            else
                            {
                                cityEvent.setUtOffSetMinutes("");
                            }

                            String description = rs.getString("DESCRIPTION");
                            if(description!=null) {
                                cityEvent.setDescription(description);   ///Description
                            }
                            else
                            {
                                cityEvent.setDescription("");
                            }


                            String workaroundReason = rs.getString("WORKAROUNDREASON");
                            if(workaroundReason!=null) {
                                cityEvent.setWorkaroundReason(workaroundReason);
                            }///workaroundreason
                            else
                            {
                                cityEvent.setWorkaroundReason("");
                            }

                            String olsonTimezone = rs.getString("OLSONTIMEZONE");
                            if(olsonTimezone!=null) {
                                cityEvent.setOlsonTimezone(olsonTimezone);
                            }///olson time
                            else
                            {
                                cityEvent.setOlsonTimezone("");
                            }

                            String portFlag = rs.getString("PORTFLAG");
                            if(portFlag!=null) {
                                cityEvent.setPortFlag(portFlag);
                            }///port flag
                            else
                            {
                                cityEvent.setPortFlag("");
                            }


                        }catch(SQLException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }catch(Exception e) {
                e.printStackTrace();
            }

            AlternateCodes cityAlternateCode = new AlternateCodes();
            List<AlternateCode> cityAlternateCodeValues = new ArrayList<AlternateCode>();
            String geoAltCodeQuery = DataBaseQueries.geoAltCodeQuery.replace("GEOROWNUMBER",rowid);

           try {
                smdsJdbcTemplate.query(geoAltCodeQuery, new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        try {

                            log.info("codetype:"+rs.getString("CODETYPE")+" code:"+rs.getString("CODE"));
                                AlternateCode altCode = new AlternateCode();

                                String altcodeType = rs.getString("CODETYPE");

                                if(altcodeType!=null)
                                {
                                    altCode.setCodeType(altcodeType);
                                }
                                else
                                {
                                    altCode.setCodeType("");
                                }


                            String altcode = rs.getString("CODE");

                            if(altcode!=null)
                            {
                                altCode.setCode(altcode);
                            }
                            else
                            {
                                altCode.setCode("");
                            }

                                cityAlternateCodeValues.add(altCode);



                        }catch(SQLException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }catch(Exception e) {
                e.printStackTrace();
            }

            cityAlternateCode.setAlternateCode(cityAlternateCodeValues);
            cityEvent.setAlternateCodes(cityAlternateCode);


            AlternateNames cityAlternateName = new AlternateNames();
            List<AlternateName> cityAlternateNameValues = new ArrayList<AlternateName>();
            String geoAltNameQuery = DataBaseQueries.geoAltNameQuery.replace("GEOROWNUMBER",rowid);

            try {
                smdsJdbcTemplate.query(geoAltNameQuery, new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        try {
                            AlternateName altName = new AlternateName();

                            String geoAltName = rs.getString("NAME");

                            if (geoAltName != null) {
                                altName.setName(geoAltName);

                            }
                            else
                            {
                                altName.setName("");
                            }


                            String geoAltStatus = rs.getString("STATUS");

                            if (geoAltStatus != null) {
                                altName.setStatus(geoAltStatus);

                            }
                            else
                            {
                                altName.setStatus("");
                            }


                            String geoAltDescription = rs.getString("DESCRIPTION");

                            if (geoAltDescription != null) {
                                altName.setDescription(geoAltDescription);

                            }
                            else
                            {
                                altName.setDescription("");
                            }

                            cityAlternateNameValues.add(altName);
                        }catch(SQLException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }catch(Exception e) {
                e.printStackTrace();
            }

            cityAlternateName.setAlternateName(cityAlternateNameValues);
            cityEvent.setAlternateNames(cityAlternateName);


            /*-----------------------------------------alt code and name for city done--------------------------------------------------------*/

            /*-----------------------------------------City Parent and its altCode--------------------------------------------------------*/

            String cityParentQuery = DataBaseQueries.cityParentQuery.replace("GEOROWNUMBER",rowid);
            Parent cityParent = new Parent();

            try {
                smdsJdbcTemplate.query(cityParentQuery, new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        try {
                            String cityParentname = rs.getString("NAME");

                            if (cityParentname != null) {
                                cityParent.setName(cityParentname);

                            }
                            else
                            {
                                cityParent.setName("");
                            }


                            String cityParenttype = rs.getString("TYPE");

                            if (cityParenttype != null) {
                                cityParent.setType(cityParenttype);

                            }
                            else
                            {
                                cityParent.setType("");
                            }



                        }catch(SQLException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }catch(Exception e) {
                e.printStackTrace();
            }


            Parent.AlternateCodes cityParentAltCodes = new Parent.AlternateCodes();
            String cityParentAltCodeQuery = DataBaseQueries.cityParentAltCodeQuery.replace("GEOROWNUMBER", rowid);
            List<Parent.AlternateCodes.AlternateCode> cityParentAlternateCodeValues = new ArrayList<Parent.AlternateCodes.AlternateCode>();
            try {
                smdsJdbcTemplate.query(cityParentAltCodeQuery, new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        try {

                            Parent.AlternateCodes.AlternateCode parentAltCode = new Parent.AlternateCodes.AlternateCode();

                            String cityParentAltCodeType = rs.getString("CODETYPE");

                            if(cityParentAltCodeType!=null)
                            {
                                parentAltCode.setCodeType(cityParentAltCodeType);
                            }
                            else
                            {
                                parentAltCode.setCodeType("");
                            }


                            String cityParentAltCode = rs.getString("CODE");

                            if(cityParentAltCode!=null)
                            {
                                parentAltCode.setCode(cityParentAltCode);
                            }
                            else
                            {
                                parentAltCode.setCode("");
                            }

                            cityParentAlternateCodeValues.add(parentAltCode);

                        }catch(SQLException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }catch(Exception e) {
                e.printStackTrace();
            }
            cityParentAltCodes.setAlternateCode(cityParentAlternateCodeValues);
            cityParent.setAlternateCodes(cityParentAltCodes);
            cityEvent.setParent(cityParent);

            /*-----------------------------------------City Parent and its altCode done--------------------------------------------------------*/

            /*-----------------------------------------City Country and its altCode--------------------------------------------------------*/

            String cityCountryQuery = DataBaseQueries.cityCountryQuery.replace("GEOROWNUMBER",rowid);
            Country cityCountry = new Country();

            try {
                smdsJdbcTemplate.query(cityCountryQuery, new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        try {

                            String cityCountryName = rs.getString("NAME");

                            if (cityCountryName != null) {
                                cityCountry.setName(cityCountryName);

                            }
                            else
                            {
                                cityCountry.setName("");
                            }


                        }catch(SQLException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }catch(Exception e) {
                e.printStackTrace();
            }
            String cityCountryAltCodeQuery = DataBaseQueries.cityCountryAltCodeQuery.replace("GEOROWNUMBER",rowid);

            Country.AlternateCodes cityCountryAltCodes = new Country.AlternateCodes();
            List<Country.AlternateCodes.AlternateCode> cityCountryAltCodeValues = new ArrayList<Country.AlternateCodes.AlternateCode>();
            try {
                smdsJdbcTemplate.query(cityCountryAltCodeQuery, new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        try {

                            Country.AlternateCodes.AlternateCode countryAltCode = new Country.AlternateCodes.AlternateCode();


                            String CityCountryAltCodeType = rs.getString("CODETYPE");

                            if(CityCountryAltCodeType!=null)
                            {
                                countryAltCode.setCodeType(CityCountryAltCodeType);
                            }
                            else
                            {
                                countryAltCode.setCodeType("");
                            }


                            String CityCountrycode = rs.getString("CODE");

                            if(CityCountrycode!=null)
                            {
                                countryAltCode.setCode(CityCountrycode);
                            }
                            else
                            {
                                countryAltCode.setCode("");
                            }

                            cityCountryAltCodeValues.add(countryAltCode);

                        }catch(SQLException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }catch(Exception e) {
                e.printStackTrace();
            }
            cityCountryAltCodes.setAlternateCode(cityCountryAltCodeValues);
            cityCountry.setAlternateCodes(cityCountryAltCodes);
            cityEvent.setCountry(cityCountry);
            /*-----------------------------------------City Country and its altCode done--------------------------------------------------------*/


            /*-----------------------------------------City SubCityParent and its altCode--------------------------------------------------------*/


            /*String citySubCityParentQuery = DataBaseQueries.citySubCityParentQuery.replace("GEOROWNUMBER",rowid);
            SubCityParent citySubCityParent = new SubCityParent();
            try {
                smdsJdbcTemplate.query(citySubCityParentQuery, new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        try {
                            citySubCityParent.setName(rs.getString("NAME"));
                            citySubCityParent.setType(rs.getString("TYPE"));
                        }catch(SQLException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }catch(Exception e) {
                e.printStackTrace();
            }
            String citySubCityParentAltCodesQuery = DataBaseQueries.citySubCityParentAltCodesQuery.replace("GEOROWNUMBER",rowid);
            SubCityParent.AlternateCodes citySubCityAltCodes = new SubCityParent.AlternateCodes();
            List<SubCityParent.AlternateCodes.AlternateCode> citySubCityAltCodeValues = new ArrayList<SubCityParent.AlternateCodes.AlternateCode>();

            try {
                smdsJdbcTemplate.query(citySubCityParentAltCodesQuery, new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        try {

                            SubCityParent.AlternateCodes.AlternateCode citySCAltCode = new SubCityParent.AlternateCodes.AlternateCode();
                            citySCAltCode.setCode(rs.getString("CODE"));
                            citySCAltCode.setCodeType(rs.getString("CODETYPE"));
                            citySubCityAltCodeValues.add(citySCAltCode);

                        }catch(SQLException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }catch(Exception e) {
                e.printStackTrace();
            }

            citySubCityAltCodes.setAlternateCode(citySubCityAltCodeValues);
            citySubCityParent.setAlternateCodes(citySubCityAltCodes);
            cityEvent.setSubCityParent(citySubCityParent);
            /*-----------------------------------------City SubCityParent and its altCode done--------------------------------------------------------*/


            /*-----------------------------------------City BDA and its altCode--------------------------------------------------------*/
            BDA cityBDA= new BDA();
            List<BDAType> cityBDAType= new ArrayList<BDAType>();




            String cityBDAQuery = DataBaseQueries.cityBDAQuery.replace("GEOROWNUMBER", rowid);



            try {
                smdsJdbcTemplate.query(cityBDAQuery, new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        try {
                            BDAType bdaType = new BDAType();
                            String bdaParent = rs.getString("BDAROWID");

                            String cityBdaName = rs.getString("NAME");

                            if (cityBdaName != null) {
                                bdaType.setName(cityBdaName);

                            }
                            else
                            {
                                bdaType.setName("");
                            }


                            String cityBdaType = rs.getString("TYPE");

                            if (cityBdaType != null) {
                                bdaType.setType(cityBdaType);

                            }
                            else
                            {
                                bdaType.setType("");
                            }



                            BDAType.AlternateCodes bdaAltCodes = new BDAType.AlternateCodes();
                            List<BDAType.AlternateCodes.AlternateCode> bdaAltCodeValues = new ArrayList<BDAType.AlternateCodes.AlternateCode>();
                            String bdaAltCodeQuery = DataBaseQueries.bdaAltCodeQuery.replace("GEOROWNUMBER", bdaParent);
                            try {
                                smdsJdbcTemplate.query(bdaAltCodeQuery, new RowCallbackHandler() {

                                    @Override
                                    public void processRow(ResultSet rs) throws SQLException {
                                        try {

                                            BDAType.AlternateCodes.AlternateCode cityBDAAltCode = new BDAType.AlternateCodes.AlternateCode();


                                            String bdaAltCodeType = rs.getString("CODETYPE");

                                            if(bdaAltCodeType!=null)
                                            {
                                                cityBDAAltCode.setCodeType(bdaAltCodeType);
                                            }
                                            else
                                            {
                                                cityBDAAltCode.setCodeType("");
                                            }


                                            String bdaAltCode = rs.getString("CODE");

                                            if(bdaAltCode!=null)
                                            {
                                                cityBDAAltCode.setCode(bdaAltCode);
                                            }
                                            else
                                            {
                                                cityBDAAltCode.setCode("");
                                            }

                                            bdaAltCodeValues.add(cityBDAAltCode);



                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }

                                        bdaAltCodes.setAlternateCode(bdaAltCodeValues);
                                    }

                                });

                                bdaType.setAlternateCodes(bdaAltCodes);
                                cityBDAType.add(bdaType);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }





                            }catch(SQLException e) {
                            e.printStackTrace();
                        }

                    }

                });
            }catch(Exception e) {
                e.printStackTrace();
            }

            cityBDA.setBdaType(cityBDAType);
            cityEvent.setBDA(cityBDA);


          /*      BDAType.AlternateCodes bdaAltCodes = new BDAType.AlternateCodes();
                List<BDAType.AlternateCodes.AlternateCode> bdaAltCodeValues = new ArrayList<BDAType.AlternateCodes.AlternateCode>();
                String bdaAltCodeQuery = DataBaseQueries.bdaAltCodeQuery.replace("GEOROWNUMBER", rowid);
                try {
                    smdsJdbcTemplate.query(bdaAltCodeQuery, new RowCallbackHandler() {

                        @Override
                        public void processRow(ResultSet rs) throws SQLException {
                            try {

                                BDAType.AlternateCodes.AlternateCode cityBDAAltCode = new BDAType.AlternateCodes.AlternateCode();


                                String codeType = rs.getString("CODETYPE");

                                if(codeType!=null)
                                {
                                    cityBDAAltCode.setCodeType(codeType);
                                }
                                else
                                {
                                    cityBDAAltCode.setCodeType("");
                                }


                                String code = rs.getString("CODE");

                                if(code!=null)
                                {
                                    cityBDAAltCode.setCode(code);
                                }
                                else
                                {
                                    cityBDAAltCode.setCode("");
                                }

                                bdaAltCodeValues.add(cityBDAAltCode);

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }

                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }*/



            cityBDA.setBdaType(cityBDAType);
            cityEvent.setBDA(cityBDA);



            if(cityEvent!=null)
            {
                geographyEvent = new Geography();
                geographyEvent.setCity(cityEvent);
            }

            return geographyEvent;
        }
        /*-----------------------------------------City BDA and its altCode done--------------------------------------------------------*/


     return  null;

    }
}
