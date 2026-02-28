-- V2__catalog.sql

CREATE TABLE categories (
                            id         BIGSERIAL PRIMARY KEY,
                            parent_id  BIGINT REFERENCES categories(id) ON DELETE SET NULL,
                            name       VARCHAR(120) NOT NULL,
                            UNIQUE (parent_id, name)
);

-- Тип товара: PHYSICAL / DIGITAL
-- Статус товара: DRAFT / ACTIVE / BLOCKED
CREATE TABLE products (
                          id               BIGSERIAL PRIMARY KEY,
                          seller_id        BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                          category_id      BIGINT NOT NULL REFERENCES categories(id) ON DELETE RESTRICT,

                          title            VARCHAR(200) NOT NULL,
                          description      TEXT,

                          type             VARCHAR(20)  NOT NULL,
                          status           VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',

                          base_price       NUMERIC(12,2) NOT NULL CHECK (base_price >= 0),
                          discount_percent INT NULL CHECK (discount_percent IS NULL OR (discount_percent >= 0 AND discount_percent <= 90)),

    -- простая версия атрибутов: JSON строка (позже можно вынести в таблицу)
                          attributes_json  TEXT NULL,

                          created_at       TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_seller ON products(seller_id);
CREATE INDEX idx_products_type ON products(type);
CREATE INDEX idx_products_status ON products(status);

-- Минимальные категории (чтобы было что показывать)
INSERT INTO categories(name, parent_id) VALUES
                                            ('Electronics', NULL),
                                            ('Services', NULL),
                                            ('Accounts', NULL),
                                            ('Subscriptions', NULL);

-- Подкатегории (пример)
INSERT INTO categories(name, parent_id)
SELECT 'Streaming', id FROM categories WHERE name='Subscriptions' AND parent_id IS NULL;