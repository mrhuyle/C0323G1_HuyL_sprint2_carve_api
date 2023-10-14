package com.example.carve.user.repository;

import com.example.carve.user.dto.UserDTO;
import com.example.carve.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

    @Query(value = " SELECT u.id as id, u.name as name, u.username as username, " +
            "            u.password as password, u.email as email, u.img as img, ru.name as rankName " +
            "            FROM user u " +
            "            JOIN rank_user ru ON u.rank_id = ru.id " +
            "            WHERE u.username = :username AND u.is_deleted = false ", nativeQuery = true)
    UserDTO findByUsernameWithRank(@Param("username") String username);
}
