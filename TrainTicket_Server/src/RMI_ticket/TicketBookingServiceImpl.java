package RMI_ticket;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import RMI_ticket.DatabaseConnector;

public class TicketBookingServiceImpl extends UnicastRemoteObject implements TicketBookingService {
    private Connection connection;
    
    protected TicketBookingServiceImpl() throws RemoteException {
        super();
        connection = DatabaseConnector.getConnection();
    }

    @Override
    public void addTicket(String train, String trainCarriage, String source, String destination, String departureTime, Date date, int seatCount, double seatPrice, String customerName, String customerID) throws RemoteException {
        String IDTicket = train + trainCarriage + seatCount + source + date;
        try {
            // Kiểm tra xem IDTicket đã tồn tại trong cơ sở dữ liệu hay không
            String checkQuery = "SELECT IDTicket FROM ticket WHERE IDTicket = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, IDTicket);
            ResultSet resultSet = checkStatement.executeQuery();

            if (!resultSet.next()) {
                // Nếu IDTicket chưa tồn tại, thêm nó vào cơ sở dữ liệu
                String insertQuery = "INSERT INTO ticket (IDTicket, train, trainCarriage, source, destination, departureTime, Date, seatCount, seatPrice, customerName, customerID) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, IDTicket);
                insertStatement.setString(2, train);
                insertStatement.setString(3, trainCarriage);
                insertStatement.setString(4, source);
                insertStatement.setString(5, destination);
                insertStatement.setString(6, departureTime);
                insertStatement.setDate(7, new java.sql.Date(date.getTime())); // Chuyển Date sang java.sql.Date
                insertStatement.setInt(8, seatCount);
                insertStatement.setDouble(9, seatPrice);
                insertStatement.setString(10, customerName);
                insertStatement.setString(11, customerID);
                insertStatement.executeUpdate();
                System.out.println("add ticket to database successfully");
            } else {
                System.out.println("IDTicket already exists. Cannot add duplicate.");
            }
        } catch (SQLException e) {
            System.err.println("Error adding ticket to the database: " + e.getMessage());
        }
    }


    @Override
    public String getTicketInfo() throws RemoteException {
        StringBuilder info = new StringBuilder();
        try {
            String query = "SELECT * FROM ticket";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String IDTicket = resultSet.getString("IDTicket");
                String train = resultSet.getString("train");
                String trainCarriage = resultSet.getString("trainCarriage");
                String source = resultSet.getString("source");
                String destination = resultSet.getString("destination");
                String departureTime = resultSet.getString("departureTime");
                String Date = resultSet.getString("Date");
                int seatCount = resultSet.getInt("seatCount");
                double seatPrice = resultSet.getDouble("seatPrice");
                String customerName = resultSet.getString("customerName");
                String customerID = resultSet.getString("customerID");
                
                info.append("").append(IDTicket).append(", ");
                info.append("").append(train).append(", ");
                info.append("").append(trainCarriage).append(", ");
                info.append("").append(source).append(", ");
                info.append("").append(destination).append(", ");
                info.append("").append(departureTime).append(", ");
                info.append("").append(Date).append(", ");
                info.append("").append(seatCount).append(", ");
                info.append("").append(seatPrice).append(" VND, ");
                info.append("").append(customerName).append(", ");
                info.append("").append(customerID).append("\n");
            }
        } catch (SQLException e) {
            System.err.println("Error getting ticket information from the database: " + e.getMessage());
        }
        
        return info.toString();
    }

    @Override
    public String searchTicketByID(String IDTicket) throws RemoteException {
        try {
            String query = "SELECT * FROM ticket WHERE IDTicket = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, IDTicket);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                String train = resultSet.getString("train");
                String trainCarriage = resultSet.getString("trainCarriage");
                String source = resultSet.getString("source");
                String destination = resultSet.getString("destination");
                String departureTime = resultSet.getString("departureTime");
                String Date = resultSet.getString("Date");
                int seatCount = resultSet.getInt("seatCount");
                double seatPrice = resultSet.getDouble("seatPrice");
                String customerName = resultSet.getString("customerName");
                String customerID = resultSet.getString("customerID");
                
                StringBuilder info = new StringBuilder();
                info.append("IDTicket: ").append(IDTicket).append(", ");
                info.append("Train: ").append(train).append(", ");
                info.append("Train Carriage: ").append(trainCarriage).append(", ");
                info.append("Source: ").append(source).append(", ");
                info.append("Destination: ").append(destination).append(", ");
                info.append("Departure Time: ").append(departureTime).append(", ");
                info.append("Date: ").append(Date).append(", ");
                info.append("Seat Count: ").append(seatCount).append(", ");
                info.append("Seat Price: ").append(seatPrice).append(" VND, ");
                info.append("Customer Name: ").append(customerName).append(", ");
                info.append("Customer ID: ").append(customerID);
                System.out.println(info.toString());
                return info.toString();
                
            }
            System.out.println("search ticket successfully");
        } catch (SQLException e) {
            System.err.println("Error searching for ticket in the database: " + e.getMessage());
        }
        
        return "Ticket with ID " + IDTicket + " not found.";
    }
 // Cập nhật phương thức updateTicket để nhận Date
    @Override
    public void updateTicket(String IDTicket, String train, String trainCarriage, String source, String destination, String departureTime, Date date, int seatCount, double seatPrice, String customerName, String customerID) throws RemoteException {
        try {
            String updateQuery = "UPDATE ticket SET train = ?, trainCarriage = ?, source = ?, destination = ?, departureTime = ?, Date = ?, seatCount = ?, seatPrice = ?, customerName = ?, customerID = ? WHERE IDTicket = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, train);
            updateStatement.setString(2, trainCarriage);
            updateStatement.setString(3, source);
            updateStatement.setString(4, destination);
            updateStatement.setString(5, departureTime);
            updateStatement.setDate(6, new java.sql.Date(date.getTime())); // Chuyển Date sang java.sql.Date
            updateStatement.setInt(7, seatCount);
            updateStatement.setDouble(8, seatPrice);
            updateStatement.setString(9, customerName);
            updateStatement.setString(10, customerID);
            updateStatement.setString(11, IDTicket);
            updateStatement.executeUpdate();
            System.out.println("update ticket successfully");
        } catch (SQLException e) {
            System.err.println("Error updating ticket in the database: " + e.getMessage());
        }
    }
    @Override
    public void deleteTicket(String IDTicket) throws RemoteException {
        try {
            String deleteQuery = "DELETE FROM ticket WHERE IDTicket = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setString(1, IDTicket);
            int rowsDeleted = deleteStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Ticket with ID " + IDTicket + " deleted successfully.");
            } else {
                System.out.println("Ticket with ID " + IDTicket + " not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting ticket from the database: " + e.getMessage());
        }
    }

}
