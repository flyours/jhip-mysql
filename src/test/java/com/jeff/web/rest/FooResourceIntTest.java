package com.jeff.web.rest;

import com.jeff.Application;
import com.jeff.domain.Foo;
import com.jeff.repository.FooRepository;
import com.jeff.service.FooService;
import com.jeff.web.rest.dto.FooDTO;
import com.jeff.web.rest.mapper.FooMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jeff.domain.enumeration.Hobby;

/**
 * Test class for the FooResource REST controller.
 *
 * @see FooResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FooResourceIntTest {

    private static final String DEFAULT_NAME = "AA";
    private static final String UPDATED_NAME = "BB";
    private static final String DEFAULT_ADDR = "AAAAAAAAAA";
    private static final String UPDATED_ADDR = "BBBBBBBBBB";

    private static final Integer DEFAULT_GRADE = 1;
    private static final Integer UPDATED_GRADE = 2;

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_SALARY = new BigDecimal(100);
    private static final BigDecimal UPDATED_SALARY = new BigDecimal(101);


    private static final Hobby DEFAULT_HOBBY = Hobby.basketball;
    private static final Hobby UPDATED_HOBBY = Hobby.basketball;

    @Inject
    private FooRepository fooRepository;

    @Inject
    private FooMapper fooMapper;

    @Inject
    private FooService fooService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFooMockMvc;

    private Foo foo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FooResource fooResource = new FooResource();
        ReflectionTestUtils.setField(fooResource, "fooService", fooService);
        ReflectionTestUtils.setField(fooResource, "fooMapper", fooMapper);
        this.restFooMockMvc = MockMvcBuilders.standaloneSetup(fooResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        foo = new Foo();
        foo.setName(DEFAULT_NAME);
        foo.setAddr(DEFAULT_ADDR);
        foo.setGrade(DEFAULT_GRADE);
        foo.setBirthday(DEFAULT_BIRTHDAY);
        foo.setSalary(DEFAULT_SALARY);
        foo.setHobby(DEFAULT_HOBBY);
    }

    @Test
    @Transactional
    public void createFoo() throws Exception {
        int databaseSizeBeforeCreate = fooRepository.findAll().size();

        // Create the Foo
        FooDTO fooDTO = fooMapper.fooToFooDTO(foo);

        restFooMockMvc.perform(post("/api/foos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fooDTO)))
                .andExpect(status().isCreated());

        // Validate the Foo in the database
        List<Foo> foos = fooRepository.findAll();
        assertThat(foos).hasSize(databaseSizeBeforeCreate + 1);
        Foo testFoo = foos.get(foos.size() - 1);
        assertThat(testFoo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFoo.getAddr()).isEqualTo(DEFAULT_ADDR);
        assertThat(testFoo.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testFoo.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testFoo.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testFoo.getHobby()).isEqualTo(DEFAULT_HOBBY);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fooRepository.findAll().size();
        // set the field null
        foo.setName(null);

        // Create the Foo, which fails.
        FooDTO fooDTO = fooMapper.fooToFooDTO(foo);

        restFooMockMvc.perform(post("/api/foos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fooDTO)))
                .andExpect(status().isBadRequest());

        List<Foo> foos = fooRepository.findAll();
        assertThat(foos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddrIsRequired() throws Exception {
        int databaseSizeBeforeTest = fooRepository.findAll().size();
        // set the field null
        foo.setAddr(null);

        // Create the Foo, which fails.
        FooDTO fooDTO = fooMapper.fooToFooDTO(foo);

        restFooMockMvc.perform(post("/api/foos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fooDTO)))
                .andExpect(status().isBadRequest());

        List<Foo> foos = fooRepository.findAll();
        assertThat(foos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGradeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fooRepository.findAll().size();
        // set the field null
        foo.setGrade(null);

        // Create the Foo, which fails.
        FooDTO fooDTO = fooMapper.fooToFooDTO(foo);

        restFooMockMvc.perform(post("/api/foos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fooDTO)))
                .andExpect(status().isBadRequest());

        List<Foo> foos = fooRepository.findAll();
        assertThat(foos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFoos() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        // Get all the foos
        restFooMockMvc.perform(get("/api/foos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(foo.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].addr").value(hasItem(DEFAULT_ADDR.toString())))
                .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
                .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
                .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.intValue())))
                .andExpect(jsonPath("$.[*].hobby").value(hasItem(DEFAULT_HOBBY.toString())));
    }

    @Test
    @Transactional
    public void getFoo() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

        // Get the foo
        restFooMockMvc.perform(get("/api/foos/{id}", foo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(foo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.addr").value(DEFAULT_ADDR.toString()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.intValue()))
            .andExpect(jsonPath("$.hobby").value(DEFAULT_HOBBY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFoo() throws Exception {
        // Get the foo
        restFooMockMvc.perform(get("/api/foos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoo() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

		int databaseSizeBeforeUpdate = fooRepository.findAll().size();

        // Update the foo
        foo.setName(UPDATED_NAME);
        foo.setAddr(UPDATED_ADDR);
        foo.setGrade(UPDATED_GRADE);
        foo.setBirthday(UPDATED_BIRTHDAY);
        foo.setSalary(UPDATED_SALARY);
        foo.setHobby(UPDATED_HOBBY);
        FooDTO fooDTO = fooMapper.fooToFooDTO(foo);

        restFooMockMvc.perform(put("/api/foos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fooDTO)))
                .andExpect(status().isOk());

        // Validate the Foo in the database
        List<Foo> foos = fooRepository.findAll();
        assertThat(foos).hasSize(databaseSizeBeforeUpdate);
        Foo testFoo = foos.get(foos.size() - 1);
        assertThat(testFoo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFoo.getAddr()).isEqualTo(UPDATED_ADDR);
        assertThat(testFoo.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testFoo.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testFoo.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testFoo.getHobby()).isEqualTo(UPDATED_HOBBY);
    }

    @Test
    @Transactional
    public void deleteFoo() throws Exception {
        // Initialize the database
        fooRepository.saveAndFlush(foo);

		int databaseSizeBeforeDelete = fooRepository.findAll().size();

        // Get the foo
        restFooMockMvc.perform(delete("/api/foos/{id}", foo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Foo> foos = fooRepository.findAll();
        assertThat(foos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
