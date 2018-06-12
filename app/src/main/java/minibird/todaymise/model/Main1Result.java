package minibird.todaymise.model;

import com.google.gson.annotations.SerializedName;

public class Main1Result {
    @SerializedName("yesterday")
    public String yesterday;

    @SerializedName("today")
    public String today;

    @SerializedName("tomorrow")
    public String tomorrow;

    @SerializedName("the_day_of_after_tomorrow")
    public String three;
}
