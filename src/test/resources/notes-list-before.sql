delete from travel_note;

insert into travel_note(id, name_note, note, user_id) values
(1, 'first', 'my-tag', 1),
(2, 'second', 'more', 1),
(3, 'third', 'my-tag', 1),
(4, 'fourth', 'another', 2);

alter sequence hibernate_sequence restart with 10;