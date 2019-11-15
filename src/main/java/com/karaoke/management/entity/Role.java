package com.karaoke.management.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "role")
public class Role {

	@Id
    @GeneratedValue
    @Column(name = "level")
	private int level;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "title", nullable = false)
	private String title;
	
	@OneToMany(targetEntity=UserAccount.class, mappedBy="roleLevel",cascade=CascadeType.ALL, fetch = FetchType.LAZY)    
	private List<UserAccount> users = new ArrayList<UserAccount>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "permission_details", 
		joinColumns = { @JoinColumn(name = "level") }, 
		inverseJoinColumns = {@JoinColumn(name = "permission_id") })
    private List<UserPermission> userPermissions = new ArrayList<UserPermission>();
	
	public Role(@NotNull String title) {
		super();
		this.title = title;
	}
	
	public Role() {
		super();
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<UserAccount> getUsers() {
		return users;
	}

	public void setUsers(List<UserAccount> users) {
		this.users = users;
	}

	public List<UserPermission> getUserPermissions() {
		return userPermissions;
	}

	public void setUserPermissions(List<UserPermission> userPermissions) {
		this.userPermissions = userPermissions;
	}
	
}
