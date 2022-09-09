package info.devcase.response;

import lombok.Getter;
import lombok.Setter;

@Getter
public class BaseResponse {
	private String result;
	private String reason;
	
	public BaseResponse() {
		this.result = MessageUtils.success;
		this.reason = "";
	}
	
	public BaseResponse(String reason) {
		this.result = MessageUtils.fail;
		this.reason = reason;
	}
}
