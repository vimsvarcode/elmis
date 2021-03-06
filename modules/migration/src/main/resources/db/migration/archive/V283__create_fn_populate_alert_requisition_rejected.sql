-- Function: fn_populate_alert_requisition_rejected()

DROP FUNCTION IF EXISTS fn_populate_alert_requisition_rejected();

CREATE OR REPLACE FUNCTION fn_populate_alert_requisition_rejected()
  RETURNS character varying AS
$BODY$
DECLARE
rec_summary RECORD ;
rec_detail RECORD ;

msg CHARACTER VARYING (2000) ;
v_summaryid integer;

BEGIN
msg := 'fn_populate_alert_requisition_rejected - Data saved successfully' ;
delete from alert_summary where alerttypeid = 'REQUISITION_REJECTED';
/*
 summary
*/

FOR rec_summary IN
SELECT
geographiczoneid, programid, periodid, count(rnrid) rec_count
FROM
vw_facility_requisitions
where  status = 'IN_APPROVAL'
group by 1,2, 3
LOOP

INSERT INTO alert_summary(
statics_value, description, geographiczoneid, alerttypeid, programid, periodid)
VALUES (rec_summary.rec_count, ' Requisitions Rejected', rec_summary.geographiczoneid, 'REQUISITION_REJECTED', rec_summary.programid, rec_summary.periodid);
end loop;

/*
 detail
*/

DELETE FROM alert_requisition_rejected;
FOR rec_detail IN
SELECT
rnrid,
CASE emergency WHEN true then 'Emergency' else 'Regular' end as req_type,
facilityname,
facilityid,
periodid,
programid,
geographiczoneid,
geographiczonename
FROM
vw_facility_requisitions
where status = 'REJECTED'

LOOP --fetch the table row inside the loop
select id into v_summaryid from alert_summary where geographiczoneid = rec_detail.geographiczoneid and programid = rec_detail.programid and  periodid = rec_detail.periodid and alerttypeid = 'REQUISITION_REJECTED';

INSERT INTO alert_requisition_rejected(
alertsummaryid, programid, periodid, geographiczoneid, geographiczonename,rnrid, rnrtype, facilityid, facilityname)
VALUES (v_summaryid, rec_detail.programid, rec_detail.periodid, rec_detail.geographiczoneid, rec_detail.geographiczonename, rec_detail.rnrid, rec_detail.req_type, rec_detail.facilityid, rec_detail.facilityname);
END LOOP;
RETURN msg ;
EXCEPTION
WHEN OTHERS THEN
RETURN 'fn_populate_alert_requisition_rejected - Error populating data. Please consult database administrtor. ' || SQLERRM ;
END ;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fn_populate_alert_requisition_rejected()
  OWNER TO postgres;

