package com.ora.script.jnerator.controller;

import com.ora.script.jnerator.domain.Field;
import com.ora.script.jnerator.domain.KeyValue;
import com.ora.script.jnerator.domain.TemplateSql;
import com.ora.script.jnerator.processor.ReadTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:gabriel.santos@ntconsult.com.br">gabriel.santos</a>
 * @since 04/04/2018 14:39:00
 */
@Controller
public class WebController {

    private final ReadTemplate readTemplate;

    public WebController(ReadTemplate readTemplate) {
        this.readTemplate = readTemplate;
    }

    @RequestMapping("/")
    public String index(WebRequest webRequest, Model model) {

        listTemplates(model);

        Map<String, String[]> map = webRequest.getParameterMap();
        map.forEach((s, strings) -> System.out.println("Chave: " + s +" - Valor:" + strings[0]));

        String templateSelected = "";
        try{
            templateSelected = map.get("templateOptions")[0];
            model.addAttribute("templateSelected", templateSelected);
        }catch (Exception e){
            model.addAttribute("templateSelected", "");
        }

        carregaTemplateSelecionado(model, templateSelected);

        Map<String, String> var = new HashMap<>();

        var.put("#{descricao}", "Descrição");
        var.put("#{autor}", "Gabriel Beck dos Santos");
        var.put("#{data}", LocalDate.now().toString());
        var.put("#{tabela}", "SEFAZ_CCI.TA_DOCUMENTO_APRESENTADO");
        var.put("#{alter_script}", "SEFAZ_CCI.TA_DOCUMENTO_APRESENTADO ADD IDEN_PENDENCIA NUMBER(12, 0)");

        model.addAttribute("final", readTemplate.generateSqlTemplate(Paths.get("sql-templates/DDL/ALTER/demo.txt"), var));
        return "index";
    }

    private void carregaTemplateSelecionado(Model model, String templateSelected) {
        Field field = new Field();
        try {

            TemplateSql template = TemplateSql.getTemplateBy(templateSelected);
            Stream<String> lines = Files.lines(Paths.get(template.getPath()));

            List<String> collect = lines.collect(Collectors.toList());

            model.addAttribute("templates", collect);

            for (String s : collect) {
                Pattern pattern = Pattern.compile("#\\{(.*?)}");
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    field.getKeyValues().add(new KeyValue(matcher.group(1), ""));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        model.addAttribute("field", Objects.isNull(field) ? new Field() : field);
    }

    private void listTemplates(Model model) {
        List<TemplateSql> listTemplates = TemplateSql.getListTemplates();
        model.addAttribute("templateOptions", listTemplates);
    }

    @PostMapping("/generate")
    public String generateSubmit(WebRequest webRequest) {

        Map<String, String[]> map = webRequest.getParameterMap();

        map.forEach((s, strings) -> System.out.println("Chave: " + s +" - Valor:" + strings));

        return "index";
    }
}
