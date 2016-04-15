package com.svs.hztb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.svs.hztb.entity.GroupEntity;

public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {

}
