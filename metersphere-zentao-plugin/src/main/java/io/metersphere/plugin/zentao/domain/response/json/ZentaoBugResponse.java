package io.metersphere.plugin.zentao.domain.response.json;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ZentaoBugResponse extends ZentaoResponse {
	@Data
	public static class Bug {
		private String id;
		private String title;
		private String steps;
		private String status;
		private String openedBy;
		private String openedDate;
		private String deleted;
		private String lastEditedDate;
		private String assignedTo;
	}
}
