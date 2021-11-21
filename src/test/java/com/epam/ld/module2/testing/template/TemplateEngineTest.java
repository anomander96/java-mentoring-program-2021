package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;

public class TemplateEngineTest {

    private TemplateEngine templateEngine;
    private Client client;
    private Template template;

    private static final String STANDART = "This is test message";

    @BeforeAll
    public void setUp() {
        templateEngine = new TemplateEngine();
        client = new Client();
        template = new Template();

        template.setTemplate("Dear #{NAME}, this is massage about #{EVENT} notification");
        Map<String, String> map = new HashMap<>();
        client.setVariables(map);

    }

    @Test
    public void testGenerateTemplateTestInFileMode() {
        client.setAddresses("src/main/resources/input.txt");
        String result = templateEngine.generateMessage(template, client);

        Assertions.assertEquals(STANDART, result);
    }

    @Test
    public void testGenerateTemplateTestInConsoleMode() {
        ByteArrayInputStream input = new ByteArrayInputStream("anyName\nanyEvent\n".getBytes());
        System.setIn(input);

        Assertions.assertEquals(STANDART, templateEngine.generateMessage(template, client));
    }

    @Test
    public void testMessageCantBeNull() {
        ByteArrayInputStream in = new ByteArrayInputStream("anyName\nanyEvent\n".getBytes());
        System.setIn(in);

        String message = templateEngine.generateMessage(template, client);

        Assertions.assertNotNull(message);
    }

    @Test
    public void testMessageCantBeEmpty() {
        ByteArrayInputStream in = new ByteArrayInputStream("anyName\nanyEvent\n".getBytes());
        System.setIn(in);

        String message = templateEngine.generateMessage(template, client);

        MatcherAssert.assertThat(message, not(isEmptyString()));
    }
}
