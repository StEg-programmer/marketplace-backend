-- V4__seed_test_users.sql
-- Пароли пока просто заглушка (hash), auth позже

INSERT INTO users(email, password_hash, status)
VALUES
    ('admin@test.com',  'hash_admin',  'ACTIVE'),
    ('seller@test.com', 'hash_seller', 'ACTIVE'),
    ('buyer@test.com',  'hash_buyer',  'ACTIVE')
    ON CONFLICT (email) DO NOTHING;

-- назначим роли
INSERT INTO user_roles(user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'ADMIN'
WHERE u.email = 'admin@test.com'
    ON CONFLICT DO NOTHING;

INSERT INTO user_roles(user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'SELLER'
WHERE u.email = 'seller@test.com'
    ON CONFLICT DO NOTHING;

INSERT INTO user_roles(user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'BUYER'
WHERE u.email = 'buyer@test.com'
    ON CONFLICT DO NOTHING;