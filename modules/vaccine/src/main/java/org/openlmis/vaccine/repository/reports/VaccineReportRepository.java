/*
 * This program was produced for the U.S. Agency for International Development. It was prepared by the USAID | DELIVER PROJECT, Task Order 4. It is part of a project which utilizes code originally licensed under the terms of the Mozilla Public License (MPL) v2 and therefore is licensed under MPL v2 or later.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the Mozilla Public License as published by the Mozilla Foundation, either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Mozilla Public License for more details.
 *
 * You should have received a copy of the Mozilla Public License along with this program. If not, see http://www.mozilla.org/MPL/
 */

package org.openlmis.vaccine.repository.reports;

import org.openlmis.vaccine.domain.reports.VaccineReport;
import org.openlmis.vaccine.repository.mapper.reports.VaccineReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaccineReportRepository {

  @Autowired
  VaccineReportMapper mapper;

  public void insert(VaccineReport report){
    mapper.insert(report);
  }

  public void update(VaccineReport report){
    mapper.update(report);
  }

  public VaccineReport getById(Long id){
    return mapper.getById(id);
  }

  public VaccineReport getByIdWithFullDetails(Long id){
    return mapper.getByIdWithFullDetails(id);
  }

  public VaccineReport getByProgramPeriod(Long facilityId, Long programId, Long periodId ){
    return mapper.getByPeriodFacilityProgram(facilityId, programId, periodId);
  }

  public VaccineReport getLastReport(Long facilityId, Long programId) {
    return mapper.getLastReport(facilityId, programId);
  }

  public Long getScheduleFor(Long facilityId, Long programId) {
    return mapper.getScheduleFor(facilityId, programId);
  }
}
