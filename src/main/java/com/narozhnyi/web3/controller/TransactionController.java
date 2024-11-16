package com.narozhnyi.web3.controller;

import static org.springframework.data.domain.Sort.Direction.DESC;

import com.narozhnyi.web3.dto.elastic.ElasticTransactionDto;
import com.narozhnyi.web3.service.ElasticTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transactions")
public class TransactionController {

  private final ElasticTransactionService elasticTransactionService;

  @GetMapping
  public Page<ElasticTransactionDto> findAllBy(
      @PageableDefault(direction = DESC) Pageable pageable,
      @RequestParam Long blockNumber) {
    return elasticTransactionService.findBy(blockNumber, pageable);
  }
}
