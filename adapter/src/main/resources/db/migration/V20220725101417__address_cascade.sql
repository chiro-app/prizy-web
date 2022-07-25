alter table addresses
  drop constraint fk_addresses_on_user,
  add constraint fk_addresses_on_user foreign key (user_id) references users (id) on delete cascade;
