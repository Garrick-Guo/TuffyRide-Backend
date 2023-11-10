package com.cs362.tuffyride.repository;

import com.cs362.tuffyride.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/*
 Spring Data JPA will automatically generate the necessary SQL based on naming conventions or custom query methods defined in the repository interface.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
