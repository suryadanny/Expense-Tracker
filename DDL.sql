CREATE TABLE USER (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255),
    email VARCHAR(255),
    mobile VARCHAR(20)
);

CREATE TABLE credential (
    user_id INT PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    OTP VARCHAR(6),
	constraint fk_credential_1 foreign key(user_id) references USER(id)
);

CREATE TABLE Expenses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    notes VARCHAR(2000),
    category VARCHAR(50),
    amount DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    txn_dttm TIMESTAMP NOT NULL,
    payment_mode VARCHAR(50),
	user_id INT NOT NULL,
    Owing_User_id INT,
    group_Id INT,
	constraint fk_expenses_1 foreign key(user_id) references USER(id),
	constraint fk_expenses_2 foreign key(Owing_User_id) references USER(id)
);


CREATE TABLE Network (
    user_id INT NOT NULL,
    conn_user_id INT NOT NULL,
    PRIMARY KEY (user_id, conn_user_id),
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (conn_user_id) REFERENCES User(id)
);

CREATE TABLE SPLIT_GROUP (
    group_id INT NOT NULL,
	group_name varchar(255) NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (group_id, user_id),
    FOREIGN KEY (user_id) REFERENCES User(id)
);
