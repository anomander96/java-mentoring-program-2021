package com.epam.ld.module2.testing.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The type Template.
 */
public class Template {
    String template;

    public Template() {

    }

    public Template(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getDefaultTemplate() {
        return "Dear #{NAME}, we are pleased to inform about #{NEWS}";
    }

    public String getTemplateFromFile(String fullFileName) {
        StringBuilder defaultMessageBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fullFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                defaultMessageBody.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } return defaultMessageBody.toString();
    }
}
