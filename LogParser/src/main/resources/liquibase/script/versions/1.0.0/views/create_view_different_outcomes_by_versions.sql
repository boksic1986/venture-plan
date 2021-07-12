create view different_outcome_by_versions as
select all_missions.addon_version,
       all_missions.count                                   as all_missions,
       different_outcome.count                              as different_outcome,
       (different_outcome.count / all_missions.count) * 100 as percentage_of_failure
from (select addon_version, count(*) as count
      from mission_report
      where addon_version <> '5.1-beta'
      group by addon_version) as all_missions
         join (select addon_version, count(*) as count
               from mission_report
               where addon_version <> '5.1-beta'
                 and different_outcome = true
               group by addon_version) different_outcome
              on all_missions.addon_version = different_outcome.addon_version;