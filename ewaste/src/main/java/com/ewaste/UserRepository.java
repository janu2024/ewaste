package com.ewaste;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
	UserInfo findByEmail(String username);

	
	List<UserInfo> findByRole(String roleName);
}
