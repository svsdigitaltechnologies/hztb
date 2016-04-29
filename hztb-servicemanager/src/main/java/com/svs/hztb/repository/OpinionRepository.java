package com.svs.hztb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.svs.hztb.entity.OpinionEntity;

public interface OpinionRepository extends CrudRepository<OpinionEntity, Integer> {
//	@Query("SELECT o FROM Opinion o WHERE o.requestedTime >= :opinionRequestTime and o.requester_user_id = :userId")
//	List<OpinionEntity> findAllOpinions(@Param("userId") int userId, @Param("opinionRequestTime") Date opinionRequestTime);		

//	@Query("SELECT o FROM OpinionEntity o WHERE o.userId = :userId")
//	List<OpinionEntity> findByUserId(@Param("userId") int userId);		
	List<OpinionEntity> findByUserId(int userId);		

}
