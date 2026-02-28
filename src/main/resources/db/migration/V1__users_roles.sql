-- V1__users_roles.sql

CREATE TABLE users (
                       id              BIGSERIAL PRIMARY KEY,
                       email           VARCHAR(255) NOT NULL UNIQUE,
                       password_hash   VARCHAR(255) NOT NULL,
                       status          VARCHAR(30)  NOT NULL DEFAULT 'ACTIVE',
                       created_at      TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE roles (
                       id      BIGSERIAL PRIMARY KEY,
                       name    VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                            role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, role_id)
);

-- базовые роли
INSERT INTO roles(name) VALUES ('ADMIN'), ('SELLER'), ('BUYER');