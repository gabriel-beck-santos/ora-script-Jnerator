package com.ora.script.jnerator.controller;

import com.ora.script.jnerator.domain.JneratorDomain;
import com.ora.script.jnerator.processor.FileUtil;
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
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * Controller to generate from a template.
 *
 * @author gabriel.santos
 * @since 04/04/2018 14:39:00
 */
@Controller
public class JneratorController {

    private final ReadTemplate read;
    private JneratorDomain domain;
    private Map<String, String[]> parameterMap;
    private Logger logger = LoggerFactory.getLogger(JneratorController.class);

    public JneratorController(ReadTemplate readTemplate) {
        this.read = readTemplate;
    }

    @RequestMapping("/")
    public ModelAndView index(WebRequest webRequest) {

        domain = new JneratorDomain();

        methodDefault(webRequest);

        ModelAndView mv = new ModelAndView("index");
        mv.addObject("domain", domain);
        return mv;
    }

    @RequestMapping("/generate")
    public ModelAndView generate(WebRequest webRequest) {

        methodDefault(webRequest);

        ModelAndView mv = new ModelAndView("index");

        try{
            domain.setGenerateDocument(read.generateSqlTemplate(domain));
        }catch (Exception e){
            logger.info(e.getMessage());
        }

        mv.addObject("domain", domain);
        return mv;
    }

    private void methodDefault(WebRequest webRequest) {
        if (Objects.isNull(domain)) {
            domain = new JneratorDomain();
        }

        if (webRequest.getParameterMap().values().stream().anyMatch(strings -> strings.length > 0)) {
            parameterMap = webRequest.getParameterMap();
        }

        domain.setMapAtributes(parameterMap);
        Map<String, String> templates = FileUtil.getFiles();

        domain.setTemplateOptions(templates);
        domain.setTemplateOptionsList(new ArrayList<>(templates.keySet()));
        read.loadSelectedTemplate(domain);
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public HttpEntity<byte[]> downloadFile() throws IOException {

        try {
            File temp = File.createTempFile("OWNER_TABLE", ".sql");
            temp.deleteOnExit();

            Files.write(temp.toPath(), domain.getGenerateDocument(), Charset.forName("UTF-8"));

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
