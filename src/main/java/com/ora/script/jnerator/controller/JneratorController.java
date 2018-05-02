package com.ora.script.jnerator.controller;

import com.ora.script.jnerator.domain.JneratorDomain;
import com.ora.script.jnerator.processor.ReadTemplate;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
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

  @GetMapping("/remove-template/{template}")
  public String removeTemplate(@PathVariable String template) {
    File file = new File("sql-templates/" + template);
    file.delete();
    return "redirect:/remove-template";
  }

  @GetMapping("/remove-template")
  public ModelAndView removeTemplate() {

    JneratorDomain domain = new JneratorDomain();
    ModelAndView modelAndView = new ModelAndView("remove-template");
    File[] files = new File("sql-templates").listFiles();

    Map<String, String> templates = new HashMap<>();

    for (File file : Objects.requireNonNull(files)) {
      templates.put(file.getName(), file.getPath());
    }

    domain.setTemplateOptions(templates);
    domain.setTemplateOptionsList(new ArrayList<>(templates.keySet()));

    modelAndView.addObject("domain", domain);
    return modelAndView;
  }

  @GetMapping("/template")
  public ModelAndView templateUpload() {
    return new ModelAndView("template");
  }

  @PostMapping("/template")
  public String submitTemplate(@RequestParam("fileTemplate") MultipartFile file) {

    String orgName = file.getOriginalFilename();
    String filePath = "sql-templates/" + orgName;

    try {
      Files.write(Paths.get(filePath), file.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return "redirect:/";
  }

  @RequestMapping(value = "/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  @ResponseBody
  public HttpEntity<byte[]> downloadFile() throws IOException {

    File temp = null;
    try {
      temp = File.createTempFile("OWNER_TABLE", ".sql");
      temp.deleteOnExit();
      //write it

      Files.write(temp.toPath(), jneratorDomain.getGenerateDocument(), Charset.forName("UTF-8"));

    } catch (IOException e) {
      e.printStackTrace();
    }

    HttpHeaders header = new HttpHeaders();
    header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "OWNER_TABLE.sql");
    header.setContentLength(temp.length());

    return new HttpEntity<>(Files.readAllBytes(temp.toPath()), header);
  }

}
