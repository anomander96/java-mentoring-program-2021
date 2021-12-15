package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@DisabledOnJre(JRE.OTHER)
public class MailServerTest {

    private static final String EXPECTED = "Dear Andrew, this is massage about insurance changes";
    private static final String INPUT_RESOURCE = "src/main/resources/input.txt";

    private final MailServer mailServer = new MailServer();
    private final TemplateEngine templateEngine = new TemplateEngine();
    private final Client client = new Client();
    private final Template template = new Template();

    @TempDir
    File file = new File("src/main/resources/");

    @BeforeEach
    public void setUp() {
        template.setTemplate("Dear #{NAME}, this is massage about #{NEWS}");
        Map<String, String> map = new HashMap<>();
        client.setVariables(map);
    }

    @Test
    public void testPartialMock(){
        MailServer mailServer = mock(MailServer.class);
        when(mailServer.getSentMessage()).thenReturn(template.getTemplate());
    }

    @Test
    public void spyTest(){
        MailServer spy = spy(mailServer);
        when(spy.getSentMessage()).thenReturn(template.getTemplate());
    }

    @Test
    @FileMode
    public void testTemporaryFolderSentInFileMode() {
        client.setAddresses(INPUT_RESOURCE);
        String generateMessage = templateEngine.generateMessage(template, client);
        mailServer.send(file.toString(), generateMessage);

        Assertions.assertEquals(EXPECTED, generateMessage);
    }

    @ParameterizedTest
    @ValueSource(strings = "src/main/resources/")
    @FileMode
    public void parameterizedTestSentInFileMode(String address) {
        client.setAddresses(INPUT_RESOURCE);
        String generateMessage = templateEngine.generateMessage(template, client);

        mailServer.send(address, generateMessage);

        Assertions.assertEquals(EXPECTED, generateMessage);
    }

    @Test
    @FileMode
    public void testSentInFileMode() {
        client.setAddresses(INPUT_RESOURCE);
        String generateMessage = templateEngine.generateMessage(template, client);

        mailServer.send("src/main/resources/", generateMessage);

        Assertions.assertEquals(EXPECTED, generateMessage);
    }

    @Test
    @Tag("consoleMode")
    public void testSentInConsoleMode() {
        ByteArrayInputStream in = new ByteArrayInputStream("Andrew\ninsurance changes\n".getBytes());
        System.setIn(in);

        String generateMessage = templateEngine.generateMessage(template, client);
        mailServer.send(client.getAddresses(), generateMessage);

        Assertions.assertEquals(EXPECTED, generateMessage);
    }
}
