-- Удаляем все существующие категории
DELETE FROM categories;

-- Добавляем только нужные
INSERT INTO categories(name, parent_id) VALUES
                                            ('Clothes', NULL),
                                            ('Electronics', NULL),
                                            ('Subscriptions', NULL);