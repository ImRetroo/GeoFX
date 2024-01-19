package es.yorman.dad.geofx.api;

import java.util.concurrent.TimeUnit;

import es.yorman.dad.geofx.api.model.Ipify;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IPIFYService {
	
	private static final String BASE_URL = "https://api.ipify.org/";
	
	private IPIFYInterface service;
	
	public IPIFYService() {
		ConnectionPool pool = new ConnectionPool(1, 5, TimeUnit.SECONDS);

		OkHttpClient client = new OkHttpClient.Builder()
                .connectionPool(pool)
                .build();
		
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.client(client)
				.build();
		
		service = retrofit.create(IPIFYInterface.class);
	}
	
	public Ipify getIP() throws Exception {
		Call<Ipify> call = service.getIP();
		Response<Ipify> response = call.execute();
		return response.body();
	}
}