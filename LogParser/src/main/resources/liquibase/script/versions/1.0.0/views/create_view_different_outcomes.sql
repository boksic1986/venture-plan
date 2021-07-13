create view different_outcomes as
select distinct different_outcomes.mission_id,
                english_report.mission_name,
                different_outcomes.different_outcomes
from (select mission_id,
             count(mission_id) as different_outcomes,
             addon_version
      from mission_report
      where different_outcome = true
      group by mission_id, addon_version
     ) as different_outcomes
         left join (select mission_name, mission_id from mission_report where is_english) as english_report
                   on english_report.mission_id = different_outcomes.mission_id
order by different_outcomes desc, mission_id asc;