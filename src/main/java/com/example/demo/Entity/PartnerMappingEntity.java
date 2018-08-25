package com.example.demo.Entity;

import javax.persistence.*;

@Entity
public class PartnerMappingEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String partnerId;
    private String inPartnerId;
    private String inPartnerName;
    private String inPartnerSurname;
    private byte[] inPartnerAvatar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

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

    public String getInPartnerId() {
        return inPartnerId;
    }

    public void setInPartnerId(String inPartnerId) {
        this.inPartnerId = inPartnerId;
    }

    public String getInPartnerName() {
        return inPartnerName;
    }

    public void setInPartnerName(String inPartnerName) {
        this.inPartnerName = inPartnerName;
    }

    public String getInPartnerSurname() {
        return inPartnerSurname;
    }

    public void setInPartnerSurname(String inPartnerSurname) {
        this.inPartnerSurname = inPartnerSurname;
    }

    public byte[] getInPartnerAvatar() {
        return inPartnerAvatar;
    }

    public void setInPartnerAvatar(byte[] inPartnerAvatar) {
        this.inPartnerAvatar = inPartnerAvatar;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
}
