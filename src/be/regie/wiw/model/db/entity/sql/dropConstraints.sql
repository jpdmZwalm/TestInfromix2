alter table "dbo"."degree"
drop constraint deg_nl_UI

alter table "dbo"."degree"
ALTER column deg_descr_nl nchar(55) NOT NULL

