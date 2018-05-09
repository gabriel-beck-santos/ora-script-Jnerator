package com.ora.script.jnerator.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gabriel.santos
 * @since 04/04/2018 15:15:00
 */
public class KeyValue<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;
    private String title;
    private String help;
    private String size;
    private String type;

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;

        if (this.key instanceof String) {
            splitKey();
        }
    }

    private void splitKey() {
        Pattern pattern = Pattern.compile("[^#{].+[^}]");
        Matcher matcher = pattern.matcher((String) this.key);

        if (matcher.find()) {

            List<String> strings = Arrays.asList(matcher.group().split("@"));

            title = strings.get(0);

            if(strings.size() > 1){
                help = strings.get(1);
            }

            if(strings.size() > 2){
                size = strings.get(2);
            }

            if(strings.size() > 3){
                type = strings.get(3);
            }
        }
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public K setKey(K key) {
        return this.key = key;
    }

    public V setValue(V value) {
        return this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyValue<?, ?> keyValue = (KeyValue<?, ?>) o;
        return Objects.equals(key, keyValue.key)
                && Objects.equals(value, keyValue.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
