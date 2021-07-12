create view different_outcomes_by_missions as
select distinct different_outcome.mission_id,
                different_outcome.different_outcome,
                different_outcome.addon_version,
                english_report.mission_name
from (select mission_id,
             count(mission_id) as different_outcome,
             addon_version
      from mission_report
      where different_outcome = true
      group by mission_id, addon_version
     ) as different_outcome
         left join (select mission_name, mission_id from mission_report where is_english) as english_report
                   on english_report.mission_id = different_outcome.mission_id
order by different_outcome desc, mission_id asc;