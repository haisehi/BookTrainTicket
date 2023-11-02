package RMI_ticket;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;


public class ClientWindow extends JFrame {
    private TicketBookingService ticketBookingService;
    private JTextArea outputTextArea;
    private JTextField dateField, seatPriceField, customerNameField, customerIDField;
    private JComboBox<String> trainComboBox, carriageComboBox, sourceComboBox, destinationComboBox, departureTimeComboBox;
    private JComboBox<Integer> seatCountComboBox;
    
    private JTable ticketTable;
    private DefaultTableModel tableModel;
    private JDateChooser dateChooser; // Thay thế JTextField bằng JDateChooser



    public ClientWindow() {
        getContentPane().setLayout(new BorderLayout());
        // Khởi tạo RMI và giao diện
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ticketBookingService = (TicketBookingService) registry.lookup("TicketBookingService");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Tạo giao diện
        setTitle("Ticket Booking Client");
        setSize(984, 574);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        outputTextArea = new JTextArea();
        outputTextArea.setFont(new Font("Arial", Font.BOLD, 12));
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Tạo JComboBox cho train
        String[] trainOptions = { "Train01", "Train02", "Train03", "Train04", "Train05", "Train06", "Train07", "Train08", "Train09", "Train10" };
        // Tạo JComboBox cho trainCarriage
        String[] carriageOptions = { "CarriageA01", "CarriageA02", "CarriageA03", "CarriageB01", "CarriageB02", "CarriageB03", "CarriageC01", "CarriageC02", "CarriageC03", "CarriageD01", "CarriageD02", "CarriageD03" };
        // Tạo JComboBox cho source
        String[] sourceOptions = { "HANOI", "HAIPHONG", "DANANG", "QUANGNAM", "NHATRANG", "DALAT", "HOCHIMINH" };
        // Tạo JComboBox cho destination
        String[] destinationOptions = { "HANOI", "HAIPHONG", "DANANG", "QUANGNAM", "NHATRANG", "DALAT", "HOCHIMINH" };
        // Tạo JComboBox cho departureTime
        String[] departureTimeOptions = { "00:00 AM", "01:00 AM", "02:00 AM", "03:00 AM", "04:00 AM", "05:00 AM", "06:00 AM", "07:00 AM", "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM", "12:00 AM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM", "06:00 PM", "07:00 PM", "08:00 PM", "09:00 PM", "10:00 PM", "11:00 PM" };
        // Tạo JComboBox cho seatCount
        Integer[] seatCountOptions = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30 };

        JButton addButton = new JButton("Add Ticket");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTicket();
            }
        });



        JButton viewButton = new JButton("View Ticket Info");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewTicketInfo();
            }
        });



        JButton searchButton = new JButton("Search Ticket");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTicketByID();
            }
        });
        JButton updateButton = new JButton("Update Ticket");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTicket();
            }
        });
        JButton deleteButton = new JButton("Delete Ticket");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTicket();
            }
        });
        getContentPane().add(mainPanel, BorderLayout.NORTH);

        // Thêm các nút vào giao diện
        buttonPanel.add(searchButton);
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        
        // Trong constructor của ClientWindow, sau khi tạo inputPanel và buttonPanel.
        tableModel = new DefaultTableModel();
        ticketTable = new JTable(tableModel);
        tableModel.addColumn("ID");
        tableModel.addColumn("Train");
        tableModel.addColumn("Carriage");
        tableModel.addColumn("Source");
        tableModel.addColumn("Destination");
        tableModel.addColumn("Departure Time");
        tableModel.addColumn("Date");
        tableModel.addColumn("Seat Count");
        tableModel.addColumn("Seat Price");
        tableModel.addColumn("Customer Name");
        tableModel.addColumn("Customer ID");

        JScrollPane tableScrollPane = new JScrollPane(ticketTable);
        mainPanel.add(tableScrollPane, BorderLayout.SOUTH);
        
                JPanel inputPanel = new JPanel();
                getContentPane().add(inputPanel, BorderLayout.SOUTH);
                inputPanel.setLayout(new GridLayout(4, 6));
                // Tạo JTextField cho date
                dateChooser = new JDateChooser();
                seatCountComboBox = new JComboBox<>(seatCountOptions);
                // Tạo JTextField cho seatPrice
                seatPriceField = new JTextField();
                // Tạo JTextField cho customerName
                customerNameField = new JTextField();
                // Tạo JTextField cho customerID
                customerIDField = new JTextField();
                
                
                        // Add label và input vào giao diện
                        inputPanel.add(new JLabel("Train:"));
                        inputPanel.add(new JLabel("Train Carriage:"));
                        inputPanel.add(new JLabel("Source:"));
                        inputPanel.add(new JLabel("Destination:"));
                        inputPanel.add(new JLabel("Departure Time:"));
                        trainComboBox = new JComboBox<>(trainOptions);
                        carriageComboBox = new JComboBox<>(carriageOptions);
                        sourceComboBox = new JComboBox<>(sourceOptions);
                        destinationComboBox = new JComboBox<>(destinationOptions);
                        departureTimeComboBox = new JComboBox<>(departureTimeOptions);
                        inputPanel.add(trainComboBox);
                        inputPanel.add(carriageComboBox);
                        inputPanel.add(sourceComboBox);
                        inputPanel.add(destinationComboBox);
                        inputPanel.add(departureTimeComboBox);
                        inputPanel.add(new JLabel("Date:"));
                        inputPanel.add(new JLabel("Seat Count:"));
                        inputPanel.add(new JLabel("Seat Price:"));
                        inputPanel.add(new JLabel("Customer Name:"));
                        inputPanel.add(new JLabel("Customer ID:"));
                        inputPanel.add(dateChooser);
                        inputPanel.add(seatCountComboBox);
                        inputPanel.add(seatPriceField);
                        inputPanel.add(customerNameField);
                        inputPanel.add(customerIDField);

        // Hiển thị cửa sổ
        setVisible(true);
    }

    private void searchTicketByID() {
        try {
            String IDTicket = JOptionPane.showInputDialog(this, "Enter the ID Ticket to search:");
            if (IDTicket != null && !IDTicket.isEmpty()) {
                String ticketInfo = ticketBookingService.searchTicketByID(IDTicket);
                if (ticketInfo != null && !ticketInfo.isEmpty()) {
                    outputTextArea.setText("Ticket Information:\n" + ticketInfo);
                } else {
                    outputTextArea.setText("Ticket not found.");
                }
            }
        } catch (RemoteException e) {
            outputTextArea.setText("Error searching for ticket: " + e.getMessage());
        }
    }

    private void addTicket() {
        try {
            String train = (String) trainComboBox.getSelectedItem();
            String trainCarriage = (String) carriageComboBox.getSelectedItem();
            String source = (String) sourceComboBox.getSelectedItem();
            String destination = (String) destinationComboBox.getSelectedItem();
            String departureTime = (String) departureTimeComboBox.getSelectedItem();
            Date date = dateChooser.getDate();
            int seatCount = (int) seatCountComboBox.getSelectedItem();
            double seatPrice = Double.parseDouble(seatPriceField.getText());
            String customerName = customerNameField.getText();
            String customerID = customerIDField.getText();

            ticketBookingService.addTicket(train, trainCarriage, source, destination, departureTime, date, seatCount, seatPrice, customerName, customerID);
            outputTextArea.append("Ticket added successfully!\n");
            ticketBookingService.getTicketInfo();
        } catch (NumberFormatException | RemoteException e) {
            outputTextArea.append("Error adding ticket: " + e.getMessage() + "\n");
        }
    }

    private void viewTicketInfo() {
        try {
            String ticketInfo = ticketBookingService.getTicketInfo();
            if (ticketInfo != null && !ticketInfo.isEmpty()) {
                String[] ticketData = ticketInfo.split("\n"); // Tách thành từng dòng
                for (String data : ticketData) {
                    String[] ticketFields = data.split(", "); // Tách dữ liệu thành từng trường
                    tableModel.addRow(ticketFields); // Thêm dữ liệu vào bảng
                }
                outputTextArea.setText("Ticket Information:\n" + ticketInfo);
            } else {
                outputTextArea.setText("No tickets found.");
            }
        } catch (RemoteException e) {
            outputTextArea.setText("Error getting ticket information: " + e.getMessage());
        }
    }

    // Thêm phương thức sửa vé
 // Thêm phương thức cập nhật vé
    private void updateTicket() {
        try {
            int selectedRow = ticketTable.getSelectedRow();
            System.out.println(selectedRow);
            if (selectedRow >= 0) {
                String IDTicket = (String) ticketTable.getValueAt(selectedRow, 0);
                String train = (String) trainComboBox.getSelectedItem();
                String trainCarriage = (String) carriageComboBox.getSelectedItem();
                String source = (String) sourceComboBox.getSelectedItem();
                String destination = (String) destinationComboBox.getSelectedItem();
                String departureTime = (String) departureTimeComboBox.getSelectedItem();
                Date date = dateChooser.getDate();
                int updateSeatCount = (int) seatCountComboBox.getSelectedItem();
                double seatPrice = Double.parseDouble(seatPriceField.getText());
                String customerName = customerNameField.getText();
                String customerID = customerIDField.getText();
                System.out.println(IDTicket);
                
                // Gọi phương thức cập nhật vé từ RMI service
                ticketBookingService.updateTicket(IDTicket, train, trainCarriage, source, destination, departureTime, date, updateSeatCount, seatPrice, customerName, customerID);
                
                // Sau khi cập nhật thành công, cập nhật các trường trong tableModel để hiển thị thông tin cập nhật trên bảng
                int columnCount = tableModel.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = tableModel.getColumnName(i);
                    if ("ID".equals(columnName)) {
                        tableModel.setValueAt(IDTicket, selectedRow, i);
                    } else if ("Train".equals(columnName)) {
                        tableModel.setValueAt(train, selectedRow, i);
                    } else if ("Carriage".equals(columnName)) {
                        tableModel.setValueAt(trainCarriage, selectedRow, i);
                    } else if ("Source".equals(columnName)) {
                        tableModel.setValueAt(source, selectedRow, i);
                    } else if ("Destination".equals(columnName)) {
                        tableModel.setValueAt(destination, selectedRow, i);
                    } else if ("Departure Time".equals(columnName)) {
                        tableModel.setValueAt(departureTime, selectedRow, i);
                    } else if ("Date".equals(columnName)) {
                        tableModel.setValueAt(date, selectedRow, i);
                    } else if ("Seat Count".equals(columnName)) {
                        tableModel.setValueAt(updateSeatCount, selectedRow, i);
                    } else if ("Seat Price".equals(columnName)) {
                        tableModel.setValueAt(seatPrice, selectedRow, i);
                    } else if ("Customer Name".equals(columnName)) {
                        tableModel.setValueAt(customerName, selectedRow, i);
                    } else if ("Customer ID".equals(columnName)) {
                        tableModel.setValueAt(customerID, selectedRow, i);
                    }
                }
                outputTextArea.append("Ticket updated successfully!\n");
                System.out.println("Ticket updated successfully!\n");
            } else {
                outputTextArea.append("Please select a ticket to update.\n");
                System.out.println("Please select a ticket to update.\n");
            }
        } catch (NumberFormatException | RemoteException e) {
            outputTextArea.append("Error updating ticket: " + e.getMessage() + "\n");
            System.out.println("Error updating ticket: " + e.getMessage() + "\n");
        }
    }



    // Thêm phương thức xoá vé
    private void deleteTicket() {
        int selectedRow = ticketTable.getSelectedRow();
        if (selectedRow >= 0) {
            String IDTicket = (String) tableModel.getValueAt(selectedRow, 0); // Lấy IDTicket từ bảng
            System.out.println(IDTicket);
            int confirmDelete = JOptionPane.showConfirmDialog(this, "Do you want to delete Ticket with ID: " + IDTicket, "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirmDelete == JOptionPane.YES_OPTION) {
                try {
                    ticketBookingService.deleteTicket(IDTicket); // Gọi phương thức RMI để xoá vé
                    // Không cần gọi tableModel.removeRow(selectedRow) nữa vì phần này đã được xử lý ở phía máy chủ (RMI).
                    tableModel.removeRow(selectedRow);
                    outputTextArea.append("Ticket deleted successfully!\n");
                } catch (RemoteException e) {
                    outputTextArea.append("Error deleting ticket: " + e.getMessage() + "\n");
                }
            }
        } else {
            outputTextArea.append("Please select a ticket to delete.\n");
        }
    }







    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });
    }
}
