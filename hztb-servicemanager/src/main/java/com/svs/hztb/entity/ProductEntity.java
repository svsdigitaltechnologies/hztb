package com.svs.hztb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@Table(name = "product", schema = "ebdb")
 @NamedQuery(name="Product.findAll", query="SELECT p FROM ProductEntity p")
public class ProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "long_desc")
	private String longDesc;

	@Id
	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private double price;

	@Column(name = "short_desc")
	private String shortDesc;

	/*
	 * //bi-directional many-to-one association to Coupon
	 * 
	 * @OneToMany(mappedBy="product") private List<CouponEntity> coupons;
	 * 
	 * //bi-directional many-to-one association to Opinion
	 * 
	 * @OneToMany(mappedBy="product") private List<OpinionEntity> opinions;
	 */

	// bi-directional many-to-one association to Retailer
	@ManyToOne
	@JoinColumn(name = "retailer_id")
	private RetailerEntity retailer;

	public ProductEntity() {
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

	/*
	 * public List<CouponEntity> getCoupons() { return this.coupons; }
	 * 
	 * public void setCoupons(List<CouponEntity> coupons) { this.coupons =
	 * coupons; }
	 */

	/*
	 * public CouponEntity addCoupon(CouponEntity coupon) {
	 * getCoupons().add(coupon); coupon.setProduct(this);
	 * 
	 * return coupon; }
	 * 
	 * public CouponEntity removeCoupon(CouponEntity coupon) {
	 * getCoupons().remove(coupon); coupon.setProduct(null);
	 * 
	 * return coupon; }
	 */
	/*
	 * public List<OpinionEntity> getOpinions() { return this.opinions; }
	 * 
	 * public void setOpinions(List<OpinionEntity> opinions) { this.opinions =
	 * opinions; }
	 * 
	 */

	public RetailerEntity getRetailer() {
		return this.retailer;
	}

	public void setRetailer(RetailerEntity retailer) {
		this.retailer = retailer;
	}

}