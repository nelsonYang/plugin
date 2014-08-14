package com.framework.generator;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class EntityContext {
  private final Map<String,EntityInfo> entityMap = new HashMap<String,EntityInfo>();
  public void putEntity(String entityName,EntityInfo entityInfo){
      this.entityMap.put(entityName, entityInfo);
  }
  public EntityInfo getEntity(String entityName){
      return entityMap.get(entityName);
  }
  
  public Map<String,EntityInfo> getEntityMap(){
      return entityMap;
  }
}
