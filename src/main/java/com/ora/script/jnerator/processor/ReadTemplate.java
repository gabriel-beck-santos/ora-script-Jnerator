package com.ora.script.jnerator.processor;

import com.ora.script.jnerator.domain.Field;
import com.ora.script.jnerator.domain.JneratorDomain;
import com.ora.script.jnerator.domain.KeyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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

    private Logger logger = LoggerFactory.getLogger(ReadTemplate.class);
    private String styleHtml = "<strong style='color:blue;background-color: #FFFF00;'>$$</strong>";

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
            logger.info("Anything to generate.");
        }
        return collect2;
    }

    /**
     * Method for load a template selected.
     *
     * @param domain receives domain with atributes.
     */
    public void loadSelectedTemplate(JneratorDomain domain) {

        try {
            loadTemplatePath(domain);
            loadGenerateTemplate(domain);

        } catch (Exception e) {
            logger.info("Anything to read.");
        }
    }

    private void loadGenerateTemplate(JneratorDomain domain) {

        Field field = new Field();

        Stream<String> lines = null;
        List<String> collect = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get(domain.getTemplatePath()));
            collect = lines.collect(Collectors.toList());
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            if (Objects.nonNull(lines)){
                lines.close();
            }
        }

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
                    newString = newString.replace(group, styleHtml.replace("$$", strings.get(0)));
                }
            }

            collectFinish.add(newString);
        }

        domain.setGenerateDocument(collectFinish);
        domain.setField(field);
    }

    private void loadTemplatePath(JneratorDomain domain) {
        if (Objects.nonNull(domain.getTemplateSelected()) && !domain.getTemplateSelected().isEmpty()) {

            Optional<Map.Entry<String, String>> any = domain.getTemplateOptions().entrySet().stream()
                    .filter(stringStringEntry -> stringStringEntry
                            .getKey().contains(domain.getTemplateSelected())).findAny();

            if (any.isPresent()) {
                domain.setTemplatePath(any.get().getValue());
            }
        }
    }
}
