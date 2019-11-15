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
@Table(name = "chitiethoadon")
public class BillDetails {
	
	@EmbeddedId
	BillDetailsKey billDetailsId;
	
	@ManyToOne(targetEntity=Bill.class, fetch = FetchType.LAZY)
    @MapsId("mahoadon")
	@JoinColumn(name = "mahoadon")
    private Bill bill;
 
    @ManyToOne(targetEntity=Menu.class, fetch = FetchType.LAZY)
    @MapsId("mathucdon")
    @JoinColumn(name = "mathucdon")
    private Menu menu;
	
	@NotNull
	@Column(name = "soluong", nullable = false)
	int number;
	
	@NotNull
	@Column(name = "dongia", nullable = false)
	double unitPrice;

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
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
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
