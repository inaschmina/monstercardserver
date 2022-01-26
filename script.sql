create table users
(
    username         varchar               not null,
    password         varchar               not null,
    coins            integer default 20    not null,
    bio              varchar,
    image            varchar,
    name             varchar,
    won              integer default 0     not null,
    lost             integer default 0     not null,
    elo              integer default 100   not null,
    ready_for_battle boolean default false not null,
    lose_win_ratio   integer default 0     not null
);

alter table users
    owner to swe1user;

create unique index users_username_uindex
    on users (username);

create table cards
(
    id                 varchar               not null
        constraint cards_pk
            primary key,
    name               varchar               not null,
    damage             double precision      not null,
    element            varchar               not null,
    package_id         integer               not null,
    "user"             varchar,
    deck               boolean default false not null,
    locked_for_trading boolean default false not null
);

alter table cards
    owner to swe1user;

create unique index cards_id_uindex
    on cards (id);

create table package
(
    id   serial
        constraint package_pk
            primary key,
    cost integer default 5 not null
);

alter table package
    owner to swe1user;

create unique index package_id_uindex
    on package (id);

create table tradings
(
    id            varchar not null
        constraint table_name_pk
            primary key,
    card_to_trade varchar not null,
    type          varchar not null,
    min_damage    integer not null
);

alter table tradings
    owner to swe1user;


