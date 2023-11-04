CREATE TABLE patients (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  cpf VARCHAR(11) NOT NULL UNIQUE,
  telephone VARCHAR(20) NOT NULL,
  public_area VARCHAR(100) NOT NULL,
  neighborhood VARCHAR(100) NOT NULL,
  cep VARCHAR(9) NOT NULL,
  uf VARCHAR(2) NOT NULL,
  city VARCHAR(100) NOT NULL,
  complement VARCHAR(100) NULL,
  number VARCHAR(20) NULL,
  PRIMARY KEY (id)
 );
