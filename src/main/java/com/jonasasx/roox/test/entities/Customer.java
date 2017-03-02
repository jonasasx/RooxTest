package com.jonasasx.roox.test.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jonasasx.roox.test.entities.enums.CustomerStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;

/**
 * Customer entity class
 * <p>
 * Created by ionas on 01.03.17.
 */
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private String fio;
	@NotNull
	private BigDecimal balance = BigDecimal.ZERO;
	@NotNull
	private CustomerStatus status = CustomerStatus.ACTIVE;
	@NotNull
	private String login;
	@NotNull
	@JsonIgnore
	private String password;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<PartnerMapping> partnerMappings = new HashSet<>();

	/**
	 * Default Customer constructor
	 */
	public Customer() {

	}

	/**
	 * Parameterized Constructor
	 *
	 * @param fio
	 * @param login
	 * @param password
	 * @param balance
	 */
	public Customer(String fio, String login, String password, BigDecimal balance) {
		setFio(fio);
		setLogin(login);
		setPassword(password);
		setBalance(balance);
	}

	/**
	 * Primary key
	 *
	 * @return primary key
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
	 * Customer's balance
	 *
	 * @return Customer's balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * Customer's balance
	 *
	 * @param balance
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * Customer's status
	 * may be {@link CustomerStatus#ACTIVE} or {@link CustomerStatus#BLOCKED}
	 *
	 * @return status
	 */
	@Enumerated(EnumType.ORDINAL)
	public CustomerStatus getStatus() {
		return status;
	}

	/**
	 * Customer's status
	 *
	 * @param status may be {@link CustomerStatus#ACTIVE} or {@link CustomerStatus#BLOCKED}
	 */
	public void setStatus(CustomerStatus status) {
		this.status = status;
	}

	/**
	 * Customer's login
	 *
	 * @return Customer's login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Customer's login
	 *
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Customer's password
	 *
	 * @return Customer's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Customer's password
	 *
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Collection of customer's mapping to any Partner's services
	 *
	 * @return partner's mappings
	 */
	@JsonIgnore
	public Collection<PartnerMapping> getPartnerMappings() {
		return partnerMappings;
	}

	/**
	 * Collection of customer's mapping to any Partner's services
	 *
	 * @param partnerMappings
	 */
	public void setPartnerMappings(Collection<PartnerMapping> partnerMappings) {
		if (partnerMappings == null) {
			return;
		}
		this.partnerMappings.clear();
		partnerMappings.forEach(v -> addPartnerMapping(v));
	}

	/**
	 * Add mapping to the collection of customer's mapping to any Partner's services
	 *
	 * @param partnerMapping
	 */
	public void addPartnerMapping(PartnerMapping partnerMapping) {
		if (this.partnerMappings.contains(partnerMapping)) {
			return;
		}
		this.partnerMappings.add(partnerMapping);
		partnerMapping.setCustomer(this);
	}

	/**
	 * Remove mapping from the collection of customer's mapping to any Partner's services
	 *
	 * @param partnerMapping
	 */
	public void removePartnerMapping(PartnerMapping partnerMapping) {
		if (!this.partnerMappings.contains(partnerMapping)) {
			return;
		}
		this.partnerMappings.remove(partnerMapping);
		partnerMapping.setCustomer(null);

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Customer customer = (Customer) o;

		return getId() != null ? getId().equals(customer.getId()) : customer.getId() == null;
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}
}
