package com.svs.hztb.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.svs.hztb.entity.OneTimePasswordEntity;

/**
 * CRUD Repository for Code Register Entity
 * 
 * @author skairamk
 *
 */
@Repository
public interface OneTimePasswordRepository extends CrudRepository<OneTimePasswordEntity, Long> {

	@Query("from OneTimePasswordEntity where device_id=?1")
	OneTimePasswordEntity findByDeviceId(String device_id);

	@Query("from OneTimePasswordEntity where pn=?1 and unique_id=?2")
	OneTimePasswordEntity findByPhoneAndUniqueId(String pn, String uniqueId);

	@Query("from OneTimePasswordEntity where pn=?1 and identity=?2")
	OneTimePasswordEntity findByPhoneAndIdentity(String pn, String identity);

	@Query("from OneTimePasswordEntity where pn=?1")
	OneTimePasswordEntity findByPhoneNumber(String pn);

}
