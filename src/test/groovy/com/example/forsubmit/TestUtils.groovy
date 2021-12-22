package com.example.forsubmit

class TestUtils {
    static def setVariable(Class clazz, String key, Object value, Object instance) {
        def field = clazz.getDeclaredField(key)
        field.setAccessible(true)
        field.set(instance, value)
    }
}
