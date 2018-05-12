package com.ora.script.jnerator.processor;

import com.ora.script.jnerator.domain.JneratorDomain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReadTemplateTest {

    @InjectMocks
    private ReadTemplate readTemplate;

    @Mock
    private JneratorDomain domain;

    @Test
    public void successToPassNoNullValuesInParameters() {

        Map<String, String[]> map = new HashMap<>();
        map.put("attr1", new String[]{"value1"});
        map.put("attr2", new String[]{"value2"});

        when(domain.getMapAtributes()).thenReturn(map);
        when(domain.getTemplatePath()).thenReturn("sql-templates/TEMPLATE-ARQ_ADD-COLUMN.sql");

        List<String> strings = readTemplate.generateSqlTemplate(domain);

        assertEquals(strings.size() > 1, Boolean.TRUE);
    }

    @Test
    public void failToPassNullValuesInParameters() {

        ReadTemplate read = mock(ReadTemplate.class);

        List<String> strings = read.generateSqlTemplate(domain);
        assertEquals(strings.isEmpty(), Boolean.TRUE);
    }
}
