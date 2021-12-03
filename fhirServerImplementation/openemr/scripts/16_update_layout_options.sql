-- Changes for Demographics Form to show necessary fields
-- And to show Health ID
UPDATE layout_options SET form_id = 'DEM',field_id = 'billing_note', group_id = '1',title = 'Billing Note',  seq = '17',data_type = '2', uor = 0,fld_length = 60,max_length = 0, titlecols = 1,datacols = 3, description = 'Billing Note (Collections)',fld_rows = 0,source = 'F' WHERE form_id= 'DEM' AND field_id= 'billing_note';
UPDATE layout_options SET form_id = 'DEM',field_id = 'genericname1', group_id = '1',title = 'User Defined',  seq = '12',data_type = '2', uor = 0,fld_length = 15,max_length = 63,titlecols = 1,datacols = 3, description = 'User Defined Field',        fld_rows = 0,source = 'F' WHERE form_id= 'DEM' AND field_id= 'genericname1';
UPDATE layout_options SET form_id = 'DEM',field_id = 'genericname2', group_id = '1',title = 'Generic Name 2',seq = '13',data_type = '2', uor = 0,fld_length = 15,max_length = 63,titlecols = 0,datacols = 0, description = 'User Defined Field',        fld_rows = 0,source = 'F' WHERE form_id= 'DEM' AND field_id= 'genericname2';
UPDATE layout_options SET form_id = 'DEM',field_id = 'genericval1',  group_id = '1',title = 'Health ID',     seq = '11',data_type = '2', uor = 1,fld_length = 15,max_length = 63,titlecols = 1,datacols = 1, description = 'User Defined Field',        fld_rows = 0,source = 'F' WHERE form_id= 'DEM' AND field_id= 'genericval1';
UPDATE layout_options SET form_id = 'DEM',field_id = 'genericval2',  group_id = '1',title = 'Generic Val 2', seq = '14',data_type = '2', uor = 0,fld_length = 15,max_length = 63,titlecols = 0,datacols = 0, description = 'User Defined Field',        fld_rows = 0,source = 'F' WHERE form_id= 'DEM' AND field_id= 'genericval2';
UPDATE layout_options SET form_id = 'DEM',field_id = 'pricelevel',   group_id = '1',title = 'Price Level',   seq = '16',data_type = '1', uor = 0,fld_length = 0, max_length = 0, titlecols = 1,datacols = 1,  description = 'Discount Level',           fld_rows = 0,source = 'F' WHERE form_id= 'DEM' AND field_id= 'pricelevel';
UPDATE layout_options SET form_id = 'DEM',field_id = 'squad',        group_id = '1',title = 'Squad',         seq = '15',data_type = '13',uor = 0,fld_length = 0, max_length = 0, titlecols = 1,datacols = 3, description = 'Squad Membership',          fld_rows = 0,source = 'F' WHERE form_id= 'DEM' AND field_id= 'squad';
UPDATE layout_options SET form_id = 'DEM',field_id = 'ss',           group_id = '1',title = 'Aadhaar',       seq = '8', data_type = '2', uor = 1,fld_length = 0, max_length = 12,titlecols = 1,datacols = 1, description = 'Aadhaar Identification',    fld_rows = 0,source = 'F' WHERE form_id= 'DEM' AND field_id= 'ss';
