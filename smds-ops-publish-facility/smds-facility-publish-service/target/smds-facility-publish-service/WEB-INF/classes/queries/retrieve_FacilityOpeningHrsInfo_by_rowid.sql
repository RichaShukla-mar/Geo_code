select
       DAY as day,
       OPEN_TIME_HRS as openTimeHours,
       OPEN_TIME_MINS as openTimeMinutes,
       CLOSE_TIME_HRS as closeTimeHours,
       CLOSE_TIME_MINS as closeTimeMinutes
from C_FCT_OPNH
where HUB_STATE_IND ='1' and FCT_ROWID=?