package com.bootcamp.passiveProduct.web.mapper;

import com.bootcamp.passiveProduct.domain.BankAccount;
import com.bootcamp.passiveProduct.web.model.BankAccountModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DebitCardMapper {
    BankAccount modelToEntity (BankAccountModel model);

    BankAccountModel entityToModel (BankAccount event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget BankAccount entity, BankAccount updateEntity);
}
