package org.openlmis.core.builder;

import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;
import org.joda.time.LocalDate;
import org.openlmis.core.domain.*;

import java.util.Date;

import static com.natpryce.makeiteasy.Property.newProperty;

public class FacilityBuilder {

  public static final Property<Facility, String> code = newProperty();
  public static final Property<Facility, String> name = newProperty();
  public static final Property<Facility, String> type = newProperty();
  public static final Property<Facility, Integer> geographicZoneId = newProperty();
  public static final Property<Facility, String> geographicZoneCode = newProperty();
  public static final Property<Facility, Boolean> sdp = newProperty();
  public static final Property<Facility, Boolean> active = newProperty();
  public static final Property<Facility, Date> goLiveDate = newProperty();
  public static final Property<Facility, String> operatedByCode = newProperty();
  public static final Property<Facility, GeographicZone> geographicZone = newProperty();
  public static Property<Facility, Integer> typeId = newProperty();
  private static Property<Facility, Integer> operatedById = newProperty();
  public static final Property<Facility, Boolean> dataReportable = newProperty();

  public static final String FACILITY_CODE = "F10010";
  public static final String FACILITY_TYPE_CODE = "warehouse";
  public static final Integer FACILITY_TYPE_ID = 1;
  public static final Integer GEOGRAPHIC_ZONE_ID = 3;

  public static final String GEOGRAPHIC_ZONE_CODE = "GEOGRAPHIC_ZONE_CODE";
  public static final Instantiator<Facility> defaultFacility = new Instantiator<Facility>() {

    @Override
    public Facility instantiate(PropertyLookup<Facility> lookup) {
      Facility facility = new Facility();
      facility.setCode(lookup.valueOf(code, FACILITY_CODE));
      FacilityType facilityType = new FacilityType();
      facilityType.setCode(lookup.valueOf(type, FACILITY_TYPE_CODE));
      facilityType.setName("Central Warehouse");
      facilityType.setId(lookup.valueOf(typeId, FACILITY_TYPE_ID));
      facilityType.setNominalMaxMonth(100);
      facilityType.setNominalEop(50.5);
      facility.setFacilityType(facilityType);
      facility.setName(lookup.valueOf(name, "Apollo Hospital"));

      GeographicZone geographicZone = new GeographicZone();
      geographicZone.setLevel(new GeographicLevel(1, "levelCode", "levelName", 4));
      geographicZone.setId(lookup.valueOf(geographicZoneId, GEOGRAPHIC_ZONE_ID));
      geographicZone.setCode(lookup.valueOf(geographicZoneCode, GEOGRAPHIC_ZONE_CODE));
      geographicZone.setName("Lusaka");
      GeographicZone parentGeographicZone = new GeographicZone();
      parentGeographicZone.setLevel(new GeographicLevel(2, "parentLevelCode", "parentLevelName", 3));
      parentGeographicZone.setId(lookup.valueOf(geographicZoneId, GEOGRAPHIC_ZONE_ID));
      parentGeographicZone.setCode(lookup.valueOf(geographicZoneCode, GEOGRAPHIC_ZONE_CODE));
      parentGeographicZone.setName("Zambia");
      geographicZone.setParent(parentGeographicZone);
      facility.setGeographicZone(lookup.valueOf(FacilityBuilder.geographicZone, geographicZone));

      facility.setSdp(lookup.valueOf(sdp, true));
      facility.setActive(lookup.valueOf(active, true));
      facility.setDataReportable(lookup.valueOf(dataReportable, true));
      FacilityOperator operatedBy = new FacilityOperator();
      operatedBy.setCode(lookup.valueOf(operatedByCode, "MoH"));
      operatedBy.setId(lookup.valueOf(operatedById, 1));
      operatedBy.setText("MOH");
      facility.setOperatedBy(operatedBy);
      facility.setGoLiveDate(lookup.valueOf(goLiveDate, new LocalDate(2013, 10, 10).toDate()));
      facility.setModifiedBy(1);
      return facility;
    }
  };
}
