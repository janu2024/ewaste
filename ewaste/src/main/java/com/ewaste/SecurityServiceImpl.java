package com.ewaste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService{

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userDetailsService;


//    @Autowired
//    private UserDetailsService service;



    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserInfo) {
            return ((UserInfo)userDetails).getEmail();
        }

        return null;
    }

    @Override
    public void autologin(String username, String password) {
    	org.springframework.security.core.userdetails.User userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        
       
        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            UsernamePasswordAuthenticationToken u1= (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
       
            u1.getName();
        }
    }
}