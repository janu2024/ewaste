package com.ewaste;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;

	public UserInfo saveUser(UserInfo u) {
		return userRepo.save(u);
	}

	@Transactional
	public void deleteUser(UserInfo u) {
		userRepo.delete(u);
	}
}
