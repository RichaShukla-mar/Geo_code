package net.apmm.mdm.ops.facility.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
public class util {
        private static long dateInLong;
        public static long changeInputCreateDate(Timestamp date) {
            // dd-mm-yy hh24:mm:ss
            SimpleDateFormat inputFormat  = new SimpleDateFormat("yyyy-mm-dd hh24:mm:ss.S");
            try {
                Date dt = new Date(date.getTime());
                //Date parsein = inputFormat.parse(String.valueOf(date));
                dateInLong = dt.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return dateInLong;
        }




}
