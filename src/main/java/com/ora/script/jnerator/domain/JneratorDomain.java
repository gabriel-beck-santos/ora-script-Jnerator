package com.ora.script.jnerator.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
public class JneratorDomain {

    private Map<String, String> templateOptions;
    private List<String> templateOptionsList;
    private String templateSelected;
    private String templatePath;
    private Field field;
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
}
