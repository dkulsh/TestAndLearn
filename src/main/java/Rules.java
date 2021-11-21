import java.util.List;

public class Rules {

    private MultiCheck multiChk;
    private List<Condition> conditions;

    public class Condition{

        private String variable;
        private SingleCheck check;
        private String value;

        private List<Condition> subConditions;
    }

    public enum SingleCheck{
        NOT_EQUALS,
        EQUALS,
        LESS_THAN,
        GREATER_THAN,
        NOT
    }

    public enum MultiCheck {
        AND,
        OR
    }
}
