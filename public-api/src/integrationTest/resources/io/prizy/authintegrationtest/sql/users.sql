insert into users (id, email, password_hash, username, birth_date, first_name, last_name, origin,
                   gender, status, roles, created)
values ('85c97ad0-e87c-4068-a10e-d5f97072362f',
        'john.doe@email.com', '$2a$10$30boBwjsNix3F.P2FMRZxOJ5JwRQgQwLK2X931vvnxJlpoTxUlPvO', 'jdoe', '1990-01-01',
        'John', 'Doe', 'EMAIL', 'MALE', 'CONFIRMED', '{USER}', '2022-08-15T21:00:50.770285Z');


insert into users (id, email, password_hash, username, birth_date, first_name, last_name, origin,
                   gender, status, roles, created)
values ('39738df0-229d-46dc-8e8f-2f635b845596', 'john.doe+2@email.com',
        '$2a$10$30boBwjsNix3F.P2FMRZxOJ5JwRQgQwLK2X931vvnxJlpoTxUlPvO', 'jdoe2', '1990-01-01', 'John', 'Doe', 'EMAIL',
        'MALE', 'CONFIRMED', '{USER}', '2022-08-15T21:00:50.770285Z');

insert into refresh_tokens (id, token, user_id)
values ('1bcf23ee-b66f-41cc-b60f-cdbe21dc6603',
        'VhLhMrtpGvgZgVRCZaEgaHcQXAPQNopTefGnGJoUSsUWohIIpTxEsOBuZIyGwHvm',
        '39738df0-229d-46dc-8e8f-2f635b845596');