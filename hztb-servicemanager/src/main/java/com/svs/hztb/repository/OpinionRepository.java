package com.svs.hztb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.svs.hztb.entity.OpinionEntity;

public interface OpinionRepository extends JpaRepository<OpinionEntity, Integer> {
	@Query("SELECT o FROM OpinionEntity o WHERE o.requestedTime >= :opinionRequestTime and o.userId = :userId")
	List<OpinionEntity> findByUserIdLastUpdatedTime(@Param("userId") Long userId,
			@Param("opinionRequestTime") Date requestedTime);

	List<OpinionEntity> findByUserId(Long userId);

	OpinionEntity findByOpinionId(int opinionId);

	List<OpinionEntity> findByGroupId(int groupId);

}
