CREATE TABLE product_stock (
                               id                  BIGSERIAL PRIMARY KEY,
                               product_id          BIGINT NOT NULL UNIQUE REFERENCES products(id) ON DELETE CASCADE,
                               seller_id           BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               available_quantity  INT NOT NULL DEFAULT 0 CHECK (available_quantity >= 0),
                               reserved_quantity   INT NOT NULL DEFAULT 0 CHECK (reserved_quantity >= 0),
                               updated_at          TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_product_stock_seller ON product_stock(seller_id);