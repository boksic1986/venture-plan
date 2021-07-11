create table spell
(
    id                   bigint not null,
    description          text,
    auto_combat_spell_id bigint,
    duration             bigint,
    name                 varchar(255),
    has_thorns_effect    bit,
    cooldown             bigint,
    creature_health      bigint,
    creature_role        bigint,
    creature_name        varchar(255),
    creature_attack      bigint,
    is_enemy             bit,
    is_english           bit,
    primary key (id)
) engine = InnoDB;