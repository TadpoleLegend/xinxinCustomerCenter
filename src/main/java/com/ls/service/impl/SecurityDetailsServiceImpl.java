package com.ls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ls.entity.Role;
import com.ls.entity.User;
import com.ls.repository.UserRepository;

/**
 * An implementation of the spring security {@link UserDetailsService}.
 *
 */
@Service("securityDetailsService")
public class SecurityDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

//    private Collection<GrantedAuthority> getAuthorities(final User user) {
//        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(2);
//        authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
//        if (user.isAdmin()) {
//            authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
//        }
//        return authorities;
//    }

    /**
     * {@inheritDoc}
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException, DataAccessException {
//        final Query q = getEm().createNamedQuery("user.findByUsername");
//        q.setParameter("username", username);
//        try {
//            final User user = (User) q.getSingleResult();
//            final org.springframework.security.core.userdetails.User userDetails
//                = new org.springframework.security.core.userdetails.User(
//                        user.getUsername(),
//                        user.getPassword(),
//                        user.isEnabled(), true, true, true, getAuthorities(user));
//            return userDetails;
//        } catch (final NoResultException ex) {
//            throw new UsernameNotFoundException(ex.getMessage());
//        }
    	System.out.println(username);
    	User user = userRepository.findByUsername(username);
    	
    	if (user == null) {
			System.out.println("No User found for " + username);
			
			return null;
		} else {
			List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
			
			List<Role> userRoles = user.getRoles();
			for (Role role : userRoles) {
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
				roles.add(grantedAuthority);
				
			}
			UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
			
			return userDetails;
		}
   }

}
