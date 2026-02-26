package ecom.ecom_app.Repo;

import ecom.ecom_app.Entity.User;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);//derived query


    //for list of value to be hit from the cached not only individual ones
    @Query("SELECT u FROM User u")
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<User> findAllCached();
}
