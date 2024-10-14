INSERT INTO car (brand, model, price)
VALUES ('Toyota', 'Camry', 30000.00),
       ('Honda', 'Accord', 28000.00),
       ('BMW', 'X5', 60000.00),
       ('Mercedes', 'C-Class', 55000.00),
       ('Audi', 'A4', 40000.00),
       ('Ford', 'Mustang', 35000.00);

INSERT INTO customer (name, phone)
VALUES ('John Doe', '555-1234'),
       ('Jane Smith', '555-5678'),
       ('Robert Johnson', '555-9012'),
       ('Emily Davis', '555-3456'),
       ('Michael Brown', '555-7890'),
       ('Sarah Wilson', '555-4321');

INSERT INTO sale (car_id, customer_id, sale_price, sale_date)
VALUES (1, 1, 29000.00, null),
       (2, 2, 27000.00, null),
       (3, 3, 59000.00, null),
       (4, 1, 54000.00, null),
       (5, 4, 39000.00, null);