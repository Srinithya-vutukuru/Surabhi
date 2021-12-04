CREATE VIEW IF NOT EXISTS ALLORDERS (id,email,items) 
AS ( 
    select id,email,items from bill
); 

CREATE VIEW IF NOT EXISTS ORDERS_BY_DATE (id,date,email,items) 
AS ( 
    select id,date,email,items from bill
); 

CREATE VIEW IF NOT EXISTS ORDERS_BY_PRICE (id,amount,L̥email,items) 
AS ( 
    select id,amount,email,items from bill
); 



