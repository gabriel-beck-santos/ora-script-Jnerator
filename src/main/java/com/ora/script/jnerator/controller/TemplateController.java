package com.ora.script.jnerator.controller;

import com.ora.script.jnerator.domain.JneratorDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Controller to maintain the template.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 04/05/2018 14:07:00
 */
@Controller
public class TemplateController {

    private Logger logger = LoggerFactory.getLogger(TemplateController.class);

    @GetMapping("/remove-template/{template}")
    public String removeTemplate(@PathVariable String template) {
        File file = new File("sql-templates/" + template);

        if (!file.delete()) {
            logger.info("Template " + template + " não removido!");
        }
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
            logger.info(e.getMessage());
        }

        return "redirect:/";
    }
}
