package com.ora.script.jnerator.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KeyValueTest {

    @Test
    public void successToLoadClass(){

        String key = "#{title@help@size@type}";
        String value = anyString();

        KeyValue keyValue = new KeyValue(key, value);

        assertEquals(keyValue.getKey(),key);
        assertEquals(keyValue.getValue(),value);
        assertEquals(keyValue.getTitle(),"title");
        assertEquals(keyValue.getHelp(),"help");
        assertEquals(keyValue.getSize(),"size");
        assertEquals(keyValue.getType(),"type");
    }

    @Test
    public void successSetNewValue(){

        String key = "#{title@help@size@type}";
        String value = anyString();
        String newValue = anyString();

        KeyValue keyValue = new KeyValue(key, value);
        keyValue.setValue(newValue);

        assertEquals(keyValue.getValue(),newValue);
    }

    @Test
    public void successSetNewTitle(){

        String key = "#{title@help@size@type}";
        String value = anyString();
        String newTitle = anyString();

        KeyValue keyValue = new KeyValue(key, value);
        keyValue.setTitle(newTitle);

        assertEquals(keyValue.getTitle(),newTitle);
    }

    @Test
    public void successSetNewType(){

        String key = "#{title@help@size@type}";
        String value = anyString();
        String newType = anyString();

        KeyValue keyValue = new KeyValue(key, value);
        keyValue.setType(newType);

        assertEquals(keyValue.getType(),newType);
    }

    @Test
    public void successToEquals(){

        String key = "#{title@help@size@type}";
        String value = anyString();

        KeyValue keyValue1 = new KeyValue(key, value);
        KeyValue keyValue2 = new KeyValue(key, value);

        assertEquals(keyValue1, keyValue2);
    }

    @Test
    public void successToHashCode(){

        String key = "#{title@help@size@type}";
        String value = anyString();

        KeyValue keyValue1 = new KeyValue(key, value);

        assertEquals(Objects.hash(key, value), keyValue1.hashCode());
    }
}
