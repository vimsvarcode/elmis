
DROP VIEW IF EXISTS vw_requisition_detail_2;

CREATE OR REPLACE VIEW vw_requisition_detail_2 AS 
 SELECT 
dosage_units.code AS du_code,
facilities.code AS facility_code,
facilities.id AS facility_id,
facilities.name AS facility_name,
facilities.sdp AS facility_is_sdp,
facilities.enabled AS facility_is_enabled,
facility_types.id AS facility_type_id,
facility_types.name AS facility_type_name,
facility_types.nominaleop,
facility_types.nominalmaxmonth,
geographic_zones.id AS zone_id,
geographic_zones.name AS region,
processing_periods.enddate AS processing_periods_end_date,
processing_periods.id AS processing_periods_id,
processing_periods.name AS processing_periods_name,
processing_periods.scheduleid AS processing_schedules_id,
processing_periods.scheduleid,
processing_periods.startdate AS processing_periods_start_date,
product_forms.code AS pf_code,
products.code AS product_code,
products.description AS product_description,
products.dispensingunit,
products.id AS product_id,
products.primaryname AS product_primaryname,
products.productgroupid,
products.tracer AS indicator_product,
product_categories.name as categoryname,
product_categories.id AS categoryid,
programs.name AS program_name,
programs.id AS program_id,
requisition_line_items.amc,
requisition_line_items.beginningbalance,
requisition_line_items.calculatedorderquantity,
requisition_line_items.fullsupply,
requisition_line_items.id AS req_line_id,
requisition_line_items.maxmonthsofstock,
requisition_line_items.maxstockquantity,
requisition_line_items.newpatientcount,
requisition_line_items.normalizedconsumption,
requisition_line_items.packsize,
requisition_line_items.packstoship,
requisition_line_items.product,
requisition_line_items.productcode,
requisition_line_items.previousstockinhand,
requisition_line_items.quantityapproved,
requisition_line_items.quantitydispensed,
requisition_line_items.quantityreceived,
requisition_line_items.quantityrequested,
requisition_line_items.stockinhand,
requisition_line_items.stockoutdays,
requisition_line_items.totallossesandadjustments,
requisitions.emergency,
requisitions.id AS req_id,
requisitions.status AS req_status,
processing_schedules.name as schedulename
   FROM requisition_line_items
   JOIN requisitions ON requisitions.id = requisition_line_items.rnrid
   JOIN facilities ON facilities.id = requisitions.facilityid
   JOIN facility_types ON facility_types.id = facilities.typeid
   JOIN processing_periods ON processing_periods.id = requisitions.periodid
   JOIN processing_schedules ON processing_schedules.id = processing_periods.scheduleid
   JOIN products ON products.code::text = requisition_line_items.productcode::text
   JOIN program_products ON requisitions.programid = program_products.programid AND products.id = program_products.productid
   JOIN product_categories ON product_categories.id = program_products.productcategoryid
   JOIN programs ON programs.id = requisitions.programid
   JOIN geographic_zones ON geographic_zones.id = facilities.geographiczoneid
   JOIN dosage_units ON dosage_units.id = products.dosageunitid
   JOIN product_forms ON product_forms.id = products.formid;

ALTER TABLE vw_requisition_detail_2
  OWNER TO postgres;
