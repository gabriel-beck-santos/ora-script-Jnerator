package com.ora.script.jnerator.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author gabriel.santos
 * @since 04/04/2018 15:50:00
 */
@Getter
@Setter
@EqualsAndHashCode
public class Field {

    private Set<KeyValue<String, String>>  keyValues = new HashSet<>();
}
