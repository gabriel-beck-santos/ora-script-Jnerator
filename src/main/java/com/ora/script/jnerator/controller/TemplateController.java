package com.ora.script.jnerator.controller;

import com.ora.script.jnerator.domain.JneratorDomain;
import com.ora.script.jnerator.domain.TemplateDomain;
import com.ora.script.jnerator.processor.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

/**
 * Controller to maintain the template.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 04/05/2018 14:07:00
 */
@Controller
public class TemplateController {

    private Logger logger = LoggerFactory.getLogger(TemplateController.class);

    @PostMapping("/remove-template")
    public String removeTemplate(TemplateDomain templateDomain) {
        File file = new File("sql-templates/" + templateDomain.getTemplate() );

        if (!file.delete()) {
            logger.info("Template " + templateDomain.getTemplate() + " não removido!");
        }
        return "redirect:/list-template";
    }

    @GetMapping("/list-template")
    public ModelAndView listTemplate(TemplateDomain templateDomain) {

        JneratorDomain domain = new JneratorDomain();
        ModelAndView modelAndView = new ModelAndView("template/list-template");
        Map<String, String> templates = FileUtil.getFiles();

        domain.setTemplateOptions(templates);
        domain.setTemplateOptionsList(new ArrayList<>(templates.keySet()));

        modelAndView.addObject("domain", domain);
        modelAndView.addObject("templateDomain", templateDomain);

        return modelAndView;
    }

    @PostMapping("/template")
    public String submitTemplate(TemplateDomain templateDomain) {

        String orgName = templateDomain.getFileTemplate().getOriginalFilename();
        String directory = "sql-templates/"+ templateDomain.getCommandSelect() + "/";
        String filePath = directory + orgName;

        try {
            FileUtil.createDir(directory);
            Files.write(Paths.get(filePath), templateDomain.getFileTemplate().getBytes());
        } catch (IOException e) {
            logger.info(e.getMessage());
        }

        return "redirect:/list-template/";
    }
}
