package com.ora.script.jnerator.controller;

import com.ora.script.jnerator.domain.JneratorDomain;
import com.ora.script.jnerator.processor.ReadTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller to generate from a template.
 *
 * @author gabriel.santos
 * @since 04/04/2018 14:39:00
 */
@Controller
public class JneratorController {

    private final ReadTemplate readTemplate;
    private JneratorDomain jneratorDomain;
    private Map<String, String[]> parameterMap;
    private Logger logger = LoggerFactory.getLogger(JneratorController.class);

    public JneratorController(ReadTemplate readTemplate) {
        this.readTemplate = readTemplate;
    }

    @RequestMapping("/")
    public ModelAndView index(WebRequest webRequest) {

        jneratorDomain = new JneratorDomain();

        methodDefault(webRequest);

        ModelAndView mv = new ModelAndView("index");
        mv.addObject("domain", jneratorDomain);
        return mv;
    }

    @RequestMapping("/generate")
    public ModelAndView generate(WebRequest webRequest) {

        methodDefault(webRequest);

        ModelAndView mv = new ModelAndView("index");

        String path = jneratorDomain.getTemplatePath();
        jneratorDomain
                .setGenerateDocument(
                        readTemplate.generateSqlTemplate(Paths.get(path), jneratorDomain.getMapAtributes()));

        mv.addObject("domain", jneratorDomain);
        return mv;
    }

    private void methodDefault(WebRequest webRequest) {
        if (Objects.isNull(jneratorDomain)) {
            jneratorDomain = new JneratorDomain();
        }

        if (webRequest.getParameterMap().values().stream().anyMatch(strings -> strings.length > 0)) {
            parameterMap = webRequest.getParameterMap();
        }

        jneratorDomain.setMapAtributes(parameterMap);
        Map<String, String> templates = getTemplates();

        jneratorDomain.setTemplateOptions(templates);
        jneratorDomain.setTemplateOptionsList(new ArrayList<>(templates.keySet()));
        readTemplate.loadSelectedTemplate(jneratorDomain);
    }

    private Map<String, String> getTemplates() {
        File[] files = new File("sql-templates").listFiles();

        Map<String, String> templates = new HashMap<>();

        for (File file : Objects.requireNonNull(files)) {
            templates.put(file.getName(), file.getPath());
        }

        templates = templates.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return templates;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public HttpEntity<byte[]> downloadFile() throws IOException {

        try {
            File temp = File.createTempFile("OWNER_TABLE", ".sql");
            temp.deleteOnExit();

            Files.write(temp.toPath(), jneratorDomain.getGenerateDocument(), Charset.forName("UTF-8"));

            HttpHeaders header = new HttpHeaders();
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "OWNER_TABLE.sql");
            header.setContentLength(temp.length());

            return new HttpEntity<>(Files.readAllBytes(temp.toPath()), header);

        } catch (IOException e) {
            logger.info(e.getMessage());
        }

        return null;
    }
}
