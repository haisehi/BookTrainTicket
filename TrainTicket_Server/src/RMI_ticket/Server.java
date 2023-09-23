package RMI_ticket;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	public static void main(String[] args) {
        try {
//          tạo một thanh ghi ở phía server
            Registry registry = LocateRegistry.createRegistry(1099);
//        	tạo một TicketTrain
            TicketBookingServiceImpl ticketBookingService = new TicketBookingServiceImpl();
//          Đăng ký object trên thanh ghi
            registry.rebind("TicketBookingService", ticketBookingService);
            System.out.println("Server is running...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
