--
-- This program is part of the OpenLMIS logistics management information system platform software.
-- Copyright © 2013 VillageReach
--
-- This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
--  
-- This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
-- You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
--

DROP TABLE IF EXISTS vaccines;
CREATE TABLE vaccines (
  id                    SERIAL PRIMARY KEY,
  name                  VARCHAR(50) NULL,
  packaging             VARCHAR(50) NULL,
  GTIN                  VARCHAR(50) NOT NULL,
  doses_per_vial        INTEGER NOT NULL,
  expire_warning_period DATE,
  type                  VARCHAR(200),
  wastage               VARCHAR(10),
  schedule              VARCHAR(200),
  status                VARCHAR(50),
  geographic_zone_id INTEGER REFERENCES geographic_zones (id),
  manufacture_id INTEGER REFERENCES manufacturers (id)
);