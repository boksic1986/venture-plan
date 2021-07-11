create view bad_predictions_by_missions as
select mission_id, count(mission_id) as bad_predictions, addon_version
from mission_report
where prediction_correct = false
group by mission_id, addon_version
order by bad_predictions desc;