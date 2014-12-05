package com.ls.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An implementation of the spring security {@link UserDetailsService}.
 *
 */
@Service("securityDetailsService")
public class SecurityDetailsServiceImpl implements UserDetailsService {

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
    	
    	
    	return null;
   }

}
