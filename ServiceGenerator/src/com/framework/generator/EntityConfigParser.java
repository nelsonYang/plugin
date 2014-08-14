package com.framework.generator;

import com.framework.entity.annotation.FieldConfig;
import com.framework.entity.annotation.EntityConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public final class EntityConfigParser<T> {

    public void parse(List<Class> clazzList, EntityContext entityContextBuilder) {



        EntityInfo entityInfo;
        List<FieldConfig> fieldConfigList;
        try {
           
            for (Class<T> clazz  : clazzList) {
                if (clazz.isAnnotationPresent(EntityConfig.class)) {
                    entityInfo = new EntityInfo();
                    EntityConfig entityConfig = (EntityConfig) clazz.getAnnotation(EntityConfig.class);
                    entityInfo.setEntityName(entityConfig.entityName());
                    entityInfo.setKeyFields(entityConfig.keyFields());
                    Field[] fields = clazz.getDeclaredFields();
                    fieldConfigList = new ArrayList<FieldConfig>(fields.length);
                    for (Field field : fields) {
                        int modifier = field.getModifiers();
                        if (!Modifier.isStatic(modifier)) {
                            FieldConfig fieldConfig = (FieldConfig) field.getAnnotation(FieldConfig.class);
                            fieldConfigList.add(fieldConfig);
                        }
                    }
                    entityInfo.setFieldInfo(fieldConfigList);
                    entityContextBuilder.putEntity(entityConfig.entityName(), entityInfo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
