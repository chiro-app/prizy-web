begin;

alter table addresses
  drop constraint fk_addresses_on_user,
  add constraint fk_addresses_on_user foreign key (user_id) references users (id) on delete cascade;

alter table confirmation_codes
  drop constraint fk_confirmation_codes_on_user,
  add constraint fk_confirmation_codes_on_user foreign key (user_id) references users (id) on delete cascade;

alter table devices
  drop constraint fk_devices_on_user,
  add constraint fk_devices_on_user foreign key (user_id) references users (id) on delete cascade;

alter table refresh_tokens
  drop constraint fk_refresh_tokens_on_user,
  add constraint fk_refresh_tokens_on_user foreign key (user_id) references users (id) on delete cascade;

alter table reset_codes
  drop constraint fk_reset_codes_on_user,
  add constraint fk_reset_codes_on_user foreign key (user_id) references users (id) on delete cascade;

alter table reset_tokens
  drop constraint fk_reset_tokens_on_user,
  add constraint fk_reset_tokens_on_user foreign key (user_id) references users (id) on delete cascade;

alter table resource_transactions
  drop constraint fk_resource_transactions_on_user,
  add constraint fk_resource_transactions_on_user foreign key (user_id) references users (id) on delete cascade;

alter table contest_subscriptions
  drop constraint fk_contest_subscriptions_on_user,
  add constraint fk_contest_subscriptions_on_user foreign key (user_id) references users (id) on delete cascade;

alter table referral_nodes
  drop constraint fk_referral_nodes_on_user,
  add constraint fk_referral_nodes_on_user foreign key (user_id) references users (id) on delete cascade;

alter table ranking_rows
  drop constraint fk_ranking_rows_on_user,
  add constraint fk_ranking_rows_on_user foreign key (user_id) references users (id) on delete cascade;

alter table rewards
  drop constraint fk_rewards_on_user,
  add constraint fk_rewards_on_user foreign key (user_id) references users (id) on delete cascade;

commit;