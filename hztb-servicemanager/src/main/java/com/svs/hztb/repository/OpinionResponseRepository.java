package com.svs.hztb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.svs.hztb.entity.OpinionResponseEntity;

public interface OpinionResponseRepository extends CrudRepository<OpinionResponseEntity, Integer>{
//	@Query("SELECT o FROM OpinionResponseEntity o WHERE o.responseTime >= :responseDate")
//	List<OpinionResponseEntity> findAllOpinionResponse(@Param("userId") int userId, @Param("responseDate") Date responseDate);
}
