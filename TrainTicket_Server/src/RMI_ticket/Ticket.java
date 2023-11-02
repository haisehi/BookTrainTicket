package RMI_ticket;

import java.io.Serializable;
import java.util.Date; // Import thư viện Date

public class Ticket implements Serializable {
    private String IDTicket;
    private String train;
    private String trainCarriage;
    private String source;
    private String destination;
    private String departureTime;
    private Date Date; // Thay đổi kiểu dữ liệu thành Date
    private int seatCount;
    private double seatPrice;
    private String customerName;
    private String customerID;

    public Ticket(String IDTicket, String train, String trainCarriage, String source, String destination, String departureTime, Date Date, int seatCount, double seatPrice, String customerName, String customerID) {
        this.IDTicket = IDTicket;
        this.trainCarriage = trainCarriage;
        this.train = train;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.Date = Date;
        this.seatCount = seatCount;
        this.seatPrice = seatPrice;
        this.customerName = customerName;
        this.customerID = customerID;
    }
    

    public String getIDTicket() {
		return IDTicket;
	}


	public void setIDTicket(String iDTicket) {
		IDTicket = iDTicket;
	}


	public String getTrainCarriage() {
		return trainCarriage;
	}


	public void setTrainCarriage(String trainCarriage) {
		this.trainCarriage = trainCarriage;
	}


	public String getTrain() {
		return train;
	}

	public void setTrain(String train) {
		this.train = train;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
    // Tạo getter và setter cho trường Date
    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	public double getSeatPrice() {
		return seatPrice;
	}

	public void setSeatPrice(double seatPrice) {
		this.seatPrice = seatPrice;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}


	@Override
    public String toString() {
        return "Ticket [IDTicket=" + IDTicket + ", train=" + train + ", trainCarriage=" + trainCarriage + ", source=" + source
                + ", destination=" + destination + ", departureTime=" + departureTime + ", Date=" + Date
                + ", seatCount=" + seatCount + ", seatPrice=" + seatPrice + " VND" + ", customerName=" + customerName
                + ", customerID=" + customerID + "]";
    }
	
}
