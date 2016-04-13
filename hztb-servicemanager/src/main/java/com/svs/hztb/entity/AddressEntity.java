package com.svs.hztb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the address database table.
 * 
 */
@Entity
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
public class AddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="address_id")
	private int addressId;

	private String address1;

	private String city;

	private String state;

	private String zipcode;

	//bi-directional many-to-one association to Store
	@OneToMany(mappedBy="address")
	private List<StoreEntity> stores;

	public AddressEntity() {
	}

	public int getAddressId() {
		return this.addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public List<StoreEntity> getStores() {
		return this.stores;
	}

	public void setStores(List<StoreEntity> stores) {
		this.stores = stores;
	}

	public StoreEntity addStore(StoreEntity store) {
		getStores().add(store);
		store.setAddress(this);

		return store;
	}

	public StoreEntity removeStore(StoreEntity store) {
		getStores().remove(store);
		store.setAddress(null);

		return store;
	}

}