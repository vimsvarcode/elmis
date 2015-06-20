<<<<<<< HEAD
package org.openlmis.stock.repository.mapper;

import org.apache.ibatis.annotations.*;
import org.openlmis.stock.domain.GeographicZoneArrivalPackage;
import org.openlmis.stock.domain.GeographicZonePackage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Morley on 6/14/2015.
 */

@Repository
public interface GeographicZoneArrivalPackageMapper {

    @Select("select * from geographic_zone_stock")
    List<GeographicZoneArrivalPackage> getAll();

    @Insert("insert into geographic_zone_stock (sscc, package_number, lot_number, number_as_expected, gtin, number_recieved, number_expected, physical_damage, vvm_status, problems, receiving_user, geographic_zone_id) values " +
            "(#{sscc}, #{package_number}, #{lot_number}, #{number_as_expected}, #{gtin}, #{number_recieved}, #{number_expected}, #{physical_damage}, #{vvm_status}, #{problems}, #{receiving_user}, #{geographic_zone_id})")
    @Options(flushCache = true, useGeneratedKeys = true)
    Integer insert(GeographicZoneArrivalPackage geographicZoneArrivalPackage);

    @Update("update geographic_zone_stock " +
            "set " +
            " sscc = #{sscc}, " +
            " package_number = #{package_number}," +
            " lot_number = #{lot_number}, " +
            " number_as_expected = #{number_as_expected}, " +
            " gtin = #{gtin} " +
            " number_recieved = #{number_recieved} " +
            " number_expected = #{number_expected} " +
            " physical_damage = #{physical_damage} " +
            " vvm_status = #{vvm_status} " +
            " problems = #{problems} " +
            " receiving_user = #{receiving_user} " +
            " geographic_zone_id = #{geographic_zone_id} " +
            "where id = #{id}")
    void update(GeographicZoneArrivalPackage geographicZoneArrivalPackage);

    @Select("select * from geographic_zone_stock where id = #{id}")
    GeographicZoneArrivalPackage getById(@Param("id") Long id);
}
=======
package org.openlmis.stock.repository.mapper;

import org.apache.ibatis.annotations.*;
import org.openlmis.core.domain.GeographicZone;
import org.openlmis.stock.domain.GeographicZoneArrivalPackage;
import org.openlmis.stock.domain.GeographicZonePackage;
import org.openlmis.stock.domain.Vaccine;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Morley on 6/14/2015.
 */

@Repository
public interface GeographicZoneArrivalPackageMapper extends HasGeographicZone,StockMapper<GeographicZoneArrivalPackage>{

    @Select("select * from geographic_zone_arrival_package")
    @Results(value = {
            @Result(property = "geographic_zone", javaType = GeographicZone.class, column = "geographic_zone_id",
                    one = @One(select = "getGeographicZoneById"))
    })
    List<GeographicZoneArrivalPackage> getAll();

    @Insert("insert into geographic_zone_arrival_package (sscc, package_number, lot_number, number_as_expected, gtin, number_recieved, number_expected, physical_damage, vvm_status, problems, receiving_user, geographic_zone_id) values " +
            "(#{sscc}, #{package_number}, #{lot_number}, #{number_as_expected}, #{gtin}, #{number_recieved}, #{number_expected}, #{physical_damage}, #{vvm_status}, #{problems}, #{receiving_user}, #{geographic_zone_id})")
    @Options(flushCache = true, useGeneratedKeys = true)
    Integer insert(GeographicZoneArrivalPackage geographicZoneArrivalPackage);

    @Update("update geographic_zone_arrival_package " +
            "set " +
            " sscc = #{sscc}, " +
            " package_number = #{package_number}," +
            " lot_number = #{lot_number}, " +
            " number_as_expected = #{number_as_expected}, " +
            " gtin = #{gtin} " +
            " number_recieved = #{number_recieved} " +
            " number_expected = #{number_expected} " +
            " physical_damage = #{physical_damage} " +
            " vvm_status = #{vvm_status} " +
            " problems = #{problems} " +
            " receiving_user = #{receiving_user} " +
            " geographic_zone_id = #{geographic_zone_id} " +
            "where id = #{id}")
    void update(GeographicZoneArrivalPackage geographicZoneArrivalPackage);

    @Select("select * from geographic_zone_arrival_package where id = #{id}")
    @Results(value = {
            @Result(property = "geographic_zone", javaType = GeographicZone.class, column = "geographic_zone_id",
                    one = @One(select = "getGeographicZoneById"))
    })
    GeographicZoneArrivalPackage getById(@Param("id") Long id);

}
>>>>>>> ab13d65384b55a34d30f8383fc3f8589e803e45d
