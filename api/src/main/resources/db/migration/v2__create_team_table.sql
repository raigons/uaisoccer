CREATE TABLE team (
    id bigint NOT NULL,
    key character varying(100),
    name character varying(150)
);

CREATE SEQUENCE team_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
