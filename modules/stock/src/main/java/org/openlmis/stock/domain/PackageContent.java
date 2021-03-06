package org.openlmis.stock.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class PackageContent  extends StockModel {

    Long id;
    String level;
    Long package_id;
    Integer number_of_boxes;
    String lot_number;
    String delivery_status;

    //@JsonIgnore
    Long vaccine_id;
    Vaccine vaccine;

    @Override
    public String getTableName() {
        return "package_contents";
    }
}
