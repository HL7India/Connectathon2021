-- Change State and Country

-- delete all existing states and countries
delete from list_options where list_id IN ('country','state');

INSERT INTO list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('country', 'India', 'India', 2, 0, 0.0, '', NULL, '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'DL', 'Delhi', 1, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'KA', 'Karnataka', 2, 0, 0.0, '', '', '', 0, 0, 1, '', 1,now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'MH', 'Maharashtra', 3, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'AP', 'Andhra Pradesh', 5, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'HR', 'Haryana', 6, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'MN', 'Manipur', 7, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'SK', 'Sikkim', 8, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options	
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'AR', 'Arunachal Pradesh', 9, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'HP', 'Himachal Pradesh', 10, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'ML', 'Meghalaya',11, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'TN', 'Tamil Nadu', 12, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'AS', 'Assam', 13, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'JH', 'Jharkhand', 14, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'MZ', 'Mizoram', 15, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'TS', 'Telangana', 16, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'BR', 'Bihar', 17, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'NL', 'Nagaland', 18, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'TR', 'Tripura', 19, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'CG', 'Chhattisgarh', 20, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'KL', 'Kerala', 21, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());


INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'OD', 'Odisha',22, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());


INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'UK', 'Uttrakhand', 23, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'GA', 'Goa', 24, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'MP', 'Madhya Pradesh', 25, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'PB', 'Punjab', 26, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());


INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'UP', 'Uttar Pradesh', 27, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());

INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'GJ', 'Gujarat	', 28, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());


INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'RJ', 'Rajasthan', 29, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());


INSERT INTO openemr.list_options
(list_id, option_id, title, seq, is_default, option_value, mapping, notes, codes, toggle_setting_1, toggle_setting_2, activity, subtype, edit_options, `timestamp`)
VALUES('state', 'WB', 'West Bengal', 30, 0, 0.0, '', '', '', 0, 0, 1, '', 1, now());


