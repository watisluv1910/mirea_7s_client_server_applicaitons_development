INSERT INTO car (id, brand, model, price)
VALUES (1, 'Toyota', 'Camry', 30000.00),
       (2, 'Honda', 'Accord', 28000.00),
       (3, 'BMW', 'X5', 60000.00),
       (4, 'Mercedes', 'C-Class', 55000.00),
       (5, 'Audi', 'A4', 40000.00),
       (6, 'Ford', 'Mustang', 35000.00);

INSERT INTO customer (id, name, phone)
VALUES (1, 'John Doe', '555-1234'),
       (2, 'Jane Smith', '555-5678'),
       (3, 'Robert Johnson', '555-9012'),
       (4, 'Emily Davis', '555-3456'),
       (5, 'Michael Brown', '555-7890'),
       (6, 'Sarah Wilson', '555-4321');

INSERT INTO sale (car_id, customer_id, sale_price, sale_date)
VALUES (1, 1, 29000.00, null),
       (2, 2, 27000.00, null),
       (3, 3, 59000.00, null),
       (4, 1, 54000.00, null),
       (5, 4, 39000.00, null);