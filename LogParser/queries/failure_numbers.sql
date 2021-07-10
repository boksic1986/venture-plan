select mission_name, count(mission_name) as failure_number
from mission_report where winner = false
group by mission_name order by failure_number desc;