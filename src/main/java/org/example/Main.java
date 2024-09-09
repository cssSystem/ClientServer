package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            try (
                    Socket client = new Socket("netology.homework", Server.port);
                    PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            ) {
                while (!client.isOutputShutdown()) {
                    if (reader.ready()) {
                        System.out.println(reader.readLine());
                        String val = in.nextLine();
                        System.out.println("____________");
                        writer.println(val);

                    }
                }

            } catch (UnknownHostException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}