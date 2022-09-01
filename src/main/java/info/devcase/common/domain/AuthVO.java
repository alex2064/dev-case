package info.devcase.common.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthVO {
	private String id;
	private String auth;
	
	public AuthVO() {
	}
	
	public AuthVO(String id, String auth) {
		this.id = id;
		this.auth = auth;
	}
}
