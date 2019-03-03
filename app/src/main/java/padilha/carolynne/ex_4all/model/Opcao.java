package padilha.carolynne.ex_4all.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Opcao {

    @Expose
    @SerializedName("lista")
    List<String> opcoes;

    public List<String> getOpcoes() { return opcoes; }
    public void setOpcoes(List<String> opcoes) { this.opcoes = opcoes; }
}
