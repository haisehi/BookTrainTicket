package RMI_ticket;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) throws UnknownHostException {
        try {
            // Tạo một thanh ghi ở phía server
            Registry registry = LocateRegistry.createRegistry(1099);
            // Tạo một TicketTrain
            TicketBookingServiceImpl ticketBookingService = new TicketBookingServiceImpl();
            // Đăng ký object trên thanh ghi
            registry.rebind("TicketBookingService", ticketBookingService);
            System.out.println("Server is running...");
            
            InetAddress localHost = InetAddress.getLocalHost();
			System.out.println("Server is running...");
			System.out.println("Server IP Address: " + localHost.getHostAddress());

            // Thêm mã để hiển thị tên của client khi kết nối
            RemoteServer.setLog(System.out);

            // Thêm mã để lắng nghe kết nối của client và hiển thị thông tin
            UnicastRemoteObject.exportObject(new RemoteServer() {
                public void clientCall(int param) throws RemoteException {
                    try {
                        String clientHost = RemoteServer.getClientHost();
                        System.out.println("Client connected from: " + clientHost);
                    } catch (ServerNotActiveException e) {
                        System.err.println("Error getting client information: " + e.getMessage());
                    }
                }
            }, 0);
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
