create view bad_predictions_by_versions as
select all_missions.addon_version,
       all_missions.count                                 as all_missions,
       bad_predictions.count                              as bad_predictions,
       (bad_predictions.count / all_missions.count) * 100 as percentage_of_failure
from (select addon_version, count(*) as count
      from mission_report
      where addon_version <> '5.1-beta'
      group by addon_version) as all_missions
         join (select addon_version, count(*) as count
               from mission_report
               where addon_version <> '5.1-beta'
                 and prediction_correct = false
               group by addon_version) bad_predictions on all_missions.addon_version = bad_predictions.addon_version;