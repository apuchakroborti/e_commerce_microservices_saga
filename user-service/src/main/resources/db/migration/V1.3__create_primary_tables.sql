drop table if exists EMPLOYEE;
drop table if exists EMPLOYEE_SALARY;

CREATE TABLE EMPLOYEE(
    ID              bigint not null auto_increment,
    USER_ID         VARCHAR(255) NOT NULL UNIQUE,
    FIRST_NAME	    varchar(255) not null,
    LAST_NAME	    varchar(255),
    EMAIL	        varchar(128) not null,
    PHONE	        varchar(32),
    TIN	            varchar(255),
    NID	            varchar(255),
    PASSPORT	    varchar(64),
    DATE_OF_JOINING datetime not null,
    DESIGNATION_ID	int,
    ADDRESS_ID	    int,

    STATUS	        BOOLEAN DEFAULT TRUE,

    CREATED_BY	    bigint NOT NULL,
    CREATE_TIME	    datetime NOT NULL,
    EDITED_BY	    bigint,
    EDIT_TIME       datetime,
    INTERNAL_VERSION bigint default 1,
    oauth_user_id     bigint,
    primary key (ID)
) engine=InnoDB;


CREATE TABLE EMPLOYEE_SALARY(
    ID              bigint not null auto_increment,
    EMPLOYEE_ID     bigint not null,
    BASIC_SALARY    double not null,
    GROSS_SALARY    double not null,
    STATUS          boolean default true,
    COMMENTS	    varchar(255),
    FROM_DATE	    datetime not null,
    TO_DATE	        datetime,

    CREATED_BY	    bigint NOT NULL,
    CREATE_TIME	    datetime NOT NULL,
    EDITED_BY	    bigint,
    EDIT_TIME       datetime,
    INTERNAL_VERSION bigint default 1,
    primary key (ID)
) engine=InnoDB;
