package es.yorman.dad.geofx.api;

import es.yorman.dad.geofx.api.model.Ipify;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IPIFYInterface {
	
	@GET("?format=json")
	public Call<Ipify> getIP();

}