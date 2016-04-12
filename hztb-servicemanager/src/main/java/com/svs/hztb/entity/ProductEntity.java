package com.svs.hztb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class ProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int code;

	@Column(name="image_url")
	private String imageUrl;

	@Column(name="long_desc")
	private String longDesc;

	private String name;

	private double price;

	@Column(name="short_desc")
	private String shortDesc;

	//bi-directional many-to-one association to Coupon
	@OneToMany(mappedBy="product")
	private List<CouponEntity> coupons;

	//bi-directional many-to-one association to Opinion
	@OneToMany(mappedBy="product")
	private List<OpinionEntity> opinions;

	//bi-directional many-to-one association to Retailer
	@ManyToOne
	@JoinColumn(name="retailer_id")
	private RetailerEntity retailer;

	public ProductEntity() {
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLongDesc() {
		return this.longDesc;
	}

	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getShortDesc() {
		return this.shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public List<CouponEntity> getCoupons() {
		return this.coupons;
	}

	public void setCoupons(List<CouponEntity> coupons) {
		this.coupons = coupons;
	}

	public CouponEntity addCoupon(CouponEntity coupon) {
		getCoupons().add(coupon);
		coupon.setProduct(this);

		return coupon;
	}

	public CouponEntity removeCoupon(CouponEntity coupon) {
		getCoupons().remove(coupon);
		coupon.setProduct(null);

		return coupon;
	}

	public List<OpinionEntity> getOpinions() {
		return this.opinions;
	}

	public void setOpinions(List<OpinionEntity> opinions) {
		this.opinions = opinions;
	}

	

	public RetailerEntity getRetailer() {
		return this.retailer;
	}

	public void setRetailer(RetailerEntity retailer) {
		this.retailer = retailer;
	}

}