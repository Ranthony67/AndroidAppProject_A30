package grp19.det_brugerinddragende_hospital.Api;


import java.util.List;

import grp19.det_brugerinddragende_hospital.Models.Child;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ChildrenService {
    @GET("children")
    Call<List<Child>> getAllChildren();
}
