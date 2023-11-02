package RMI_ticket;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

public interface TicketBookingService extends Remote {
//	định nghĩa các phương thức (bán vé , lấy thông tin , lưu thông tin )
	void addTicket(String train, String trainCarriage, String source, String destination, String departureTime, Date date, int seatCount, double seatPrice, String customerName, String customerID) throws RemoteException;
	String getTicketInfo() throws RemoteException;
	public String searchTicketByID(String IDTicket) throws RemoteException;
	void updateTicket(String IDTicket, String train, String trainCarriage, String source, String destination, String departureTime, Date date, int seatCount, double seatPrice, String customerName, String customerID) throws RemoteException;
	void deleteTicket(String IDTicket) throws RemoteException;
	
}
