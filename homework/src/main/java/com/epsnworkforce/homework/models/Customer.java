package com.epsnworkforce.homework.models;


/*The disadvantage of BigDecimal is that it's slower and it's a bit more difficult to program algorithms:(sum and rest are more dusty...We prefer double) 
import java.math.BigDecimal;
 */
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.epsnworkforce.homework.validators.CurrencyConstraint;

/**Custumer Dao */
@Entity
@Table(name = "Custumer", catalog = "testdb")
public class Customer implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;

    @Column(nullable = false)
    @JsonProperty
    private String name;

    /**
     * In currency data type, the must importat part is the currency code,with the introduction of several news currency, like cryptocurrency,
     * this codes are not standard any more, and can vary from one year to ather. In order to incorporate a great number of currebcy, we just
     * consider the currency code ( String) , and using Locale or a list of possible currency, we filter them.
     */
    @Column()
    @JsonProperty
    @CurrencyConstraint
    private String currencyCode;

    /*We prefer double instead of BigDecimal:The reason that people use BigDecimal for monetary calculations is not that it can represent any number, 
    but that it can represent all numbers that can be represented in decimal notion and that include virtually all numbers in the monetary world, but with
    double type we can get that BigDecimal b = new BigDecimal(double, MathContext.DECIMAL64); so fotr this test we'll use double*/
    @Column()
    @JsonProperty
    private double balance;

    @Column()
    @JsonProperty
    private boolean treasury;

    public Customer() {
        super();
    }

    public Customer(Long customerId, String name, String currencyCode, double balance, boolean treasury) {
        super();
        this.id = customerId;
        this.name = name;
        this.currencyCode = currencyCode;
        this.balance = balance;
        this.treasury = treasury;

    }

    public Long getCustomerId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return this.currencyCode;
    }

    public void setCurrency(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getMoney() {
        return this.balance;
    }

    public void setMoney(final double balance) {
        this.balance = balance;
    }

    public boolean getTreasury() {
        return this.treasury;
    }

    public void setTreasury(final boolean treasury) {
        this.treasury = treasury;
    }

    @Override
    public String toString() {
        return "Customer {" + "customerId=" + this.id + ", name='" + this.name + '\'' + ", currency='"
                + this.currencyCode + '\'' + ", balance='" + this.balance + '\'' + ", treasury=" + this.treasury + '}';
    }

}