package Coding.personal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Webomates {

    public static void main(String[] args) throws FileNotFoundException {

        String s = "In covid times we should wear mask";
        Map<Character, Integer> map = new HashMap<>();

        List<Character> vovels = List.of('a', 'e', 'i', 'o', 'u');
        for (int i = 0; i < s.length(); i++) {

//            if (vovels.contains(s.charAt(i)) || ) { continue; }

            if (map.containsKey(s.charAt(i))) {
                Integer val = map.get(s.charAt(i));
                map.put(s.charAt(i), val + 1);
            }
            else { map.put(s.charAt(i), 1); }
        }

        System.out.println(map);


//        Facade facade = new Facade();
//        facade.turnOnCar();
//        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("")));
    }
}

class Facade {

    void turnOnCar() {

        Fuel.injectfuel();
        Air.flowAir();
        Air.filterAir();
        Oil.oilTransmission();
        Coolant.coolantTransmission();
    }

    void turnOffCar() {

        Fuel.stoFuel();
        Air.stopAir();
        Oil.stopOilTransmission();
        Coolant.stopCoolantTransmission();
    }
}

interface sendCommunication{
    void sendMessage();
}

//class SMSSender implements sendCommunication {
//
//    Facade facade;
//
//    public SMSSender(Facade facade) {
//        this.facade = facade;
//    }
//
//    @Override
//    public void sendMessage() {
//        facade.turnOnCar();
//        sendInternetMessage;
//    }
//}
//
//class MessageFacade extends Facade {
//
//    void turnOnCar() {
//
//        super.turnOnCar();
//        switch (internet) {
//
//            case Boolean.TRUE:
//                sendSMS
//
//            case Boolean.FALSE:
//                doNotSendSMS
//        }
//    }
//}

class Fuel {

    static void injectfuel() {}

    public static void stoFuel() {
    }
}

class Air {

    static void flowAir() {}

    static void filterAir() {}

    public static void stopAir() {
    }
}

class Oil {

    static void oilTransmission() {}

    public static void stopOilTransmission() {
    }
}

class Coolant {

    static void coolantTransmission() {}

    public static void stopCoolantTransmission() {
    }
}