package info.devcase.common.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
	private String id;
	private String pwd;
	private boolean enabled;
	private List<AuthVO> authList;
}
