package appprojgrp_nineteen.det_brugerinddragende_hospital.Api;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Report;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReportService {
    @POST("reports")
    Call<Report> create(@Body Report report);
}
