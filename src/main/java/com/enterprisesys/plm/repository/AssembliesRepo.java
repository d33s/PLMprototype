package com.enterprisesys.plm.repository;

import com.enterprisesys.plm.model.Assembly;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface AssembliesRepo extends CrudRepository<Assembly, Integer> {

    @Modifying(clearAutomatically = true)
    @Query("update Assembly a set a.assemblyName =:newName where a.id =:entryId")
    void updateAssemblyName(@Param("entryId") Integer entryId, @Param("newName") String newName);
}
