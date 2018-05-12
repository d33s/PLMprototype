package com.enterprisesys.plm.assemblies;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Transactional
public interface AssembliesRepo extends CrudRepository<Assembly, Integer> {

    @Modifying(clearAutomatically = true)
    @Query("update Assembly a set a.assemblyName =:newName where a.id =:entryId")
    void updateAssemblyName(@Param("entryId") Integer entryId, @Param("newName") String newName);
}
