CREATE TABLE orders (
                        id              BIGSERIAL PRIMARY KEY,
                        buyer_id        BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                        status          VARCHAR(20) NOT NULL DEFAULT 'CREATED',
                        payment_status  VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                        total_amount    NUMERIC(12,2) NOT NULL DEFAULT 0,
                        created_at      TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE order_items (
                             id              BIGSERIAL PRIMARY KEY,
                             order_id        BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
                             product_id      BIGINT NOT NULL REFERENCES products(id) ON DELETE RESTRICT,
                             title_snapshot  VARCHAR(200) NOT NULL,
                             price_snapshot  NUMERIC(12,2) NOT NULL,
                             quantity        INT NOT NULL CHECK (quantity > 0),
                             kind            VARCHAR(20) NOT NULL,
                             created_at      TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_order_items_order ON order_items(order_id);

CREATE TABLE payments (
                          id              BIGSERIAL PRIMARY KEY,
                          order_id        BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
                          provider        VARCHAR(30) NOT NULL,
                          status          VARCHAR(20) NOT NULL,
                          amount          NUMERIC(12,2) NOT NULL,
                          created_at      TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_payments_order ON payments(order_id);