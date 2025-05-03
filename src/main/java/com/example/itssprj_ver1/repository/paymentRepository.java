package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.dto.monthlyRevenueDto;
import com.example.itssprj_ver1.model.payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface paymentRepository extends JpaRepository<payment, Integer> {

    List<payment> findByCustomerId(Integer customerId);


    @Query(value = """
    WITH months AS (
        SELECT generate_series(1, 12) AS month
    )
    SELECT 
        m.month AS month,
        COALESCE(SUM(p.amount), 0) AS totalAmount
    FROM 
        months m
    LEFT JOIN payment p 
        ON EXTRACT(MONTH FROM p.create_at) = m.month 
        AND EXTRACT(YEAR FROM p.create_at) = :year
    GROUP BY m.month
    ORDER BY m.month
    """, nativeQuery = true)
    List<monthlyRevenueDto> findMonthlyRevenueByYear(@Param("year") int year);
}
