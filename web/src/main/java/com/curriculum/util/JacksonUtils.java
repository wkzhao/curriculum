package com.curriculum.util;

//
//Source code recreated from a .class file by IntelliJ IDEA
//(powered by Fernflower decompiler)
//


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class JacksonUtils {
    private static final ObjectMapper objMapper = new ObjectMapper();

    public JacksonUtils() {
    }

    public static String toJson(Object obj) throws JsonProcessingException {
        return objMapper.writeValueAsString(obj);
    }

    public static <T> String toJson(Object obj, Class<T> jsonViewClazz) throws JsonProcessingException {
        return objMapper.writerWithView(jsonViewClazz).writeValueAsString(obj);
    }

    public static String toJsonWithInclude(Object obj, Class clazz, String... includeFileds) throws JsonProcessingException {
        HashMap include = new HashMap();
        include.put(clazz, new HashSet(Arrays.asList(includeFileds)));
        return toJson(obj, include, (Map) null);
    }

    public static String toJsonWithInclude(Object obj, Map<Class, Set<String>> include) throws JsonProcessingException {
        return toJson(obj, include, (Map) null);
    }

    public static String toJsonWithExclude(Object obj, Class clazz, String... excludeFields) throws JsonProcessingException {
        HashMap exclude = new HashMap();
        exclude.put(clazz, new HashSet(Arrays.asList(excludeFields)));
        return toJson(obj, (Map) null, exclude);
    }

    public static String toJsonWithExclude(Object obj, Map<Class, Set<String>> exclude) throws JsonProcessingException {
        return toJson(obj, (Map) null, exclude);
    }

    public static String toJson(Object obj, Map<Class, Set<String>> include, Map<Class, Set<String>> exclude) throws JsonProcessingException {
        if ((null == include || include.isEmpty()) && (null == exclude || exclude.isEmpty())) {
            toJson(obj);
        }

        ObjectMapper mapper = new ObjectMapper();
        SimpleFilterProvider filters = new SimpleFilterProvider();
        Iterator filterNames;
        Entry e;
        Class clazz;
        Set excludeFileds;
        if (null != include && !include.isEmpty()) {
            filterNames = include.entrySet().iterator();

            while (filterNames.hasNext()) {
                e = (Entry) filterNames.next();
                clazz = (Class) e.getKey();
                excludeFileds = (Set) e.getValue();
                ((SimpleFilterProvider) filters).addFilter(clazz.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(excludeFileds));
            }
        }

        if (null != exclude && !exclude.isEmpty()) {
            filterNames = exclude.entrySet().iterator();

            while (filterNames.hasNext()) {
                e = (Entry) filterNames.next();
                clazz = (Class) e.getKey();
                excludeFileds = (Set) e.getValue();
                ((SimpleFilterProvider) filters).addFilter(clazz.getName(), SimpleBeanPropertyFilter.serializeAllExcept(excludeFileds));
            }
        }

        mapper.setFilters(filters);
        final HashSet filterNames1 = new HashSet();
        Iterator e1;
        if (null != include && !include.isEmpty()) {
            e1 = include.keySet().iterator();

            while (e1.hasNext()) {
                clazz = (Class) e1.next();
                filterNames1.add(clazz.getName());
            }
        }

        if (null != exclude && !exclude.isEmpty()) {
            e1 = exclude.keySet().iterator();

            while (e1.hasNext()) {
                clazz = (Class) e1.next();
                filterNames1.add(clazz.getName());
            }
        }

        mapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
            public Object findFilterId(Annotated ac) {
                String name = ac.getName();
                return filterNames1.contains(name) ? name : null;
            }
        });
        return mapper.writeValueAsString(obj);
    }

    public static JsonNode toJsonNode(String jsonText) {
        JsonNode jsonNode = null;

        try {
            jsonNode = objMapper.readTree(jsonText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonNode;
    }

    public static <T> T fromJson(String jsonText, Class<T> clazz) throws IOException {
        return jsonText != null && !"".equals(jsonText) ? objMapper.readValue(jsonText, clazz) : null;
    }

    public static <T> T fromJson(String jsonText, TypeReference<T> valueTypeRef) throws IOException {
        return jsonText != null && !"".equals(jsonText) ? objMapper.readValue(jsonText, valueTypeRef) : null;
    }

    public static <T> List<T> fromJson2List(String jsonText, Class<T> clazz) throws IOException {
        if (jsonText != null && !"".equals(jsonText)) {
            List objList = null;

            try {
                JavaType t = objMapper.getTypeFactory().constructParametricType(List.class, new Class[]{clazz});
                objList = (List) objMapper.readValue(jsonText, t);
            } catch (Exception var4) {
                ;
            }

            return objList;
        } else {
            return null;
        }
    }

    public static JsonNode getValueFromJson(String json, String key) throws JsonProcessingException, IOException {
        JsonNode node = objMapper.readTree(json);
        return node.get(key);
    }

    public static List<JsonNode> getListFromJson(String json, String key) throws JsonProcessingException, IOException {
        ArrayList jsonNodes = null;
        JsonNode node = objMapper.readTree(json);
        JsonNode arrayNode = node.get(key);
        if (arrayNode.isArray()) {
            jsonNodes = new ArrayList();
            Iterator var5 = arrayNode.iterator();

            while (var5.hasNext()) {
                JsonNode jsonNode = (JsonNode) var5.next();
                jsonNodes.add(jsonNode);
            }
        }

        return jsonNodes;
    }

    public static String map2Json(Map<String, Object> map) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    public static Map<String, Object> json2Map(String json) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return (Map) mapper.readValue(json, new TypeReference() {
        });
    }


    static {
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}

