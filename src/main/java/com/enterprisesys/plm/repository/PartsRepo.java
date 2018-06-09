package com.enterprisesys.plm.repository;

import com.enterprisesys.plm.model.Assembly;
import com.enterprisesys.plm.model.Part;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PartsRepo extends CrudRepository<Part, Integer> {

//    add custom methods here by naming them appropriately e.g.:
//    @Modifying(clearAutomatically = true)
//    //jak to zrobic zeby brac z bazki od razu tylko id i nazwe czesci???
//    @Query("select prt from Part prt where prt.assembly =:assId order by prt.partName asc")
//    List<Part> getSortedPartsByAssemblyId(@Param("assId") Assembly assId);

    @Modifying(clearAutomatically = true)
    @Query("update Part p set p.partName =:newName, p.assembly =:newAssembly, p.pdfPath =:newPath where p.id =:entryId")
    void updatePartData(@Param("entryId") Integer entryId, @Param("newName") String newName, @Param("newAssembly") Assembly newAssembly, @Param("newPath") String newPath);

    List<Part> findByAssemblyAssemblyName(String name);
    List<Part> findByAssemblyIdAssembly(Integer id);
    void deletePartByAssemblyAssemblyName(String name);
}