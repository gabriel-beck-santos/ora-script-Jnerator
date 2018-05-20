package com.ora.script.jnerator.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class TypeOfCommand {

    private String name;
    private List<String> commands;

    TypeOfCommand(String name, List<String> commands){
        this.name = name;
        this.commands = commands;
    }
}
