package com.karaoke.management.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karaoke.management.entity.UserPermission;

@Repository("userPermissionRepository")
public interface UserPermissionRepository extends JpaRepository<UserPermission, Integer> {
	UserPermission findByPermissionId(int permissionId);
	UserPermission findByTitle(String title);
	UserPermission findByPermission(String permission);
}
