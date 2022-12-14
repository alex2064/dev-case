package info.devcase.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import info.devcase.common.dao.CommonDAO;
import info.devcase.common.domain.AuthVO;
import info.devcase.common.domain.CustomUser;
import info.devcase.common.domain.MenuVO;
import info.devcase.common.domain.UserVO;

public class MenuPreparer implements ViewPreparer{

	@Autowired
	private CommonDAO commonDAO;
			
	private static String namespace = "CommonMapper";
	
	
	@Override
	public void execute(Request tilesContext, AttributeContext attributeContext) throws PreparerException{
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		List<AuthVO> authList = new ArrayList<AuthVO>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null && auth.getPrincipal() instanceof CustomUser) {
			UserVO user = ((CustomUser)auth.getPrincipal()).getUser();
			authList = user.getAuthList();
		}
		authList.add(new AuthVO("","ALL"));
		hashMap.put("authList", authList);
		 
		List<MenuVO> list = commonDAO.selectList(namespace + ".selectMenuList", hashMap);
		
        attributeContext.putAttribute("menuList", new Attribute(list), true);
	}
}
