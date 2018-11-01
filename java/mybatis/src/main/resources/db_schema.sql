CREATE TABLE AUTHOR(
    ID NVARCHAR primary key,
    NAME NVARCHAR,
    EMAIL NVARCHAR
)

CREATE TABLE blog
(
    id int AUTO_INCREMENT PRIMARY KEY,
    title nvarchar(100),
    content text,
    create_dt datetime,
    author_id varchar(40)
);