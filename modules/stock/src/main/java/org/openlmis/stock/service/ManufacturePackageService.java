package org.openlmis.stock.service;

import org.openlmis.stock.domain.ManufacturePackage;
import org.openlmis.stock.repository.ManufacturePackageRepository;
import org.openlmis.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Morley on 6/14/2015.
 */

@Service
public class ManufacturePackageService extends StockService<ManufacturePackage>{

    @Autowired
    private ManufacturePackageRepository repository;

    /*public List<ManufacturePackage> getAll(){
        return repository.getAll();
    }

    public void save(ManufacturePackage manufacturePackage){
        if(manufacturePackage.getId() == null){
            repository.insert(manufacturePackage);
        }else {
            repository.update(manufacturePackage);
        }
    }

    public ManufacturePackage getById(Long id){
        return repository.getById(id);
    }*/

    @Override
    public StockRepository getRepository() {
        return repository;
    }
}
