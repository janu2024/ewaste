package com.ewaste;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Transactional
	public UserInfo findByEmail(String username) {
		return userRepo.findByEmail(username);
	}
	
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user = userRepo.findByEmail(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
