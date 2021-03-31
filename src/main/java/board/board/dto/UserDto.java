package board.board.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="UserDto : 유저 정보",description="유저 정보")
@Data
public class UserDto {
	@ApiModelProperty(value="유저 아이디")
	private String id;
	
	@ApiModelProperty(value="유저 비밀번호")
	private String password;
	
	@ApiModelProperty(value="유저 닉네임")
	private String nickname;
	
	@ApiModelProperty(value="가입 날짜")
	private String joinDate;
	
	@ApiModelProperty(value="접속 시간")
	private String loginDate;
	
	@ApiModelProperty(value="유저 권한")
	private String auth;
}
