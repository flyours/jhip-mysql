package com.jeff.repository.search;

import com.jeff.domain.Foo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Foo entity.
 */
public interface FooSearchRepository extends ElasticsearchRepository<Foo, Long> {
}
