package com.ora.script.jnerator.processor;

import com.ora.script.jnerator.domain.Field;
import com.ora.script.jnerator.domain.JneratorDomain;
import com.ora.script.jnerator.domain.KeyValue;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utilitarian class for reading the template.
 *
 * @author gabriel.santos
 * @since 04/04/2018 13:01:00
 */
@Component
public class ReadTemplate {

    /**
     * Method for generating a template in sql.
     *
     * @param path      path receives a parameter containing a PATH.
     * @param atributes receives an attribute map.
     * @return the list of string.
     */
    public List<String> generateSqlTemplate(Path path, Map<String, String[]> atributes) {
        List<String> collect2 = new ArrayList<>();
        try {

            Stream<String> lines = Files.lines(path);
            List<String> collect = lines.collect(Collectors.toList());

            for (String line : collect) {

                String finalS = line;

                Set<Map.Entry<String, String[]>> entries = atributes.entrySet();

                for (Map.Entry<String, String[]> entrie : entries) {
                    if (finalS.contains(entrie.getKey())) {
                        finalS = finalS.replace(entrie.getKey(), entrie.getValue()[0]);
                    }
                }

                collect2.add(finalS);
            }

            lines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return collect2;
    }

    /**
     * Method for load a template selected.
     *
     * @param domain receives domain with atributes.
     */
    public void loadSelectedTemplate(JneratorDomain domain) {

        Field field = new Field();
        try {

            if (Objects.nonNull(domain.getTemplateSelected()) && !domain.getTemplateSelected().isEmpty()) {

                domain.setTemplatePath(domain.getTemplateOptions().entrySet().stream()
                        .filter(stringStringEntry -> stringStringEntry
                                .getKey().contains(domain.getTemplateSelected())).findAny().get().getValue());
            }

            Stream<String> lines = Files.lines(Paths.get(domain.getTemplatePath()));
            List<String> collect = lines.collect(Collectors.toList());
            List<String> collectFinish = new ArrayList<>();


            for (String s : collect) {
                Pattern pattern = Pattern.compile("#\\{(.*?)}");
                Matcher matcher = pattern.matcher(s);

                String newString = s;
                while (matcher.find()) {
                    String group = matcher.group();

                    field.getKeyValues().add(new KeyValue(group, ""));

                    Pattern pattern2 = Pattern.compile("[^#{].+[^}]");
                    Matcher matcher2 = pattern2.matcher(group);

                    if (matcher2.find()) {

                        List<String> strings = Arrays.asList(matcher2.group().split("@"));
                        newString = newString.replace(group, "<strong style='color:red'>" + strings.get(0) +
                                "</strong>");
                    }
                }

                collectFinish.add(newString);
            }

            domain.setGenerateDocument(collectFinish);

        } catch (Exception e) {
            e.printStackTrace();
        }

        domain.setField(field);
    }
}
