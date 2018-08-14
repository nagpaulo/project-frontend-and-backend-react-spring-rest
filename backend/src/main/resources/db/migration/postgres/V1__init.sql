CREATE TABLE ponto.tb_empresa
(
    ci_empresa bigserial NOT NULL,
    nr_razaosocial character varying(300) COLLATE pg_catalog."default" NOT NULL,
    nr_cnpj character varying(18) COLLATE pg_catalog."default" NOT NULL,
    dt_criacao timestamp without time zone NOT NULL,
    dt_atualizacao timestamp without time zone,
    CONSTRAINT pk_empresa PRIMARY KEY (ci_empresa)
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;

CREATE TABLE ponto.tb_funcionario
(
    ci_funcionario bigserial NOT NULL,
    nm_funcionario character varying(300) COLLATE pg_catalog."default" NOT NULL,
    ds_email character varying(180) COLLATE pg_catalog."default" NOT NULL,
    nm_senha character varying(180) COLLATE pg_catalog."default" NOT NULL,
    nr_cpf character varying(14) COLLATE pg_catalog."default" NOT NULL,
    nr_valor_hora numeric,
    nr_hora_trabalho_dia numeric,
    nr_hora_almoco numeric,
    cd_perfil integer NOT NULL,
    dt_criacao timestamp without time zone NOT NULL,
    dt_atualizacao timestamp without time zone,
    empresa_ci_empresa bigint,
    CONSTRAINT pk_funcionario PRIMARY KEY (ci_funcionario),
    CONSTRAINT fk_empresa FOREIGN KEY (empresa_ci_empresa)
    REFERENCES ponto.tb_empresa (ci_empresa) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;

CREATE TABLE ponto.tb_lancamento
(
    ci_lancamento bigserial NOT NULL,
    dt_ponto time without time zone NOT NULL,
    ds_informacao character varying(500) COLLATE pg_catalog."default" NOT NULL,
    ds_localizacao character varying(300) COLLATE pg_catalog."default" NOT NULL,
    dt_criacao timestamp without time zone NOT NULL,
    dt_atualizacao timestamp without time zone,
    cd_tipo integer NOT NULL,
    funcionario_ci_funcionario bigint NOT NULL,
    CONSTRAINT pk_lancamento PRIMARY KEY (ci_lancamento),
    CONSTRAINT fk_funcionario FOREIGN KEY (funcionario_ci_funcionario)
    REFERENCES ponto.tb_funcionario (ci_funcionario) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE ponto.tb_empresa OWNER to postgres;
ALTER TABLE ponto.tb_funcionario OWNER to postgres;
ALTER TABLE ponto.tb_lancamento OWNER to postgres;