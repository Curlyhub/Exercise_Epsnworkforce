package com.epsnworkforce.homework.models;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
/**Transfer Dao */
@Entity
@Table(name = "Transferency", catalog = "testdb")
public class Transferency implements java.io.Serializable {

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
    private long fromId;

    @Column()
    @JsonProperty
    private long toId;

    @Column()
    @JsonProperty
    private double quantity;

    @Column()
    @JsonProperty
    private String motive;

    @Column()
    @JsonProperty
    private boolean status;

    public Transferency() {
        super();
    }

    public Transferency(Long transferId, Long fromId, Long toId, double quantity, String motive, boolean status) {
        super();
        this.id = transferId;
        this.fromId = fromId;
        this.toId = toId;
        this.quantity = quantity;
        this.motive = motive;
        this.status = status;
    }

    public Long getTransferencyId() {
        return id;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setName(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return this.toId;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public void setCurrency(Long toId) {
        this.toId = toId;
    }

    public String getMotive() {
        return this.motive;
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Customer {" + "customerId=" + this.id + ", fromId='" + this.fromId + '\'' + ", toId='" + this.toId
                + '\'' + ", quantity='" + this.quantity + '\'' + ", motive='" + this.motive + '\'' + ", status="
                + this.status + '}';
    }

}
