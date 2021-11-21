import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Validation {

    @SerializedName("topLevelCheck")
    @Expose
    private TopCondition topCondition;

    public TopCondition getTopCondition() {
        return topCondition;
    }

    public void setTopCondition(TopCondition topCondition) {
        this.topCondition = topCondition;
    }
}
