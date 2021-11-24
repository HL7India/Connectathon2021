-- Delete encounters that do not have LAB Orders/ Results 
delete from form_encounter where encounter not in 
(select distinct encounter from forms);