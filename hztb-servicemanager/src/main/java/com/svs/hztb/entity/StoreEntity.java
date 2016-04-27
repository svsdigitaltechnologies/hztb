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
 * The persistent class for the store database table.
 * 
 */
@Entity
@Table(name = "store", schema = "ebdb")
 @NamedQuery(name="Store.findAll", query="SELECT s FROM StoreEntity s")
public class StoreEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "store_id")
	private int storeId;

	@Column(name = "geo_code")
	private String geoCode;

	// bi-directional many-to-one association to Opinion
	/*
	 * @OneToMany(mappedBy="store") private List<OpinionEntity> opinions;
	 */
	// bi-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name = "address_id")
	private AddressEntity address;

	public StoreEntity() {
	}

	public int getStoreId() {
		return this.storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getGeoCode() {
		return this.geoCode;
	}

	public void setGeoCode(String geoCode) {
		this.geoCode = geoCode;
	}

	/*
	 * public List<OpinionEntity> getOpinions() { return this.opinions; }
	 * 
	 * public void setOpinions(List<OpinionEntity> opinions) { this.opinions =
	 * opinions; }
	 */

	public AddressEntity getAddress() {
		return this.address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

}