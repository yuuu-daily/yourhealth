DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users (
  user_id SERIAL NOT NULL,
  authority VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS weight_histories CASCADE;

CREATE TABLE IF NOT EXISTS weight_histories (
  id SERIAL NOT NULL,
  user_id INT NOT NULL,
  weight DECIMAL(3,1) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE weight_histories ADD CONSTRAINT FK_users_weight_histories FOREIGN KEY (user_id) REFERENCES users;