CREATE TABLE login_users (
  id INTEGER PRIMARY KEY AUTOINCREMENT not null,
  userName VARCHAR(50) NOT NULL,
  userPass VARCHAR(20) NULL NULL,
  CONSTRAINT userUnique UNIQUE (userName)
);
