package com.karaoke.management.entity;

import java.util.List;

public class RoleDetails {
	
	Role role;
	
	List<UserPermission> listUserPermission;

	public RoleDetails(Role role, List<UserPermission> listUserPermission) {
		super();
		this.role = role;
		this.listUserPermission = listUserPermission;
	}
	
	public RoleDetails() {
		super();
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<UserPermission> getListUserPermission() {
		return listUserPermission;
	}

	public void setListUserPermission(List<UserPermission> listUserPermission) {
		this.listUserPermission = listUserPermission;
	}
	
	
}
