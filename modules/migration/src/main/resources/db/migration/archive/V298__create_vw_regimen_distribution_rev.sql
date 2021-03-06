DROP VIEW IF EXISTS vw_regimen_district_distribution;

CREATE OR REPLACE VIEW vw_regimen_district_distribution AS 
 SELECT DISTINCT r.programid, ps.id AS scheduleid, pp.id AS periodid, rg.id AS rgroupid, 
 regimens.categoryid, regimens.id AS regimenid, regimens.name AS regimen, gz.name AS district, 
 li.patientsontreatment, li.patientstoinitiatetreatment, li.patientsstoppedtreatment, 
 r.facilityid, r.status, f.name AS facilityname, f.typeid AS facilitytypeid, gz.id AS zoneid
   FROM regimen_line_items li
   JOIN requisitions r ON li.rnrid = r.id
   JOIN facilities f ON r.facilityid = f.id
   JOIN facility_types ft ON f.typeid = ft.id
   JOIN geographic_zones gz ON gz.id = f.geographiczoneid
   JOIN geographic_zones zone ON gz.parentid = zone.id
   JOIN geographic_zones c ON zone.parentid = c.id
   JOIN processing_periods pp ON r.periodid = pp.id
   JOIN processing_schedules ps ON pp.scheduleid = ps.id
   JOIN requisition_group_members ON r.facilityid = requisition_group_members.facilityid
   JOIN requisition_groups rg ON requisition_group_members.requisitiongroupid = rg.id
   JOIN regimens ON r.programid = regimens.programid;