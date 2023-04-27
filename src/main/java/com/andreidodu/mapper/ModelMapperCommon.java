package com.andreidodu.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMapperCommon<S, D> {
    @Autowired
    @Qualifier("modelMapperBean")
    private ModelMapper modelMapperBean;
    private final Type sourceType;
    private final Type destinationType;

    public ModelMapperCommon(Class<S> modelClazz, Class<D> dtoClass) {
        this.sourceType = modelClazz;
        this.destinationType = dtoClass;
    }

    public D toDTO(S inputObject) {
        return modelMapperBean.map(inputObject, destinationType);
    }

    public List<D> toListDTO(List<S> inptuList) {
        return inptuList.stream().map(s -> this.toDTO(s)).collect(Collectors.toList());
    }

    public S toModel(D modelObject) {
        return modelMapperBean.map(modelObject, sourceType);
    }

    public List<S> toListModel(List<D> inptuList) {
        return inptuList.stream().map(d -> this.toModel(d)).collect(Collectors.toList());
    }

    public ModelMapper getModelMapper() {
        return modelMapperBean;
    }

}
