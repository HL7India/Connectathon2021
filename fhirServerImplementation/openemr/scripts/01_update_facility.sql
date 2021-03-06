use openemr; 

-- Updading the facility with a synthetic name and contact information 
update facility f set
f.name = "Riverside Hospital"
, f.street = "4357 Woodlawn Drive"
, f.city = "Green Bay"
, f.state = "Wisconsin"
, f.postal_code = "531510"
, f.email = "BenjaminDWard@armyspy.com"
, f.phone = "414-698-8023"
, f.fax = "414-698-8024"
, f.facility_npi = "122134435665"
, f.mail_street = "4357 Woodlawn Drive"
, f.mail_city = "Green bay"
, f.mail_state = "WI"
, f.mail_zip = "531510"
where id = 3; 


-- Changing Facility details to Indian data
UPDATE facility
SET street='13/1 Mount Joy Road Hanumanthanagar', city='Bangalore', state='Karnataka', postal_code='560019',
    mail_street='Mount Joy Road', mail_street2='', mail_city='Bangalore', mail_state='KR', mail_zip='560019'
WHERE id=3;

