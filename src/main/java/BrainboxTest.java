//import brainboxes.io.connection.IConnection;
//import brainboxes.io.connection.TCPConnection;
//import brainboxes.io.device.EDDevice;
//import brainboxes.io.device.IIOLineInterface;
//import brainboxes.io.device.IOLine;
//import brainboxes.io.protocol.ASCIIProtocol;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class BrainboxTest  {

//    public static void main(String[] args) {
//        // Setup a new TCPConnection to connect to a device with the
//        // default static IP address and default TCP Port number 9500
//        IConnection tcpConnection = new TCPConnection("192.168.9.121", 9500);
//
//        // Create a new EDDevice object called ED using
//        // the TCP connection and the ASCII Protocol
//        EDDevice ed = new EDDevice(tcpConnection, new ASCIIProtocol());
//
//        // Set up a new String called response
//        String response = "";
//
//        IIOLineInterface interface1 = new IIOLineInterface() {
//            @Override
//            public void getAllLineStates() {
//                System.out.println("getAllLineStates from interface 1");
//            }
//
//            @Override
//            public void setLineState(IOLine ioLine, int i) {
//                System.out.println("setLineState from interface 1");
//            }
//
//            @Override
//            public void getAllInputLowLatchedStatus() {
//                System.out.println("getAllInputLowLatchedStatus from interface 1");
//            }
//
//            @Override
//            public void getAllInputHighLatchedStatus() {
//                System.out.println("getAllInputHighLatchedStatus from interface 1");
//            }
//        };
//
//        // Initiate the connection to the ED Device
//        ed.Connect();
//
//        //Check if Connection was iniated successfully
//        if(ed.IsConnected()){
//
//            // Send the ACSII command to read the name of the device
//            // and save the response to the string called response
//            response = ed.SendCommand("$01M");
//
//            ed.SendCommand("$016");
//            ed.SendCommand("@01");
//
//            IOLine ioLine1 = new IOLine(1, 1, IOLine.IODirection.Input, IOLine.IOType.Digital, interface1);
//            IOLine ioLine2 = new IOLine(2, 2, IOLine.IODirection.Output, IOLine.IOType.Digital, interface1);
//
//            IOLine ioLine3 = new IOLine(3, 3, IOLine.IODirection.Input, IOLine.IOType.Analogue, interface1);
//            IOLine ioLine4 = new IOLine(4, 4, IOLine.IODirection.Output, IOLine.IOType.Analogue, interface1);
//
//            IOLine ioLine5 = new IOLine(5, 5, IOLine.IODirection.Input, IOLine.IOType.Relay, interface1);
//            IOLine ioLine6 = new IOLine(6, 6, IOLine.IODirection.Output, IOLine.IOType.Relay, interface1);
//
//            List<IOLine> ioList = List.of(ioLine1, ioLine2, ioLine3, ioLine4, ioLine5, ioLine6);
//            ed._ioLines = ioList;
//
//        }
//
//        Scanner scanner = new Scanner(new InputStreamReader(System.in));
//        System.out.println("Please enter your input: ");
////        String input = scanner.nextLine();
//        // Print out the response to the console
////        System.out.println("Reponse: " + response);
//
//        // Close the connection to the ED Device
////        ed.Disconnect();
//    }
}
