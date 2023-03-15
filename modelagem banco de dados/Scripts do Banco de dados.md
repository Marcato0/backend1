### Criação do banco de dados
    CREATE DATABASE backend1
        WITH
        OWNER = postgres
        ENCODING = 'UTF8'
        CONNECTION LIMIT = -1
        IS_TEMPLATE = False;

### Criação da extensçao de UUID
    CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

### Tabela de cotações
        CREATE TABLE IF NOT EXISTS budget (
            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
            email VARCHAR(50) NOT NULL,
            name VARCHAR(80) NOT NULL,
            phone VARCHAR(11) NOT NULL,
            request_date DATE NOT NULL,
            product_fk UUID NOT NULL,

            FOREIGN KEY (product_fk)
                    REFERENCES product (id)
        );

### Tabela de Produtos
    CREATE TABLE IF NOT EXISTS product(
            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
            description VARCHAR(255) NOT NULL,
            name VARCHAR(80) NOT NULL,
            registration_date DATE NOT NULL,
            status BOOLEAN NOT NULL
    );

### Tabela de Culturas
    CREATE TABLE IF NOT EXISTS culture(
            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
            name VARCHAR(50) NOT NULL
    
    );

### Tabela de relacionamento de Produtos com Culturas
    CREATE TABLE IF NOT EXISTS product_culture (
            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
            area_size VARCHAR(50),
            product_fk UUID NOT NULL,
            culture_fk UUID NOT NULL,
            
    
            FOREIGN KEY (culture_fk)
                    REFERENCES culture (id),
    
            FOREIGN KEY (product_fk)
                    REFERENCES product (id)
    );


### Tabela de fotos dos produtos
    CREATE TABLE IF NOT EXISTS photo(
            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
            image BYTEA NOT NULL,
            name VARCHAR(80) NOT NULL,
            TYPE VARCHAR NOT NULL,
            product_fk UUID,
           
            FOREIGN KEY (product_fk)
                    REFERENCES product (id)
    );

