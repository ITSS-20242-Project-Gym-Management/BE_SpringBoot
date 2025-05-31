package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.exerciseSession;
import com.example.itssprj_ver1.model.staff;
import com.example.itssprj_ver1.service.exerSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface exerSessionRepository extends JpaRepository<exerciseSession, Integer> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM exerciseSession e " +
            "WHERE e.staff = :trainer AND ((:beginAt BETWEEN e.beginAt AND e.endAt) OR (:endAt BETWEEN e.beginAt AND e.endAt))")
    boolean existsByStaffAndTimeOverlap(@Param("trainer") staff trainer, @Param("beginAt") LocalDateTime beginAt, @Param("endAt") LocalDateTime endAt);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM exerciseSession e " +
            "WHERE e.customer = :customer AND ((:beginAt BETWEEN e.beginAt AND e.endAt) OR (:endAt BETWEEN e.beginAt AND e.endAt))")
    boolean existsByCustomerAndTimeOverlap(@Param("customer") customer customer, @Param("beginAt") LocalDateTime beginAt, @Param("endAt") LocalDateTime endAt);

    @Query("SELECT DISTINCT es.customer FROM exerciseSession es JOIN customer c on c.id = es.customer.id WHERE es.staff.id = :trainerId")
    List<customer> findCustomer(@Param("trainerId") Integer trainerId);

    List<exerciseSession> findByStaff_Userid_Role_Roleid(Integer roleId);

    List<exerciseSession> findByStaff_Id(Integer staffId);
}
