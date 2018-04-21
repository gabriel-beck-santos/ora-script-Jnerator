package com.ora.script.jnerator.domain;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class JneratorDomain {

  private Map<String, String> templateOptions;
  private List<String> templateOptionsList;
  private String templateSelected;
  private String templatePath;
  private Field field;
  private List<String> templates;
  private List<String> generateDocument;
  private Map<String, String[]> mapAtributes;
  private String path;

  public void setMapAtributes(Map<String, String[]> map) {
    this.mapAtributes = map;
    atributeTemplateOptions();
  }

  private void atributeTemplateOptions() {
    try {
      this.templateSelected = mapAtributes.get("templateOptions")[0];
      this.path = templateOptions.entrySet().stream()
          .filter(stringStringEntry -> stringStringEntry
              .getKey().contains(templateSelected)).findAny().get().getValue();
    } catch (Exception e) {
      this.templateSelected = "";
    }
  }

  public void loadSelectedTemplate() {
    Field field = new Field();
    try {

      if (Objects.nonNull(this.templateSelected) && !this.templateSelected
          .isEmpty()) {
        templatePath = templateOptions.entrySet().stream()
            .filter(stringStringEntry -> stringStringEntry
                .getKey().contains(templateSelected)).findAny().get().getValue();
      }

      Stream<String> lines = Files.lines(Paths.get(templatePath));
      List<String> collect = lines.collect(Collectors.toList());

      this.setTemplates(collect);

      for (String s : collect) {
        Pattern pattern = Pattern.compile("#\\{(.*?)}");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
          field.getKeyValues().add(new KeyValue(matcher.group(), ""));
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    this.setField(field);
  }
}
