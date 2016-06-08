package com.svs.hztb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.svs.hztb.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	@Query("from UserEntity where mobileNumber=?1 and imeiCode=?2")
	UserEntity findByMobileAndImei(String mobileNumber, String imeiCode);

	@Query("from UserEntity where mobileNumber=?1")
	UserEntity findByMobileNumber(String mobileNumber);
	
	List<UserEntity> findByMobileNumberIn(List<String> mobileNumbers);
}
