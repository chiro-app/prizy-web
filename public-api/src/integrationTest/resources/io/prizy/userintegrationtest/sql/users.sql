insert into users (id, email, password_hash, username, birth_date, first_name, last_name, origin, gender, status, roles,
                   created)
values ('00000000-0000-0000-0000-000000000000', 'john.doe@email.com',
        '$2a$10$aa0unphxokskx/qokwlwq.pll/ar0gkhbt0jfhxekgc7wtts0h.pq', 'jdoe', '1990-10-10', 'John', 'Doe', 'email',
        'male', 'confirmed', '{user}', now());
