package com.karaoke.management.reponsitory.custom;

import java.util.List;

import com.karaoke.management.entity.Role;
import com.karaoke.management.entity.RoleDetails;
import com.karaoke.management.entity.UserPermission;

public interface RoleDetailsRepository {
	void insertRole(Role role, List<UserPermission> listUserPermission);
	RoleDetails findByAllPermissionOfRole(int roleId);
	RoleDetails updatePermissionOfRole(int roleId, List<Integer> userPermissionId);
	
}
