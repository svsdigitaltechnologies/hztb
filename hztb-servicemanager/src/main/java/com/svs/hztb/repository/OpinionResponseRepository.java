package com.svs.hztb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.svs.hztb.entity.OpinionResponseEntity;

public interface OpinionResponseRepository extends CrudRepository<OpinionResponseEntity, Integer>{
	@Query("SELECT o FROM OpinionResponseEntity o WHERE o.responderUserId = :responderUserId and o.responseTime >= :responseTime")
	List<OpinionResponseEntity> findByResponderUserIdLastUpdatedTime(@Param("responderUserId") int responderUserId, @Param("responseTime") Date responseTime);		

	List<OpinionResponseEntity> findByResponderUserId(int userId);	
	
	List<OpinionResponseEntity> findByOpinionId(int opinionId);	
	@Query("SELECT o FROM OpinionResponseEntity o WHERE o.opinionId = :opinionId and o.responseTime >= :responseTime")
	List<OpinionResponseEntity> findByOpinionIdLastUpdatedTime(@Param("opinionId") int opinionId, @Param("responseTime") Date responseTime);		

	@Query("SELECT o FROM OpinionResponseEntity o WHERE o.opinionId = :opinionId and o.responderUserId >= :responderUserId")
	List<OpinionResponseEntity> findByOpinionIdResponderUserId(@Param("opinionId") int opinionId, @Param("responderUserId") int responderUserId);		

	
}
