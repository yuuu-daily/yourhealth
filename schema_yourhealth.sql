DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users (
  user_id SERIAL NOT NULL,
  authority VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  target_weight DECIMAL(4,1) NOT NULL DEFAULT 0,
  purpose VARCHAR(255) NOT NULL DEFAULT'Not set',
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS weight_histories CASCADE;

CREATE TABLE IF NOT EXISTS weight_histories (
  id SERIAL NOT NULL,
  user_id INT NOT NULL,
  weight DECIMAL(4,1) NOT NULL DEFAULT 0,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE weight_histories ADD CONSTRAINT FK_users_weight_histories FOREIGN KEY (user_id) REFERENCES users;

DROP TABLE IF EXISTS training_menus CASCADE;

CREATE TABLE IF NOT EXISTS training_menus (
  id SERIAL NOT NULL,
  user_id INT NOT NULL,
  category VARCHAR(255) NOT NULL DEFAULT'NoCategory',
  title VARCHAR(255) NOT NULL DEFAULT'NoTitle',
  description VARCHAR(1000) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE training_menus ADD CONSTRAINT FK_users_training_menus FOREIGN KEY (user_id) REFERENCES users;