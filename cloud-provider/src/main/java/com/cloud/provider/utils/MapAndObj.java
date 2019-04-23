package com.cloud.provider.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public final class MapAndObj {

	public static Map<String,Object> obj2Map(Object obj) throws Exception{
        Map<String,Object> map=new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
         for (PropertyDescriptor property : propertyDescriptors) { 
             String key = property.getName();
             if (key.compareToIgnoreCase("class") == 0) {   
                    continue;  
                }  
                 Method getter = property.getReadMethod();  
                Object value = getter!=null ? getter.invoke(obj) : null;  
                map.put(key, value); 
         }
        return map;
    }
    
    public static Object map2Obj(Map<String,Object> map,Class<?> clz) throws Exception{
        if (map == null)   
            return null;    
        Object obj = clz.newInstance();  
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {  
            Method setter = property.getWriteMethod();    
            if (setter != null) {  
                setter.invoke(obj, map.get(property.getName()));   
            }  
        }  
        return obj;
    }
}
