package misc;

import decurtis.dxp.batchjob.parent.common.entity.referencedata.Port;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HashMapTesting {

    static Map<String, String> map = new HashMap<>();

    static {
        map.put("1", "1");
        map.put("2", "2");
    }

    public static void main(String[] args) {

        String result = map.computeIfAbsent("3", key -> {
            Parent parent = new Parent();
            return Optional.ofNullable(parent).map(Parent::getChild).map(Child::getName).orElseThrow(() -> new RuntimeException("PORT_NOT_FOUND + portCode"));
        });

        System.out.println(result);
    }
}

class Parent{

    Child child;
    Sibling sibling;

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Sibling getSibling() {
        return sibling;
    }

    public void setSibling(Sibling sibling) {
        this.sibling = sibling;
    }
}

class Child {

    String name;
    Sibling sibling;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sibling getSibling() {
        return sibling;
    }

    public void setSibling(Sibling sibling) {
        this.sibling = sibling;
    }
}

class Sibling{

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}