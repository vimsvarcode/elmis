/*
 * This program was produced for the U.S. Agency for International Development. It was prepared by the USAID | DELIVER PROJECT, Task Order 4. It is part of a project which utilizes code originally licensed under the terms of the Mozilla Public License (MPL) v2 and therefore is licensed under MPL v2 or later.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the Mozilla Public License as published by the Mozilla Foundation, either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Mozilla Public License for more details.
 *
 * You should have received a copy of the Mozilla Public License along with this program. If not, see http://www.mozilla.org/MPL/
 */

package org.openlmis.report.model.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openlmis.report.model.ReportData;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFillRateReport implements ReportData {

    private String facility;
    @Column(name = "approved")
    private Integer approved;
    @Column(name="receipts")
    private Integer receipts;
    @Column( name = "productcode")
    private String productcode;
    @Column( name = "product")
    private String product;
    private Integer item_fill_rate;
    private String ORDER_FILL_RATE;
    @Column(name="facilitytype")
    private String facilityType;
    @Column(name="supplyingfacility")
    private String supplyingFacility ;
    @Column(name="category")
    private String category;
    private Integer totalproductsreceived;
    private Integer totalproductsapproved;
    private String name;
    private String nameLabel;
    private String count;

}
