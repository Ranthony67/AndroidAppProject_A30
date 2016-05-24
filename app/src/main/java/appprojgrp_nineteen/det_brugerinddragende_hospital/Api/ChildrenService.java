package appprojgrp_nineteen.det_brugerinddragende_hospital.Api;


import java.util.List;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Child;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Report;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ChildrenService {
    @GET("children")
    Call<List<Child>> getAllChildren();
}
