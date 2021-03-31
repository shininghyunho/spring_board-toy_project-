package board.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import board.board.dto.UserDto;
import board.board.entity.UserEntity;
import board.board.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	
	// 필수 메소드 구현
	// 유저가 없을떄 예외 발생
	@Override
	public UserEntity loadUserByUsername(String id) throws UsernameNotFoundException{
		return userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException((id)));
	}
	
	public Long save(UserDto userDto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		userDto.setPassword(encoder.encode(userDto.getPassword()));
		
		return userRepository.save(UserEntity.builder()
				.id(userDto.getId())
				.password(userDto.getPassword())
				.nickname(userDto.getNickname())
				.auth(userDto.getAuth()).build()).getCode();
	}
}