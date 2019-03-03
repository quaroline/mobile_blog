package padilha.carolynne.ex_4all.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import padilha.carolynne.ex_4all.R;
import padilha.carolynne.ex_4all.adapter.ComentarioAdapter;
import padilha.carolynne.ex_4all.model.Postagem;
import padilha.carolynne.ex_4all.service.APIService;
import padilha.carolynne.ex_4all.service.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaPrincipal extends AppCompatActivity implements OnMapReadyCallback {

    public static final int ID_TAREFA = 1;

    private GoogleMap mMap;
    private Button btnLigar, btnFavorito, btnServico, btnComentario, btnEndereco;
    private TextView tvTitulo, tvTexto, tvLocalizacao;
    private ImageView ivBackground, ivLogo, ivUser;
    private Postagem postagem = null;
    private ActionBar actionBar;
    private RecyclerView rvComentario;
    private SupportMapFragment mapFragment;
    private ScrollView scrollView;
    private ProgressBar progressBar;
    private AlertDialog.Builder msgError;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        msgError = new AlertDialog.Builder(TelaPrincipal.this);

        actionBar = getSupportActionBar();

        progressBar = findViewById(R.id.tp_progressbar);

        rvComentario = findViewById(R.id.rv_comentarios);
        scrollView = findViewById(R.id.tp_scrollview);

        btnLigar = findViewById(R.id.tp_btn_ligar);
        btnFavorito = findViewById(R.id.tp_btn_favorito);
        btnServico = findViewById(R.id.tp_btn_servico);
        btnComentario = findViewById(R.id.tp_btn_comentario);
        btnEndereco = findViewById(R.id.tp_btn_localizacao);

        ivBackground = findViewById(R.id.tp_iv_background);
        ivLogo = findViewById(R.id.tp_iv_logo);
        ivUser = findViewById(R.id.comentario_rv_iv_user);

        tvTitulo = findViewById(R.id.tp_tv_titulo);
        tvTexto = findViewById(R.id.tp_tv_texto);
        tvLocalizacao = findViewById(R.id.tp_tv_localizacao);

        progressBar.setVisibility(ProgressBar.VISIBLE);
        progressBar.setIndeterminate(false);

        carregarJSON();

        swipeRefreshLayout = findViewById(R.id.tp_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregarJSON();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + postagem.getTelefone());
                Intent it = new Intent(Intent.ACTION_DIAL,uri);
                startActivity(it);
            }
        });

        btnServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TelaPrincipal.this, TelaServico.class);
                TelaPrincipal.this.startActivity(i);
            }
        });

        btnComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        btnEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaEndereco();
            }
        });

        btnFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void alertaEndereco() {
        AlertDialog.Builder msg = new AlertDialog.Builder(TelaPrincipal.this);

        msg.setTitle(getResources().getText(R.string.address));
        msg.setMessage(postagem.getEndereco() + "\n" + postagem.getBairro() + " - " + postagem.getCidade());
        msg.setNeutralButton( getResources().getText(R.string.alert_botao), null);

        msg.show();
    }

    private void alterarTextos() {
        actionBar.setTitle(postagem.getCidade() + " - " + postagem.getBairro());

        tvTitulo.setText(postagem.getTitulo());
        tvTexto.setText(postagem.getTexto());
        tvLocalizacao.setText(postagem.getEndereco());
    }

    private void inicializarImagens() {
        //Inicialização de imagens de tela principal.
        Picasso.get()
                .load(postagem.getUrlLogo())
                .placeholder(R.drawable.placeholder_logo)
                .into(ivLogo);

        Picasso.get()
                .load(postagem.getUrlFoto())
                .placeholder(R.drawable.placeholder)
                .into(ivBackground);
    }

    private void inicializarRecyclerView() {
        ComentarioAdapter adapter = new ComentarioAdapter(this, postagem.getComentarios());

        adapter.notifyDataSetChanged();

        LinearLayoutManager manager = new LinearLayoutManager(this);

        rvComentario.setLayoutManager(manager);
        rvComentario.setHasFixedSize(true);
        rvComentario.setAdapter(adapter);
    }


    private void carregarJSON() {
        //Método assíncrono de resposta do Retrofit referente a lista List<Postagem>.
        try{
            Client client = new Client();
            APIService apiService = client.getClient().create(APIService.class);
            Call<Postagem> call = apiService.getPostagens(ID_TAREFA);
            call.enqueue(new Callback<Postagem>() {
                @Override
                public void onResponse(Call<Postagem> call, Response<Postagem> response) {
                    postagem = response.body();

                    mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(TelaPrincipal.this);

                    alterarTextos();
                    inicializarRecyclerView();
                    inicializarImagens();

                    progressBar.setVisibility(ProgressBar.GONE);
                }

                @Override
                public void onFailure(Call<Postagem> call, Throwable t) {
                    msgError.setTitle(getResources().getText(R.string.connection_error));
                    msgError.setMessage(getResources().getText(R.string.connection_error_details));
                    msgError.setNeutralButton(getResources().getText(R.string.alert_botao), null);

                    msgError.show();
                }
            });

        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Marcadores do Google Maps.
        mMap = googleMap;

        LatLng latLng = new LatLng(postagem.getLatitude(), postagem.getLongitude());

        mMap.addMarker(new MarkerOptions().position(latLng).title(postagem.getCidade()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}
