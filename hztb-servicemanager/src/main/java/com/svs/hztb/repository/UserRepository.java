package com.svs.hztb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.svs.hztb.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	@Query("from UserEntity where mobileNumber=?1 and device_id=?2")
	UserEntity findByMobileAndDeviceId(String mobileNumber, String device_id);

	@Query("from UserEntity where mobileNumber=?1")
	UserEntity findByMobileNumber(String mobileNumber);

	List<UserEntity> findByMobileNumberIn(List<String> mobileNumbers);

	@Query("from UserEntity where mobileNumber=?1 and userId<>?2 and registered_already = 'Y'")
	UserEntity findByMobileNumberAndRegisteredAndNotUserId(String mobileNumber, Long userId);

	@Query("from UserEntity where userId=?1 and device_id=?2 and registered='Y'")
	UserEntity findByUserIdAndDeviceIdAndRegistered(Long userId, String deviceId);

}
