create view bad_predictions as
select distinct bad_predictions.mission_id,
                english_report.mission_name,
                bad_predictions.bad_predictions
from (select mission_id,
             count(mission_id) as bad_predictions,
             addon_version
      from mission_report
      where prediction_correct = false
      group by mission_id, addon_version
     ) as bad_predictions
         left join (select mission_name, mission_id from mission_report where is_english) as english_report
                   on english_report.mission_id = bad_predictions.mission_id
order by bad_predictions desc, mission_id asc;