package padilha.carolynne.ex_4all.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Postagem {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("cidade")
    @Expose
    private String cidade;

    @SerializedName("bairro")
    @Expose
    private String bairro;

    @SerializedName("urlFoto")
    @Expose
    private String urlFoto;

    @SerializedName("urlLogo")
    @Expose
    private String urlLogo;

    @SerializedName("titulo")
    @Expose
    private String titulo;

    @SerializedName("telefone")
    @Expose
    private String telefone;

    @SerializedName("texto")
    @Expose
    private String texto;

    @SerializedName("endereco")
    @Expose
    private String endereco;

    @SerializedName("latitude")
    @Expose
    private Double latitude;

    @SerializedName("longitude")
    @Expose
    private Double longitude;

    @SerializedName("comentarios")
    @Expose
    private List<Comentario> comentarios;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getUrlFoto() { return urlFoto; }
    public void setUrlFoto(String urlFoto) { this.urlFoto = urlFoto; }

    public String getUrlLogo() { return urlLogo; }
    public void setUrlLogo(String urlLogo) { this.urlLogo = urlLogo; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public List<Comentario> getComentarios() { return comentarios; }
    public void setComentarios(List<Comentario> comentarios) { this.comentarios = comentarios; }

}
