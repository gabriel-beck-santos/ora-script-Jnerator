package com.ora.script.jnerator.processor;

import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        Set<Map.Entry<String, String[]>> entries = variables.entrySet();

        for (Map.Entry<String, String[]> entrie : entries){
          if (finalS.contains(entrie.getKey())) {
            finalS = finalS.replace(entrie.getKey(), entrie.getValue()[0]);
          }
        }

        collect2.add(finalS);
      }

      lines.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return collect2;
  }
}
