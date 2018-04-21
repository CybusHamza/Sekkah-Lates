package in.radioactivegames.sekkah.data.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AntiSaby on 1/8/2018.
 * www.radioactivegames.in
 */

public class LoginRequest
{
    @Expose
    @SerializedName("userId")
    public String username;

    @Expose
    @SerializedName("password")
    public String password;
}
