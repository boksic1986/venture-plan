create table auto_attack_override
(
    id        bigint not null,
    is_ranged integer,
    primary key (id)
) engine = InnoDB;