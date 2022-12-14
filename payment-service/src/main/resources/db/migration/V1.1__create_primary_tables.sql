drop table if exists EMPLOYEE_MONTHLY_PAY_SLIP;
CREATE TABLE EMPLOYEE_MONTHLY_PAY_SLIP(
    ID                  bigint not null auto_increment,
    EMPLOYEE_ID         bigint not null,
    GROSS_SALARY        double not null,
    BASIC_SALARY        double not null,
    HOUSE_RENT	        double,
    CONVEYANCE	        double,
    MEDICAL_ALLOWANCE	double,
    DUE	                double,
    PF_DEDUCTION        double,
    TAX_DEDUCTION	    double,
    ARREARS	            double,
    FESTIVAL_BONUS      double,
    INCENTIVE_BONUS	    double,
    OTHER_PAY	        double,
    NET_PAYMENT         double,
    STATUS              boolean default true,
    COMMENTS	        varchar(255),
    FROM_DATE	        datetime not null,
    TO_DATE	            datetime not null,

    CREATED_BY	        bigint NOT NULL,
    CREATE_TIME	        datetime NOT NULL,
    EDITED_BY	        bigint,
    EDIT_TIME           datetime,
    INTERNAL_VERSION    bigint default 1,
    primary key (ID)
) engine=InnoDB;
