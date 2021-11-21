package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.exception.TemplateException;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Template engine.
 */
public class TemplateEngine {
    private static final String templateFile = "src/main/resources/defaultTemplate.txt";
    private static final String PLACEHOLDER_PATTERN = "#\\{(.[^}]*)}";
    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessage(Template template, Client client) {
        String message = template.getTemplate();
        Map<String, String> variables = client.getVariables();
        Set<Map.Entry<String, String>> entrySet = variables.entrySet();

        if (!checkPlaceholders(template, variables)) {
            throw new TemplateException("Something went wrong during checking a placeholders in template");
        }

        for (Map.Entry<String, String> e : entrySet) {
            String pattern = "#\\{" + e.getKey() + "}";
            message = message.replaceAll(pattern, e.getValue());
        }
        return message;
    }

    private boolean checkPlaceholders(Template template, Map<String, String> variables) {
        boolean statement = true;
        Pattern pattern = Pattern.compile(PLACEHOLDER_PATTERN);
        Matcher matcher = pattern.matcher(template.getTemplate());

        while (matcher.find()) {
            String placeholder = matcher.group(1);
            if (!variables.containsKey(placeholder)) {
                statement = false;
            }
        } return statement;
    }

    private Map<String, String> consoleMode(Client client, Template template) {
        Pattern pattern = Pattern.compile(PLACEHOLDER_PATTERN);
        Matcher matcher = pattern.matcher(template.getTemplate());
        Scanner scanner = new Scanner(System.in);
        Map<String, String> variables = client.getVariables();

        while (matcher.find()) {
            variables.put(matcher.group(1), scanner.nextLine());
        } return variables;
    }

    private void fileMode(Client client, Template template, String filePath, String outputFileName) {
        TemplateEngine templateEngine = new TemplateEngine();
        Pattern pattern = Pattern.compile(PLACEHOLDER_PATTERN);
        Matcher matcher = pattern.matcher(template.getTemplate());
        List<String> placeholders = new ArrayList<>();
        while(matcher.find()) {
            placeholders.add(matcher.group(1));
        }

        int numberOfPlaceholders = placeholders.size();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                if (split.length < numberOfPlaceholders) {
                    throw new TemplateException("Not acceptable size of fields");
                }

                Map<String, String> variables = new HashMap<>();
                for (int i = 0; i < numberOfPlaceholders; i++) {
                    variables.put(placeholders.get(i), split[i].trim());
                }

                client.setVariables(variables);

                String message = templateEngine.generateMessage(template, client);

                fileWriter.write(message);
                fileWriter.newLine();
            }
        } catch (IOException ex) {
        }
    }
}
