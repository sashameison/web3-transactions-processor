package com.narozhnyi.web3.repository;

import com.narozhnyi.web3.entity.ElasticTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticTransactionRepository extends ElasticsearchRepository<ElasticTransaction, String> {

  Page<ElasticTransaction> findByBlockNumber(Long blockNumber, Pageable pageable);


}
