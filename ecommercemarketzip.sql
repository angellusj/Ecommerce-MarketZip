CREATE DATABASE ecommercemarketzip
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE IF NOT EXISTS public.usuario
(
    id_usu integer NOT NULL DEFAULT nextval('usuario_id_usu_seq'::regclass),
    nome_usu character varying(100) COLLATE pg_catalog."default" NOT NULL,
    cpf_usu character varying(14) COLLATE pg_catalog."default" NOT NULL,
    email_usu character varying(200) COLLATE pg_catalog."default" NOT NULL,
    telefone character varying(16) COLLATE pg_catalog."default" NOT NULL,
    senha character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT usuario_pkey PRIMARY KEY (id_usu),
    CONSTRAINT usuario_cpf_usu_key UNIQUE (cpf_usu)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.usuario
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.cliente
(
    id_cli integer NOT NULL DEFAULT nextval('cliente_id_cli_seq'::regclass),
    id_usu integer NOT NULL,
    endereco_cli character varying(200) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT cliente_pkey PRIMARY KEY (id_cli),
    CONSTRAINT cliente_id_usu_fkey FOREIGN KEY (id_usu)
        REFERENCES public.usuario (id_usu) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.cliente
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public.funcionario
(
    id_func integer NOT NULL DEFAULT nextval('funcionario_id_func_seq'::regclass),
    id_usu integer NOT NULL,
    cargo_func character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT funcionario_pkey PRIMARY KEY (id_func),
    CONSTRAINT funcionario_id_usu_fkey FOREIGN KEY (id_usu)
        REFERENCES public.usuario (id_usu) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.funcionario
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.pedido
(
    id_pedido integer NOT NULL DEFAULT nextval('pedido_id_pedido_seq'::regclass),
    data_pedido date NOT NULL,
    finalizar_pedido boolean NOT NULL,
    id_cli integer NOT NULL,
    valor_total_pedido numeric(10,2) NOT NULL,
    CONSTRAINT pedido_pkey PRIMARY KEY (id_pedido),
    CONSTRAINT pedido_id_cli_fkey FOREIGN KEY (id_cli)
        REFERENCES public.cliente (id_cli) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.pedido
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.produto
(
    id_prod integer NOT NULL DEFAULT nextval('produto_id_prod_seq'::regclass),
    nome_prod character varying(200) COLLATE pg_catalog."default" NOT NULL,
    descricao_prod character varying(400) COLLATE pg_catalog."default" NOT NULL,
    preco_prod numeric(10,2) NOT NULL,
    categoria_prod character varying(200) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT produto_pkey PRIMARY KEY (id_prod)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.produto
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.item_pedido
(
    id_item integer NOT NULL DEFAULT nextval('item_pedido_id_item_seq'::regclass),
    id_prod integer NOT NULL,
    id_pedido integer NOT NULL,
    quantidade_item integer NOT NULL,
    CONSTRAINT item_pedido_pkey PRIMARY KEY (id_item),
    CONSTRAINT item_pedido_id_prod_fkey FOREIGN KEY (id_prod)
        REFERENCES public.produto (id_prod) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT item_pedido_id_pedido_fkey FOREIGN KEY (id_pedido)
        REFERENCES public.pedido (id_pedido) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.item_pedido
    OWNER TO postgres;

INSERT INTO usuario (nome_usu, cpf_usu, email_usu, telefone_usu, senha_usu) 
VALUES ('admin', '00000000001', 'admin@ecommerce.com', '11999999999', 'admin123')
ON CONFLICT (cpf_usu) DO NOTHING;

INSERT INTO funcionario (id_usu, cargo_func) 
SELECT id_usu, 'Administrador' FROM usuario 
WHERE nome_usu = 'admin' 
AND id_usu NOT IN (SELECT id_usu FROM funcionario WHERE id_usu IS NOT NULL);