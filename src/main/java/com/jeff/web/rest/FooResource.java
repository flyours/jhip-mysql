package com.jeff.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jeff.domain.Foo;
import com.jeff.service.FooService;
import com.jeff.web.rest.util.HeaderUtil;
import com.jeff.web.rest.util.PaginationUtil;
import com.jeff.web.rest.dto.FooDTO;
import com.jeff.web.rest.mapper.FooMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Foo.
 */
@RestController
@RequestMapping("/api")
public class FooResource {

    private final Logger log = LoggerFactory.getLogger(FooResource.class);
        
    @Inject
    private FooService fooService;
    
    @Inject
    private FooMapper fooMapper;
    
    /**
     * POST  /foos -> Create a new foo.
     */
    @RequestMapping(value = "/foos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FooDTO> createFoo(@Valid @RequestBody FooDTO fooDTO) throws URISyntaxException {
        log.debug("REST request to save Foo : {}", fooDTO);
        if (fooDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("foo", "idexists", "A new foo cannot already have an ID")).body(null);
        }
        FooDTO result = fooService.save(fooDTO);
        return ResponseEntity.created(new URI("/api/foos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("foo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /foos -> Updates an existing foo.
     */
    @RequestMapping(value = "/foos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FooDTO> updateFoo(@Valid @RequestBody FooDTO fooDTO) throws URISyntaxException {
        log.debug("REST request to update Foo : {}", fooDTO);
        if (fooDTO.getId() == null) {
            return createFoo(fooDTO);
        }
        FooDTO result = fooService.save(fooDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("foo", fooDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /foos -> get all the foos.
     */
    @RequestMapping(value = "/foos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<FooDTO>> getAllFoos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Foos");
        Page<Foo> page = fooService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/foos");
        return new ResponseEntity<>(page.getContent().stream()
            .map(fooMapper::fooToFooDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /foos/:id -> get the "id" foo.
     */
    @RequestMapping(value = "/foos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FooDTO> getFoo(@PathVariable Long id) {
        log.debug("REST request to get Foo : {}", id);
        FooDTO fooDTO = fooService.findOne(id);
        return Optional.ofNullable(fooDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /foos/:id -> delete the "id" foo.
     */
    @RequestMapping(value = "/foos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFoo(@PathVariable Long id) {
        log.debug("REST request to delete Foo : {}", id);
        fooService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("foo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/foos/:query -> search for the foo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/foos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<FooDTO> searchFoos(@PathVariable String query) {
        log.debug("Request to search Foos for query {}", query);
        return fooService.search(query);
    }
}
