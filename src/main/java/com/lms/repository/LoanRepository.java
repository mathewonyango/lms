package com.lms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lms.model.Customer;
import com.lms.model.Loan;
import com.lms.model.Loan.LoanStatus;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findTopByCustomerNumberOrderByCreatedAtDesc(String customerNumber);
    List<Loan> findByCustomerNumber(String customerNumber);
    
    @Query("SELECT l FROM Loan l WHERE l.customerNumber = :customerNumber AND l.status IN :statuses")
    List<Loan> findActiveLoans(@Param("customerNumber") String customerNumber, @Param("statuses") List<LoanStatus> statuses);
    boolean existsByCustomerNumberAndStatusIn(Customer customerNumber, List<LoanStatus> ongoingStatuses);
    List<Loan> findByStatus(Loan.LoanStatus status);
    List<Loan> findByCustomerNumberAndStatus(String customerNumber, Loan.LoanStatus status);
    List<Loan> findByCustomerNumberAndStatusIn(String customerNumber, List<Loan.LoanStatus> statuses);

}
