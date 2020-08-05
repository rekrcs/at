package com.sbs.byk.at.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleReply {
	private int id;
	private String regDate;
	private String updateDate;
	private boolean delStatus;
	private String delDate;
	private boolean displayStatus;
	private String body;
}
