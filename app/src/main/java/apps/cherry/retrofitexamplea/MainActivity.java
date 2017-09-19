package apps.cherry.retrofitexamplea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import apps.cherry.retrofitexamplea.data.entities.Repo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // Trailing slash is needed
    public static final String BASE_URL = "https://api.github.com/";
    private static final String TAG ="MainActivity" ;


    EditText edt_user;


    RecyclerView rv_repositories;
    List<Repo> repositories;
    RepositoryAdapter repoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_user            = (EditText) findViewById(R.id.a_main_et);

        repositories        = new ArrayList<>();
        repoAdapter         = new RepositoryAdapter(repositories);
        rv_repositories     = (RecyclerView) findViewById(R.id.a_main_recycler);
        rv_repositories.setLayoutManager(new LinearLayoutManager(this));
        rv_repositories.setAdapter(repoAdapter);

    }

    public void getInfo(View view) {
        Log.d(TAG, "getInfo: ");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<Repo>> repos = service.listRepos(edt_user.getText().toString());
        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {

                List <Repo>  result = response.body();

                for(Repo repo: result){
                    Log.d(TAG,"response "+ repo);
                }

                repositories.clear();
                repositories.addAll(result);
                repoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });


    }
}
