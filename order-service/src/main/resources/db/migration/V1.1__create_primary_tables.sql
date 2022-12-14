drop table if exists EMPLOYEE_TAX_DEPOSIT;
CREATE TABLE EMPLOYEE_TAX_DEPOSIT(
    ID              bigint not null auto_increment,
    EMPLOYEE_ID     bigint not null,
    AMOUNT          double not null,
    CHALAN_NO       varchar(64) not null,
    COMMENTS	    varchar(255),
    FROM_DATE	    datetime not null,
    TO_DATE	        datetime not null,

    CREATED_BY	    bigint NOT NULL,
    CREATE_TIME	    datetime NOT NULL,
    EDITED_BY	    bigint,
    EDIT_TIME       datetime,
    INTERNAL_VERSION bigint default 1,
    primary key (ID)
) engine=InnoDB;