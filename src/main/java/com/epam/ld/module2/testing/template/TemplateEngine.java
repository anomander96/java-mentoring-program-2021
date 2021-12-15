package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.exception.TemplateException;
import org.apache.commons.text.StringSubstitutor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The type Template engine.
 */
public class TemplateEngine {

    Logger logger = Logger.getLogger(String.valueOf(TemplateEngine.class));

    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessage(Template template, Client client) {
        String inputTemplate = template.getTemplate();
        String addresses = client.getAddresses();
        Map<String, String> inputMap;

        if (addresses == null) {
            inputMap = consoleMode(client, inputTemplate);
        } else {
            inputMap = fileMode(client, inputTemplate);
        }

        StringSubstitutor sub = new StringSubstitutor(inputMap, "#{", "}");
        return sub.replace(inputTemplate);
    }

    private Map<String, String> consoleMode(Client client, String inputTemplate) {
        String regexp = "#\\{(.+?)}";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(inputTemplate);
        Scanner scanner = new Scanner(System.in);

        Map<String, String> inputMap = client.getVariables();

        while (matcher.find()) {
            logger.info("Please enter " + matcher.group(1).toLowerCase() + " :");
            inputMap.put(matcher.group(1), scanner.nextLine());
        }
        return inputMap;
    }

    private Map<String, String> fileMode(Client client, String inputTemplate) {
        Map<String, String> clientData = client.getVariables();

        String regexp = "#\\{(.+?)}";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(inputTemplate);

        List<String> collect = null;
        try {
            collect = Files.lines(Paths.get(client.getAddresses()))
                    .filter(line -> line.contains("="))
                    .map(value -> value.split("=")[1])
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int count = 0;
        while (matcher.find()) {
            clientData.put(matcher.group(1), Objects.requireNonNull(collect).get(count));
            count++;
        }

        return clientData;
    }

    public static void main(String[] args) {
        TemplateEngine templateEngine = new TemplateEngine();
        Client client = new Client();
        Template template = new Template();

        template.setTemplate("Dear #{NAME}, this is massage about #{NEWS}");
        Map<String, String> map = new HashMap<>();
        client.setVariables(map);

        System.out.println(templateEngine.generateMessage(template, client));
    }
}
