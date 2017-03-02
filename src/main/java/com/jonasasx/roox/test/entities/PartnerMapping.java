package com.jonasasx.roox.test.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by ionas on 01.03.17.
 */
@Entity
public class PartnerMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String partnerId;
	private String accountId;
	private String fio;
	private String avatarUrl;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Customer customer;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	@JsonIgnore
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		if (this.customer == customer) {
			return;
		}
		Customer old = this.customer;
		this.customer = customer;
		if (old != null) {
			old.removePartnerMapping(this);
		}
		if (customer != null) {
			customer.addPartnerMapping(this);
		}
	}

	public void copyData(PartnerMapping that) {
		setAvatarUrl(that.getAvatarUrl());
		setFio(that.getFio());
		setPartnerId(that.getPartnerId());
		setAccountId(that.getAccountId());
	}
}
