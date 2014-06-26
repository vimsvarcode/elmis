/*
 * This program was produced for the U.S. Agency for International Development. It was prepared by the USAID | DELIVER PROJECT, Task Order 4. It is part of a project which utilizes code originally licensed under the terms of the Mozilla Public License (MPL) v2 and therefore is licensed under MPL v2 or later.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the Mozilla Public License as published by the Mozilla Foundation, either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Mozilla Public License for more details.
 *
 * You should have received a copy of the Mozilla Public License along with this program. If not, see http://www.mozilla.org/MPL/
 */

package org.openlmis.report.service;

import org.openlmis.report.DataSourceType;
import org.openlmis.report.model.ReportData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ReportDataProvider {

  private long userId;

  public void setUserId(Long id){
    userId = id;
  }

  public long getUserId(){
    return userId;
  }

  public final List<? extends ReportData> getReportDataByFilterCriteria(Map<String, String[]> params, DataSourceType dataSourceType){
    return getResultSetReportData(params);
  }

  public final List<? extends ReportData> getReportDataByFilterCriteria(Map<String, String[]> params){
      return getReportDataByFilterCriteria(params, DataSourceType.BEAN_COLLECTION_DATA_SOURCE);
  }

  protected abstract List<? extends ReportData> getResultSetReportData(Map<String, String[]> params);

  public abstract List<? extends ReportData> getMainReportData(Map<String, String[]> filter, Map<String, String[]> sorter, int page, int pageSize);

  public HashMap<String,String> getAdditionalReportData(Map params){
      return null;
  }

  public String getFilterSummary(Map<String, String[]> params){
    return "";
  }
}
