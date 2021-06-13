package com.eva.core.aware;

import com.eva.core.constants.DataPermissionConstants;
import org.springframework.core.annotation.AnnotationConfigurationException;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

public class BaseDataPermissionAware {

    Map<Integer, Method> getMethods () {
        Method[] methods = this.getClass().getDeclaredMethods();
        Map<Integer, Method> sortedMethods = new TreeMap<>();
        for (Method method : methods) {
            DataPermissionMapping mapping = method.getAnnotation(DataPermissionMapping.class);
            if (mapping == null || mapping.value().equals(DataPermissionConstants.Type.ALL)) {
                continue;
            }
            if (sortedMethods.get(mapping.priority()) != null) {
                throw new AnnotationConfigurationException("Data permission contains the same priority.");
            }
            sortedMethods.put(mapping.priority(), method);
        }
        return sortedMethods;
    }
}
