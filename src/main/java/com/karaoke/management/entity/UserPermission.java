package com.karaoke.management.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_permission")
public class UserPermission {
	
	@Id
    @GeneratedValue
    @NotNull
    @Column(name = "permission_id", nullable = false)
	int permissionId;
	
	@NotNull
	@Size(max = 100)
	@Column(name = "title", nullable = false)
	String title;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "permission", nullable = false)
	String permission;
	
	@ManyToMany(mappedBy = "userPermissions")
    private List<Role> roles = new ArrayList<Role>();

	public UserPermission(@NotNull int permissionId, @NotNull String title, @NotNull String permission) {
		super();
		this.permissionId = permissionId;
		this.title = title;
		this.permission = permission;
	}

	public UserPermission() {
		super();
	}

	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	
}
