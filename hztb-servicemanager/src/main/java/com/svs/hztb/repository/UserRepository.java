package com.svs.hztb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.svs.hztb.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer>{
	@Query("from UserEntity")
	List<UserEntity> findAllUsers();
	
	@Query("from UserEntity where idtest = ?1")
	UserEntity findUserByUserId(String userName);
}
