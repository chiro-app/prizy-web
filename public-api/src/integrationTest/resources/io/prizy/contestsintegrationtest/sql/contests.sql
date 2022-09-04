insert into merchants (id, name, siren, capital, address, logo_asset_id)
values ('00000000-0000-0000-0000-000000000000', 'Apple Inc', '123456789', 1024, '1 Apple Park, Cupertino, USA',
        '00000000-0000-0000-0000-000000000000');

insert into contests (id, name, from_date, to_date, description, category, cover_asset_id, cost, access_code,
                      newsletter_subscription, adult_only, merchant_id, board_id)
values ('00000000-0000-0000-0000-000000000000', 'PREM', now() - interval '1 days', now() + interval '2 days', 'é',
        'FASHION', '00000000-0000-0000-0000-000000000000', 1, 'XCSTRY', false,
        false, '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000');

insert into contests (id, name, from_date, to_date, description, category, cover_asset_id, cost, access_code,
                      newsletter_subscription, adult_only, merchant_id, board_id)
values ('00000000-0000-0000-0000-000000000001', 'PREM', now() - interval '3 days', now() - interval '1 days', 'é',
        'FASHION', '00000000-0000-0000-0000-000000000000', 1, 'ABCXYZ', false,
        false, '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000');


insert into packs (id, dtype, name, first_winner_position, last_winner_position, contest_id, code, expiration_date)
values ('00000000-0000-0000-0000-000000000000', 'Coupon', 'Reward 1', 1, 4, '00000000-0000-0000-0000-000000000000',
        '000000', '2022-08-15T21:00:00.000000Z'),
       ('00000000-0000-0000-0000-000000000001', 'Coupon', 'Reward 2', 4, 7, '00000000-0000-0000-0000-000000000000',
        '000000', '2022-08-15T21:00:00.000000Z'),
       ('00000000-0000-0000-0000-000000000002', 'Coupon', 'Reward 3', 7, 11, '00000000-0000-0000-0000-000000000000',
        '000000', '2022-08-15T21:00:00.000000Z');