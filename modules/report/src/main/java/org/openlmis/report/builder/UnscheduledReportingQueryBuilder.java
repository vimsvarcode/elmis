/*
 * This program was produced for the U.S. Agency for International Development. It was prepared by the USAID | DELIVER PROJECT, Task Order 4. It is part of a project which utilizes code originally licensed under the terms of the Mozilla Public License (MPL) v2 and therefore is licensed under MPL v2 or later.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the Mozilla Public License as published by the Mozilla Foundation, either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Mozilla Public License for more details.
 *
 * You should have received a copy of the Mozilla Public License along with this program. If not, see http://www.mozilla.org/MPL/
 */


package org.openlmis.report.builder;


import java.util.Map;


public class UnscheduledReportingQueryBuilder {


    public static String getQuery(Map params) {

        Map filterCriteria = (Map) params.get("filterCriteria");
        Long userId = (Long) params.get("userId");

        String sql = "";

        sql = "SELECT facilityCode,facilityName,facilityTypeName,vw_districts.district_name district,vw_districts.region_name region,vw_districts.zone_name depot,createdDate duration   " +
                "                FROM vw_timeliness_report \n" +
                "                JOIN vw_districts on geographiczoneId= vw_districts.district_id  " +
              writePredicates(filterCriteria, userId) +
             " GROUP BY facilitycode,facilityname,facilitytypename,region,depot,duration,vw_districts.district_name\n" +
             " ORDER BY district,depot ";

        return sql;


    }


    private static String writePredicates(Map params, Long userId) {

        String predicate = "  WHERE ";
        String period = params.get("period") == null ? null : ((String[]) params.get("period"))[0];
        String program = params.get("program") == null ? null : ((String[]) params.get("program"))[0];

        String zone = params.get("zone") == null ? null : ((String[]) params.get("zone"))[0];

        String schedule = params.get("schedule") == null ? null : ((String[]) params.get("schedule"))[0];
        predicate += "  facilityId in (select facility_id from vw_user_facilities where user_id = " + userId + " and program_id = " + program + ")";
        predicate += " and periodId = " + period;

        predicate += " and programId = " + program;

        predicate += " and scheduleId = " + schedule;

        predicate += " and reportingStatus = 'U' and parent is not null ";


        if (zone != null && !zone.isEmpty() && !zone.equals("0") && !zone.equals("-1")) {

            predicate += " and (district_id = " + zone + " or zone_id =  " + zone + "  or region_id =  " + zone + "  or parent =  " + zone + " ) ";
        }

        return predicate;
    }

}
