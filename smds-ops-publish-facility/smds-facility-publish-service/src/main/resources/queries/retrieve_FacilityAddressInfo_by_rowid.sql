select B.HOUSE_NUM as houseNumber,
       B.STREET as street,
       C.NAME as city,
       B.PSTCD as postalCode,
       B.PO_BOX as poBox,
       B.DSTRCT as district,
       D.NAME as territory,
       E.NAME as countryName,
       F.CODE as countryCode,
       B.ADDR_LN_2 as addressLine2,
       B.ADDR_LN_3 as addressLine3,
       B.LAT_GEOSPTL as latitude,
       B.LNG_GEOSPTL as longitude,
       A.ADDR_DCTR_IND as addressQualityCheckIndicator
from C_FCT_ADDR_REL A
         LEFT OUTER JOIN C_CTM_PSTL_ADDR B ON B.ROWID_OBJECT = A.ADDR_ROWID AND A.HUB_STATE_IND = 1
         LEFT OUTER JOIN C_GDA_DFND_AREA C ON C.ROWID_OBJECT = B.CITY_ROWID AND C.HUB_STATE_IND = 1
         LEFT OUTER JOIN C_GDA_DFND_AREA D ON D.ROWID_OBJECT = B.TRTY_ROWID AND D.HUB_STATE_IND = 1
         LEFT OUTER JOIN C_GDA_DFND_AREA E ON E.ROWID_OBJECT = B.CTRY_ROWID AND E.HUB_STATE_IND = 1
         LEFT OUTER JOIN C_ALT_CODE F ON F.GDA_DFND_AREA_ROWID = E.ROWID_OBJECT AND F.HUB_STATE_IND = 1
         INNER JOIN C_TYP_TYPE G ON G.ROWID_OBJECT = F.TYP_TYPE_ROWID AND G.CODE ='ALT_CODE.RKST'  AND G.HUB_STATE_IND = 1
where A.HUB_STATE_IND ='1' and A.FCT_ROWID=?
