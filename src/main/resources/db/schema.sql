create table app_user
(
    id        serial primary key not null,
    name      varchar(255)       not null,
    email     varchar(100)       not null,
    created   timestamp          not null,
    user_type varchar(20)        not null
);

-- Sincronizar valor inicial de acuerdo a DMLs (actualmente 2 registros)
ALTER SEQUENCE app_user_id_seq RESTART WITH 3;