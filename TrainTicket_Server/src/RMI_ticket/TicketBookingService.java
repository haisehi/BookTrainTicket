package RMI_ticket;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface TicketBookingService extends Remote {
//	định nghĩa các phương thức (bán vé , lấy thông tin , lưu thông tin )
	void addTicket(
			String train,
			String trainCarriage,
			String source,
			String destination,
			String departureTime,
			String Date,
			int seatCount,
			double seatPrice,
			String customerName,
			String customerID) throws RemoteException;
    String getTicketInfo() throws RemoteException;
	void saveToFile() throws RemoteException;
	String readFile() throws RemoteException;
	String searchTicketByID(String IDTicket) throws RemoteException;
	
}
