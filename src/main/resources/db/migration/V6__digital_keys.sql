CREATE TABLE digital_keys (
                              id              BIGSERIAL PRIMARY KEY,
                              product_id      BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
                              seller_id       BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,

                              key_value       VARCHAR(500) NOT NULL,
                              status          VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',

                              reserved_until  TIMESTAMP NULL,
                              order_item_id   BIGINT NULL,

                              created_at      TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_digital_keys_product_status ON digital_keys(product_id, status);
CREATE INDEX idx_digital_keys_seller ON digital_keys(seller_id);