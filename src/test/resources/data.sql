INSERT INTO product (productName, price)
SELECT 'Smartphone', 599.99
WHERE NOT EXISTS (SELECT 1 FROM product WHERE productId = 1);

INSERT INTO product (productName, price)
SELECT 'Laptop', 1299.99
WHERE NOT EXISTS (SELECT 2 FROM product WHERE productId = 2);

INSERT INTO product (productName, price)
SELECT 'Gaming Console', 399.99
WHERE NOT EXISTS (SELECT 3 FROM product WHERE productId = 3);

INSERT INTO review (reviewContent, rating, productId)
SELECT 'Great battery life!', 5, 1
WHERE NOT EXISTS (SELECT 1 FROM review WHERE reviewId = 1);

INSERT INTO review (reviewContent, rating, productId)
SELECT 'Lags sometimes.', 3, 1
WHERE NOT EXISTS (SELECT 2 FROM review WHERE reviewId = 2);

INSERT INTO review (reviewContent, rating, productId)
SELECT 'Perfect for my daily tasks!', 4, 2
WHERE NOT EXISTS (SELECT 3 FROM review WHERE reviewId = 3);

INSERT INTO review (reviewContent, rating, productId)
SELECT 'Bit pricey, but worth it.', 4, 2
WHERE NOT EXISTS (SELECT 4 FROM review WHERE reviewId = 4);

INSERT INTO review (reviewContent, rating, productId)
SELECT 'Awesome gaming experience!', 5, 3
WHERE NOT EXISTS (SELECT 5 FROM review WHERE reviewId = 5);

INSERT INTO review (reviewContent, rating, productId)
SELECT 'Needs more exclusive games.', 4, 3
WHERE NOT EXISTS (SELECT 6 FROM review WHERE reviewId = 6);