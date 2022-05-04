create table assets
(
  id            text not null,
  original_name text not null,
  path          text not null,
  type          text not null,
  size          bigint not null,
  constraint pk_assets primary key (id)
);

create table merchants
(
  id            uuid  not null,
  name          text  not null,
  siren         text  not null,
  capital       float not null,
  address       text  not null,
  logo_asset_id text  not null,
  constraint pk_merchants primary key (id)
);

alter table merchants
  add constraint fk_merchant_on_asset foreign key (logo_asset_id) references assets (id);

create table contests
(
  id                      uuid        not null,
  name                    text        not null,
  from_date               timestamptz not null,
  to_date                 timestamptz not null,
  description             text        not null,
  category                text        not null,
  cover_asset_id          text        not null,
  cost                    integer     not null,
  facebook_page           text,
  instagram_page          text,
  website                 text,
  newsletter_subscription boolean     not null,
  adult_only              boolean     not null,
  merchant_id             uuid        not null,
  board_id                uuid        not null,
  constraint pk_contest primary key (id)
);

alter table contests
  add constraint fk_contest_cover_on_asset foreign key (cover_asset_id) references assets (id);

alter table contests
  add constraint fk_contest_on_merchant foreign key (merchant_id) references merchants (id);

create table contest_asset_ids
(
  contest_id uuid not null,
  asset_ids  text not null
);

alter table contest_asset_ids
  add constraint fk_contest_asset_id_on_contest foreign key (contest_id) references contests (id);

alter table contest_asset_ids
  add constraint fk_contest_asset_id_on_asset foreign key (asset_ids) references assets (id);

create table packs
(
  id                    uuid    not null,
  dtype                 text    not null,
  name                  text    not null,
  first_winner_position integer not null,
  last_winner_position  integer not null,
  contest_id            uuid    not null,
  value                 float,
  asset_id              text,
  quantity              integer,
  code                  text,
  expiration_date       timestamptz,
  constraint pk_packs primary key (id)
);

alter table packs
  add constraint fk_packs_on_asset foreign key (asset_id) references assets (id);

alter table packs
  add constraint fk_packs_on_contest foreign key (contest_id) references contests (id);

create table addresses
(
  id           uuid not null,
  street       text not null,
  city         text not null,
  country      text not null,
  zipcode      text not null,
  extra_line_1 text,
  extra_line_2 text,
  constraint pk_addresses primary key (id)
);

create table users
(
  id              uuid        not null,
  email           text        not null unique,
  password_hash   text        not null,
  username        text        not null unique,
  birth_date      date        not null,
  first_name      text        not null,
  last_name       text        not null,
  avatar_asset_id text,
  mobile_phone    text,
  origin          text        not null,
  gender          text        not null,
  status          text        not null,
  roles           text[] not null,
  created         timestamptz not null,
  address_id      uuid,
  constraint pk_users primary key (id)
);

alter table users
  add constraint fk_users_on_asset foreign key (avatar_asset_id) references assets (id);

alter table users
  add constraint uc_users_email unique (email);

alter table users
  add constraint fk_users_on_address foreign key (address_id) references addresses (id);

create table user_preferences
(
  id            uuid not null,
  notifications json not null,
  emails        json not null,
  constraint pk_user_preferences primary key (id)
);

alter table user_preferences
  add constraint fk_user_preferences_on_user foreign key (id) references users (id);

create table confirmation_codes
(
  id      uuid not null,
  code    text not null,
  user_id uuid not null,
  created timestamp without time zone,
  constraint pk_confirmation_codes primary key (id)
);

alter table confirmation_codes
  add constraint fk_confirmation_codes_on_user foreign key (user_id) references users (id);

create table devices
(
  id        uuid not null,
  device_id text not null,
  user_id   uuid not null,
  constraint pk_devices primary key (id)
);

alter table devices
  add constraint fk_devices_on_user foreign key (user_id) references users (id);

create table refresh_tokens
(
  id      uuid not null,
  token   text not null unique,
  user_id uuid not null,
  constraint pk_refresh_tokens primary key (id)
);

alter table refresh_tokens
  add constraint fk_refresh_tokens_on_user foreign key (user_id) references users (id);

create table reset_codes
(
  id      uuid not null,
  code    text not null unique,
  user_id uuid not null,
  created timestamptz,
  constraint pk_reset_codes primary key (id)
);

alter table reset_codes
  add constraint fk_reset_codes_on_user foreign key (user_id) references users (id);

create table reset_tokens
(
  id      uuid not null,
  token   text not null unique,
  user_id uuid not null,
  constraint pk_reset_tokens primary key (id)
);

alter table reset_tokens
  add constraint fk_reset_tokens_on_user foreign key (user_id) references users (id);

create table resource_transactions
(
  id         uuid        not null,
  dtype      text        not null,
  currency   text        not null,
  type       text        not null,
  amount     bigint      not null,
  user_id    uuid        not null,
  date_time  timestamptz not null,
  contest_id uuid,
  constraint pk_resource_transactions primary key (id)
);

alter table resource_transactions
  add constraint fk_resource_transactions_on_contest foreign key (contest_id) references contests (id);

alter table resource_transactions
  add constraint fk_resource_transactions_on_user foreign key (user_id) references users (id);

create table contest_subscriptions
(
  id         uuid        not null,
  contest_id uuid        not null,
  user_id    uuid        not null,
  date_time  timestamptz not null,
  constraint pk_contest_subscriptions primary key (id)
);

alter table contest_subscriptions
  add constraint fk_contest_subscriptions_on_contest foreign key (contest_id) references contests (id);

alter table contest_subscriptions
  add constraint fk_contest_subscriptions_on_user foreign key (user_id) references users (id);

create table referral_nodes
(
  user_id     uuid    not null,
  referrer_id uuid,
  confirmed   boolean not null,
  code        text    not null,
  constraint pk_referral_nodes primary key (user_id)
);

alter table referral_nodes
  add constraint fk_referral_nodes_on_referrer foreign key (referrer_id) references referral_nodes (user_id);

alter table referral_nodes
  add constraint fk_referral_nodes_on_user foreign key (user_id) references users (id);

create table ranking_rows
(
  id         uuid not null,
  score      bigint not null,
  contest_id uuid not null,
  user_id    uuid not null,
  created    timestamptz not null,
  constraint pk_ranking_rows primary key (id)
);

alter table ranking_rows
  add constraint fk_ranking_rows_on_contest foreign key (contest_id) references contests (id);

alter table ranking_rows
  add constraint fk_ranking_rows_on_user foreign key (user_id) references users (id);

create table rewards
(
  id      uuid not null,
  user_id uuid not null,
  pack_id uuid not null,
  created timestamptz not null,
  constraint pk_rewards primary key (id)
);

alter table rewards
  add constraint fk_rewards_on_pack foreign key (pack_id) references packs (id);

alter table rewards
  add constraint fk_rewards_on_user foreign key (user_id) references users (id);