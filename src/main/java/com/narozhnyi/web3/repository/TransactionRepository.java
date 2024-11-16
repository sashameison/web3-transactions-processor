package com.narozhnyi.web3.repository;

import java.math.BigInteger;
import java.util.Optional;

import com.narozhnyi.web3.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  @Query(value = "FROM Transaction t ORDER BY t.createdAt DESC limit 1")
  Optional<Transaction> findLatestTransaction();

  Page<Transaction> findByBlockNumber(BigInteger blockHash, Pageable pageable);

}
