package biblioteca;

import org.json.JSONObject;


public class Livro {
    private String titulo;
    private String autor;
    private String genero;
    private int exemplares;

    public Livro(String titulo, String autor, String genero, int exemplares) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.exemplares = exemplares;
    }

    // Getters e Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public int getExemplares() { return exemplares; }
    public void setExemplares(int exemplares) { this.exemplares = exemplares; }

    @Override
    public String toString() {
        return String.format("Título: %s, Autor: %s, Gênero: %s, Exemplares: %d", titulo, autor, genero, exemplares);
    }

    public static Livro fromJSON(JSONObject json) {
        return new Livro(
            json.getString("titulo"),
            json.getString("autor"),
            json.getString("genero"),
            json.getInt("exemplares")
        );
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("titulo", titulo);
        json.put("autor", autor);
        json.put("genero", genero);
        json.put("exemplares", exemplares);
        return json;
    }
}
