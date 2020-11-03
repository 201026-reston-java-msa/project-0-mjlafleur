DROP TABLE user_account_junction;
DROP TABLE accountinformation;
DROP TABLE userinformation;
DROP TABLE roles;
DROP TABLE accountstatus;
DROP TABLE AccountType;

-----------------TABLE CREATION----------------

CREATE TABLE UserInformation(
user_id SERIAL PRIMARY KEY
, user_name VARCHAR(50) NOT NULL UNIQUE
, pass_word VARCHAR(50) NOT NULL
, first_name VARCHAR(50) NOT NULL
, last_name VARCHAR(50) NOT NULL
, email VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE AccountInformation(
account_id SERIAL PRIMARY KEY
,account_balance DECIMAL(18,2)
,account_type VARCHAR(20) NOT NULL
,account_status VARCHAR(10) NOT NULL
);

CREATE TABLE user_account_junction(
user_fk INTEGER NOT NULL
, account_fk INTEGER NOT NULL
,CONSTRAINT my_combo_key PRIMARY KEY (user_fk, account_fk)
, FOREIGN KEY (user_fk) REFERENCES UserInformation (user_id)
, FOREIGN KEY (account_fk) REFERENCES AccountInformation (account_id)
);

CREATE TABLE roles(
role_id SERIAL PRIMARY KEY
, role_name VARCHAR(10)
);

CREATE TABLE AccountStatus(
status_id SERIAL PRIMARY KEY
, status_name VARCHAR(10)
);

CREATE TABLE AccountType(
type_id SERIAL PRIMARY KEY
, type_name VARCHAR(10)
);

--------------------------INSERTS-----------------

INSERT INTO userinformation (user_name,pass_word,first_name,last_name,email) 
	VALUES ('user','pass','first','last','firstlast@email.com');
INSERT INTO userinformation (user_name,pass_word,first_name,last_name,email) 
	VALUES ('user2','pass2','first2','last2','firstlast@email2.com');
INSERT INTO accountinformation (account_balance,account_type,account_status) 
	VALUES (0,'Checking','OPEN');
INSERT INTO accountinformation (account_balance,account_type,account_status) 
	VALUES (2,'Checking','OPEN');
INSERT INTO user_account_junction VALUES (1,1);
INSERT INTO user_account_junction VALUES (2,2);


INSERT INTO roles (role_name) VALUES ('Admin');
INSERT INTO roles (role_name) VALUES ('Employee');
INSERT INTO roles (role_name) VALUES ('Standard');
INSERT INTO roles (role_name) VALUES ('Premium');

INSERT INTO AccountStatus (status_name) VALUES ('Pending');
INSERT INTO AccountStatus (status_name) VALUES ('Open');
INSERT INTO AccountStatus (status_name) VALUES ('Closed');
INSERT INTO AccountStatus (status_name) VALUES ('Denied');

INSERT INTO AccountType (Type_name) VALUES ('Checking');
INSERT INTO AccountType (Type_name) VALUES ('Savings');
INSERT INTO accountType (Type_name) VALUES ('Pending');

---------------------JOIN------------
SELECT A.user_name, A.first_name, A.last_name, A.email, C.account_type, C.account_status 
FROM UserInformation A 
INNER JOIN user_account_junction B 
ON A.user_id  = B.user_fk 
INNER JOIN AccountInformation C 
ON C.account_id  = B.account_fk 
ORDER BY user_id

SELECT A.user_name, A.first_name, A.last_name, A.email, C.account_id, C.account_type, C.account_status 
FROM UserInformation A   
INNER JOIN user_account_junction B  
ON A.user_id  = B.user_fk  
INNER JOIN AccountInformation C  
ON C.account_id  = B.account_fk
WHERE C.account_id=1;



----------------------QUERIES----------------

SELECT A.user_name, A.pass_word 
FROM UserInformation A 
WHERE A.user_name='user' AND A.pass_word ='pass';

SELECT user_id FROM userinformation WHERE user_name = 'ASeaholm';

SELECT max(user_id) FROM userinformation;
SELECT max(account_id) FROM accountinformation;
INSERT INTO userinformation (user_name,pass_word,first_name,last_name,email)
 VALUES ('m','l','n','m','nm@email.com');

-------------------DELETES------------------
DELETE FROM user_account_junction WHERE user_fk = 3;
DELETE FROM userinformation WHERE user_id = 3;
DELETE FROM accountinformation WHERE account_id = 3;


SELECT * FROM UserInformation;
SELECT * FROM AccountInformation;
SELECT * FROM user_account_junction;
SELECT * FROM roles;
SELECT * FROM AccountStatus;
SELECT * FROM AccountType;

