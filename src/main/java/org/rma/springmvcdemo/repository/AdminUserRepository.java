package org.rma.springmvcdemo.repository;

import org.rma.springmvcdemo.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

}
