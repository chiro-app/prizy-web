CREATE TABLE merchants
(
  id            UUID NOT NULL,
  name          TEXT,
  siren         TEXT,
  capital       FLOAT,
  address       TEXT,
  logo_media_id TEXT,
  CONSTRAINT pk_merchants PRIMARY KEY (id)
);

CREATE TABLE contests
(
  id                      UUID NOT NULL,
  name                    TEXT,
  from_date               TIMESTAMPTZ,
  to_date                 TIMESTAMPTZ,
  description             TEXT,
  category                TEXT,
  cover_media_id          TEXT,
  cost                    INTEGER,
  facebook_page           TEXT,
  instagram_page          TEXT,
  website                 TEXT,
  newsletter_subscription BOOLEAN,
  adult_only              BOOLEAN,
  merchant_id             UUID,
  board_id                UUID,
  CONSTRAINT pk_contest PRIMARY KEY (id)
);

ALTER TABLE contests
  ADD CONSTRAINT FK_CONTEST_ON_MERCHANT FOREIGN KEY (merchant_id) REFERENCES merchants (id);

CREATE TABLE contest_media_ids
(
  contest_id UUID NOT NULL,
  media_ids  TEXT NOT NULL
);

ALTER TABLE contest_media_ids
  ADD CONSTRAINT FK_CONTEST_MEDIA_ID_ON_CONTEST FOREIGN KEY (contest_id) REFERENCES contests (id);

CREATE TABLE packs
(
  id                    UUID NOT NULL,
  dtype                 TEXT,
  name                  TEXT,
  first_winner_position INTEGER,
  last_winner_position  INTEGER,
  contest_id            UUID,
  value                 FLOAT,
  media_id              TEXT,
  quantity              INTEGER,
  code                  TEXT,
  expiration_date       TIMESTAMPTZ,
  CONSTRAINT pk_packs PRIMARY KEY (id)
);

ALTER TABLE packs
  ADD CONSTRAINT FK_PACKS_ON_CONTEST FOREIGN KEY (contest_id) REFERENCES contests (id);

CREATE TABLE users
(
  id UUID NOT NULL,
  CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE resource_transactions
(
  id         UUID NOT NULL,
  dtype      TEXT,
  currency   TEXT,
  type       TEXT,
  amount     BIGINT,
  user_id    UUID,
  date_time  TIMESTAMPTZ,
  contest_id UUID,
  CONSTRAINT pk_resource_transactions PRIMARY KEY (id)
);

ALTER TABLE resource_transactions
  ADD CONSTRAINT FK_RESOURCE_TRANSACTIONS_ON_CONTEST FOREIGN KEY (contest_id) REFERENCES contests (id);

ALTER TABLE resource_transactions
  ADD CONSTRAINT FK_RESOURCE_TRANSACTIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE contest_subscriptions
(
  id         UUID NOT NULL,
  contest_id UUID,
  user_id    UUID,
  date_time  TIMESTAMPTZ,
  CONSTRAINT pk_contest_subscriptions PRIMARY KEY (id)
);

ALTER TABLE contest_subscriptions
  ADD CONSTRAINT FK_CONTEST_SUBSCRIPTIONS_ON_CONTEST FOREIGN KEY (contest_id) REFERENCES contests (id);

ALTER TABLE contest_subscriptions
  ADD CONSTRAINT FK_CONTEST_SUBSCRIPTIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE referral_nodes
(
  id          UUID NOT NULL,
  user_id     UUID,
  referrer_id UUID,
  confirmed   BOOLEAN,
  code        TEXT,
  CONSTRAINT pk_referral_nodes PRIMARY KEY (id)
);

ALTER TABLE referral_nodes
  ADD CONSTRAINT FK_REFERRAL_NODES_ON_REFERRER FOREIGN KEY (referrer_id) REFERENCES referral_nodes (id);

ALTER TABLE referral_nodes
  ADD CONSTRAINT FK_REFERRAL_NODES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);