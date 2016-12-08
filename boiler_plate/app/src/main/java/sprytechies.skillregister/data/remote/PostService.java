package sprytechies.skillregister.data.remote;

import org.json.JSONObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sprytechies.skillregister.data.remote.remote_model.Awrd;
import sprytechies.skillregister.data.remote.remote_model.Cert;
import sprytechies.skillregister.data.remote.remote_model.Cont;
import sprytechies.skillregister.data.remote.remote_model.Edu;
import sprytechies.skillregister.data.remote.remote_model.Exp;
import sprytechies.skillregister.data.remote.remote_model.ExportResume;
import sprytechies.skillregister.data.remote.remote_model.GetProfile;
import sprytechies.skillregister.data.remote.remote_model.Login;
import sprytechies.skillregister.data.remote.remote_model.PermissionBit;
import sprytechies.skillregister.data.remote.remote_model.Person;
import sprytechies.skillregister.data.remote.remote_model.Pro;
import sprytechies.skillregister.data.remote.remote_model.Pub;
import sprytechies.skillregister.data.remote.remote_model.SaveProfilePost;
import sprytechies.skillregister.data.remote.remote_model.User;
import sprytechies.skillregister.data.remote.remote_model.Volun;


/**
 * Created by sprydev5 on 8/11/16.
 */

public interface PostService {

    @FormUrlEncoded
    @POST("people/signin")
    Call<Login> login(@Field("email") String email, @Field("password") String password);
    @POST("people/signup")
    Call<User> createUser(@Body User user);
    @POST("people/{id}/education")
    Call<Edu>post_education(@Path("id") String id, @Query("access_token")String access_token, @Body Edu edu);
    @POST("people/{id}/experience")
    Call<Exp>post_experience(@Path("id") String id, @Query("access_token")String access_token, @Body Exp exp);
    @POST("people/{id}/awards")
    Call<Awrd>post_award(@Path("id") String id, @Query("access_token")String access_token, @Body Awrd awrd);
    @POST("people/{id}/project")
    Call<Pro>post_project(@Path("id") String id, @Query("access_token")String access_token, @Body Pro pro);
    @POST("people/{id}/setContacts")
    Call<Cont>post_contact(@Path("id") String id, @Query("access_token")String access_token, @Body Cont cont);
    @POST("people/{id}/certificate")
    Call<Cert>post_certificate(@Path("id") String id, @Query("access_token")String access_token, @Body Cert cert);
    @POST("people/{id}/publications")
    Call<Pub>post_publications(@Path("id") String id, @Query("access_token")String access_token, @Body Pub pub);
    @POST("people/{id}/volunteer")
    Call<Volun>post_volunteer(@Path("id") String id, @Query("access_token")String access_token, @Body Volun volun);
    @POST("people/{id}/profile")
    Call<SaveProfilePost>post_profile(@Path("id") String id, @Query("access_token")String access_token, @Body SaveProfilePost volun);

    //////////////////////////PUT SERVICES////////////////////////////////////////////

    @PUT("people/{id}/awards/{fk}")
    Call<Awrd> put_awards(@Path("id") String id,@Path("fk")String update_id,@Body Awrd awrd, @Query("access_token")String access_token);
    @PUT("people/{id}/education/{fk}")
    Call<Edu> put_education(@Path("id") String id,@Path("fk")String update_id,@Body Edu edu, @Query("access_token")String access_token);
    @PUT("people/{id}/experience/{fk}")
    Call<Exp> put_experience(@Path("id") String id,@Path("fk")String update_id,@Body Exp exp, @Query("access_token")String access_token);
    @PUT("people/{id}/project/{fk}")
    Call<Pro> put_project(@Path("id") String id,@Path("fk")String update_id,@Body Pro pro, @Query("access_token")String access_token);
    @PUT("people/{id}/setContacts/{fk}")
    Call<Cont> put_contact(@Path("id") String id,@Path("fk")String update_id,@Body Cont cont, @Query("access_token")String access_token);
    @PUT("people/{id}/certificate/{fk}")
    Call<Cert> put_certificate(@Path("id") String id,@Path("fk")String update_id,@Body Cert cert, @Query("access_token")String access_token);
    @PUT("people/{id}/publications/{fk}")
    Call<Pub> put_publication(@Path("id") String id,@Path("fk")String update_id,@Body Pub pub, @Query("access_token")String access_token);
    @PUT("people/{id}/volunteer/{fk}")
    Call<Volun> put_volunteer(@Path("id") String id,@Path("fk")String update_id,@Body Volun volun, @Query("access_token")String access_token);
    @PUT("people/{id}")
    Call<Person>put_user(@Path("id")String id, @Query("access_token")String token, @Body Person person);
    @PUT("people/{id}")
    Call<PermissionBit>put_permission(@Path("id")String id, @Query("access_token")String token, @Body PermissionBit person);


    //////////////////////////GET SERVICES////////////////////////////////////////////

    @GET("people/{id}/awards")
    Call<List<Awrd>> list_award(@Path("id") String user, @Query("access_token")String id);
    @GET("people/{id}/education")
    Call<List<Edu>> list_education(@Path("id") String user,@Query("access_token")String id);
    @GET("people/{id}/experience")
    Call<List<Exp>> list_experience(@Path("id") String user,@Query("access_token")String id);
    @GET("people/{id}/project")
    Call<List<Pro>> list_project(@Path("id") String user,@Query("access_token")String id);
    @GET("people/{id}/publications")
    Call<List<Pub>> list_publication(@Path("id") String user,@Query("access_token")String id);
    @GET("people/{id}/volunteer")
    Call<List<Volun>> list_volunteer(@Path("id") String user,@Query("access_token")String id);
    @GET("people/{id}/certificate")
    Call<List<Cert>> list_certificate(@Path("id") String user,@Query("access_token")String id);
    @GET("people/{id}")
    Call<Person>get_user(@Path("id")String id, @Query("access_token")String token);
    @GET("people/{id}/profile")
    Call<List<GetProfile>>get_profile(@Path("id")String id, @Query("filter")JSONObject filter, @Query("access_token")String token);
    @GET("people/{id}/exportResume")
    Call<ExportResume>export_resume(@Path("id")String id, @Query("template")String template, @Query("onlyPreview")String only, @Query("access_token")String token);

}
