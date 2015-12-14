package com.jeff.web.rest.mapper;

import com.jeff.domain.*;
import com.jeff.web.rest.dto.FooDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Foo and its DTO FooDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FooMapper {

    FooDTO fooToFooDTO(Foo foo);

    Foo fooDTOToFoo(FooDTO fooDTO);
}
