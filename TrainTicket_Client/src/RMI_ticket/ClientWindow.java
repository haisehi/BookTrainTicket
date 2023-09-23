package RMI_ticket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientWindow extends JFrame {
    private TicketBookingService ticketBookingService;
    private JTextArea outputTextArea;
    private JTextField  dateField, seatPriceField, customerNameField, customerIDField;
    private JComboBox<String> trainComboBox, carriageComboBox, sourceComboBox, destinationComboBox, departureTimeComboBox;
    private JComboBox<Integer> seatCountComboBox;
    public ClientWindow() {
    	setBackground(new Color(192, 192, 192));
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
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        outputTextArea = new JTextArea();
        outputTextArea.setFont(new Font("Arial", Font.BOLD, 12));
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(0, 255, 128));
        inputPanel.setLayout(new GridLayout(12, 2));

        // Tạo JComboBox cho train
        String[] trainOptions = {
        		"Train01", 
        		"Train02", 
        		"Train03", 
        		"Train04", 
        		"Train05", 
        		"Train06", 
        		"Train07", 
        		"Train08", 
        		"Train09", 
        		"Train10"}; 
        trainComboBox = new JComboBox<>(trainOptions);
        trainComboBox.setFont(new Font("Arial", Font.BOLD, 12));
        // Tạo JComboBox cho trainCarriage
        String[] carriageOptions = {
        		"CarriageA01", 
        		"CarriageA02", 
        		"CarriageA03", 
        		"CarriageB01", 
        		"CarriageB02", 
        		"CarriageB03", 
        		"CarriageC01", 
        		"CarriageC02", 
        		"CarriageC03", 
        		"CarriageD01", 
        		"CarriageD02", 
        		"CarriageD03"}; 
        carriageComboBox = new JComboBox<>(carriageOptions);
        carriageComboBox.setFont(new Font("Arial", Font.BOLD, 12));
        // Tạo JComboBox cho source
        String[] sourceOptions = {
        		"HANOI", 
        		"HAIPHONG", 
        		"DANANG", 
        		"QUANGNAM", 
        		"NHATRANG", 
        		"DALAT", 
        		"HOCHIMINH"}; 
        sourceComboBox = new JComboBox<>(sourceOptions);
        sourceComboBox.setFont(new Font("Arial", Font.BOLD, 12));
        // Tạo JComboBox cho destination
        String[] destinationOptions = {
        		"HANOI", 
        		"HAIPHONG", 
        		"DANANG", 
        		"QUANGNAM", 
        		"NHATRANG", 
        		"DALAT", 
        		"HOCHIMINH"}; 
        destinationComboBox = new JComboBox<>(destinationOptions);
        destinationComboBox.setFont(new Font("Arial", Font.BOLD, 12));
        // Tạo JComboBox cho departureTime
        String[] departureTimeOptions = {
        		"00:00 AM",
        		"01:00 AM",
        		"02:00 AM",
        		"03:00 AM",
        		"04:00 AM",
        		"05:00 AM",
        		"06:00 AM",
        		"07:00 AM",
        		"08:00 AM",
        		"09:00 AM",
        		"10:00 AM",
        		"11:00 AM",
        		"12:00 AM",
        		"01:00 PM",
        		"02:00 PM",
        		"03:00 PM",
        		"04:00 PM",
        		"05:00 PM",
        		"06:00 PM",
        		"07:00 PM",
        		"08:00 PM",
        		"09:00 PM",
        		"10:00 PM",
        		"11:00 PM"}; 
        departureTimeComboBox = new JComboBox<>(departureTimeOptions);
        departureTimeComboBox.setFont(new Font("Arial", Font.BOLD, 12));
        dateField = new JTextField();
        dateField.setFont(new Font("Arial", Font.BOLD, 12));
        // Tạo JComboBox cho seatCount
        Integer[] seatCountOptions = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,20,21,22,23,24,25,26,27,28,29,30}; 
        seatCountComboBox = new JComboBox<>(seatCountOptions);
        seatCountComboBox.setFont(new Font("Arial", Font.BOLD, 12));
        // Tạo JTextField cho seatPrice
        seatPriceField = new JTextField();
        seatPriceField.setFont(new Font("Arial", Font.BOLD, 12));
        // Tạo JTextField cho customerName
        customerNameField = new JTextField();
        customerNameField.setFont(new Font("Arial", Font.BOLD, 12));
        // Tạo JTextField cho customerID
        customerIDField = new JTextField();
        customerIDField.setFont(new Font("Arial", Font.BOLD, 12));

        JButton addButton = new JButton("Add Ticket");
        addButton.setForeground(new Color(255, 255, 255));
        addButton.setBackground(new Color(255, 128, 128));
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTicket();
            }
        });

        JButton readButton = new JButton("Read Information Ticket");
        readButton.setBackground(new Color(0, 255, 0));
        readButton.setForeground(new Color(255, 255, 255));
        readButton.setFont(new Font("Arial", Font.BOLD, 12));
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readTicketInformation();
            }
        });

        JButton viewButton = new JButton("View Ticket Info");
        viewButton.setForeground(new Color(255, 255, 255));
        viewButton.setBackground(new Color(0, 0, 255));
        viewButton.setFont(new Font("Arial", Font.BOLD, 12));
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewTicketInfo();
            }
        });

        JButton saveButton = new JButton("Save to File");
        saveButton.setFont(new Font("Arial", Font.BOLD, 12));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });
        
        //add component vào giao diện
        JLabel label = new JLabel("Train:");
        label.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(label);
        inputPanel.add(trainComboBox);
        JLabel label_1 = new JLabel("Train Carriage:");
        label_1.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(label_1);
        inputPanel.add(carriageComboBox);
        JLabel label_2 = new JLabel("Source:");
        label_2.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(label_2);
        inputPanel.add(sourceComboBox);
        JLabel label_3 = new JLabel("Destination:");
        label_3.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(label_3);
        inputPanel.add(destinationComboBox);
        JLabel label_4 = new JLabel("Departure Time:");
        label_4.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(label_4);
        inputPanel.add(departureTimeComboBox);
        JLabel label_5 = new JLabel("Date:");
        label_5.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(label_5);
        inputPanel.add(dateField);
        JLabel label_6 = new JLabel("Seat Count:");
        label_6.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(label_6);
        inputPanel.add(seatCountComboBox);
        JLabel label_7 = new JLabel("Seat Price:");
        label_7.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(label_7);
        inputPanel.add(seatPriceField);
        JLabel label_8 = new JLabel("Customer Name:");
        label_8.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(label_8);
        inputPanel.add(customerNameField);
        JLabel label_9 = new JLabel("Customer ID:");
        label_9.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(label_9);
        inputPanel.add(customerIDField);
        inputPanel.add(addButton);
        inputPanel.add(readButton);
        inputPanel.add(viewButton);
        inputPanel.add(saveButton);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);

        // Hiển thị cửa sổ
        setVisible(true);
    }

    private void addTicket() {
    	 try {
    	        String train = (String) trainComboBox.getSelectedItem();
    	        String trainCarriage = (String) carriageComboBox.getSelectedItem();
    	        String source = (String) sourceComboBox.getSelectedItem();
    	        String destination = (String) destinationComboBox.getSelectedItem();
    	        String departureTime = (String) departureTimeComboBox.getSelectedItem();
    	        String date = dateField.getText();
    	        int seatCount = (int) seatCountComboBox.getSelectedItem();
    	        double seatPrice = Double.parseDouble(seatPriceField.getText());
    	        String customerName = customerNameField.getText();
    	        String customerID = customerIDField.getText();

    	        ticketBookingService.addTicket(train, trainCarriage, source, destination, departureTime, date, seatCount, seatPrice, customerName, customerID);
    	        outputTextArea.append("Ticket added successfully!\n");
    	    } catch (NumberFormatException | RemoteException e) {
    	        outputTextArea.append("Error adding ticket: " + e.getMessage() + "\n");
    	    }
    }

    private void readTicketInformation() {
        try {
            String ticketInfo = ticketBookingService.readFile();
            outputTextArea.setText("Ticket Information:\n" + ticketInfo);
        } catch (RemoteException e) {
            outputTextArea.setText("Error reading file from server: " + e.getMessage());
        }
    }

    private void viewTicketInfo() {
        try {
            String ticketInfo = ticketBookingService.getTicketInfo();
            outputTextArea.setText("Ticket Information:\n" + ticketInfo);
        } catch (RemoteException e) {
            outputTextArea.setText("Error getting ticket information: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try {
            ticketBookingService.saveToFile();
            outputTextArea.append("Ticket information saved to file!\n");
        } catch (RemoteException e) {
            outputTextArea.append("Error saving ticket information to file: " + e.getMessage() + "\n");
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
