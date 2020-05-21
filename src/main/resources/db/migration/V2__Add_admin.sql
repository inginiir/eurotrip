insert into usr (id, username, password, is_active, email)
    values (1, 'inginiir', '123', true, 'eur0trip@yandex.ru');

insert into user_role (user_id, roles)
    values (1, 'USER'), (1, 'ADMIN');