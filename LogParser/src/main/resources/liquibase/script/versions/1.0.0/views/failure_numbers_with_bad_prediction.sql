create view failure_numbers_with_bad_prediction as
select mission_id, mission_name, count(mission_name) as failure_number, addon_version
from mission_report
where winner = false and prediction_correct = false
group by mission_id, addon_version
order by failure_number desc;