package com.ora.script.jnerator.processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Controller;

/**
 * @author gabriel.santos
 * @since 04/04/2018 13:01:00
 */
@Controller
public class ReadTemplate {

  public List<String> generateSqlTemplate(Path path, Map<String, String[]> variables) {
    List<String> collect2 = new ArrayList<>();
    try {

      Stream<String> lines = Files.lines(path);
      List<String> collect = lines.collect(Collectors.toList());

      for (String s : collect) {

        String finalS = s;
        Optional<Entry<String, String[]>> keyValue = variables.entrySet().stream()
            .filter(stringStringKeyValue -> finalS.contains(stringStringKeyValue.getKey()))
            .findAny();

        if (keyValue.isPresent()) {
          s = s.replace(keyValue.get().getKey(), keyValue.get().getValue()[0]);
        }
        collect2.add(s);
      }

      lines.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return collect2;
  }

//  public void generateSql(Path path, Map<String, String> variables) {
//    List<String> collect2 = generateSqlTemplate(path, variables);
//    try {
//      Files.write(Paths.get("teste.sql"), collect2);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
}
