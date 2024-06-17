package biblioteca;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado na porta " + PORT);
            Gerenciador gerenciador = new Gerenciador();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket, gerenciador)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Gerenciador gerenciador;

    public ClientHandler(Socket clientSocket, Gerenciador gerenciador) {
        this.clientSocket = clientSocket;
        this.gerenciador = gerenciador;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String request;
            while ((request = in.readLine()) != null) {
                String[] parts = request.split(";");
                String command = parts[0];

                String response;
                switch (command.toUpperCase()) {
                    case "LISTAR":
                        response = gerenciador.listarLivros().toString();
                        break;
                    case "ALUGAR":
                        response = gerenciador.alugarLivro(parts[1]);
                        break;
                    case "DEVOLVER":
                        response = gerenciador.devolverLivro(parts[1]);
                        break;
                    case "CADASTRAR":
                        response = gerenciador.cadastrarLivro(parts[1], parts[2], parts[3], Integer.parseInt(parts[4]));
                        break;
                    default:
                        response = "Comando inválido.";
                        break;
                }
                out.println(response);
                out.println("END");  // Marcador de fim de mensagem
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
