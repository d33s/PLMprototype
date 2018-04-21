package com.enterprisesys.plm.assemblies;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface AssembliesRepo extends CrudRepository<Assembly, Integer> {

}
