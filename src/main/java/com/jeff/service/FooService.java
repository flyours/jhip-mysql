package com.jeff.service;

import com.jeff.domain.Foo;
import com.jeff.web.rest.dto.FooDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Foo.
 */
public interface FooService {

    /**
     * Save a foo.
     * @return the persisted entity
     */
    public FooDTO save(FooDTO fooDTO);

    /**
     *  get all the foos.
     *  @return the list of entities
     */
    public Page<Foo> findAll(Pageable pageable);

    /**
     *  get the "id" foo.
     *  @return the entity
     */
    public FooDTO findOne(Long id);

    /**
     *  delete the "id" foo.
     */
    public void delete(Long id);

    /**
     * search for the foo corresponding
     * to the query.
     */
    public List<FooDTO> search(String query);
}
