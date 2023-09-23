package RMI_ticket;

import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
//        	tìm kiếm thanh ghi từ máy chủ ở server từ xa dựa trên ip và port
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
//          lấy ra object
            TicketBookingService ticketBookingService = (TicketBookingService) registry.lookup("TicketBookingService");
            Scanner scanner = new Scanner(System.in);

            while (true) {
            	System.out.println("----------------------------------");
                System.out.println("Menu:");
                System.out.println("1. Read Information Ticket");
                System.out.println("2. Add Ticket");
                System.out.println("3. View Ticket Info");
                System.out.println("4. Save to File");
                System.out.println("0. Exit");
                System.out.println("Enter your choice: ");
                System.out.println("----------------------------------");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                	case 1:
                		try {
                		    String ticketInfo = ticketBookingService.readFile();
                		    System.out.println("Ticket Information:\n" + ticketInfo);
                		} catch (RemoteException e) {
                		    System.err.println("Error reading file from server: " + e.getMessage());
                		}
                		break;
                    case 2:
                        System.out.print("Enter Train: ");
                        String train = scanner.nextLine();
                        System.out.print("Enter Train Carriage: ");
                        String trainCarriage = scanner.nextLine();
                        System.out.print("Enter source: ");
                        String source = scanner.nextLine();
                        System.out.print("Enter destination: ");
                        String destination = scanner.nextLine();
                        System.out.print("Enter departureTime: ");
                        String departureTime = scanner.nextLine();
                        System.out.print("Enter Date: ");
                        String Date = scanner.nextLine();
                        System.out.print("Enter seatCount: ");
                        int seatCount = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter seatPrice: ");
                        double seatPrice = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter customerName: ");
                        String customerName = scanner.nextLine();
                        System.out.print("Enter customerID: ");
                        String customerID = scanner.nextLine();
                        ticketBookingService.addTicket(train, trainCarriage,source,destination, departureTime, Date, seatCount, seatPrice, customerName, customerID);
                        System.out.println("Ticket added successfully!");
                        break;

                    case 3:
                        String ticketInfo = ticketBookingService.getTicketInfo();
                        System.out.println("Ticket Information:\n" + ticketInfo);
                        System.out.println("----------------------------------");
                        break;

                    case 4:
                        ticketBookingService.saveToFile();
                        System.out.println("Ticket information saved to file!");
                        break;

                    case 0:
                        System.exit(0);
                        System.out.println("Exit");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
