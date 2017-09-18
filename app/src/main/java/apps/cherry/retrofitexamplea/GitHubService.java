package apps.cherry.retrofitexamplea;

import java.util.List;

import apps.cherry.retrofitexamplea.data.entities.Repo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by anelelizabethcervantes on 9/18/17.
 */

public interface GitHubService {
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
}