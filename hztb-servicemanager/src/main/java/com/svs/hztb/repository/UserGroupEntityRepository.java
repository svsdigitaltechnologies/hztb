package com.svs.hztb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.svs.hztb.entity.UserGroupEntity;

public interface UserGroupEntityRepository extends CrudRepository<UserGroupEntity, Integer>  {
	@Query("SELECT u FROM UserGroupEntity u WHERE u.id.groupId = :groupId")
	List<UserGroupEntity> findByGroupId(@Param("groupId") int groupId);

	@Query("SELECT u FROM UserGroupEntity u WHERE u.id.userId = :userId")
	List<UserGroupEntity> findByUserId(@Param("userId") int userId);
}
