create extension if not exists pgcrypto;

UPDATE usr set password = crypt(password, gen_salt('bf', 8));