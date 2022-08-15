-- InMemory database, no need to create new migrations to apply schema changes, just edit this file

create table addresses
(
  id           uuid         not null
    constraint pk_addresses
      primary key,
  street       varchar(255) not null,
  city         varchar(255) not null,
  country      varchar(255) not null,
  zipcode      varchar(255) not null,
  extra_line_1 varchar(255),
  extra_line_2 varchar(255)
);


create table assets
(
  id            varchar(255) not null
    constraint pk_assets
      primary key,
  original_name varchar(255) not null,
  path          varchar(255) not null,
  type          varchar(255) not null,
  size          bigint       not null
);


create table game_boards
(
  id       uuid not null
    constraint pk_game_boards
      primary key,
  name     varchar(255),
  cells array,
  row_size integer
);


create table merchants
(
  id            uuid             not null
    constraint pk_merchants
      primary key,
  name          varchar(255)     not null,
  siren         varchar(255)     not null,
  capital       double precision not null,
  address       varchar(255)     not null,
  logo_asset_id varchar(255)     not null
    constraint fk_merchant_on_asset
      references assets
);


create table contests
(
  id                      uuid                     not null
    constraint pk_contest
      primary key,
  name                    varchar(255)             not null,
  from_date               timestamp with time zone not null,
  to_date                 timestamp with time zone not null,
  description             varchar(255)             not null,
  category                varchar(255)             not null,
  cover_asset_id          varchar(255)             not null
    constraint fk_contest_cover_on_asset
      references assets,
  cost                    integer                  not null,
  access_code             varchar(255)             not null
    constraint uc_contest_access_code
      unique,
  facebook_page           varchar(255),
  instagram_page          varchar(255),
  website                 varchar(255),
  newsletter_subscription boolean                  not null,
  adult_only              boolean                  not null,
  merchant_id             uuid                     not null
    constraint fk_contest_on_merchant
      references merchants,
  board_id                uuid                     not null
    constraint fk_contest_on_game_board
      references game_boards
);


create table packs
(
  id                    uuid         not null
    constraint pk_packs
      primary key,
  dtype                 varchar(255) not null,
  name                  varchar(255) not null,
  first_winner_position integer      not null,
  last_winner_position  integer      not null,
  contest_id            uuid         not null
    constraint fk_packs_on_contest
      references contests,
  value                 double precision,
  asset_id              varchar(255)
    constraint fk_packs_on_asset
      references assets,
  quantity              integer,
  code                  varchar(255),
  expiration_date       timestamp with time zone
);


create table users
(
  id              uuid                     not null
    constraint pk_users
      primary key,
  email           varchar(255)             not null
    unique,
  password_hash   varchar(255)             not null,
  username        varchar(255)             not null
    unique,
  birth_date      date                     not null,
  first_name      varchar(255)             not null,
  last_name       varchar(255)             not null,
  avatar_asset_id varchar(255)
    constraint fk_users_on_asset
      references assets
      on delete cascade,
  mobile_phone    varchar(255),
  origin          varchar(255)             not null,
  gender          varchar(255)             not null,
  status          varchar(255)             not null,
  roles array not null,
  created         timestamp with time zone not null,
  address_id      uuid
    constraint fk_users_on_address
      references addresses
      on delete cascade
);


create table confirmation_codes
(
  id      uuid         not null
    constraint pk_confirmation_codes
      primary key,
  code    varchar(255) not null,
  user_id uuid         not null
    constraint fk_confirmation_codes_on_user
      references users
      on delete cascade,
  created timestamp
);


create table contest_asset_ids
(
  contest_id uuid         not null
    constraint fk_contest_asset_id_on_contest
      references contests,
  asset_ids  varchar(255) not null
    constraint fk_contest_asset_id_on_asset
      references assets
);


create table contest_subscriptions
(
  id         uuid                     not null
    constraint pk_contest_subscriptions
      primary key,
  contest_id uuid                     not null
    constraint fk_contest_subscriptions_on_contest
      references contests,
  user_id    uuid                     not null
    constraint fk_contest_subscriptions_on_user
      references users
      on delete cascade,
  date_time  timestamp with time zone not null
);


create table devices
(
  id        uuid         not null
    constraint pk_devices
      primary key,
  device_id varchar(255) not null,
  user_id   uuid         not null
    constraint fk_devices_on_user
      references users
      on delete cascade
);


create table ranking_rows
(
  id         uuid                     not null
    constraint pk_ranking_rows
      primary key,
  score      bigint                   not null,
  contest_id uuid                     not null
    constraint fk_ranking_rows_on_contest
      references contests,
  user_id    uuid                     not null
    constraint fk_ranking_rows_on_user
      references users
      on delete cascade,
  created    timestamp with time zone not null
);


create table referral_nodes
(
  user_id     uuid         not null,
  referrer_id uuid,
  confirmed   boolean      not null,
  code        varchar(255) not null
);

alter table referral_nodes
  add constraint pk_referral_nodes
    primary key (user_id);

alter table referral_nodes
  add constraint fk_referral_nodes_on_user
    foreign key (user_id) references users
      on
        delete cascade;

alter table referral_nodes
  add
    constraint fk_referral_nodes_on_referrer
      foreign key (referrer_id) references referral_nodes;

create table refresh_tokens
(
  id      uuid         not null
    constraint pk_refresh_tokens
      primary key,
  token   varchar(255) not null
    unique,
  user_id uuid         not null
    constraint fk_refresh_tokens_on_user
      references users
      on delete cascade
);


create table reset_codes
(
  id      uuid         not null
    constraint pk_reset_codes
      primary key,
  code    varchar(255) not null
    unique,
  user_id uuid         not null
    constraint fk_reset_codes_on_user
      references users
      on delete cascade,
  created timestamp with time zone
);


create table reset_tokens
(
  id      uuid         not null
    constraint pk_reset_tokens
      primary key,
  token   varchar(255) not null
    unique,
  user_id uuid         not null
    constraint fk_reset_tokens_on_user
      references users
      on delete cascade
);


create table resource_transactions
(
  id         uuid                     not null
    constraint pk_resource_transactions
      primary key,
  dtype      varchar(255)             not null,
  currency   varchar(255)             not null,
  type       varchar(255)             not null,
  amount     bigint                   not null,
  user_id    uuid                     not null
    constraint fk_resource_transactions_on_user
      references users
      on delete cascade,
  date_time  timestamp with time zone not null,
  contest_id uuid
    constraint fk_resource_transactions_on_contest
      references contests
);


create table rewards
(
  id      uuid                     not null
    constraint pk_rewards
      primary key,
  user_id uuid                     not null
    constraint fk_rewards_on_user
      references users
      on delete cascade,
  pack_id uuid                     not null
    constraint fk_rewards_on_pack
      references packs,
  created timestamp with time zone not null
);


create table user_preferences
(
  id            uuid not null
    constraint pk_user_preferences
      primary key,
  notifications json not null,
  emails        json not null
);


alter table user_preferences
  add constraint fk_user_preferences_on_user
    foreign key (id) references users
      on delete cascade;

