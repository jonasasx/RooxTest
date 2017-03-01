package com.jonasasx.roox.test.entities;

import com.jonasasx.roox.test.entities.enums.CustomerStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by ionas on 01.03.17.
 */
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String fio;
	private BigDecimal balance;
	private CustomerStatus status = CustomerStatus.ACTIVE;
	private String login;
	private String password;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<PartnerMapping> partnerMappings = new HashSet<>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Enumerated(EnumType.ORDINAL)
	public CustomerStatus getStatus() {
		return status;
	}

	public void setStatus(CustomerStatus status) {
		this.status = status;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Collection<PartnerMapping> getPartnerMappings() {
		return partnerMappings;
	}

	public void setPartnerMappings(Collection<PartnerMapping> partnerMappings) {
		if (partnerMappings == null) {
			return;
		}
		this.partnerMappings.clear();
		partnerMappings.forEach(v -> addPartnerMapping(v));
	}

	public void addPartnerMapping(PartnerMapping partnerMapping) {
		if (this.partnerMappings.contains(partnerMapping)) {
			return;
		}
		this.partnerMappings.add(partnerMapping);
		partnerMapping.setCustomer(this);
	}

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
