package com.epam.ld.module2.testing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Mail server class.
 */
public class MailServer {

    /**
     * Send notification.
     *
     * @param addresses  the addresses
     * @param messageContent the message content
     */
    public void send(String addresses, String messageContent) {
        System.out.println(messageContent);
    }

    public void sendToFile(String fullFileName, String messageContent) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullFileName))) {
            writer.write(messageContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
