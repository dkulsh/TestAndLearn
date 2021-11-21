package OfficeUtil;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MethodChainingNullCheck {

    public static void main(String[] args) {
        Optional.ofNullable(new Outer())
                .map(out -> out.getNested())
                .map(nest -> nest.getInner())
                .map(in -> in.getFoo())
                .ifPresent(foo -> System.out.println("foo: " + foo));

        Optional.ofNullable(new OuterInit())
                .map(out -> out.getNestedInit())
                .map(nest -> nest.getInnerInitList())
                .map(list -> list.get(0))
                .map(in -> in.getFoo())
//                .filter(StringUtils::isNumeric)
                .ifPresent(foo -> System.out.println("foo: " + foo));
    }
}

class Outer {
    Nested nested;
    Nested getNested() {
        return nested;
    }
}
class Nested {
    Inner inner;
    Inner getInner() {
        return inner;
    }
}
class Inner {
    String foo = "yeah!";
    String getFoo() {
        return foo;
    }
}

class OuterInit {
    NestedInit nested = new NestedInit();
    NestedInit getNestedInit() {
        return nested;
    }
}
class NestedInit {
    List<InnerInit> innerInitList = new ArrayList<>();
    InnerInit inner = new InnerInit();
    InnerInit getInnerInit() {
        return inner;
    }

    public List<InnerInit> getInnerInitList() {
        return innerInitList;
    }

    public void setInnerInitList(List<InnerInit> innerInitList) {
        this.innerInitList = innerInitList;
    }
}
class InnerInit {
    String foo = "yeah!";
    String integer = "100";

    String getFoo() {
        return foo;
    }

    public String getInteger() {
        return integer;
    }

    public void setInteger(String integer) {
        this.integer = integer;
    }
}