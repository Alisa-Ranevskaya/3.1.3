INSERT INTO users (age, name, last_name, login, password)
VALUES
    (25, 'Петя', 'Иванов', 'petya', '$2a$10$A37InQ2hWV0lM9GKIQhWXuGPu9sJrAyEXVRLaC5TsTuvI.8mgifkq'),
    (30, 'Аня', 'Сидорова', 'anya', '$2a$10$A37InQ2hWV0lM9GKIQhWXuGPu9sJrAyEXVRLaC5TsTuvI.8mgifkq');
INSERT INTO roles (role_name)
values
    ('ROLE_ADMIN'),
    ('ROLE_USER');
INSERT INTO users_roles (user_id, role_id)
SELECT * FROM (VALUES (1, 1), (1, 2), (2, 2)) AS data(user_id, role_id)
WHERE NOT EXISTS (
    SELECT 1 FROM users_roles
    WHERE user_id = data.user_id AND role_id = data.role_id
)