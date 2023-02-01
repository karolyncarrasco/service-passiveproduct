package com.bootcamp.passiveProduct.web.mapper;

import com.bootcamp.passiveProduct.domain.DebitCard;
import com.bootcamp.passiveProduct.web.model.DebitCardModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DebitCardMapper {
    DebitCard modelToEntity (DebitCardModel model);

    DebitCardModel entityToModel (DebitCard event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget DebitCard entity, DebitCard updateEntity);
}
