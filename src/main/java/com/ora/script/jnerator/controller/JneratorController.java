package com.ora.script.jnerator.controller;

import com.ora.script.jnerator.domain.JneratorDomain;
import com.ora.script.jnerator.processor.ReadTemplate;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author gabriel.santos
 * @since 04/04/2018 14:39:00
 */
@Controller
public class JneratorController {

  private final ReadTemplate readTemplate;
  private JneratorDomain jneratorDomain;
  private Map<String, String[]> parameterMap;

  public JneratorController(ReadTemplate readTemplate) {
    this.readTemplate = readTemplate;
  }

  @RequestMapping("/")
  public ModelAndView index(WebRequest webRequest) throws IOException {

    if (Objects.isNull(jneratorDomain)) {
      jneratorDomain = new JneratorDomain();
    }

    ModelAndView mv = new ModelAndView("index");

    if (webRequest.getParameterMap().values().stream().anyMatch(strings -> strings.length > 0)) {
      parameterMap = webRequest.getParameterMap();
    }

    jneratorDomain.setMapAtributes(parameterMap);
    File[] files = new File("sql-templates").listFiles();

    Map<String, String> templates = new HashMap<>();

    for (File file : Objects.requireNonNull(files)) {
      templates.put(file.getName(), file.getPath());
    }

    jneratorDomain.setTemplateOptions(templates);
    jneratorDomain.setTemplateOptionsList(new ArrayList<>(templates.keySet()));

    jneratorDomain.loadSelectedTemplate();

    try {
      String path = jneratorDomain.getTemplatePath();
      jneratorDomain
          .setGenerateDocument(
              readTemplate.generateSqlTemplate(Paths.get(path), jneratorDomain.getMapAtributes()));
    } catch (Exception e) {
      e.getStackTrace();
    }

    mv.addObject("domain", jneratorDomain);
    return mv;
  }

}
