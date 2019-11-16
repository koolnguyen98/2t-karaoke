package com.karaoke.management.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bill_details")
public class BillDetails {
	
	@EmbeddedId
	BillDetailsKey billDetailsId;
	
	@ManyToOne(targetEntity=Bill.class, fetch = FetchType.LAZY)
    @MapsId("bill_id")
	@JoinColumn(name = "bill_id")
    private Bill bill;
 
    @ManyToOne(targetEntity=Food.class, fetch = FetchType.LAZY)
    @MapsId("food_id")
    @JoinColumn(name = "food_id")
    private Food food;
	
	@NotNull
	@Column(name = "number", nullable = false)
	private int number;
	
	@NotNull
	@Column(name = "unit_price", nullable = false)
	private double unitPrice;

	public BillDetails(BillDetailsKey billDetailsId, @NotNull int number, @NotNull double unitPrice) {
		super();
		this.billDetailsId = billDetailsId;
		this.number = number;
		this.unitPrice = unitPrice;
	}
	
	public BillDetails(BillDetailsKey billDetailsId) {
		super();
		this.billDetailsId = billDetailsId;
	}

	public BillDetails() {
		super();
	}

	public BillDetailsKey getBillDetailsId() {
		return billDetailsId;
	}

	public void setBillDetailsId(BillDetailsKey billDetailsId) {
		this.billDetailsId = billDetailsId;
	}
	
	@JsonIgnore
	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	@JsonIgnore
	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
}
