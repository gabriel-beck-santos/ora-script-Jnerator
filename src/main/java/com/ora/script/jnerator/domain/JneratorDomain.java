package com.ora.script.jnerator.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@EqualsAndHashCode
public class JneratorDomain {

    private List<TemplateSql> templateOptions;
    private String templateSelected;
    private Field field;
    private List<String> templates;
    private List<String> generateDocument;
    private Map<String, String[]> mapAtributes;
    private String path;

    public void setMapAtributes(Map<String, String[]> map){
        this.mapAtributes = map;
        atributeTemplateOptions();
        map.forEach((s, strings) -> System.out.println("Chave: " + s +" - Valor:" + strings[0]));
    }

    private void atributeTemplateOptions() {
        try{
            this.templateSelected = mapAtributes.get("templateOptions")[0];

            TemplateSql templateBy = TemplateSql.getTemplateBy(this.templateSelected);
            this.path = templateBy.getPath();
        }catch (Exception e){
            this.templateSelected = "";
        }
    }

    public void loadSelectedTemplate() {
        Field field = new Field();
        try {

            TemplateSql templateBy = TemplateSql.getTemplateBy(this.templateSelected);

            Stream<String> lines = Files.lines(Paths.get(templateBy.getPath()));
            List<String> collect = lines.collect(Collectors.toList());

            this.setTemplates(collect);

            for (String s : collect) {
                Pattern pattern = Pattern.compile("#\\{(.*?)}");
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    field.getKeyValues().add(new KeyValue(matcher.group(1), ""));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        this.setField(field);
    }
}
