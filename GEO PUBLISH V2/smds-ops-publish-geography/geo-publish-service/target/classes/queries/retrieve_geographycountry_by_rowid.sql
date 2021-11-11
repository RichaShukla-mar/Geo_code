select COUNTRYROWID as countryRowID, NAME as name,
          CASE WHEN TYPE ='GDA.CONTINENT' THEN 'Continent'
                     WHEN TYPE ='GDA.COUNTRY' THEN 'Country'
                     WHEN TYPE ='GDA.STATE/PROV' THEN 'State/Prov'
                     WHEN TYPE ='GDA.CITY' THEN 'City'
                     WHEN TYPE ='GDA.CITY_SUBAREA' THEN 'CitySubArea'
                     WHEN TYPE ='GDA.SITE' THEN 'Site'
                     WHEN TYPE ='GDA.POSTAL_CODE' THEN 'Postal Code'
                     WHEN TYPE ='GDA.BDA' THEN 'Business Defined Area'
                     ELSE TYPE end  as type from
        ((select DFND.ROWID_OBJECT as countryRowID, DFND.NAME as name, DFND.TYP_TYPE_CD as type
          from C_GDA_DFND_AREA_REL RL,
               C_GDA_DFND_AREA DFND,
               (select B.GDA_DFND_AREA_PRNT_ROWID as StateRowid
                from C_GDA_DFND_AREA A
                         INNER JOIN C_GDA_DFND_AREA_REL B
                                    ON B.GDA_DFND_AREA_CHLD_ROWID = A.ROWID_OBJECT AND B.HUB_STATE_IND = 1 and
                                       B.TYP_TYPE_CD not in 'GDA_REL.GDA_IN_BDA'
                         INNER JOIN C_GDA_DFND_AREA C
                                    ON C.ROWID_OBJECT = B.GDA_DFND_AREA_PRNT_ROWID AND C.HUB_STATE_IND = 1
                         INNER JOIN C_TYP_TYPE D ON D.CODE = C.TYP_TYPE_CD AND D.HUB_STATE_IND = 1
                where A.HUB_STATE_IND = 1
                  and C.TYP_TYPE_CD = 'GDA.STATE/PROV'
                  AND A.ROWID_OBJECT=?
               ) RLFK
          where RL.GDA_DFND_AREA_CHLD_ROWID = RLFK.STATEROWID
            and RL.TYP_TYPE_CD = 'GDA_REL.TRTY_IN_CTRY'
            AND DFND.ROWID_OBJECT = RL.GDA_DFND_AREA_PRNT_ROWID
            AND DFND.TYP_TYPE_CD = 'GDA.COUNTRY')
         UNION
         (select C.ROWID_OBJECT, C.NAME as CountryNm, C.TYP_TYPE_CD as Type
          from C_GDA_DFND_AREA A
                   INNER JOIN C_GDA_DFND_AREA_REL B ON B.GDA_DFND_AREA_CHLD_ROWID = A.ROWID_OBJECT AND B.HUB_STATE_IND = 1
              and B.TYP_TYPE_CD not in 'GDA_REL.GDA_IN_BDA'
                   INNER JOIN C_GDA_DFND_AREA C
                              ON C.ROWID_OBJECT = B.GDA_DFND_AREA_PRNT_ROWID AND C.HUB_STATE_IND = 1
                   INNER JOIN C_TYP_TYPE D ON D.CODE = C.TYP_TYPE_CD AND D.HUB_STATE_IND = 1
          where A.HUB_STATE_IND = 1
            and C.TYP_TYPE_CD = 'GDA.COUNTRY'
            AND A.ROWID_OBJECT=?)
         UNION
         (select dfnd.rowid_object, dfnd.NAME, dfnd.TYP_TYPE_CD
          from c_gda_dfnd_area city,
               c_gda_dfnd_area_rel r1,
               c_gda_dfnd_area regn,
               c_gda_dfnd_area_rel r2,
               c_gda_dfnd_area cntry,
               c_gda_dfnd_area_rel r3,
               c_gda_dfnd_area dfnd
          where r1.gda_dfnd_area_chld_rowid = city.rowid_object
            and regn.rowid_object = r1.GDA_DFND_AREA_PRNT_ROWID
            and regn.TYP_TYPE_CD = 'GDA.CITY'
            and r2.gda_dfnd_area_chld_rowid = regn.rowid_object
            and cntry.ROWID_OBJECT = r2.GDA_DFND_AREA_PRNT_ROWID
            and cntry.TYP_TYPE_CD = 'GDA.STATE/PROV'
            and r3.gda_dfnd_area_chld_rowid = cntry.rowid_object
            and dfnd.ROWID_OBJECT = r3.GDA_DFND_AREA_PRNT_ROWID
            and dfnd.TYP_TYPE_CD = 'GDA.COUNTRY'
            AND city.ROWID_OBJECT=?)
         UNION
         (select cntry.rowid_object, cntry.NAME, cntry.TYP_TYPE_CD
          from c_gda_dfnd_area city,
               c_gda_dfnd_area_rel r1,
               c_gda_dfnd_area regn,
               c_gda_dfnd_area_rel r2,
               c_gda_dfnd_area cntry
          where r1.gda_dfnd_area_chld_rowid = city.rowid_object
            and regn.rowid_object = r1.GDA_DFND_AREA_PRNT_ROWID
            and regn.TYP_TYPE_CD = 'GDA.CITY'
            and r2.gda_dfnd_area_chld_rowid = regn.rowid_object
            and cntry.ROWID_OBJECT = r2.GDA_DFND_AREA_PRNT_ROWID
            and cntry.TYP_TYPE_CD = 'GDA.COUNTRY'
            and city.ROWID_OBJECT=?)
        )