--
-- This program is part of the OpenLMIS logistics management information system platform software.
-- Copyright © 2013 VillageReach
--
-- This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
--  
-- This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
-- You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
--
DROP TABLE IF EXISTS geographic_zone_stocks;
CREATE TABLE geographic_zone_stocks (
  id              SERIAL PRIMARY KEY,
  expire_date     DATE,
  lot_number      VARCHAR(50) NOT NULL,
  number_of_doses INTEGER,
  vaccine_id         INTEGER REFERENCES vaccines (id),
  vaccine_packaging_id INTEGER REFERENCES vaccine_packaging (id),
  geographic_zone_id INTEGER REFERENCES geographic_zones (id)
);