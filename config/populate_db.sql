insert into resume (uuid, full_name)
values ('3b4423fa-c3c7-49f5-bcd5-c76b580bb723', 'Alice'),
       ('588f2132-01a3-4ed1-8a15-858dd2b0bb37', 'Edward'),
       ('90147138-7c17-4dd9-a2d4-57549b078398', 'Alex');

insert into contact (resume_uuid, type, value)
values ('3b4423fa-c3c7-49f5-bcd5-c76b580bb723', 'PHONE', '+7(921) 855-0482)'),
       ('3b4423fa-c3c7-49f5-bcd5-c76b580bb723', 'SKYPE', 'skype');