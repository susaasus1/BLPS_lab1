package com.example.blps_lab1.config;


import com.example.blps_lab1.dto.UpdateRecipeRequest;
import com.example.blps_lab1.model.Recipe;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface RecipeMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRecipeFromDto(UpdateRecipeRequest updateRecipeRequest, @MappingTarget Recipe recipe);

}
