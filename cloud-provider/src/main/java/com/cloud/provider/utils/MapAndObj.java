package com.cloud.provider.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * map 和 obj 转换帮助类
 */
public final class MapAndObj {
    /**
     * obj转换为map
     * @param obj
     * @return map
     * @throws Exception
     */
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

    /***
     * map转换为obj
     * @param map
     * @param clz
     * @return obj
     * @throws Exception
     */
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
