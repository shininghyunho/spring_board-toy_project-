package board.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import board.board.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
	Optional<UserEntity> findById(String id);
}
