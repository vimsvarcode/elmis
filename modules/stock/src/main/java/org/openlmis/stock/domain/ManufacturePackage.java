<<<<<<< HEAD
package org.openlmis.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.openlmis.core.domain.BaseModel;

import java.util.Date;

/**
 * Created by Morley on 6/14/2015.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ManufacturePackage extends BaseModel {
    Long id;
    String sscc;
    Date manufacture_date;
    Date expire_date;
    Integer lot_numbe;
    Integer number_of_doses;
    String delivery_status;
    Integer vaccine_id;
}
=======
package org.openlmis.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.openlmis.core.domain.BaseModel;

import java.util.Date;

/**
 * Created by Morley on 6/14/2015.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ManufacturePackage  extends StockModel {
    Long id;
    String sscc;
    Date manufacture_date;
    Date expire_date;
    Integer lot_number;
    Integer number_of_doses;
    String delivery_status;
    Integer vaccine_id;

    Vaccine vaccine;
    @Override
    public String getTableName() {
        return "manufacture_package";
    }
}
>>>>>>> ab13d65384b55a34d30f8383fc3f8589e803e45d
