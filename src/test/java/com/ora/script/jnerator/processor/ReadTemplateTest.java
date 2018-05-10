package com.ora.script.jnerator.processor;

import com.ora.script.jnerator.domain.JneratorDomain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReadTemplateTest {

    private Path path;

    @Test
    public void failToPassNullValuesInParameters() {

        ReadTemplate readTemplate = new ReadTemplate();
        JneratorDomain domain = new JneratorDomain();

        List<String> strings = readTemplate.generateSqlTemplate(path, domain);

        assertEquals(Objects.isNull(strings), Boolean.TRUE);
    }
}
