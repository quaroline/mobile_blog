package padilha.carolynne.ex_4all.controller;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;

import android.widget.Toast;

import java.util.List;

import padilha.carolynne.ex_4all.R;
import padilha.carolynne.ex_4all.adapter.MenuAdapter;
import padilha.carolynne.ex_4all.model.Opcao;
import padilha.carolynne.ex_4all.service.APIService;
import padilha.carolynne.ex_4all.service.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaInicial extends AppCompatActivity {

    private RecyclerView rvMenu;
    private AlertDialog.Builder msgError;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        msgError = new AlertDialog.Builder(TelaInicial.this);

        rvMenu = findViewById(R.id.ti_rv_menu);
        carregarJSON();

        swipeRefreshLayout = findViewById(R.id.ti_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregarJSON();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void carregarJSON() {
        //Método assíncrono de resposta do Retrofit referente a lista inicial.
        try{
            Client client = new Client();
            APIService apiService = client.getClient().create(APIService.class);
            Call<Opcao> call = apiService.getOpcoes();
            call.enqueue(new Callback<Opcao>() {
                @Override
                public void onResponse(Call<Opcao> call, Response<Opcao> response) {
                    List<String> opcoes = response.body().getOpcoes();
                    inicializarRecyclerView(opcoes);
                }

                @Override
                public void onFailure(Call<Opcao> call, Throwable t) {
                    msgError.setTitle(getResources().getText(R.string.connection_error));
                    msgError.setMessage(getResources().getText(R.string.connection_error_details));
                    msgError.setNeutralButton(getResources().getText(R.string.alert_botao), null);

                    msgError.show();
                }
            });

        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void inicializarRecyclerView(List<String> opcoes) {
        MenuAdapter adapter = new MenuAdapter(this, opcoes);

        adapter.notifyDataSetChanged();

        LinearLayoutManager manager = new LinearLayoutManager(this);

        rvMenu.setLayoutManager(manager);
        rvMenu.setHasFixedSize(true);
        rvMenu.setAdapter(adapter);
    }
}
