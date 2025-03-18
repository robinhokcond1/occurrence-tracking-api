CREATE TABLE cliente (
    cod_cliente SERIAL PRIMARY KEY,
    nme_cliente VARCHAR(50) NOT NULL,
    dta_nascimento DATE,
    nro_cpf VARCHAR(11) UNIQUE NOT NULL,
    dta_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE endereco (
    cod_endereco SERIAL PRIMARY KEY,
    nme_logradouro VARCHAR(100),
    nme_bairro VARCHAR(50),
    nro_cep VARCHAR(10),
    nme_cidade VARCHAR(50),
    nme_estado VARCHAR(50)
);

CREATE TABLE ocorrencia (
    cod_ocorrencia SERIAL PRIMARY KEY,
    cod_cliente INT NOT NULL,
    cod_endereco INT NOT NULL,
    dta_ocorrencia TIMESTAMP NOT NULL,
    sta_ocorrencia VARCHAR(20) NOT NULL,
    FOREIGN KEY (cod_cliente) REFERENCES cliente (cod_cliente),
    FOREIGN KEY (cod_endereco) REFERENCES endereco (cod_endereco)
);

CREATE TABLE foto_ocorrencia (
    cod_foto_ocorrencia SERIAL PRIMARY KEY,
    cod_ocorrencia INT NOT NULL,
    dta_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    dsc_path_bucket VARCHAR(255) NOT NULL,
    dsc_hash VARCHAR(255) NOT NULL,
    FOREIGN KEY (cod_ocorrencia) REFERENCES ocorrencia (cod_ocorrencia)
);
