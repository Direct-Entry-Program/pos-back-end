CREATE TABLE Customer (
                          id VARCHAR(36) PRIMARY KEY ,
                          name VARCHAR(50) NOT NULL ,
                          address VARCHAR(100) NOT NULL
);

INSERT INTO Customer VALUES (UUID(),'Kasun','Galle'),
                            (UUID(),'Nipuna','Colombo'),
                            (UUID(),'Nisal','Kandy');


INSERT INTO Customer VALUES (UUID(),'Pubudu','Panadura'),
                            (UUID(),'Eranga','Horana'),
                            (UUID(),'Danula','Gampaha');

CREATE TABLE Item (
                      code VARCHAR(36) PRIMARY KEY,
                      stock INT NOT NULL,
                      unit_price DECIMAL(5,2) NOT NULL,
                      description VARCHAR(250) NOT NULL
);


CREATE TABLE Order (
                       id VARCHAR(36) PRIMARY KEY,
                       date VARCHAR(10) NOT NULL ,
                       customer_id VARCHAR(36) NOT NULL ,
                       FOREIGN KEY(customer_id) REFERENCES Customer(id)
);

CREATE TABLE Order_details (
                               order_id VARCHAR(36) NOT NULL ,
                               item_code VARCHAR(36) NOT NULL ,
                               unit_price DECIMAL(5,2) NOT NULL ,
                               qty INT NOT NULL ,
                               PRIMARY KEY (order_id,item_code),
                               FOREIGN KEY (order_id) REFERENCES Order(id),
                               FOREIGN KEY (item_code) REFERENCES Item(code)
);