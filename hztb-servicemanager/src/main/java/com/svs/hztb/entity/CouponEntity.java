package com.svs.hztb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the coupon database table.
 * 
 */
@Entity
// @NamedQuery(name="Coupon.findAll", query="SELECT c FROM Coupon c")
public class CouponEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "coupon_id")
	private int couponId;

	@Column(name = "coupon_code")
	private String couponCode;

	@Column(name = "coupon_desc")
	private String couponDesc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "coupon_expiry_date")
	private Date couponExpiryDate;

	// bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name = "coupon_product_id")
	private ProductEntity product;

	// bi-directional many-to-one association to Retailer
	@ManyToOne
	@JoinColumn(name = "coupon_retailer_id")
	private RetailerEntity retailer;

	public CouponEntity() {
	}

	public int getCouponId() {
		return this.couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public String getCouponCode() {
		return this.couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getCouponDesc() {
		return this.couponDesc;
	}

	public void setCouponDesc(String couponDesc) {
		this.couponDesc = couponDesc;
	}

	public Date getCouponExpiryDate() {
		return this.couponExpiryDate;
	}

	public void setCouponExpiryDate(Date couponExpiryDate) {
		this.couponExpiryDate = couponExpiryDate;
	}

	public ProductEntity getProduct() {
		return this.product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public RetailerEntity getRetailer() {
		return this.retailer;
	}

	public void setRetailer(RetailerEntity retailer) {
		this.retailer = retailer;
	}

}