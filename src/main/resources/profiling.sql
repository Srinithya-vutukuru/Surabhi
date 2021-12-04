CREATE USER GUEST PASSWORD 'abc';

CREATE VIEW IF NOT EXISTS ALLORDERS (id,email,items) 
AS ( 
    select id,email,items from bill
); 

CREATE VIEW IF NOT EXISTS AllOrdersByFilters (id,date,email,items,amount) 
AS ( 
    select id,date,email,items,amount from bill
); 

CREATE VIEW IF NOT EXISTS ALLORDERSBYCITY (id,country,email,items) 
AS ( 
    select id,country,email,items from bill, userlocation 
    where bill.id=userlocation.user_id;
); 


CREATE VIEW IF NOT EXISTS MaxSales (year,month,amount) 
AS ( 
    select YEAR(date) as year,MONTH(date) as month,amount from bill;
); 



