alter table users
  drop constraint fk_users_on_address,
  add constraint fk_users_on_address foreign key (address_id) references addresses (id) on delete cascade;

alter table users
  drop constraint fk_users_on_asset,
  add constraint fk_users_on_asset foreign key (avatar_asset_id) references assets (id) on delete cascade;
