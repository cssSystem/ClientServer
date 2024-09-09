package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int port = 8080;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {// порт можете выбрать любой в доступном диапазоне 0-65536. Но чтобы не нарваться на уже занятый - рекомендуем использовать около 8080
            boolean a0 = false;
            String res = "";
            int a = 0;
            String[] c = {
                    "Как твое имя?",
                    "Ты ребенок? (да/нет)",
                    res
            };
            String[] b = new String[c.length];

            try (
                    Socket clientSocket = serverSocket.accept(); // ждем подключения
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                System.out.println("New connection accepted");
                while (!clientSocket.isClosed()) {
                    if (!a0) {
                        out.println(c[a]);
                        a0 = true;
                    } else {
                        if (in.ready()) {
                            b[a] = in.readLine();
                            if (a == 1) {
                                switch (b[a].toLowerCase()) {
                                    case "да":
                                        res = "Добро пожаловать в детскую зону, " + b[0] + "! Давайте играть!";
                                        c[a + 1] = res;
                                        break;
                                    case "нет":
                                        res = "Добро пожаловать в зону для взрослых, " + b[0] + "! Хорошего отдыха или удачного рабочего дня!";
                                        c[a + 1] = res;
                                        break;
                                    default:
                                        a--;
                                        break;
                                }
                            }
                            a0 = false;
                            a++;
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
