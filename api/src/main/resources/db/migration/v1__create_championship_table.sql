CREATE TABLE championship (
    id bigint NOT NULL,
    name character varying(255)
);

CREATE SEQUENCE championship_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
