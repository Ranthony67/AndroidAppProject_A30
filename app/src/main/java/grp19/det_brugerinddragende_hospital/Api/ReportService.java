package grp19.det_brugerinddragende_hospital.Api;

import java.util.List;

import grp19.det_brugerinddragende_hospital.Models.Report;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReportService {
    @POST("reports")
    Call<Report> create(@Body Report report);

    @GET("children/{id}/reports")
    Call<List<Report>> getAllReports(@Path("id") int childId);
}
