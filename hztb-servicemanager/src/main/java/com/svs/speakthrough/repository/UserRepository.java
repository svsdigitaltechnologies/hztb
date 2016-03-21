package com.svs.speakthrough.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.svs.speakthrough.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer>{
	@Query("from UserEntity")
	List<UserEntity> findAllUsers();
	
	@Query("from UserEntity where idtest = ?1")
	UserEntity findUserByUserId(String userName);
/*	
	@Query("from UserEntity U " 
			+ "left join fetch U.events e "
			+ "where username = ?1 and e.activitydate = ?2")
	UserEntity findMeetingsBySfid(String sfid, String activityDate);
	
	@Query("from UserEntity U " 
			+ "left join fetch U.events e "
			+ "right join fetch U.stUserEntity st "
			+ "where e.description is NULL and e.subject not in ('EMAIL')")
	List<UserEntity> notifyUsersForEvents();*/
}
