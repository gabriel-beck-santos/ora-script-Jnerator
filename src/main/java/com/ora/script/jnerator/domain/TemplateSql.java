package com.ora.script.jnerator.domain;

import java.util.Arrays;
import java.util.List;

public enum TemplateSql {

    TEMPLATE_DDL_ALTER_DEMO("TEMPLATE_DDL_ALTER_DEMO", "sql-templates/DDL/ALTER/demo.txt"),
    TEMPLATE_DDL_DROP_DEMO("TEMPLATE_DDL_DROP_DEMO", "sql-templates/DDL/DROP/demo.txt"),
    TEMPLATE_DDL_CREATE_DEMO("TEMPLATE_DDL_CREATE_DEMO", "sql-templates/DDL/CREATE/demo.txt");

    private final String name;
    private final String path;

    TemplateSql(String name, String path){
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public static List<TemplateSql> getListTemplates(){
        return Arrays.asList(TemplateSql.values());
    }

    public static TemplateSql getTemplateBy(String s) {
        for (TemplateSql template : TemplateSql.values()) {
            if (template.getName().equals(s)) {
                return template;
            }
        }
        // throw an IllegalArgumentException or return null
        throw new IllegalArgumentException("the given string doesn't match any Template.");
    }
}
