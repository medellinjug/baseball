package org.medellinjug.baseball.strategy.model.entity;

import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;

public abstract class FilterProvider {
    public abstract BeanPropertyFilter findFilter(Object filterId);
}