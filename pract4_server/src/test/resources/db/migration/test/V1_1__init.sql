CREATE TABLE car
(
    id    SERIAL PRIMARY KEY,
    brand VARCHAR(50)    NOT NULL,
    model VARCHAR(50)    NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE customer
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(100) NOT NULL,
    phone VARCHAR(15)  NOT NULL
);

CREATE TABLE sale
(
    id         SERIAL PRIMARY KEY,
    car_id     INT REFERENCES car (id) ON DELETE CASCADE,
    customer_id  INT REFERENCES customer (id) ON DELETE CASCADE,
    sale_price DECIMAL(10, 2) NOT NULL,
    sale_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION calculate_discount(c_id BIGINT)
    RETURNS NUMERIC AS
$$
DECLARE
    cars_bought INT;
    total_spent DECIMAL(10, 2);
    discount    NUMERIC;
BEGIN
    SELECT COUNT(s.id), COALESCE(SUM(s.sale_price), 0)
    INTO cars_bought, total_spent
    FROM sale s
    WHERE s.customer_id = c_id;

    discount := LEAST(0.2, (cars_bought / 10.0) + (total_spent / 1000000.0));

    RETURN discount;
END;
$$ LANGUAGE plpgsql;