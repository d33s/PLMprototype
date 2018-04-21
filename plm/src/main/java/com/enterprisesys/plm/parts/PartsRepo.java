package com.enterprisesys.plm.parts;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface PartsRepo extends CrudRepository<Part, Integer> {

    //add custom methods here by naming them appropriately e.g.:
    public void deletePartsByAssemblyName(String name);

}
