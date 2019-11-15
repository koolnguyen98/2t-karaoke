package com.karaoke.management.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "user_account")
public class UserAccount {
	
	@Id
    @GeneratedValue
    @Column(name = "id")
	private int id;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "username", nullable = false)
	private String userName;
	
	@NotNull
	@Size(max = 100)
	@Column(name = "password", nullable = false)
	private String password;
	
	@NotNull
	@Size(max = 100)
	@Column(name = "name", nullable = false)
	private String name;
	
	@NotNull
	@ManyToOne(targetEntity=Role.class)
    @JoinColumn(name="level", nullable=false) 
	private Role roleLevel;
	
	@OneToMany(targetEntity=Bill.class, mappedBy="userAccount",cascade=CascadeType.ALL, fetch = FetchType.LAZY)    
	private List<Bill> bills = new ArrayList<Bill>();
	
	public UserAccount(@NotNull String userName, @NotNull String password, @NotNull String name,
			@NotNull Role roleLevel) {
		super();
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.roleLevel = roleLevel;
	}
	
	public UserAccount(@NotNull String userName, @NotNull String password, @NotNull String name) {
		super();
		this.userName = userName;
		this.password = password;
		this.name = name;
	}
	
	public UserAccount() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonIgnore
	public Role getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(Role roleLevel) {
		this.roleLevel = roleLevel;
	}

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	
	
}
