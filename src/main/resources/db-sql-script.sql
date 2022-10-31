CREATE TABLE Customer (
                          id VARCHAR(36) PRIMARY KEY ,
                          name VARCHAR(50) NOT NULL ,
                          address VARCHAR(100) NOT NULL
);

INSERT INTO Customer VALUES (UUID(),'Kasun','Galle'),
                            (UUID(),'Nipuna','Colombo'),
                            (UUID(),'Nisal','Kandy');