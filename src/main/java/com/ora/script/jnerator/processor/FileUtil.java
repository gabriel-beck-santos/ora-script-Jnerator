package com.ora.script.jnerator.processor;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileUtil {

    // TODO: refactoring
    public static void createDir(String directoryName){

        String[] splited = directoryName.split("/");

        File directory = new File(String.valueOf(splited[0] +  "/" +splited[1]));

        if(!directory.exists()){
            directory.mkdir();
        }

        File directoryTwo = new File(String.valueOf(directoryName));

        if(!directoryTwo.exists()){
            directoryTwo.mkdir();
        }
    }

    // TODO: refactoring
    public static Map<String, String> getFiles() {
        File[] files = new File("sql-templates").listFiles();

        Map<String, String> templates = new HashMap<>();

        for (File file : Objects.requireNonNull(files)) {
            if(file.isDirectory()){
                for (File fileTwo : Objects.requireNonNull(file.listFiles())) {
                    if(fileTwo.isDirectory()){
                        for (File fileThree : Objects.requireNonNull(fileTwo.listFiles())) {
                            String[] splitThree = fileThree.getPath().split("/");
                            templates.put(splitThree[1] + "/" + splitThree[2] + "/"+ fileThree.getName(), fileThree.getPath());
                        }
                    }else{
                        templates.put(fileTwo.getPath().split("/")[1] + "/" + fileTwo.getName(), fileTwo.getPath());
                    }
                }
            }else{
                templates.put(file.getName(), file.getPath());
            }
        }

        templates = templates.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return templates;
    }
}
