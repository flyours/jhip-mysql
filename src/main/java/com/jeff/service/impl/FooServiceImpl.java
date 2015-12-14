package com.jeff.service.impl;

import com.jeff.service.FooService;
import com.jeff.domain.Foo;
import com.jeff.repository.FooRepository;
import com.jeff.repository.search.FooSearchRepository;
import com.jeff.web.rest.dto.FooDTO;
import com.jeff.web.rest.mapper.FooMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Foo.
 */
@Service
@Transactional
public class FooServiceImpl implements FooService{

    private final Logger log = LoggerFactory.getLogger(FooServiceImpl.class);
    
    @Inject
    private FooRepository fooRepository;
    
    @Inject
    private FooMapper fooMapper;
    
    @Inject
    private FooSearchRepository fooSearchRepository;
    
    /**
     * Save a foo.
     * @return the persisted entity
     */
    public FooDTO save(FooDTO fooDTO) {
        log.debug("Request to save Foo : {}", fooDTO);
        Foo foo = fooMapper.fooDTOToFoo(fooDTO);
        foo = fooRepository.save(foo);
        FooDTO result = fooMapper.fooToFooDTO(foo);
        fooSearchRepository.save(foo);
        return result;
    }

    /**
     *  get all the foos.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Foo> findAll(Pageable pageable) {
        log.debug("Request to get all Foos");
        Page<Foo> result = fooRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one foo by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public FooDTO findOne(Long id) {
        log.debug("Request to get Foo : {}", id);
        Foo foo = fooRepository.findOne(id);
        FooDTO fooDTO = fooMapper.fooToFooDTO(foo);
        return fooDTO;
    }

    /**
     *  delete the  foo by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Foo : {}", id);
        fooRepository.delete(id);
        fooSearchRepository.delete(id);
    }

    /**
     * search for the foo corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<FooDTO> search(String query) {
        
        log.debug("REST request to search Foos for query {}", query);
        return StreamSupport
            .stream(fooSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(fooMapper::fooToFooDTO)
            .collect(Collectors.toList());
    }
}
