package padilha.carolynne.ex_4all.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comentario {

    @SerializedName("urlFoto")
    @Expose
    private String urlFoto;

    @SerializedName("nome")
    @Expose
    private String nome;

    @SerializedName("nota")
    @Expose
    private Integer nota;

    @SerializedName("titulo")
    @Expose
    private String titulo;

    @SerializedName("comentario")
    @Expose
    private String comentario;

    public String getUrlFoto() { return urlFoto; }
    public void setUrlFoto(String urlFoto) { this.urlFoto = urlFoto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario;}

}