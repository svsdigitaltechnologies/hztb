package com.svs.hztb.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * The persistent class for the retailer database table.
 * 
 */
@Entity
// @NamedQuery(name="Retailer.findAll", query="SELECT r FROM Retailer r")
public class RetailerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "retailer_id")
	private int retailerId;

	@Column(name = "retailer_desc")
	private String retailerDesc;

	@Column(name = "retailer_name")
	private String retailerName;

	// bi-directional many-to-one association to Coupon
	@OneToMany(mappedBy = "retailer")
	private List<CouponEntity> coupons;

	// bi-directional many-to-one association to Product
	@OneToMany(mappedBy = "retailer")
	private List<ProductEntity> products;

	public RetailerEntity() {
	}

	public int getRetailerId() {
		return this.retailerId;
	}

	public void setRetailerId(int retailerId) {
		this.retailerId = retailerId;
	}

	public String getRetailerDesc() {
		return this.retailerDesc;
	}

	public void setRetailerDesc(String retailerDesc) {
		this.retailerDesc = retailerDesc;
	}

	public String getRetailerName() {
		return this.retailerName;
	}

	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}

	public List<CouponEntity> getCoupons() {
		return this.coupons;
	}

	public void setCoupons(List<CouponEntity> coupons) {
		this.coupons = coupons;
	}

	public CouponEntity addCoupon(CouponEntity coupon) {
		getCoupons().add(coupon);
		coupon.setRetailer(this);

		return coupon;
	}

	public CouponEntity removeCoupon(CouponEntity coupon) {
		getCoupons().remove(coupon);
		coupon.setRetailer(null);

		return coupon;
	}

	public List<ProductEntity> getProducts() {
		return this.products;
	}

	public void setProducts(List<ProductEntity> products) {
		this.products = products;
	}

	public ProductEntity addProduct(ProductEntity product) {
		getProducts().add(product);
		product.setRetailer(this);

		return product;
	}

	public ProductEntity removeProduct(ProductEntity product) {
		getProducts().remove(product);
		product.setRetailer(null);

		return product;
	}

}