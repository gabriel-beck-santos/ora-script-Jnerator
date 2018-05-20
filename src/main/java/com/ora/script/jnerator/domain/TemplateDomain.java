package com.ora.script.jnerator.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class TemplateDomain {

    private String template;
    private String commandSelect;
    private MultipartFile fileTemplate;
    private List<TypeOfCommand> typeOfCommand = new ArrayList<>();

    TemplateDomain(){
        typeOfCommand.add(new TypeOfCommand("DML", Arrays.asList("INSERT", "UPDATE", "DELETE", "SELECT")));
        typeOfCommand.add(new TypeOfCommand("DDL", Arrays.asList("ALTER", "DROP", "CREATE")));
        typeOfCommand.add(new TypeOfCommand("DCL", Arrays.asList("GRANT", "REVOKE")));
        typeOfCommand.add(new TypeOfCommand("GENERAL", Arrays.asList("OTHER")));
    }
}
