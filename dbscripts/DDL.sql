CREATE SCHEMA APP AUTHORIZATION ADMIN;    

create table APP.EQUITY (  
    ID INTEGER primary key,  
    SYMBOL VARCHAR(10),
    NAME VARCHAR(50)
);

create table APP.PRICE (  
    EQUITY INTEGER not null,
    TRADE_DT DATE not null,
    PRICE_OPEN FLOAT,
    PRICE_LOW FLOAT,
    PRICE_CLOSE FLOAT,
    PRICE_ADJ FLOAT,
    TRADE_VOLUMN INTEGER
);

create table APP.HDB_TRANSECTION (
	street varchar(50),
	story varchar(10),
	hdb_type VARCHAR(20),
	floor_area float,
	Lease_Commence_date date,
	resale_price integer,
	resale_approval_dt date,

);

create table APP.HDB_STREET(
	id int primary key,
	varchar(30) not null
);

create table APP.HDB_TOWN(
	id int primary key,
	varchar(30) not null
);

ALTER TABLE APP.PRICE ADD CONSTRAINT PRICE_PRIMARY_KEY PRIMARY KEY (EQUITY, TRADE_DT);
ALTER TABLE APP.PRICE ADD CONSTRAINT PRICE_EQUITY_FOREIGN_KEY FOREIGN KEY(EQUITY) REFERENCES APP.EQUITY(ID);
