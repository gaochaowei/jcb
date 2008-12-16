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

create table APP.HDB_RESALES (
	blk varchar(10),	
	street varchar(50),
	story varchar(10),
	town varchar(30),	
	hdb_type varchar(10),
	floor_area float,
	lease_commence_dt date,
	resale_price int,
	resale_approval_dt date,
	create_dt timestamp
);

create table APP.HDB_STREET(
	id int primary key,
	name varchar(30) not null
);

create table APP.HDB_TOWN(
	id int primary key,
	name varchar(30) not null
);

create table APP.HDB_TYPE (
	REF varchar(10) primary key,
	NAME varchar(30)
);

ALTER TABLE APP.PRICE ADD CONSTRAINT PRICE_PRIMARY_KEY PRIMARY KEY (EQUITY, TRADE_DT);
ALTER TABLE APP.PRICE ADD CONSTRAINT PRICE_EQUITY_FOREIGN_KEY FOREIGN KEY(EQUITY) REFERENCES APP.EQUITY(ID);
