package com.enterprisesys.plm.parts;

import com.enterprisesys.plm.assemblies.Assembly;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PartsRepo extends CrudRepository<Part, Integer> {

    //add custom methods here by naming them appropriately e.g.:
//    public void deletePartsByAssemblyName(String name);
//    public List<Part> getPartsByAssemblyName(String assemblyName);

//    @Modifying(clearAutomatically = true)
//    //jak to zrobic zeby brac z bazki od razu tylko id i nazwe czesci???
//    @Query("select prt from Part prt where prt.assemblyName =:assNam order by prt.partName asc")
//    List<Part> getSortedPartsByAssemblyName(@Param("assNam") String assNam);

    List<Part> findByAssemblyIdAssembly(Integer id);
}