package info.devcase.common.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuVO {
	private String menu;
	private String url;
	private String icon;
	private List<SubMenuVO> subMenuList;
}
