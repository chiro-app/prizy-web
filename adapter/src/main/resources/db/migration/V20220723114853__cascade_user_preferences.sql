alter table user_preferences
  drop constraint fk_user_preferences_on_user,
  add constraint fk_user_preferences_on_user foreign key (id) references users (id) on delete cascade;
