package biblioteca;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Gerenciador {
    private List<Livro> livros;
    private static final String FILE_PATH = "livros.json";

    public Gerenciador() throws IOException {
        loadLivros();
    }

    private void loadLivros() throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        JSONArray jsonArray = new JSONObject(content.toString()).getJSONArray("livros");
        livros = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            livros.add(Livro.fromJSON(jsonArray.getJSONObject(i)));
        }
    }

    private void saveLivros() throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (Livro livro : livros) {
            jsonArray.put(livro.toJSON());
        }
        JSONObject json = new JSONObject();
        json.put("livros", jsonArray);

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            writer.write(json.toString());
        }
    }

    public List<Livro> listarLivros() {
        return livros;
    }

    public synchronized String alugarLivro(String titulo) throws IOException {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo) && livro.getExemplares() > 0) {
                livro.setExemplares(livro.getExemplares() - 1);
                saveLivros();
                return "Livro alugado com sucesso.";
            }
        }
        return "Livro não disponível para aluguel.";
    }

    public synchronized String devolverLivro(String titulo) throws IOException {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                livro.setExemplares(livro.getExemplares() + 1);
                saveLivros();
                return "Livro devolvido com sucesso.";
            }
        }
        return "Livro não encontrado.";
    }

    public synchronized String cadastrarLivro(String titulo, String autor, String genero, int exemplares) throws IOException {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                return "Livro já cadastrado.";
            }
        }
        livros.add(new Livro(titulo, autor, genero, exemplares));
        saveLivros();
        return "Livro cadastrado com sucesso.";
    }
}
