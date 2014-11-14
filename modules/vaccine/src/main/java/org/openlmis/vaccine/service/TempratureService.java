package org.openlmis.vaccine.service;/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 *   Copyright © 2013 VillageReach
 *
 *   This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *    
 *   This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *   You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

import org.openlmis.vaccine.domain.Temprature;
import org.openlmis.vaccine.repository.TempratureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class TempratureService {
    @Autowired
    private TempratureRepository tempratureRepository;
    public List<Temprature> loadTempratureList(){
        return this.tempratureRepository.loadTempratureList();
    }

    public void addTemprature(Temprature temprature){
        this.tempratureRepository.addTemprature(temprature);
    }
    public Temprature loadTempratureDetail(long id){
        return  this.tempratureRepository.loadTempratureDetail(id);
    }
    public void updateTemprature(Temprature temprature){
        this.tempratureRepository.updateTemprature(temprature);
    }
    public void removeTemprature(Temprature temprature){
        this.tempratureRepository.removeTemprature(temprature);
    }
}
