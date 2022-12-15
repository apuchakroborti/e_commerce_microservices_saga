drop table if exists PRODUCTS;
CREATE TABLE PRODUCTS(
    ID bigint not null auto_increment,
    PRODUCT_NAME VARCHAR(128) NOT NULL,
    PRODUCT_CODE VARCHAR(32) NOT NULL,
    BAR_CODE VARCHAR(128),
    QR_CODE VARCHAR(256),

    PRODUCT_PRICE double not null,
    DISCOUNT_PERCENTAGE double,
    STATUS boolean not null,

    CREATED_BY	    bigint NOT NULL,
    CREATE_TIME	    datetime NOT NULL,
    EDITED_BY	    bigint,
    EDIT_TIME       datetime,
    INTERNAL_VERSION bigint default 1,
    primary key (ID)
) engine=InnoDB;

drop table if exists IMAGES;
CREATE TABLE IMAGES(
    ID bigint not null auto_increment,
    PRODUCT_ID bigint not null,

    IMAGE_URL VARCHAR(256),
    IMAGE_TYPE ENUM ('PNG', 'JPG', 'JPEG', 'GIF', 'BMP', 'SVG'),

    primary key (ID)
) engine=InnoDB;