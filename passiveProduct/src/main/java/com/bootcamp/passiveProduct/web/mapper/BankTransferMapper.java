package com.bootcamp.passiveProduct.web.mapper;

import com.bootcamp.passiveProduct.domain.BankTransfer;
import com.bootcamp.passiveProduct.web.model.BankTransferModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BankTransferMapper {

    BankTransfer modelToEntity (BankTransferModel model);

    BankTransferModel entityToModel (BankTransfer event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget BankTransfer entity, BankTransfer updateEntity);
}
