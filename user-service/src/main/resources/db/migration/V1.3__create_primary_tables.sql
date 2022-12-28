drop table if exists CUSTOMERS;

CREATE TABLE CUSTOMERS(
    ID              bigint not null auto_increment,
    USER_ID         VARCHAR(255) NOT NULL UNIQUE,
    FIRST_NAME	    varchar(255) not null,
    LAST_NAME	    varchar(255),
    EMAIL	        varchar(128) not null,
    PHONE	        varchar(32),
    NID	            varchar(64),
    PASSPORT	    varchar(64),
    DATE_OF_BIRTH   datetime not null,
    OAUTH_USER_ID bigint not null,

    STATUS	        BOOLEAN DEFAULT TRUE,
    WALLET_STATUS   BOOLEAN,

    CREATED_BY	    bigint NOT NULL,
    CREATE_TIME	    datetime NOT NULL,
    EDITED_BY	    bigint,
    EDIT_TIME       datetime,
    INTERNAL_VERSION bigint default 1,
    primary key (ID)
) engine=InnoDB;

drop table if exists DISTRICT;
CREATE TABLE DISTRICT(
    ID              bigint not null auto_increment,
    NAME	        varchar(255) not null,
    NAME_UTF	    varchar(255),
    country_id     bigint not null,
    primary key (ID)
) engine=InnoDB;

drop table if exists COUNTRY;
CREATE TABLE COUNTRY(
    ID              bigint not null auto_increment,
    NAME	        varchar(255) not null,
    NAME_UTF	    varchar(255),
    CODE_2	        char(2),
    CODE_3	        char(3),
    primary key (ID)
) engine=InnoDB;

drop table if exists CUSTOMER_ADDRESS;
CREATE TABLE CUSTOMER_ADDRESS(
    ID bigint not null auto_increment,
    customer_id bigint not null,
    address_type ENUM ('PRESENT_ADDRESS', 'PERMANENT_ADDRESS', 'BILLING_ADDRESS') not null,
    details VARCHAR(255),
    district_id bigint not null,

    primary key (ID)
) engine=InnoDB;