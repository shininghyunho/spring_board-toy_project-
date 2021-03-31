package board.board.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Table(name="t_user")
@Entity
@Data
public class UserEntity implements UserDetails {
	@Id
	@Column(name="code")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long code;
	
	@Column(name="id",unique=true,nullable=false)
	private String id;
	
	@Column(name="password",nullable=false)
	private String password;
	
	@Column(name="nickname",nullable=false)
	private String nickname;
	
	@Column(name="auth",nullable=false)
	private String auth;
	
	@Builder
	public UserEntity(String id,String password, String nickname, String auth) {
		this.id=id;
		this.password=password;
		this.nickname=nickname;
		this.auth=auth;
	}
	
	// ## UserDetails 인터페이스 구현 ##
	// 사용자 권한을 콜렉션 형태로 반환
	// GrantedAuthority를 구현 해줘야함
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		Set<GrantedAuthority> roles = new HashSet<>();
		for (String role : auth.split(",")) {
			roles.add(new SimpleGrantedAuthority(role));
		}
		return roles;
	}
	
	@Override
	public String getUsername() {
		return id;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	// 계정 만료 여부
	@Override
	public boolean isAccountNonExpired() {
		return true; // 계정이 만료되지 않음
	}
	
	// 계정 잠금 여부
	@Override
	public boolean isAccountNonLocked() {
		return true; // 계정이 잠금되지 않았음
	}
	
	// 비밀번호 만료 여부
	@Override
	public boolean isCredentialsNonExpired() {
		return true; // 비밀번호가 만료되지 않았음
	}
	
	// 계정 사용 가능 여부
	@Override
	public boolean isEnabled() {
		return true; // 계정 사용 가능
	}
}
