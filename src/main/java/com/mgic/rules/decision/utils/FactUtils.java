package com.mgic.rules.decision.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.kie.api.definition.type.FactField;
import org.kie.api.definition.type.FactType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mgic.rules.decision.annotations.Fact;
import com.mgic.rules.enums.KieConstant;

public class FactUtils {

	   

	    public static <T> Object getFactFromObject(final FactType factType, final Object factObj, final T t) {
	        List<String> factFieldNames = retrieveFactFieldNames(factType);
	        try {
	            for (Field field : t.getClass().getDeclaredFields()) {
	                Fact fact = field.getAnnotation(Fact.class);

	                if (fact != null && factFieldNames.contains(fact.fieldName())) {
	                    if (KieConstant.class.isAssignableFrom(field.getType())) {
	                        if (PropertyUtils.getProperty(t, field.getName()) != null) {
	                            factType.set(factObj, fact.fieldName(),
	                                    ((KieConstant) PropertyUtils.getProperty(t, field.getName())).getRulesText());
	                        }
	                    } else {
	                        factType.set(factObj, fact.fieldName(), PropertyUtils.getProperty(t, field.getName()));
	                    }

	                }
	            }

	            return factObj;
	        } catch (Exception e) {
	            throw new RuntimeException("Exception at getFactFromObject :" + t.getClass() + " to factType " + factType, e);
	        }
	    }

	    public static <T> T getObjectFromFact(final FactType factType, final Object factObj, final T t) {
	        List<String> factFieldNames = retrieveFactFieldNames(factType);
	        try {
	            for (Field field : t.getClass().getDeclaredFields()) {
	                Fact fact = field.getAnnotation(Fact.class);
	                if (fact != null && factFieldNames.contains(fact.fieldName())) {
	                    PropertyUtils.setProperty(t, field.getName(), factType.get(factObj, fact.fieldName()));
	                }
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("Exception at getObjectFromFact : " + factType + " to object type " + t.getClass(), e);
	        }

	        return t;
	    }

	    private static List<String> retrieveFactFieldNames(final FactType factType) {
	        List<String> factFieldNames = new ArrayList<String>();
	        CollectionUtils.collect(factType.getFields(), new Transformer() {
	            @Override
	            public Object transform(final Object input) {
	                return ((FactField) input).getName();
	            }
	        }, factFieldNames);

	        return factFieldNames;
	    }

	}
