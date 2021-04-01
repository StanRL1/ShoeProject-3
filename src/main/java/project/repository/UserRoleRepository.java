package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.entities.UserRoleEntity;
import project.enums.UserRole;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity,Long> {

    Optional<UserRoleEntity> findByRole(UserRole role);
}
