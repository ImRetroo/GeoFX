package es.yorman.dad.geofx.api;

import java.util.concurrent.TimeUnit;

import es.yorman.dad.geofx.api.model.Ipapi;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IPAPIService {
	
	private static final String BASE_URL = "https://ipapi.com/";
	
	private IPAPInterface service;
	
	public IPAPIService() {
		ConnectionPool pool = new ConnectionPool(1, 5, TimeUnit.SECONDS);

		OkHttpClient client = new OkHttpClient.Builder()
                .connectionPool(pool)
                .build();
		
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.client(client)
				.build();
		
		service = retrofit.create(IPAPInterface.class);
	}
	
	public Ipapi getIPData(String ip) throws Exception {
		Call<Ipapi> call = service.getIPData(ip);
		Response<Ipapi> response = call.execute();
		return response.body();
	}
}