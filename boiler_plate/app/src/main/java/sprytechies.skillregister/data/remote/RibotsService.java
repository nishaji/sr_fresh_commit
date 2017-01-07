package sprytechies.skillregister.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.AutoValueGsonTypeAdapterFactory;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import sprytechies.skillregister.data.model.AwardInsert;


public interface RibotsService {

    String ENDPOINT = "http://sr.api.sprytechies.net:3003/api/";

    @GET("people/awards/58296e1c1c91b95people/awards/58296e1c1c91b9d8e97cf2b?access_token=QctiMWTmEjYmJcdwnFr9RW3PNCTTZnAEZE8ctdiWhm55cOmC8F54wTRugxhFXoA3")
    Observable<List<AwardInsert>> getAwards();
    /******** Helper class that sets up a new services *******/
    class Creator {

        public static RibotsService newRibotsService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(new AutoValueGsonTypeAdapterFactory())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RibotsService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(RibotsService.class);
        }
    }
}
