package com.ora.script.jnerator.processor;

import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:gabriel.beck.santos@gmail.com">gabriel.beck.santos</a>
 * @since 04/04/2018 13:01:00
 */
@Controller
public class ReadTemplate {

    public List<String> generateSqlTemplate(Path path, Map<String, String> variables) {
        List<String> collect2 = new ArrayList<>();
        try {

            Stream<String> lines = Files.lines(path);

            List<String> collect = lines.collect(Collectors.toList());


            for (String s : collect) {
                if (variables.keySet().stream().anyMatch(s::contains)) {
                    for (Map.Entry<String, String> variable : variables.entrySet()) {
                        s = s.replace(variable.getKey(), variable.getValue());
                    }
                }
                collect2.add(s);
            }

            lines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return collect2;
    }

    public void generateSql(Path path, Map<String, String> variables) {
        List<String> collect2 = generateSqlTemplate(path, variables);
        try {
            Files.write(Paths.get("teste.sql"), collect2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void main(String[] args) {
        Path path = Paths.get("demo.txt");
        Map<String, String> variables = new HashMap<>();

        variables.put("#{descricao}", "Descrição");
        variables.put("#{autor}", "Gabriel Beck dos Santos");
        variables.put("#{data}", LocalDate.now().toString());
        variables.put("#{tabela}", "SEFAZ_CCI.TA_DOCUMENTO_APRESENTADO");
        variables.put("#{alter_script}", "SEFAZ_CCI.TA_DOCUMENTO_APRESENTADO ADD IDEN_PENDENCIA NUMBER(12, 0)");

        generateSql(path, variables);
    }
}
