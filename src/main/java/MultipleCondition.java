import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MultipleCondition implements TopCondition {

    @SerializedName("checkType")
    @Expose
    private String typeOfCheck;

    @SerializedName("multiConditions")
    @Expose
    private List<? extends TopCondition> multipleConditions;

    public String getTypeOfCheck() {
        return typeOfCheck;
    }

    public void setTypeOfCheck(String typeOfCheck) {
        this.typeOfCheck = typeOfCheck;
    }

    public List<? extends TopCondition> getMultipleConditions() {
        return multipleConditions;
    }

    public void setMultipleConditions(List<? extends TopCondition> multipleConditions) {
        this.multipleConditions = multipleConditions;
    }
}