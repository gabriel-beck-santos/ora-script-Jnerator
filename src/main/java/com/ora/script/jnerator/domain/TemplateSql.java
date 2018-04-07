package com.ora.script.jnerator.domain;

import java.util.Arrays;
import java.util.List;

public enum TemplateSql {

    TEMPLATE_DDL_ALTER_DEMO("TEMPLATE_DDL_ALTER_DEMO", "sql-templates/DDL/ALTER/demo.txt"),
    TEMPLATE_DDL_DROP_DEMO("TEMPLATE_DDL_DROP_DEMO", "sql-templates/DDL/DROP/demo.txt"),
    TEMPLATE_DDL_CREATE_DEMO("TEMPLATE_DDL_CREATE_DEMO", "sql-templates/DDL/CREATE/demo.txt");

    private final String nome;
    private final String path;

    TemplateSql(String nome, String path){
        this.nome = nome;
        this.path = path;
    }

    public String getNome() {
        return nome;
    }

    public String getPath() {
        return path;
    }

    public static List<TemplateSql> getListTemplates(){
        return Arrays.asList(TemplateSql.values());
    }
}
