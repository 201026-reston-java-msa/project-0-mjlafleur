DROP TABLE user_account_junction;
DROP TABLE accountinformation;
DROP TABLE userinformation;
DROP TABLE roles;
DROP TABLE accountstatus;
DROP TABLE AccountType;

-----------------TABLE CREATION----------------
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


CREATE TABLE UserInformation(
user_id SERIAL PRIMARY KEY
, user_name VARCHAR(50) NOT NULL UNIQUE
, pass_word VARCHAR(50) NOT NULL
, first_name VARCHAR(50) NOT NULL
, last_name VARCHAR(50) NOT NULL
, rolenum INTEGER NOT NULL
,FOREIGN KEY (rolenum) REFERENCES roles(role_id)
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




--------------------------INSERTS-----------------

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


INSERT INTO userinformation (user_name,pass_word,first_name,last_name,rolenum) 
	VALUES ('user','pass','first','last',1);
INSERT INTO userinformation (user_name,pass_word,first_name,last_name,rolenum) 
	VALUES ('user2','pass2','first2','last2',2);
INSERT INTO accountinformation (account_balance,account_type,account_status) 
	VALUES (0,'Checking','OPEN');
INSERT INTO accountinformation (account_balance,account_type,account_status) 
	VALUES (2,'Checking','OPEN');
INSERT INTO user_account_junction VALUES (1,1);
INSERT INTO user_account_junction VALUES (2,2);


--INSERT INTO accountinformation (userid) INTEGER NOT NULL 
--FOREIGN KEY (userid) REFERENCES UserInformation (user_id);
---------------------JOIN------------

SELECT A.user_name, A.first_name, A.last_name, A.rolenum, C.account_id, C.account_balance, C.account_type, C.account_status 
FROM UserInformation A 
INNER JOIN user_account_junction B 
ON A.user_id  = B.user_fk 
INNER JOIN AccountInformation C 
ON C.account_id  = B.account_fk 
ORDER BY user_id

SELECT C.account_id, C.account_balance, C.account_type, C.account_status 
FROM UserInformation A 
INNER JOIN user_account_junction B 
ON A.user_id  = B.user_fk 
INNER JOIN AccountInformation C 
ON C.account_id  = B.account_fk


SELECT A.user_name, A.first_name, A.last_name, C.account_id, C.account_type, C.account_status 
FROM UserInformation A   
INNER JOIN user_account_junction B  
ON A.user_id  = B.user_fk  
INNER JOIN AccountInformation C  
ON C.account_id  = B.account_fk
WHERE A.user_id =7;

SELECT C.account_id, C.account_balance, C.account_type, C.account_status
FROM UserInformation A  
INNER JOIN user_account_junction B 
ON A.user_id  = B.user_fk  
INNER JOIN AccountInformation C 
ON C.account_id  = B.account_fk
WHERE A.user_id=7;
---------ACCOUNT ID FROM USER ID--------------------
SELECT c.account_id
FROM UserInformation A  
INNER JOIN user_account_junction B 
ON A.user_id  = B.user_fk  
INNER JOIN AccountInformation C 
ON C.account_id  = B.account_fk
WHERE A.user_id=7;

SELECT ai.account_id
FROM user_account_junction uaj  
INNER JOIN AccountInformation ai 
ON ai.account_id  = uaj.account_fk
WHERE uaj.user_fk =1;


---------------------UPDATES------------------
--DEPOSIT
	UPDATE accountinformation SET account_balance = ? WHERE account_id = ?;
	
--WITHDRAW
	UPDATE accountinformation SET account_balance = ? WHERE account_id = ?

--TRANSFER

UPDATE accountinformation
	SET account_balance =   
	CASE  account_id 
	WHEN 7 THEN 121
	WHEN 8 THEN 35
	END 
	WHERE account_id IN (7,8);

--PENDING TO OPEN (ALL)
UPDATE accountinformation 
	SET account_status = 'OPEN'
	WHERE account_status = 'Pending';
	
--PENDING TO OPEN

UPDATE accountinformation A
SET account_status = 'OPEN'
FROM user_account_junction uaj
WHERE uaj.account_fk = A.account_id 
AND uaj.user_fk =? AND A.account_status ='Pending';

	-----UserInfo Edit----
	
--UserName
UPDATE userinformation 
SET user_name = ?
WHERE user_id = ?

----------------------QUERIES----------------

SELECT A.user_name, A.pass_word 
FROM UserInformation A 
WHERE A.user_name='user' AND A.pass_word ='pass';

SELECT user_id FROM userinformation WHERE user_name = 'ASeaholm';

SELECT * FROM userinformation WHERE user_id = 7;

SELECT max(user_id) FROM userinformation;
SELECT max(account_id) FROM accountinformation;


-------------------DELETES------------------
DELETE FROM user_account_junction WHERE user_fk = 3;
DELETE FROM userinformation WHERE user_id = 3;
DELETE FROM accountinformation WHERE account_id = 3;

-------------------SELECTS-------------------
SELECT * FROM UserInformation;
SELECT * FROM AccountInformation;
SELECT * FROM user_account_junction;
SELECT * FROM roles;
SELECT * FROM AccountStatus;
SELECT * FROM AccountType;


--we use create or replace to denote
--that we may want to rerun this script multiple times
CREATE OR REPLACE FUNCTION get_current_time() RETURNS 
	TIME WITH TIME ZONE 
AS 
$$
	-- current_time is a built in value, which is just the current time
	-- we do not need to return statement since teh function
	-- will just return the last statement's result by default
	SELECT CURRENT_TIME;
$$ LANGUAGE SQL;

SELECT get_current_time();

CREATE OR REPLACE FUNCTION get_string_literal() RETURNS VARCHAR(40)
AS 
$func$
	SELECT 'Hello World!';
$func$ LANGUAGE SQL;

SELECT get_String_literal();