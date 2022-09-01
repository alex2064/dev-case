package info.devcase.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import info.devcase.common.dao.CommonDAO;
import info.devcase.common.domain.CustomUser;
import info.devcase.common.domain.UserVO;

public class CustomUserDetailsService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private CommonDAO commonDAO;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		logger.warn("Load User By UserName : " + userName);
		
		// userName means id
		UserVO vo = commonDAO.selectOne("CommonMapper.login", userName);
		if(vo == null) {
			throw new UsernameNotFoundException(userName);
		}
		
		logger.warn("queried by member mapper: " + vo);
		
		return new CustomUser(vo);	// Spring Security에 전달할 완성된 객체
	}
}
