package com.ora.script.jnerator.controller;

import com.ora.script.jnerator.domain.JneratorDomain;
import com.ora.script.jnerator.domain.TemplateSql;
import com.ora.script.jnerator.processor.ReadTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:gabriel.santos@ntconsult.com.br">gabriel.santos</a>
 * @since 04/04/2018 14:39:00
 */
@Controller
public class JneratorController {

    private final ReadTemplate readTemplate;

    public JneratorController(ReadTemplate readTemplate) {
        this.readTemplate = readTemplate;
    }

    @RequestMapping("/")
    public ModelAndView index(WebRequest webRequest) {

        JneratorDomain jneratorDomain = new JneratorDomain();

        ModelAndView mv = new ModelAndView("index");

        jneratorDomain.setMapAtributes(webRequest.getParameterMap());
        jneratorDomain.setTemplateOptions(TemplateSql.getListTemplates());
        jneratorDomain.loadSelectedTemplate();

        Map<String, String> var = new HashMap<>();

        var.put("#{descricao}", "Descrição");
        var.put("#{autor}", "Gabriel Beck dos Santos");
        var.put("#{data}", LocalDate.now().toString());
        var.put("#{tabela}", "SEFAZ_CCI.TA_DOCUMENTO_APRESENTADO");
        var.put("#{alter_script}", "SEFAZ_CCI.TA_DOCUMENTO_APRESENTADO ADD IDEN_PENDENCIA NUMBER(12, 0)");

        jneratorDomain.setGenerateDocument(readTemplate.generateSqlTemplate(Paths.get("sql-templates/DDL/ALTER/demo.txt"), var));

        mv.addObject("domain", jneratorDomain);
        return mv;
    }


}
