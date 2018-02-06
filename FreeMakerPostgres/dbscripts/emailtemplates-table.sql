
-- Table: public.emailtemplates

-- DROP TABLE public.emailtemplates;

CREATE TABLE public.emailtemplates
(
  
    id character varying COLLATE pg_catalog."default",
    template_content character varying COLLATE pg_catalog."default"
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.emailtemplates
    OWNER to postgres;