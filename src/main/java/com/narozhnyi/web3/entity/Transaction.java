package com.narozhnyi.web3.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
    name = "transactions",
    indexes = @Index(name = "idx_block_number", columnList = "block_number"))
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
  @SequenceGenerator(name = "seqGen", sequenceName = "transaction_id_seq", allocationSize = 20)
  @Column(name = "transaction_id")
  private Long transactionId;
  private String hash;
  private String blockHash;
  private BigInteger blockNumber;
  private BigInteger transactionIndex;
  private String transferFrom;
  private String transferTo;
  @Column(precision = 100, scale = 2)
  private BigDecimal transferValue;
  @CreatedDate
  private Instant createdAt;
}
