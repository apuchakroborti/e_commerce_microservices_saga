drop table if exists PAYMENTS;
CREATE TABLE PAYMENTS(
    ID                  bigint not null auto_increment,
    ORDER_ID            bigint not null,
    CUSTOMER_ID         bigint not null,
    AMOUNT              double not null,
    DUE                 double,

    COMMENTS            varchar(255),
    PAYMENT_MEDIUM ENUM ('BKASH', 'ROCKET', 'NAGAD', 'CREDIT_CARD', 'DEBIT_CARD') not null,

    TRANSACTION_ID	    varchar(255),
    PAYMENT_DATE	    datetime not null,

    STATUS              boolean not null,

    CREATED_BY	        bigint NOT NULL,
    CREATE_TIME	        datetime NOT NULL,
    EDITED_BY	        bigint,
    EDIT_TIME           datetime,
    INTERNAL_VERSION    bigint default 1,
    primary key (ID)
) engine=InnoDB;

drop table if exists WALLETS;
CREATE TABLE WALLETS(
    ID                  bigint not null auto_increment,
    CUSTOMER_ID         bigint not null,
    BALANCE             double not null,
    STATUS              boolean not null,

    CREATED_BY	        bigint NOT NULL,
    CREATE_TIME	        datetime NOT NULL,
    EDITED_BY	        bigint,
    EDIT_TIME           datetime,
    INTERNAL_VERSION    bigint default 1,
    primary key (ID)
) engine=InnoDB;

drop table if exists CUSTOMERS_TRANSACTIONS;
CREATE TABLE CUSTOMERS_TRANSACTIONS(
    ORDER_ID            bigint not null,
    CUSTOMER_ID         bigint not null,
    AMOUNT             double not null,

    CREATE_TIME	        datetime NOT NULL,
    primary key (ORDER_ID)
) engine=InnoDB;
