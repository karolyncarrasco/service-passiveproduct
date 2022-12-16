package com.bootcamp.passiveProduct.web.mapper;

import com.bootcamp.passiveProduct.domain.AccountMovement;
import com.bootcamp.passiveProduct.domain.BankAccount;
import com.bootcamp.passiveProduct.web.model.AccountMovementModel;
import com.bootcamp.passiveProduct.web.model.BankAccountModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMovementMapper {
    AccountMovement modelToEntity (AccountMovementModel model);

    AccountMovementModel entityToModel (AccountMovement event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget AccountMovement entity, AccountMovement updateEntity);
}
