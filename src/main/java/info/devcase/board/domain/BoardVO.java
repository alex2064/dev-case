package info.devcase.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardVO {
	private String seq;
	private String title;
	private String content;
	private String targetCode;
	private String createdDate;
	private String createdBy;
	private String updatedDate;
	private String updatedBy;
}
