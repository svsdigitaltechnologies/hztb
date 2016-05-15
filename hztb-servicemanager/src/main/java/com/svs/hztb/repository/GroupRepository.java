package com.svs.hztb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.svs.hztb.entity.GroupEntity;

public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {
//	@Modifying
//	@Query("UPDATE Space c SET c.owner = :name WHERE c.id = :id")
//	Integer setNameForId(@Param("name") String name, @Param("id")
	@Modifying
	@Query("update GroupEntity g set g.groupName = ?1 where g.groupId = ?2 and g.groupOwner=?3")
	Integer setGroupNameFor(String groupName, int groupId, 
			int groupOwner);
	
	
}
