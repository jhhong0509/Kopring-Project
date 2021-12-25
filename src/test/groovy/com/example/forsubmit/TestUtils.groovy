package com.example.forsubmit

class TestUtils {
    static def setVariable(String key, Object value, Object instance) {
        def field = instance.getClass().getDeclaredField(key)
        field.setAccessible(true)
        field.set(instance, value)
    }
}
