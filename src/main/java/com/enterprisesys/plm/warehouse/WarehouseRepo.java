package com.enterprisesys.plm.warehouse;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Transactional
public interface WarehouseRepo extends CrudRepository<WarehousePart, Integer> {

    @Modifying(clearAutomatically = true)
    @Query("update WarehousePart w set w.partName =:newName, w.availablePieces =:newPieces, w.description =:newDescription where w.id =:entryId")
    void updatePartData(@Param("entryId") Integer entryId, @Param("newName") String newName, @Param("newPieces") String newPieces, @Param("newDescription") String newDescription);
}
