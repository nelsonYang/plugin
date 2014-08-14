package com.framework.generator;

import com.framework.entity.annotation.FieldConfig;
import java.util.List;

/**
 *
 * @author nelson
 */
public class EntityInfo {
    private String entityName;
    private String[] keyFields;
    private List<FieldConfig> fieldInfo;

    /**
     * @return the entityName
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * @param entityName the entityName to set
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /**
     * @return the keyFields
     */
    public String[] getKeyFields() {
        return keyFields;
    }

    /**
     * @param keyFields the keyFields to set
     */
    public void setKeyFields(String[] keyFields) {
        this.keyFields = keyFields;
    }

    /**
     * @return the fieldInfo
     */
    public List<FieldConfig> getFieldInfo() {
        return fieldInfo;
    }

    /**
     * @param fieldInfo the fieldInfo to set
     */
    public void setFieldInfo(List<FieldConfig> fieldInfo) {
        this.fieldInfo = fieldInfo;
    }
    
}
