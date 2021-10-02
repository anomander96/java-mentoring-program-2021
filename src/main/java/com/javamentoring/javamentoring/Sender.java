package com.javamentoring.javamentoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Sender {

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage(String destination) {
        Scanner scanner = new Scanner(System.in);
        String order = "";
        System.out.println("Fill in your name:");
        String userName = scanner.nextLine();
        System.out.println("You order liquids or countable item? Enter a preferable type of item:");
        String orderType = scanner.nextLine();
        if (orderType.equalsIgnoreCase("liquid")) {
            System.out.println("Enter an item which you need: ");
            String itemName = scanner.nextLine();
            System.out.println("Please enter a volume which you need");
            String volume = scanner.nextLine();
            order = "Dear " + userName + ", your ordered " + itemName + "\n" +
            "Order total is: " + volume + "L";
            System.out.println("Thank you for your order!");
        } else if (orderType.equalsIgnoreCase("countable")) {
            System.out.println("Enter an item which you need: ");
            String itemName = scanner.nextLine();
            System.out.println("Please enter a number of items:");
            String number = scanner.nextLine();
            order = "Dear " + userName + ", your ordered " + itemName + "\n" +
            "Order total is: " + number + " units";
            System.out.println("Thank you for your order!");
        } else {
            System.out.println("You entered an invalid value");
        }
        jmsTemplate.convertAndSend(destination, order);
    }
}
