package com.ora.script.jnerator.controller;

import com.ora.script.jnerator.domain.Field;
import com.ora.script.jnerator.domain.KeyValue;
import com.ora.script.jnerator.processor.ReadTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

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
    public String welcome(Model model) {

        Field field = new Field();
        try {
            Stream<String> lines = Files.lines(Paths.get("sql-templates/DDL/ALTER/demo.txt"));

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
        }

        Map<String, String> var = new HashMap<>();

        var.put("#{descricao}", "Descrição");
        var.put("#{autor}", "Gabriel Beck dos Santos");
        var.put("#{data}", LocalDate.now().toString());
        var.put("#{tabela}", "SEFAZ_CCI.TA_DOCUMENTO_APRESENTADO");
        var.put("#{alter_script}", "SEFAZ_CCI.TA_DOCUMENTO_APRESENTADO ADD IDEN_PENDENCIA NUMBER(12, 0)");

        model.addAttribute("field", field);

        model.addAttribute("final", readTemplate.generateSqlTemplate(Paths.get("sql-templates/DDL/ALTER/demo.txt"), var));
        return "index";
    }

    @PostMapping("/generate")
    public String generateSubmit(WebRequest webRequest) {

        Map<String, String[]> map = webRequest.getParameterMap();

        map.forEach((s, strings) -> System.out.println("Chave: " + s +" - Valor:" + strings));

        System.out.println("CHEGA aqui");
        return "index";
    }
}
