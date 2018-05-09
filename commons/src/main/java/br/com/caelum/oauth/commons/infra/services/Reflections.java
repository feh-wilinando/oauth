package br.com.caelum.oauth.commons.infra.services;

import java.lang.reflect.Field;

public class Reflections {

    private final Class<?> clazz;
    private final Object target;

    public Reflections(Object object) {
        this.target = object;
        this.clazz = object.getClass();
    }



    public Object getFieldValueByName(String fieldName) {
        Field field = getFieldByName(fieldName);

        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            throw new ReflectionPermissionDenied("Haven't permission to read field " + fieldName, e);
        }
    }


    private Field getFieldByName(String name){
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);

            return field;
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Field " + name +" not found!");
        }
    }
}
