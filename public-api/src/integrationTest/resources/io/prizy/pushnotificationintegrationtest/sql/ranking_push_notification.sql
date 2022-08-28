insert into users (id, email, password_hash, username, birth_date, first_name, last_name, origin,
                   gender, status, roles, created)
values ('00000000-0000-0000-0000-000000000000', 'john.doe@email.com',
        '$2a$10$30boBwjsNix3F.P2FMRZxOJ5JwRQgQwLK2X931vvnxJlpoTxUlPvO', 'jdoe', '1990-01-01', 'John', 'Doe', 'EMAIL',
        'MALE', 'CONFIRMED', '{USER}', '2022-08-15T21:00:50.770285Z'),
       ('00000000-0000-0000-0000-000000000001', 'john.doe+1@email.com',
        '$2a$10$30boBwjsNix3F.P2FMRZxOJ5JwRQgQwLK2X931vvnxJlpoTxUlPvO', 'jdoe1', '1990-01-01', 'John', 'Doe', 'EMAIL',
        'MALE', 'CONFIRMED', '{USER}', '2022-08-15T21:00:50.770285Z'),
       ('00000000-0000-0000-0000-000000000002', 'john.doe+2@email.com',
        '$2a$10$30boBwjsNix3F.P2FMRZxOJ5JwRQgQwLK2X931vvnxJlpoTxUlPvO', 'jdoe2', '1990-01-01', 'John', 'Doe', 'EMAIL',
        'MALE', 'CONFIRMED', '{USER}', '2022-08-15T21:00:50.770285Z'),
       ('00000000-0000-0000-0000-000000000003', 'john.doe+3@email.com',
        '$2a$10$30boBwjsNix3F.P2FMRZxOJ5JwRQgQwLK2X931vvnxJlpoTxUlPvO', 'jdoe3', '1990-01-01', 'John', 'Doe', 'EMAIL',
        'MALE', 'CONFIRMED', '{USER}', '2022-08-15T21:00:50.770285Z'),
       ('00000000-0000-0000-0000-000000000004', 'john.doe+4@email.com',
        '$2a$10$30boBwjsNix3F.P2FMRZxOJ5JwRQgQwLK2X931vvnxJlpoTxUlPvO', 'jdoe4', '1990-01-01', 'John', 'Doe', 'EMAIL',
        'MALE', 'CONFIRMED', '{USER}', '2022-08-15T21:00:50.770285Z'),
       ('00000000-0000-0000-0000-000000000005', 'john.doe+5@email.com',
        '$2a$10$30boBwjsNix3F.P2FMRZxOJ5JwRQgQwLK2X931vvnxJlpoTxUlPvO', 'jdoe5', '1990-01-01', 'John', 'Doe', 'EMAIL',
        'MALE', 'CONFIRMED', '{USER}', '2022-08-15T21:00:50.770285Z');


insert into assets(id, original_name, path, type, size)
values ('00000000-0000-0000-0000-000000000000', 'sample.png', 'path/sample.png', 'image/png', 1024);


insert into merchants (id, name, siren, capital, address, logo_asset_id)
values ('00000000-0000-0000-0000-000000000000', 'Apple Inc', '123456789', 1024, '1 Apple Park, Cupertino, USA',
        '00000000-0000-0000-0000-000000000000');

insert into game_boards (id, name, cells, row_size)
values ('00000000-0000-0000-0000-000000000000', 'Default', '{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}', 4);


insert into contests (id, name, from_date, to_date, description, category, cover_asset_id, cost, access_code,
                      newsletter_subscription, adult_only, merchant_id, board_id)
values ('00000000-0000-0000-0000-000000000000', 'PREM', '2022-06-15 22:17:00.000000 +00:00',
        '2022-06-30 22:17:00.000000 +00:00', 'é', 'FASHION', '00000000-0000-0000-0000-000000000000', 1, 'XCSTRY', false,
        false, '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000');


insert into packs (id, dtype, name, first_winner_position, last_winner_position, contest_id, quantity, code,
                   expiration_date)
values ('00000000-0000-0000-0000-000000000000', 'Coupon', 'Reward 1', 1, 2, '00000000-0000-0000-0000-000000000000', 1,
        '000000', '2022-08-15T21:00:00.000000Z'),
       ('00000000-0000-0000-0000-000000000001', 'Coupon', 'Reward 2', 2, 4, '00000000-0000-0000-0000-000000000000', 1,
        '000000', '2022-08-15T21:00:00.000000Z');


insert into ranking_rows (id, score, user_id, contest_id, created)
values ('90122fc0-e74a-4162-807d-0acca9ad93f0', 1001, '00000000-0000-0000-0000-000000000005',
        '00000000-0000-0000-0000-000000000000', now()),
       ('2dad6215-c537-40c1-a714-5a42e4310797', 1000, '00000000-0000-0000-0000-000000000000',
        '00000000-0000-0000-0000-000000000000', now()),
       ('361ed22f-83ee-4120-9173-da53bfe48362', 999, '00000000-0000-0000-0000-000000000001',
        '00000000-0000-0000-0000-000000000000', now()),
       ('9b95c912-0ce3-48a8-b9b0-9c7507e91085', 100, '00000000-0000-0000-0000-000000000002',
        '00000000-0000-0000-0000-000000000000', now()),
       ('b5879e12-3c7e-40e9-8a3c-ddf474302e9e', 10, '00000000-0000-0000-0000-000000000003',
        '00000000-0000-0000-0000-000000000000', now()),
       ('989dcba1-59f7-4f16-97a2-9e26ce504964', 1, '00000000-0000-0000-0000-000000000004',
        '00000000-0000-0000-0000-000000000000', now());