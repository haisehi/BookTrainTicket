package RMI_ticket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;



public class TicketBookingServiceImpl extends UnicastRemoteObject implements TicketBookingService {
    public ArrayList<Ticket> tickets;

    protected TicketBookingServiceImpl() throws RemoteException {
        super();
        tickets = new ArrayList<>();
    }

    @Override
    public void addTicket(String train, String trainCarriage, String source, String destination, String departureTime,String Date, int seatCount, double seatPrice, String customerName, String customerID) throws RemoteException {
    	String IDTicket = train + trainCarriage + seatCount + source + Date;
        // Kiểm tra xem IDTicket đã tồn tại trong danh sách vé hay không
        boolean ticketExists = false;
        for (Ticket existingTicket : tickets) {
            if (existingTicket.getIDTicket().equals(IDTicket)) {
                ticketExists = true;
                break;
            }
        }
        if (!ticketExists) {
            // Nếu IDTicket chưa tồn tại, thêm nó vào danh sách vé
            Ticket ticket = new Ticket(IDTicket,train, trainCarriage,source,destination, departureTime, Date, seatCount, seatPrice, customerName, customerID);
            tickets.add(ticket);
            // Ghi thông tin vé vào tệp
            //saveToFile();
        } else {
            System.out.println("IDTicket already exists. Cannot add duplicate.");
        }

    }


    @Override
    public String getTicketInfo() throws RemoteException {
        StringBuilder info = new StringBuilder();
        for (Ticket ticket : tickets) {
            info.append(ticket.toString()).append("\n");
        }
        return info.toString();
    }
    
    @Override
    public String readFile() throws RemoteException {
        StringBuilder fileContent = new StringBuilder();
        try {
            FileReader fileReader = new FileReader("tickets.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return fileContent.toString();
    }

    @Override
    public void saveToFile() throws RemoteException {
        try {
            FileWriter fileWriter = new FileWriter("tickets.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (Ticket ticket : tickets) {
            	printWriter.print("ID Ticket: " + ticket.getIDTicket() +",");
                printWriter.print("Train: " + ticket.getTrain() +",");
                printWriter.print("Train Carriage: " + ticket.getTrainCarriage() +",");
                printWriter.print("Source: " + ticket.getSource() +",");
                printWriter.print("Destination: " + ticket.getDestination() +",");
                printWriter.print("Departure Time: " + ticket.getDepartureTime() +",");
                printWriter.print("Date: " + ticket.getDate() +",");
                printWriter.print("Seat Count: " + ticket.getSeatCount() +",");
                printWriter.print("Seat Price: " + ticket.getSeatPrice() +"VND,");
                printWriter.print("Customer Name: " + ticket.getCustomerName() +",");
                printWriter.println("Customer ID: " + ticket.getCustomerID() +";");
                printWriter.print("----------------------------------------------------");
                printWriter.print("----------------------------------------------------");
                printWriter.println("----------------------------------------------------");
                
            }

            printWriter.close();
            fileWriter.close();

            System.out.println("Ticket information saved to tickets.txt");
        } catch (IOException e) {
            System.err.println("Error saving ticket information to file: " + e.getMessage());
        }
    }
}
