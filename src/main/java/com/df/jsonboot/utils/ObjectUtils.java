package com.df.jsonboot.utils;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

/**
 * 类型工具类
 *
 * @author qinghuo
 * @since 2021/03/22 14:40
 */
public class ObjectUtils {

    /**
     * 转换String类型对象为目标类型对象
     *
     * @param targetClass 需要转换的目标类型
     * @param content String文本对象
     * @return 转换后的对象
     */
    public static Object convertToClass(Class<?> targetClass, String content){
        PropertyEditor editor = PropertyEditorManager.findEditor(targetClass);
        editor.setAsText(content);
        return editor.getValue();
    }

    /**
     * 如果是基本数据类型就转换为其包装类 (已废弃)
     *
     * @param type 传入的类型
     * @return 处理后的类型
     */
    @Deprecated
    public static Class<?> convertBaseClass(Class<?> type){
        switch (type.getTypeName()){
            case "int":
                return Integer.class;
            case "short":
                return Short.class;
            case "long":
                return Long.class;
            case "boolean":
                return Boolean.class;
            case "byte":
                return Byte.class;
            case "float":
                return Float.class;
            case "char":
                return Character.class;
            case "double":
                return Double.class;
            default:
                return type;
        }
    }
}
