package com.svs.hztb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.svs.hztb.entity.User;
import com.svs.hztb.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<User, String>{
//	@Query("from User")
//	List<UserEntity> findAllUsers();
//	
//	@Query("from User where user = ?1")
//	UserEntity findUserByUserId(String userName);
}
