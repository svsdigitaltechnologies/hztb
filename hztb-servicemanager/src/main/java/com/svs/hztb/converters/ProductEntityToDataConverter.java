package com.svs.hztb.converters;

import java.util.function.Function;

import com.svs.hztb.api.sm.model.product.Product;
import com.svs.hztb.entity.ProductEntity;

public class ProductEntityToDataConverter  implements 
Function<ProductEntity, Product> {

	@Override
	public Product apply(ProductEntity t) {
		Product product = new Product();
		product.setName(t.getName());
		product.setLongDesc(t.getLongDesc());
		product.setPrice(t.getPrice());
		product.setShortDesc(t.getShortDesc());
		return product;
	}

}
