package biblioteca;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            Scanner scanner = new Scanner(System.in);
            String command;

            while (true) {
                System.out.println("Digite o comando (LISTAR, ALUGAR, DEVOLVER, CADASTRAR) ou SAIR para encerrar:");
                command = scanner.nextLine();

                if (command.equalsIgnoreCase("SAIR")) {
                    break;
                }

                out.println(command);

                // Para comandos que podem ter múltiplas linhas na resposta, vamos ler até o fim
                StringBuilder response = new StringBuilder();
                String line;
                while (!(line = in.readLine()).equals("END")) {
                    response.append(line).append("\n");
                }
                System.out.println(response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
