package com.svs.hztb.repository;

import org.springframework.data.repository.CrudRepository;

import com.svs.hztb.entity.ProductEntity;

public interface ProductRepository extends CrudRepository<ProductEntity, String> {
	
}
