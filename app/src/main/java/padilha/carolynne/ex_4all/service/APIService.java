package padilha.carolynne.ex_4all.service;

import padilha.carolynne.ex_4all.model.Opcao;
import padilha.carolynne.ex_4all.model.Postagem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {
    @GET("tarefa")
    Call<Opcao> getOpcoes();

    @GET("tarefa/{id}")
    Call<Postagem> getPostagens(@Path("id") int id);
}
