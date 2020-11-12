create table app_user
(
    id        int primary key not null,
    name      varchar(255)    not null,
    email     varchar(100)    not null,
    created   timestamp       not null,
    user_type varchar(20)     not null
);