package com.epam.ld.module2.testing;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Mail server class.
 */
public class MailServer {

    private String sentMessage;

    public String getSentMessage() {
        return sentMessage;
    }

    /**
     * Send notification.
     *
     * @param addresses  the addresses
     * @param messageContent the message content
     */
    public void send(String addresses, String messageContent) {
        System.out.println(messageContent);
        this.sentMessage = messageContent;

        if (addresses != null) {
            sendToFile(addresses, messageContent);
        }
    }

    public void sendToFile(String addresses, String messageContent) {
        String path = addresses + "output.txt";

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(path), StandardCharsets.UTF_8))) {
            writer.write(messageContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
