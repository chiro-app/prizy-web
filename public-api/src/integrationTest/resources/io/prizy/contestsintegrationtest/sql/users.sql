insert into users (id, email, password_hash, username, birth_date, first_name, last_name, origin, gender,
                   status, roles, created)
values ('00000000-0000-0000-0000-000000000000', 'john.admin@email.com', '$2a$12$o4sX93ha0J2OdgGNO48gv.u1Q7MEeiGgVGfOtQdjm4W3oB5A6WArC',
        'admin', '1990-01-01', 'John', 'Doe', 'EMAIL', 'MALE', 'CONFIRMED', '{USER,ADMIN}', now());

insert into users (id, email, password_hash, username, birth_date, first_name, last_name, origin, gender,
                   status, roles, created)
values ('00000000-0000-0000-0000-000000000001', 'john.guest@email.com', '$2a$12$o4sX93ha0J2OdgGNO48gv.u1Q7MEeiGgVGfOtQdjm4W3oB5A6WArC',
        'guest', '1990-01-01', 'John', 'Doe', 'EMAIL', 'MALE', 'CONFIRMED', '{USER}', now());