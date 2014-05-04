/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     03-05-2014 21:20:02                          */
/*==============================================================*/


drop table if exists ADDRESS;

drop table if exists CATALOG;

drop table if exists CATEGORY;

drop table if exists CITY;

drop table if exists CLIENT;

drop table if exists COMPANY;

drop table if exists COUNTRY;

drop table if exists ITEM;

drop table if exists PRODUCT;

drop table if exists ROLE;

drop table if exists USER;

/*==============================================================*/
/* Table: ADDRESS                                               */
/*==============================================================*/
create table ADDRESS
(
   ADDRESS_ID           int not null auto_increment,
   USER_ID              int,
   CITY_ID              int not null,
   COMPANY_ID           int,
   ADDRESS_TYPE         varchar(40) not null,
   ADDRESS_NAME         varchar(128) not null,
   ADDRESS_DETAIL       varchar(128) not null,
   primary key (ADDRESS_ID)
);

/*==============================================================*/
/* Table: CATALOG                                               */
/*==============================================================*/
create table CATALOG
(
   CATALOG_ID           int not null auto_increment,
   COMPANY_ID           int,
   CATALOG_NAME         varchar(128) not null,
   CATALOG_DESCRIPTION  text,
   primary key (CATALOG_ID)
);

/*==============================================================*/
/* Table: CATEGORY                                              */
/*==============================================================*/
create table CATEGORY
(
   CATEGORY_ID          int not null auto_increment,
   CATALOG_ID           int not null,
   CATEGORY_NAME        varchar(128) not null,
   CATEGORY_DESCRIPTION text,
   primary key (CATEGORY_ID)
);

/*==============================================================*/
/* Table: CITY                                                  */
/*==============================================================*/
create table CITY
(
   CITY_ID              int not null auto_increment,
   COUNTRY_ID           int not null,
   CITY_NAME            varchar(128) not null,
   CITY_TYPE            varchar(40) not null,
   primary key (CITY_ID)
);

/*==============================================================*/
/* Table: CLIENT                                                */
/*==============================================================*/
create table CLIENT
(
   CLIENT_ID            int not null auto_increment,
   USER_ID              int not null,
   COMPANY_ID           int not null,
   CLIENT_NAMES         varchar(128) not null,
   CLIENT_LASTNAMES     varchar(128) not null,
   CLIENT_UNR           varchar(128),
   CLIENT_EMAIL         varchar(128) not null,
   CLIENT_PHONE         varchar(20),
   primary key (CLIENT_ID)
);

/*==============================================================*/
/* Table: COMPANY                                               */
/*==============================================================*/
create table COMPANY
(
   COMPANY_ID           int not null auto_increment,
   COMPANY_NAME         varchar(128) not null,
   COMPANY_DESCRIPTION  text,
   COMPANY_CODE         varchar(128) not null,
   primary key (COMPANY_ID)
);

/*==============================================================*/
/* Table: COUNTRY                                               */
/*==============================================================*/
create table COUNTRY
(
   COUNTRY_ID           int not null auto_increment,
   COUNTRY_NAME         varchar(40) not null,
   primary key (COUNTRY_ID)
);

/*==============================================================*/
/* Table: ITEM                                                  */
/*==============================================================*/
create table ITEM
(
   ITEM_ID              int not null auto_increment,
   PRODUCT_ID           int not null,
   ADDRESS_ID           int not null,
   ITEM_CODE            varchar(128) not null,
   ITEM_STATE           varchar(40),
   primary key (ITEM_ID)
);

/*==============================================================*/
/* Table: PRODUCT                                               */
/*==============================================================*/
create table PRODUCT
(
   PRODUCT_ID           int not null auto_increment,
   CATEGORY_ID          int,
   PRODUCT_NAME         varchar(128) not null,
   PRODUCT_DESCRIPTION  text not null,
   PRODUCT_CODE         varchar(128) not null,
   PRODUCT_STOCK        int not null,
   primary key (PRODUCT_ID)
);

/*==============================================================*/
/* Table: ROLE                                                  */
/*==============================================================*/
create table ROLE
(
   ROLE_ID              int not null auto_increment,
   ROLE_NAME            varchar(40) not null,
   ROLE_DESCRIPTION     text not null,
   primary key (ROLE_ID)
);

/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/
create table USER
(
   USER_ID              int not null auto_increment,
   COMPANY_ID           int,
   ROLE_ID              int not null,
   USER_NAME            varchar(40),
   USER_PASSWORD        varchar(128) not null,
   USER_EMAIL           varchar(40) not null,
   primary key (USER_ID)
);

alter table ADDRESS add constraint FK_CITY_ADDRESS foreign key (CITY_ID)
      references CITY (CITY_ID) on delete restrict on update restrict;

alter table ADDRESS add constraint FK_COMPANY_ADDRESS foreign key (COMPANY_ID)
      references COMPANY (COMPANY_ID) on delete restrict on update restrict;

alter table ADDRESS add constraint FK_USER_ADDRESS foreign key (USER_ID)
      references USER (USER_ID) on delete restrict on update restrict;

alter table CATALOG add constraint FK_COMPANY_CATALOG foreign key (COMPANY_ID)
      references COMPANY (COMPANY_ID) on delete restrict on update restrict;

alter table CATEGORY add constraint FK_CATALOG_CATEGORY foreign key (CATALOG_ID)
      references CATALOG (CATALOG_ID) on delete restrict on update restrict;

alter table CITY add constraint FK_COUNTRY_CITY foreign key (COUNTRY_ID)
      references COUNTRY (COUNTRY_ID) on delete restrict on update restrict;

alter table CLIENT add constraint FK_CIENT_USER foreign key (USER_ID)
      references USER (USER_ID) on delete restrict on update restrict;

alter table CLIENT add constraint FK_COMPANY_CLIENT foreign key (COMPANY_ID)
      references COMPANY (COMPANY_ID) on delete restrict on update restrict;

alter table ITEM add constraint FK_ADDRESS_ITEM foreign key (ADDRESS_ID)
      references ADDRESS (ADDRESS_ID) on delete restrict on update restrict;

alter table ITEM add constraint FK_PRODUCT_ITEM foreign key (PRODUCT_ID)
      references PRODUCT (PRODUCT_ID) on delete restrict on update restrict;

alter table PRODUCT add constraint FK_CATEGORY_PRODUCT foreign key (CATEGORY_ID)
      references CATEGORY (CATEGORY_ID) on delete restrict on update restrict;

alter table USER add constraint FK_COMPANY_USER foreign key (COMPANY_ID)
      references COMPANY (COMPANY_ID) on delete restrict on update restrict;

alter table USER add constraint FK_USER_ROLE foreign key (ROLE_ID)
      references ROLE (ROLE_ID) on delete restrict on update restrict;

