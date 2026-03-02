-- order_item_id уже есть в таблице digital_keys (если ты делал по моему V6).
-- но у нас нет внешнего ключа и уникальности. Добавим.

ALTER TABLE digital_keys
    ADD CONSTRAINT fk_digital_keys_order_item
        FOREIGN KEY (order_item_id) REFERENCES order_items(id) ON DELETE SET NULL;

-- один ключ может быть продан только один раз
CREATE UNIQUE INDEX IF NOT EXISTS ux_digital_keys_order_item
    ON digital_keys(order_item_id)
    WHERE order_item_id IS NOT NULL;