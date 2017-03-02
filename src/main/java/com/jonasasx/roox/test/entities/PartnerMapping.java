package com.jonasasx.roox.test.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Mapping {@link Customer} to Partner's service
 * <p>
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

	/**
	 * Default Partner Mapping constructor
	 */
	public PartnerMapping() {
	}

	/**
	 * Parameterized Partner Mapping constructor
	 */
	public PartnerMapping(String partnerId, String accountId, String avatarUrl) {
		setPartnerId(partnerId);
		setAccountId(accountId);
		setAvatarUrl(avatarUrl);
	}

	/**
	 * Primary key
	 *
	 * @return Primary key
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Primary key
	 *
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Partner's identifier
	 * For example, identifier of facebook application
	 *
	 * @return Partner's identifier
	 */
	public String getPartnerId() {
		return partnerId;
	}

	/**
	 * Partner's identifier
	 * For example, identifier of facebook application
	 *
	 * @param partnerId
	 */
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * Account's identifier of this Partner's service
	 * For example, identifier of the profile in this Partner's service
	 *
	 * @return Account's identifier
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * Account's identifier of this Partner's service
	 * For example, identifier of the profile in this Partner's service
	 *
	 * @param accountId
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * First name, last name
	 *
	 * @return First name, last name
	 */
	public String getFio() {
		return fio;
	}

	/**
	 * First name, last name
	 *
	 * @param fio
	 */
	public void setFio(String fio) {
		this.fio = fio;
	}

	/**
	 * Url of the user's picture
	 *
	 * @return url
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}

	/**
	 * Url of the user's picture
	 *
	 * @param avatarUrl
	 */
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	/**
	 * Owner of mapping
	 *
	 * @return customer
	 */
	@JsonIgnore
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Owner of mapping
	 *
	 * @param customer
	 */
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

	/**
	 * Copy fields from another mapping
	 *
	 * @param that
	 */
	public void copyData(PartnerMapping that) {
		setAvatarUrl(that.getAvatarUrl());
		setFio(that.getFio());
		setPartnerId(that.getPartnerId());
		setAccountId(that.getAccountId());
	}
}
