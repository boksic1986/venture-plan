create table mission_report
(
    id                 bigint not null,
    addon_version      varchar(255),
    log_content        longtext,
    mission_id         bigint,
    mission_name       varchar(255),
    prediction_correct bit,
    winner             bit,
    primary key (id)
) engine = InnoDB;