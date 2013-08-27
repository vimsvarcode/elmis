DROP VIEW IF EXISTS vw_stock_status;


CREATE OR REPLACE VIEW vw_stock_status AS
 SELECT fn_get_supplying_facility_name(requisitions.supplyingfacilityid) AS supplyingfacility,
    facilities.code AS facilitycode, facilities.name AS facility,
    requisition_line_items.product, requisition_line_items.stockinhand,
    requisition_line_items.amc,
        CASE
            WHEN COALESCE(requisition_line_items.amc, 0) = 0 THEN 0::numeric
            ELSE round((requisition_line_items.stockinhand / requisition_line_items.amc)::numeric, 1)
        END AS mos,
    COALESCE(
        CASE
            WHEN (COALESCE(requisition_line_items.amc, 0) * facility_types.nominalmaxmonth - requisition_line_items.stockinhand) < 0 THEN 0
            ELSE COALESCE(requisition_line_items.amc, 0) * facility_types.nominalmaxmonth - requisition_line_items.stockinhand
        END, 0) AS required,
        CASE
            WHEN requisition_line_items.stockinhand = 0 THEN 'SO'::text
            ELSE
            CASE
                WHEN requisition_line_items.stockinhand > 0 AND requisition_line_items.stockinhand::numeric <= (COALESCE(requisition_line_items.amc, 0)::numeric * facility_types.nominaleop) THEN 'US'::text
                ELSE
                CASE
                    WHEN requisition_line_items.stockinhand > (COALESCE(requisition_line_items.amc, 0) * facility_types.nominalmaxmonth) THEN 'OS'::text
                    ELSE 'SP'::text
                END
            END
        END AS status,
    facility_types.name AS facilitytypename, geographic_zones.name AS location,
    products.id AS productid, processing_periods.startdate,
    processing_periods.enddate, processing_periods.id AS periodid,
    facility_types.id AS facilitytypeid,
    requisition_group_members.requisitiongroupid AS rgid, products.categoryid,
    products.tracer AS indicator_product
   FROM requisition_line_items
   JOIN requisitions ON requisitions.id = requisition_line_items.rnrid
   JOIN facilities ON facilities.id = requisitions.facilityid
   JOIN facility_types ON facility_types.id = facilities.typeid
   JOIN processing_periods ON processing_periods.id = requisitions.periodid
   JOIN processing_schedules ON processing_schedules.id = processing_periods.scheduleid
   JOIN products ON products.code::text = requisition_line_items.productcode::text
   JOIN product_categories ON product_categories.id = products.categoryid
   JOIN programs ON programs.id = requisitions.programid
   JOIN requisition_group_members ON requisition_group_members.facilityid = facilities.id
   JOIN geographic_zones ON geographic_zones.id = facilities.geographiczoneid
  WHERE requisition_line_items.stockinhand IS NOT NULL AND requisitions.status::text = 'RELEASED'::text;

ALTER TABLE vw_stock_status
  OWNER TO postgres;