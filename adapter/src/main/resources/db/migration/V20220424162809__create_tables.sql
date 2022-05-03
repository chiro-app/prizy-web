CREATE TABLE assets
(
  id            TEXT NOT NULL,
  original_name TEXT NOT NULL,
  path          TEXT NOT NULL,
  type          TEXT NOT NULL,
  size          BIGINT NOT NULL,
  CONSTRAINT pk_assets PRIMARY KEY (id)
);

CREATE TABLE merchants
(
  id            UUID  NOT NULL,
  name          TEXT  NOT NULL,
  siren         TEXT  NOT NULL,
  capital       FLOAT NOT NULL,
  address       TEXT  NOT NULL,
  logo_asset_id TEXT  NOT NULL,
  CONSTRAINT pk_merchants PRIMARY KEY (id)
);

ALTER TABLE merchants
  ADD CONSTRAINT FK_MERCHANT_ON_ASSET FOREIGN KEY (logo_asset_id) REFERENCES assets (id);

CREATE TABLE contests
(
  id                      UUID        NOT NULL,
  name                    TEXT        NOT NULL,
  from_date               TIMESTAMPTZ NOT NULL,
  to_date                 TIMESTAMPTZ NOT NULL,
  description             TEXT        NOT NULL,
  category                TEXT        NOT NULL,
  cover_asset_id          TEXT        NOT NULL,
  cost                    INTEGER     NOT NULL,
  facebook_page           TEXT,
  instagram_page          TEXT,
  website                 TEXT,
  newsletter_subscription BOOLEAN     NOT NULL,
  adult_only              BOOLEAN     NOT NULL,
  merchant_id             UUID        NOT NULL,
  board_id                UUID        NOT NULL,
  CONSTRAINT pk_contest PRIMARY KEY (id)
);

ALTER TABLE contests
  ADD CONSTRAINT FK_CONTEST_COVER_ON_ASSET FOREIGN KEY (cover_asset_id) REFERENCES assets (id);

ALTER TABLE contests
  ADD CONSTRAINT FK_CONTEST_ON_MERCHANT FOREIGN KEY (merchant_id) REFERENCES merchants (id);

CREATE TABLE contest_asset_ids
(
  contest_id UUID NOT NULL,
  asset_ids  TEXT NOT NULL
);

ALTER TABLE contest_asset_ids
  ADD CONSTRAINT FK_CONTEST_ASSET_ID_ON_CONTEST FOREIGN KEY (contest_id) REFERENCES contests (id);

ALTER TABLE contest_asset_ids
  ADD CONSTRAINT FK_CONTEST_ASSET_ID_ON_ASSET FOREIGN KEY (asset_ids) REFERENCES assets (id);

CREATE TABLE packs
(
  id                    UUID    NOT NULL,
  dtype                 TEXT    NOT NULL,
  name                  TEXT    NOT NULL,
  first_winner_position INTEGER NOT NULL,
  last_winner_position  INTEGER NOT NULL,
  contest_id            UUID    NOT NULL,
  value                 FLOAT,
  asset_id              TEXT,
  quantity              INTEGER,
  code                  TEXT,
  expiration_date       TIMESTAMPTZ,
  CONSTRAINT pk_packs PRIMARY KEY (id)
);

ALTER TABLE packs
  ADD CONSTRAINT FK_PACKS_ON_ASSET FOREIGN KEY (asset_id) REFERENCES assets (id);

ALTER TABLE packs
  ADD CONSTRAINT FK_PACKS_ON_CONTEST FOREIGN KEY (contest_id) REFERENCES contests (id);

CREATE TABLE addresses
(
  id           UUID NOT NULL,
  street       TEXT NOT NULL,
  city         TEXT NOT NULL,
  country      TEXT NOT NULL,
  zipcode      TEXT NOT NULL,
  extra_line_1 TEXT,
  extra_line_2 TEXT,
  CONSTRAINT pk_addresses PRIMARY KEY (id)
);

CREATE TABLE users
(
  id              UUID        NOT NULL,
  email           TEXT        NOT NULL UNIQUE,
  password_hash   TEXT        NOT NULL,
  username        TEXT        NOT NULL UNIQUE,
  birth_date      date        NOT NULL,
  first_name      TEXT        NOT NULL,
  last_name       TEXT        NOT NULL,
  avatar_asset_id TEXT,
  mobile_phone    TEXT,
  origin          TEXT        NOT NULL,
  gender          TEXT        NOT NULL,
  status          TEXT        NOT NULL,
  roles           TEXT[] NOT NULL,
  created         TIMESTAMPTZ NOT NULL,
  address_id      UUID,
  CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
  ADD CONSTRAINT FK_USERS_ON_ASSET FOREIGN KEY (avatar_asset_id) REFERENCES assets (id);

ALTER TABLE users
  ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
  ADD CONSTRAINT FK_USERS_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES addresses (id);

CREATE TABLE confirmation_codes
(
  id      UUID NOT NULL,
  code    TEXT NOT NULL,
  user_id UUID NOT NULL,
  created TIMESTAMP WITHOUT TIME ZONE,
  CONSTRAINT pk_confirmation_codes PRIMARY KEY (id)
);

ALTER TABLE confirmation_codes
  ADD CONSTRAINT FK_CONFIRMATION_CODES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE devices
(
  id        UUID NOT NULL,
  device_id TEXT NOT NULL,
  user_id   UUID NOT NULL,
  CONSTRAINT pk_devices PRIMARY KEY (id)
);

ALTER TABLE devices
  ADD CONSTRAINT FK_DEVICES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE refresh_tokens
(
  id      UUID NOT NULL,
  token   TEXT NOT NULL UNIQUE,
  user_id UUID NOT NULL,
  CONSTRAINT pk_refresh_tokens PRIMARY KEY (id)
);

ALTER TABLE refresh_tokens
  ADD CONSTRAINT FK_REFRESH_TOKENS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE reset_codes
(
  id      UUID NOT NULL,
  code    TEXT NOT NULL UNIQUE,
  user_id UUID NOT NULL,
  created TIMESTAMPTZ,
  CONSTRAINT pk_reset_codes PRIMARY KEY (id)
);

ALTER TABLE reset_codes
  ADD CONSTRAINT FK_RESET_CODES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE reset_tokens
(
  id      UUID NOT NULL,
  token   TEXT NOT NULL UNIQUE,
  user_id UUID NOT NULL,
  CONSTRAINT pk_reset_tokens PRIMARY KEY (id)
);

ALTER TABLE reset_tokens
  ADD CONSTRAINT FK_RESET_TOKENS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE resource_transactions
(
  id         UUID        NOT NULL,
  dtype      TEXT        NOT NULL,
  currency   TEXT        NOT NULL,
  type       TEXT        NOT NULL,
  amount     BIGINT      NOT NULL,
  user_id    UUID        NOT NULL,
  date_time  TIMESTAMPTZ NOT NULL,
  contest_id UUID,
  CONSTRAINT pk_resource_transactions PRIMARY KEY (id)
);

ALTER TABLE resource_transactions
  ADD CONSTRAINT FK_RESOURCE_TRANSACTIONS_ON_CONTEST FOREIGN KEY (contest_id) REFERENCES contests (id);

ALTER TABLE resource_transactions
  ADD CONSTRAINT FK_RESOURCE_TRANSACTIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE contest_subscriptions
(
  id         UUID        NOT NULL,
  contest_id UUID        NOT NULL,
  user_id    UUID        NOT NULL,
  date_time  TIMESTAMPTZ NOT NULL,
  CONSTRAINT pk_contest_subscriptions PRIMARY KEY (id)
);

ALTER TABLE contest_subscriptions
  ADD CONSTRAINT FK_CONTEST_SUBSCRIPTIONS_ON_CONTEST FOREIGN KEY (contest_id) REFERENCES contests (id);

ALTER TABLE contest_subscriptions
  ADD CONSTRAINT FK_CONTEST_SUBSCRIPTIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE referral_nodes
(
  user_id     UUID    NOT NULL,
  referrer_id UUID,
  confirmed   BOOLEAN NOT NULL,
  code        TEXT    NOT NULL,
  CONSTRAINT pk_referral_nodes PRIMARY KEY (user_id)
);

ALTER TABLE referral_nodes
  ADD CONSTRAINT FK_REFERRAL_NODES_ON_REFERRER FOREIGN KEY (referrer_id) REFERENCES referral_nodes (user_id);

ALTER TABLE referral_nodes
  ADD CONSTRAINT FK_REFERRAL_NODES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE ranking_rows
(
  id         UUID NOT NULL,
  score      BIGINT NOT NULL,
  contest_id UUID NOT NULL,
  user_id    UUID NOT NULL,
  created    TIMESTAMPTZ NOT NULL,
  CONSTRAINT pk_ranking_rows PRIMARY KEY (id)
);

ALTER TABLE ranking_rows
  ADD CONSTRAINT FK_RANKING_ROWS_ON_CONTEST FOREIGN KEY (contest_id) REFERENCES contests (id);

ALTER TABLE ranking_rows
  ADD CONSTRAINT FK_RANKING_ROWS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);