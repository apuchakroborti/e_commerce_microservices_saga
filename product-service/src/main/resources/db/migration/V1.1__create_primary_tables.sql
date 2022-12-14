drop table if exists EMPLOYEE_PROVIDENT_FUND;
CREATE TABLE EMPLOYEE_PROVIDENT_FUND(
    ID bigint not null auto_increment,
    EMPLOYEE_ID bigint not null,
    EMPLOYEE_CONTRIBUTION double,
    COMPANY_CONTRIBUTION double,
    COMMENTS	varchar(128),
    FROM_DATE	datetime not null,
    TO_DATE	    datetime not null,

    CREATED_BY	    bigint NOT NULL,
    CREATE_TIME	    datetime NOT NULL,
    EDITED_BY	    bigint,
    EDIT_TIME       datetime,
    INTERNAL_VERSION bigint default 1,
    primary key (ID)
) engine=InnoDB;