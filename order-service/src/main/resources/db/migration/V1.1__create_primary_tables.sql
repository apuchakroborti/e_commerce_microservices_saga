drop table if exists ORDERS;
CREATE TABLE ORDERS(
    ID                  bigint not null auto_increment,
    CUSTOMER_ID         bigint not null,
    ORDER_PLACING_DATE	datetime not null,
    EXPECTED_DELIVERY_DATE	datetime not null,

    ORDER_DETAILS       varchar(255) not null,
    TOTAL_AMOUNT        double not null,
    PAYMENT_AMOUNT      double,

    COMMENTS	    varchar(255),
    STATUS          boolean not null,
    ORDER_STATUS    ENUM ('ORDER_CREATED', 'ORDER_COMPLETED', 'ORDER_CANCELLED') not null,
    PAYMENT_STATUS  varchar(64),
    CREATED_BY	    bigint NOT NULL,
    CREATE_TIME	    datetime NOT NULL,
    EDITED_BY	    bigint,
    EDIT_TIME       datetime,
    INTERNAL_VERSION bigint default 1,
    primary key (ID)
) engine=InnoDB;


drop table if exists PRODUCT_SUMMARY;
CREATE TABLE PRODUCT_SUMMARY(
    ID                  bigint not null auto_increment,
    PRODUCT_PRICE       double not null,
    order_id            bigint not null,
    primary key (ID)
) engine=InnoDB;