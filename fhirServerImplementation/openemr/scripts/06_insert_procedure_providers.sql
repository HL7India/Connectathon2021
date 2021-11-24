insert into procedure_providers(name, npi, DorP, direction, protocol, lab_director, active)
values (
(select organization from users where  abook_type = 'ord_lab'), 
'1221344356', 'D', 'B', 'DL', 
(select id from users where abook_type = 'ord_lab'), 
'1');