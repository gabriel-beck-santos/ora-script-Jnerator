package com.ora.script.jnerator.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
    private Logger logger = LoggerFactory.getLogger(JneratorDomain.class);

    public void setMapAtributes(Map<String, String[]> map) {
        this.mapAtributes = map;
        atributeTemplateOptions();
    }

    private void atributeTemplateOptions() {

        try {
            this.templateSelected = mapAtributes.get("templateOptions")[0];

        } catch (Exception e) {
            logger.info("Template is " + this.templateSelected);
        }

        if (Objects.nonNull(templateOptions)){
            Optional<Map.Entry<String, String>> any = templateOptions.entrySet()
                    .stream()
                    .filter(stringStringEntry -> stringStringEntry.getKey().contains(templateSelected))
                    .findAny();

            if (any.isPresent()) {
                this.path = any.get().getValue();
            }
        }
    }
}
