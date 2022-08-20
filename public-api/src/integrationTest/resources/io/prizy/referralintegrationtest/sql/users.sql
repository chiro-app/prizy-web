insert into users (id, email, password_hash, username, birth_date, first_name, last_name, origin, gender, status, roles,
                   created)
values ('00000000-0000-0000-0000-000000000000', 'john.doe@email.com',
        '$2a$10$30boBwjsNix3F.P2FMRZxOJ5JwRQgQwLK2X931vvnxJlpoTxUlPvO', 'jdoe', '1990-01-01', 'John', 'Doe', 'EMAIL',
        'MALE', 'CONFIRMED', '{USER}', '2022-08-15T21:00:50.770285Z'),
       ('00000000-0000-0000-0000-000000000001', 'john.doe+1@email.com',
        '$2a$10$30boBwjsNix3F.P2FMRZxOJ5JwRQgQwLK2X931vvnxJlpoTxUlPvO', 'jdoe1', '1990-01-01', 'John', 'Doe', 'EMAIL',
        'MALE', 'CONFIRMED', '{USER}', '2022-08-15T21:00:50.770285Z');

insert into referral_nodes (user_id, confirmed, code)
values ('00000000-0000-0000-0000-000000000000', false, 'AABBCC'),
       ('00000000-0000-0000-0000-000000000001', false, 'XXYYZZ');