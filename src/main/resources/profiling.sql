CREATE USER GUEST PASSWORD 'abc';

CREATE VIEW TEST_VIEW AS SELECT * FROM Bill WHERE email = :email;

CREATE VIEW ORDERS_DATE AS SELECT * FROM Bill WHERE date > :date;

CREATE VIEW ORDERS_PRICE AS SELECT * FROM Bill WHERE amount > :amount;