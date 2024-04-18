package FlightManagement;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import java.util.Scanner;
import java.sql.*;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;


public class FlightManage extends JFrame {
	
    // JFrame
    static JFrame f =  new JFrame("Galactics Airlines");
    
    
    static JPanel MainPage = new JPanel();
    static JPanel SearchPage = new JPanel();

    
    static JPanel SearchByDatePage = new JPanel();
    static JPanel SearchByDatePrintPage = new JPanel();
    static JPanel SearchByPilotPage = new JPanel();
    static JPanel SearchByPilotPrintPage = new JPanel();
    static JPanel SearchByAirportPage = new JPanel();
    static JPanel SearchByAirportPrintPage = new JPanel();
    static JPanel SearchByAirlinePage = new JPanel();
    static JPanel SearchByAirlinePrintPage = new JPanel();
    
    
    static JPanel ScheduleMainPage = new JPanel();
    static JPanel ScheduleAirlinePage = new JPanel();
    static JPanel ScheduleAirplanePage = new JPanel();
    static JPanel SchedulePilotPage = new JPanel();
    static JPanel ScheduleGatePage = new JPanel();
    static JPanel ScheduleInsertPage = new JPanel();
    static JPanel ScheduleSuccessPage = new JPanel();
    
    
    static JButton flightSearchButton = new JButton("Flight Search");
    static JButton searchByDateButton = new JButton("Flight Search By Date");
    static JButton searchByDateConfirmButton = new JButton("Confirm");
    static JButton searchByPilotButton = new JButton("Flight Search By Pilot");
    static JButton searchByPilotConfirmButton = new JButton("Confirm");
    static JButton searchByAirportButton = new JButton("Flight Search By Airport");
    static JButton searchByAirportConfirmButton = new JButton("Confirm");
    static JButton searchByAirlineButton = new JButton("Flight Search By Airline");
    static JButton searchByAirlineConfirmButton = new JButton("Confirm");
    static JButton backToMainPageButton = new JButton("Back to Main Page");
    
    static JButton backToSearchPageButton1 = new JButton("Back to Search Page");
    static JButton backToSearchPageButton2 = new JButton("Back to Search Page");
    static JButton backToSearchPageButton3 = new JButton("Back to Search Page");
    static JButton backToSearchPageButton4 = new JButton("Back to Search Page");
    
    static JButton flightScheduleButton = new JButton("Flight Schedule");
    static JButton scheduleInputConfirmButton = new JButton("Confirm");
    static JButton airlineSelectButton = new JButton("Select");
    static JButton airplaneSelectButton = new JButton("Select");
    static JButton pilotSelectButton = new JButton("Select");
    static JButton gateSelectButton = new JButton("Select");
    
    static JButton insertButton= new JButton("Insert");
    static JButton cancelButton = new JButton("Cancel");
    
    static JButton backToMainPageButton2 = new JButton("Back to Main Page");
    
    static JLabel airportList = new JLabel();
    static JLabel airportList2 = new JLabel();
    static JLabel pilotList = new JLabel();
    static JLabel airlineList = new JLabel();
    
    static JLabel textLabelSBD = new JLabel();
    static JLabel textLabelSBP = new JLabel();
    static JLabel textLabelSBAP = new JLabel();
    static JLabel textLabelSBAL = new JLabel();
    
    static JLabel textLabelAVAL = new JLabel();
    static JLabel textLabelAVAP = new JLabel();
    static JLabel textLabelAVP = new JLabel();
    static JLabel textLabelAVG = new JLabel();
    
    static JLabel textLabelflight = new JLabel();
    static JLabel textLabelsuccess = new JLabel();
    
    // label to display text
    static JTextField SBDairportCode, SBDdepartureDate;
    static JTextField SBPpilotSSN;
    static JTextField SBAPoAirportCode, SBAPdAirportCode;
    static JTextField SBALairlineCode;

  
    static JTextField InputOAirport, InputDAirport, InputDDate, InputDTime, InputADate, InputATime;
    static JTextField AVALairlineCode;
    static JTextField AVAPairplaneId;
    static JTextField AVPpilotSSN;
    static JTextField AVGgate;
    
	static Connection conn = null;
	static PreparedStatement stmt = null;
	static ResultSet rs = null;
	
	static String driver;
	static String url;
	static String user;
	static String pwd;
	
	
	public FlightManage() {
		
		f.add(MainPage);
		f.setVisible(true);
		f.setSize(1280, 400);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		InitPanels();
		
		// 1. Flight Search
		flightSearchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				f.getContentPane().removeAll();
				f.getContentPane().add(SearchPage);
				f.revalidate();
				f.repaint();
			}
		});
		
		backToMainPageButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				f.getContentPane().removeAll();
				f.getContentPane().add(MainPage);
				f.revalidate();
				f.repaint();
			}
		});
		
		// 1-1) Flight Search By Date
		FlightSearchByDateButtonEvent();
		
		// 1-2) Flight Search By Pilot
		FlightSearchByPilotButtonEvent();
		
		// 1-3) Flight Search by the Airport
		FlightSearchByAirportButtonEvent();
		
		// 1-4) Flight Search by the Airline
		FlightSearchByAirlineButtonEvent();
		
		
		// 2. Flight Schedule
		FlightScheduleButtonEvent();
	}
	public static void FlightSearchByDateButtonEvent() {
		
		searchByDateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				f.getContentPane().removeAll();
				f.getContentPane().add(SearchByDatePage);
				f.revalidate();
				f.repaint();
			}
		});
		
		searchByDateConfirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StringBuilder result;
				result = SearchByDate(SBDairportCode.getText(), SBDdepartureDate.getText());
				textLabelSBD.setText("<html>Origin Airport: " + SBDairportCode.getText() + "<br>"
									+ "Departure Date: "+ SBDdepartureDate.getText() + "<br>"+ result + "</html>");
				f.getContentPane().removeAll();
				f.getContentPane().add(SearchByDatePrintPage);
				f.revalidate();
				f.repaint();
			}
		});
		
		backToSearchPageButton1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				f.getContentPane().removeAll();
				f.getContentPane().add(SearchPage);
				f.revalidate();
				f.repaint();
			}
		});
	}
	
	public static void FlightSearchByPilotButtonEvent() {
		searchByPilotButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				f.getContentPane().removeAll();
				f.getContentPane().add(SearchByPilotPage);
				f.revalidate();
				f.repaint();
			}
		});
		
		searchByPilotConfirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StringBuilder result;
				result = SearchByPilot(SBPpilotSSN.getText());
				textLabelSBP.setText("<html>Pilot SSN: "+ SBPpilotSSN.getText() + "<br>"+ result + "</html>");
				f.getContentPane().removeAll();
				f.getContentPane().add(SearchByPilotPrintPage);
				f.revalidate();
				f.repaint();
			}
		});
		backToSearchPageButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				f.getContentPane().removeAll();
				f.getContentPane().add(SearchPage);
				f.revalidate();
				f.repaint();
			}
		});
	}
	
	public static void FlightSearchByAirportButtonEvent() {
		searchByAirportButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				f.getContentPane().removeAll();
				f.getContentPane().add(SearchByAirportPage);
				f.revalidate();
				f.repaint();
			}
		});
		
		searchByAirportConfirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StringBuilder result;
				result = SearchByAirport(SBAPoAirportCode.getText(), SBAPdAirportCode.getText());
				textLabelSBAP.setText("<html>Origin Airport Code: " + SBAPoAirportCode.getText() + "<br>Destination Airport Code: " + SBAPdAirportCode.getText()
				+ "<br>" + result + "</html>");
				f.getContentPane().removeAll();
				f.getContentPane().add(SearchByAirportPrintPage);
				f.revalidate();
				f.repaint();
			}
		});
		
		backToSearchPageButton3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				f.getContentPane().removeAll();
				f.getContentPane().add(SearchPage);
				f.revalidate();
				f.repaint();
			}
		});
	}
	
	public static void FlightSearchByAirlineButtonEvent() {
		
		searchByAirlineButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				f.getContentPane().removeAll();
				f.getContentPane().add(SearchByAirlinePage);
				f.revalidate();
				f.repaint();
			}
		});
		
		searchByAirlineConfirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StringBuilder result;
				result = SearchByAirline(SBALairlineCode.getText());
				textLabelSBAL.setText("<html>Airline Code: " + SBALairlineCode.getText() + "<br>"
									+ result + "</html>");
				f.getContentPane().removeAll();
				f.getContentPane().add(SearchByAirlinePrintPage);
				f.revalidate();
				f.repaint();
			}
		});
		
		backToSearchPageButton4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				f.getContentPane().removeAll();
				f.getContentPane().add(SearchPage);
				f.revalidate();
				f.repaint();
			}
		});
			
	}
	
	public static void FlightScheduleButtonEvent() {
		flightScheduleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				f.getContentPane().removeAll();
				f.getContentPane().add(ScheduleMainPage);
				f.revalidate();
				f.repaint();
			}
		});
		
		scheduleInputConfirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StringBuilder result;
				result = AvailableAirline(InputOAirport.getText(), InputDAirport.getText());
				textLabelAVAL.setText("<html>Available Airlines<br>"+ result + "</html>");
				f.getContentPane().removeAll();
				f.getContentPane().add(ScheduleAirlinePage);
				f.revalidate();
				f.repaint();
			}
		});
		
		// 2-1) Print Available Airline & Select One Airline
		airlineSelectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StringBuilder result;
				result = AvailableAirplane(AVALairlineCode.getText(), InputDDate.getText());
				textLabelAVAP.setText("<html>Available Airplanes<br>"+ result + "</html>");
				f.getContentPane().removeAll();
				f.getContentPane().add(ScheduleAirplanePage);
				f.revalidate();
				f.repaint();
			}
		});
		
		// 2-2) Print Available Airplane & Select One Airplane
		airplaneSelectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StringBuilder result;
				result = AvailablePilot(InputDDate.getText(), AVALairlineCode.getText());
				textLabelAVP.setText("<html>Available Pilots<br>"+ result + "</html>");
				f.getContentPane().removeAll();
				f.getContentPane().add(SchedulePilotPage);
				f.revalidate();
				f.repaint();
			}
		});
		
		// 2-3) Print Available Pilots & Select One Pilot
		
		pilotSelectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StringBuilder result;
				result = AvailableGate(InputDDate.getText(), InputDTime.getText(), InputOAirport.getText());
				textLabelAVG.setText("<html>Available Gates<br>"+ result + "</html>");
				f.getContentPane().removeAll();
				f.getContentPane().add(ScheduleGatePage);
				f.revalidate();
				f.repaint();
			}
		});
		
		// 2-4)) Print Available Gates & Select One Gate 	
		gateSelectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String AirlineCode = AVALairlineCode.getText();
				String DepartureTime = InputDDate.getText() + " " + InputDTime.getText();
				String ArrivalTime = InputADate.getText() + " " + InputATime.getText();
				String FGate = AVGgate.getText();
				String PilotSSN = AVPpilotSSN.getText();
				String AirplaneID = AVAPairplaneId.getText();
				String OIATACode = InputOAirport.getText();
				String DIATACode = InputDAirport.getText();
				
				String FlightNumber = findflightNum(AirlineCode);
				String DvsI =  checkDorI(OIATACode, DIATACode);
				
				textLabelflight.setText("<html>Flight Information Confirm<br>"+
						"FlightNumber&emsp AirlineCode&emsp DepartureTime&emsp&emsp&emsp&emsp&emsp&emsp&emsp ArrivalTime&emsp&emsp&emsp&emsp&emsp&emsp FGate&emsp PilotSSN&emsp AirplaneID&emsp OIATACode&emsp DIATACode&emsp DvsI<br>"
						+"------------------------------------------------------------------------------------------------------------------------------------------<br>"
						+ FlightNumber + "&emsp&emsp&emsp&emsp " + AirlineCode + "&emsp&emsp&emsp&emsp " +DepartureTime + "&emsp&emsp&emsp&emsp "
						+ ArrivalTime + "&emsp " +  FGate + "&emsp " + PilotSSN + "&emsp " + AirplaneID + "&emsp "
						+OIATACode + "&emsp&emsp&emsp&emsp " + DIATACode + "&emsp&emsp&emsp&emsp " + DvsI +"<br>"
						+ "</html>");
				f.getContentPane().removeAll();
				f.getContentPane().add(ScheduleInsertPage);
				f.revalidate();
				f.repaint();
			}
		});
		
		insertButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String AirlineCode = AVALairlineCode.getText();
				String DepartureTime = InputDDate.getText() + " " + InputDTime.getText();
				String ArrivalTime = InputADate.getText() + " " + InputATime.getText();
				String FGate = AVGgate.getText();
				String PilotSSN = AVPpilotSSN.getText();
				String AirplaneID = AVAPairplaneId.getText();
				String OIATACode = InputOAirport.getText();
				String DIATACode = InputDAirport.getText();
				
				String FlightNumber = findflightNum(AirlineCode);
				String DvsI =  checkDorI(OIATACode, DIATACode);
				
				InsertFlight(FlightNumber, AirlineCode, DepartureTime, ArrivalTime, FGate, PilotSSN, AirplaneID, OIATACode, DIATACode, DvsI);
				
				textLabelsuccess.setText("<html>Your Flight is Successfully Scheduled!<br></html>");
				f.getContentPane().removeAll();
				f.getContentPane().add(ScheduleSuccessPage);
				f.revalidate();
				f.repaint();
			}
		});
		
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				f.getContentPane().removeAll();
				f.getContentPane().add(MainPage);
				f.revalidate();
				f.repaint();
			}
		});
		
		backToMainPageButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				f.getContentPane().removeAll();
				f.getContentPane().add(MainPage);
				f.revalidate();
				f.repaint();
			}
		});
		
	}
	
	
	public static void InitPanels() {
		// Main Page Setting
		MainPage.add(flightSearchButton);
		MainPage.add(flightScheduleButton);
		MainPage.setBackground(Color.lightGray);
		
		// Search Page Setting
		SearchPage.add(searchByDateButton);
		SearchPage.add(searchByPilotButton);
		SearchPage.add(searchByAirportButton);
		SearchPage.add(searchByAirlineButton);
		SearchPage.add(backToMainPageButton);
		SearchPage.setBackground(Color.black);
		
		// Search By Date Setting
		SearchByDatePage.add(new JLabel("Enter the origin airport: "));
		SBDairportCode = new JTextField(3);
		SearchByDatePage.add(SBDairportCode);
		SearchByDatePage.add(new JLabel("Enter the departure date(YYYY-MM-DD): "));
		SBDdepartureDate = new JTextField(10);
		SearchByDatePage.add(SBDdepartureDate);
		SearchByDatePage.add(searchByDateConfirmButton);
		airportList.setText("<html>Airport Lists<br>"+ AirportList() + "<br></html>");
		SearchByDatePage.add(airportList);
		
		
		SearchByDatePrintPage.add(textLabelSBD);
		SearchByDatePrintPage.add(backToSearchPageButton1);
		
		// Search By Pilot Setting
		SearchByPilotPage.add(new JLabel("Enter the pilot SSN: "));
		SBPpilotSSN = new JTextField(9);
		SearchByPilotPage.add(SBPpilotSSN);
		SearchByPilotPage.add(searchByPilotConfirmButton);
		pilotList.setText("<html>Pilot Lists<br>"+ PilotList() + "<br></html>");
		SearchByPilotPage.add(pilotList);
		
		SearchByPilotPrintPage.add(textLabelSBP);
		SearchByPilotPrintPage.add(backToSearchPageButton2);
		
		// Search By Airport Setting
		SearchByAirportPage.add(new JLabel("Enter the origin airport: "));
		SBAPoAirportCode = new JTextField(3);
		SearchByAirportPage.add(SBAPoAirportCode);
		SearchByAirportPage.add(new JLabel("Enter the destination airport: "));
		SBAPdAirportCode = new JTextField(3);
		SearchByAirportPage.add(SBAPdAirportCode);
		SearchByAirportPage.add(new JLabel("'*' includes all airports. ex. * PHX or PHX *"));
		SearchByAirportPage.add(searchByAirportConfirmButton);
		airportList2.setText("<html>Airport Lists<br>"+ AirportList() + "<br></html>");
		SearchByAirportPage.add(airportList2);
		
		
		SearchByAirportPrintPage.add(textLabelSBAP);
		SearchByAirportPrintPage.add(backToSearchPageButton3);
		
		// Search By Airline Setting
		SearchByAirlinePage.add(new JLabel("Enter the airline code: "));
		SBALairlineCode = new JTextField(3);
		SearchByAirlinePage.add(SBALairlineCode);
		SearchByAirlinePage.add(searchByAirlineConfirmButton);
		airlineList.setText("<html>Airline Lists<br>"+ AirlineList() + "<br></html>");
		SearchByAirlinePage.add(airlineList);
		
		
		SearchByAirlinePrintPage.add(textLabelSBAL);
		SearchByAirlinePrintPage.add(backToSearchPageButton4);
		
		
		// Schedule Main Page Setting
		ScheduleMainPage.add(new JLabel("Enter the origin airport: "));
		InputOAirport = new JTextField(3);
		ScheduleMainPage.add(InputOAirport);
		
		ScheduleMainPage.add(new JLabel("Enter the destination airport: "));
		InputDAirport = new JTextField(3);
		ScheduleMainPage.add(InputDAirport);
		
		ScheduleMainPage.add(new JLabel("Enter the departure date(YYYY-MM-DD): "));
		InputDDate = new JTextField(10);
		ScheduleMainPage.add(InputDDate);
		
		ScheduleMainPage.add(new JLabel("Enter the departure time(HH:MM:SS): "));
		InputDTime = new JTextField(8);
		ScheduleMainPage.add(InputDTime);
		
		ScheduleMainPage.add(new JLabel("Enter the arrival date(YYYY-MM-DD): "));
		InputADate = new JTextField(10);
		ScheduleMainPage.add(InputADate);
		
		ScheduleMainPage.add(new JLabel("Enter the arrival time(HH:MM:SS): "));
		InputATime = new JTextField(8);
		ScheduleMainPage.add(InputATime);
		
		ScheduleMainPage.add(scheduleInputConfirmButton);
		
		
		// Schedule Airline Setting
		ScheduleAirlinePage.setBackground(Color.white);
		ScheduleAirlinePage.add(textLabelAVAL);
		ScheduleAirlinePage.add(new JLabel("Select an airline code: "));
		AVALairlineCode = new JTextField(3);
		ScheduleAirlinePage.add(AVALairlineCode);
		ScheduleAirlinePage.add(airlineSelectButton);
		
		// Schedule Airplane Setting
		ScheduleAirplanePage.add(textLabelAVAP);
		ScheduleAirplanePage.add(new JLabel("Select an airplane ID: "));
		AVAPairplaneId = new JTextField(7);
		ScheduleAirplanePage.add(AVAPairplaneId);
		ScheduleAirplanePage.add(airplaneSelectButton);
		
		// Schedule Pilot Setting
		SchedulePilotPage.add(textLabelAVP);
		SchedulePilotPage.add(new JLabel("Select a pilot SSN: "));
		AVPpilotSSN = new JTextField(9);
		SchedulePilotPage.add(AVPpilotSSN);
		SchedulePilotPage.add(pilotSelectButton);
		
		// Schedule Gate Setting
		ScheduleGatePage.add(textLabelAVG);
		ScheduleGatePage.add(new JLabel("Select a gate: "));
		AVGgate = new JTextField(3);
		ScheduleGatePage.add(AVGgate);
		ScheduleGatePage.add(gateSelectButton);
		
		// Schedule Insert Setting
		ScheduleInsertPage.add(textLabelflight);
		ScheduleInsertPage.add(insertButton);
		ScheduleInsertPage.add(cancelButton);
		
		// Schedule Success Setting
		
		ScheduleSuccessPage.add(textLabelsuccess);
		ScheduleSuccessPage.add(backToMainPageButton2);


	}
	public static StringBuilder PilotList() {
		StringBuilder result = new StringBuilder();

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
			stmt = conn.prepareStatement("SELECT * FROM PILOT;");

			rs = stmt.executeQuery();
			result.append("Full Name&emsp&emsp&emsp PActivity&emsp&emsp&emsp SSN&emsp&emsp&emsp&emsp&emsp&emsp BirthDate&emsp&emsp&emsp License Number&emsp EPTA Grade&emsp Flight Experience<br>");
			result.append("------------------------------------------------------------------------------------------------<br>");
			while(rs.next()) {
				String FullName = rs.getString(1);
				String PActivity = rs.getString(2);
				String SSN = rs.getString(3);
				String BDate = rs.getString(4);
				int LicenseNum = rs.getInt(5);
				int EPTAgrade = rs.getInt(6);
				int FExperience = rs.getInt(7);

				result.append(FullName + "&emsp&emsp " + PActivity + "&emsp&emsp " + SSN + "&emsp&emsp "
								+ BDate + "&emsp&emsp " + LicenseNum + "&emsp&emsp&emsp&emsp&emsp " + EPTAgrade + "&emsp&emsp&emsp&emsp&emsp&emsp " + FExperience + "<br>");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB Driver Error!");
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("DB Connection Error!");
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) { e.printStackTrace(); }
			try { if(stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(conn != null) conn.close(); } catch (SQLException e) {e.printStackTrace(); }
		}
		
		return result;
	}
	public static StringBuilder AirlineList() {
		StringBuilder result = new StringBuilder();
		String sql = null;
		
		  try {
	          Class.forName(driver);
	          conn = DriverManager.getConnection(url, user, pwd);
	          
	          sql = "SELECT CName, L.* FROM AIRLINE AS L, COUNTRY WHERE CountryCode=L.AirlineCountry;";
	         
	         stmt = conn.prepareStatement(sql);

	         // 4. query execute
	         rs = stmt.executeQuery();
	         
	         // 5. print result
	         result.append("ICAOCode&emsp&emsp Airline Name&emsp&emsp&emsp&emsp Country<br>");
	         result.append("----------------------------------------------------------------------------------------------------------<br>");
	         while(rs.next()) {
	             String CName = rs.getString(1);
	             String AirlineCountry = rs.getString(2);
	             String AirlineName = rs.getString(3);
	             String ICAOCode = rs.getString(4);
	             
	            result.append(ICAOCode + "&emsp&emsp&emsp&emsp&emsp&emsp " + AirlineName + "&emsp&emsp&emsp&emsp&emsp " + AirlineCountry + "(" + CName + ")"+ "<br>");
	         }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB Driver Error!");
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("DB Connection Error!");
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) { e.printStackTrace(); }
			try { if(stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(conn != null) conn.close(); } catch (SQLException e) {e.printStackTrace(); }
		}
		
		return result;
	}
	
	public static StringBuilder AirportList() {
		StringBuilder result = new StringBuilder();
		String sql = null;
		
		  try {
	          Class.forName(driver);
	          conn = DriverManager.getConnection(url, user, pwd);
	          
	          sql = "SELECT * FROM AIRPORT";
	         
	         stmt = conn.prepareStatement(sql);

	         // 4. query execute
	         rs = stmt.executeQuery();
	         
	         // 5. print result
	         result.append("IATACode&emsp&emsp Country&emsp&emsp&emsp&emsp Airport Name<br>");
	         result.append("----------------------------------------------------------------------------------------------------------<br>");
	         while(rs.next()) {
	        	 String IATACode = rs.getString(1);
	             String Country = rs.getString(3);
	             String AirportName = rs.getString(2);
	             
	            result.append(IATACode + "&emsp&emsp&emsp&emsp&emsp&emsp " + Country + "&emsp&emsp&emsp&emsp&emsp " + AirportName + "<br>");
	         }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB Driver Error!");
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("DB Connection Error!");
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) { e.printStackTrace(); }
			try { if(stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(conn != null) conn.close(); } catch (SQLException e) {e.printStackTrace(); }
		}
		
		return result;
		
	}
	public static StringBuilder SearchByAirline(String inputAirlineCode) {
		StringBuilder result = new StringBuilder();

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
			stmt = conn.prepareStatement("SELECT * FROM FLIGHT WHERE AirlineCode = ?;"	);
			stmt.setString(1, inputAirlineCode);
			rs = stmt.executeQuery();
			result.append("FlightNumber&emsp AirlineCode&emsp DepartureTime&emsp&emsp&emsp&emsp&emsp&emsp&emsp ArrivalTime&emsp&emsp&emsp&emsp&emsp&emsp FGate&emsp PilotSSN&emsp AirplaneID&emsp OIATACode&emsp DIATACode&emsp DvsI<br>");
			result.append("------------------------------------------------------------------------------------------------------------------------------------------<br>");
			while(rs.next()) {
				result.append(rs.getString(1) + "&emsp&emsp&emsp&emsp " + rs.getString(2) + "&emsp&emsp&emsp&emsp " + rs.getString(3) + "&emsp&emsp&emsp&emsp "
						+ rs.getString(4) + "&emsp " + rs.getString(5) + "&emsp " + rs.getString(6) + "&emsp " + rs.getString(7) + "&emsp "
						+ rs.getString(8) + "&emsp&emsp&emsp&emsp&emsp " + rs.getString(9) + "&emsp&emsp&emsp&emsp " + rs.getString(10) +"<br>");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB Driver Error!");
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("DB Connection Error!");
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) { e.printStackTrace(); }
			try { if(stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(conn != null) conn.close(); } catch (SQLException e) {e.printStackTrace(); }
		}
		
		return result;
		
	}
	
	public static StringBuilder SearchByAirport(String inputOAirportCode, String inputDAirportCode) {
		
		StringBuilder result = new StringBuilder();
		String sql;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
			 if(inputOAirportCode.equals("*")) {
			         sql = "SELECT * FROM FLIGHT WHERE DIATACode = ?";
			         stmt = conn.prepareStatement(sql);
			         stmt.setString(1, inputDAirportCode);
			 }else if(inputDAirportCode.equals("*")) {
			         sql = "SELECT * FROM FLIGHT WHERE OIATACode = ?";
			         stmt = conn.prepareStatement(sql);
			         stmt.setString(1,inputOAirportCode);
			 }else {
			         sql = "SELECT * FROM FLIGHT WHERE OIATACode = ? AND DIATACode = ?";
			         stmt = conn.prepareStatement(sql);
			         stmt.setString(1, inputOAirportCode);
			         stmt.setString(2, inputDAirportCode);
			 }
			 
			rs = stmt.executeQuery();
			result.append("FlightNumber&emsp AirlineCode&emsp DepartureTime&emsp&emsp&emsp&emsp&emsp&emsp&emsp ArrivalTime&emsp&emsp&emsp&emsp&emsp&emsp FGate&emsp PilotSSN&emsp AirplaneID&emsp OIATACode&emsp DIATACode&emsp DvsI<br>");
			result.append("------------------------------------------------------------------------------------------------------------------------------------------<br>");
			while(rs.next()) {
				result.append(rs.getString(1) + "&emsp&emsp&emsp&emsp " + rs.getString(2) + "&emsp&emsp&emsp&emsp " + rs.getString(3) + "&emsp&emsp&emsp&emsp "
						+ rs.getString(4) + "&emsp " + rs.getString(5) + "&emsp " + rs.getString(6) + "&emsp " + rs.getString(7) + "&emsp "
						+ rs.getString(8) + "&emsp&emsp&emsp&emsp&emsp " + rs.getString(9) + "&emsp&emsp&emsp&emsp " + rs.getString(10) +"<br>");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB Driver Error!");
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("DB Connection Error!");
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) { e.printStackTrace(); }
			try { if(stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(conn != null) conn.close(); } catch (SQLException e) {e.printStackTrace(); }
		}
		
		return result;
		
	}
	
	public static StringBuilder SearchByPilot(String inputPilotSSN) {

		StringBuilder result = new StringBuilder();

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
			stmt = conn.prepareStatement("SELECT F.* FROM FLIGHT AS F WHERE F.PilotSSN = ?;"	);
			stmt.setString(1, inputPilotSSN);
			rs = stmt.executeQuery();
			result.append("FlightNumber&emsp AirlineCode&emsp DepartureTime&emsp&emsp&emsp&emsp&emsp&emsp&emsp ArrivalTime&emsp&emsp&emsp&emsp&emsp&emsp FGate&emsp PilotSSN&emsp AirplaneID&emsp OIATACode&emsp DIATACode&emsp DvsI<br>");
			result.append("------------------------------------------------------------------------------------------------------------------------------------------<br>");
			while(rs.next()) {
				result.append(rs.getString(1) + "&emsp&emsp&emsp&emsp " + rs.getString(2) + "&emsp&emsp&emsp&emsp " + rs.getString(3) + "&emsp&emsp&emsp&emsp "
						+ rs.getString(4) + "&emsp " + rs.getString(5) + "&emsp " + rs.getString(6) + "&emsp " + rs.getString(7) + "&emsp "
						+ rs.getString(8) + "&emsp&emsp&emsp&emsp&emsp " + rs.getString(9) + "&emsp&emsp&emsp&emsp " + rs.getString(10) +"<br>");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB Driver Error!");
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("DB Connection Error!");
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) { e.printStackTrace(); }
			try { if(stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(conn != null) conn.close(); } catch (SQLException e) {e.printStackTrace(); }
		}
		
		return result;
		
	}
	public static StringBuilder SearchByDate(String inputAirport, String inputDate) {
		
		StringBuilder result = new StringBuilder();

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
			stmt = conn.prepareStatement("SELECT * FROM FLIGHT AS F WHERE DATE(F.DepartureTime)=? AND F.OIATACode=?;"	);
			stmt.setString(1, inputDate);
			stmt.setString(2, inputAirport);
			rs = stmt.executeQuery();
			result.append("FlightNumber&emsp AirlineCode&emsp DepartureTime&emsp&emsp&emsp&emsp&emsp&emsp&emsp ArrivalTime&emsp&emsp&emsp&emsp&emsp&emsp FGate&emsp PilotSSN&emsp AirplaneID&emsp OIATACode&emsp DIATACode&emsp DvsI<br>");
			result.append("------------------------------------------------------------------------------------------------------------------------------------------<br>");
			while(rs.next()) {
				result.append(rs.getString(1) + "&emsp&emsp&emsp&emsp " + rs.getString(2) + "&emsp&emsp&emsp&emsp " + rs.getString(3) + "&emsp&emsp&emsp&emsp "
						+ rs.getString(4) + "&emsp " + rs.getString(5) + "&emsp " + rs.getString(6) + "&emsp " + rs.getString(7) + "&emsp "
						+ rs.getString(8) + "&emsp&emsp&emsp&emsp&emsp " + rs.getString(9) + "&emsp&emsp&emsp&emsp " + rs.getString(10) +"<br>");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB Driver Error!");
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("DB Connection Error!");
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) { e.printStackTrace(); }
			try { if(stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(conn != null) conn.close(); } catch (SQLException e) {e.printStackTrace(); }
		}
		
		return result;
	}
	
	public static StringBuilder AvailableAirline(String inputOAirport, String inputDAirport) {
		StringBuilder result = new StringBuilder();
		String sql = null;
		
		  try {
	          Class.forName(driver);
	          conn = DriverManager.getConnection(url, user, pwd);
	          
	          sql = "(SELECT C.CName ,L.* FROM AIRLINE AS L, COUNTRY AS C, AIRPORT AS A WHERE C.CountryCode = A.AirportCountry AND A.AirportIATACode = ? AND L.AirlineCountry = C.CountryCode ) " + 
	                "UNION " + 
	                "(SELECT C.CName, L.* FROM AIRLINE AS L, COUNTRY AS C, AIRPORT AS A WHERE C.CountryCode = A.AirportCountry AND A.AirportIATACode = ? AND L.AirlineCountry = C.CountryCode )";
	         
	         stmt = conn.prepareStatement(sql);
	         stmt.setString(1, inputOAirport);
	         stmt.setString(2, inputDAirport);

	         // 4. query execute
	         rs = stmt.executeQuery();
	         
	         // 5. print result
	         result.append("ICAOCode&emsp&emsp Airline Name&emsp&emsp&emsp&emsp Country<br>");
	         result.append("----------------------------------------------------------------------------------------------------------<br>");
	         while(rs.next()) {
	             String CName = rs.getString(1);
	             String AirlineCountry = rs.getString(2);
	             String AirlineName = rs.getString(3);
	             String ICAOCode = rs.getString(4);
	             
	            result.append(ICAOCode + "&emsp&emsp&emsp&emsp&emsp&emsp " + AirlineName + "&emsp&emsp&emsp&emsp&emsp " + AirlineCountry + "(" + CName + ")"+ "<br>");
	         }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB Driver Error!");
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("DB Connection Error!");
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) { e.printStackTrace(); }
			try { if(stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(conn != null) conn.close(); } catch (SQLException e) {e.printStackTrace(); }
		}
		
		return result;
	}
	
	public static StringBuilder AvailableAirplane(String airlineCode, String date) {
		StringBuilder result = new StringBuilder();
		String sql = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
		    sql = "SELECT P.* FROM Airplane AS P, AIRLINE AS L "
			            + "WHERE L.ICAOCode=? AND P.AirlineCode=L.ICAOCode AND P.AActivity='Available' "
			            + "AND P.AirplaneID NOT IN ( SELECT F.AirplaneID FROM FLIGHT AS F "
			            + "WHERE DATE(F.DepartureTime) = ? "
			            + "OR DATE(DATE_ADD(F.DepartureTime, INTERVAL 1 DAY))=? "
			            + "OR DATE(DATE_SUB(F.DepartureTime, INTERVAL 1 DAY))=?)";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, airlineCode);
		    stmt.setString(2, date);
		    stmt.setString(3, date);
		    stmt.setString(4, date);
		    
			rs = stmt.executeQuery();
			result.append("Airplane ID&emsp TypeCode&emsp&emsp&emsp Occupant&emsp&emsp&emsp Activity&emsp&emsp&emsp Airline Code&emsp&emsp Model <br>");
			result.append("----------------------------------------------------------------------------------------------------------<br>");
			while(rs.next()) {
				result.append(rs.getString(1) + "&emsp&emsp&emsp&emsp " + rs.getString(2) + "&emsp&emsp&emsp&emsp "
						+ rs.getInt(4) + "&emsp&emsp&emsp&emsp&emsp&emsp " + rs.getString(5) + "&emsp&emsp&emsp " + rs.getString(6) + "&emsp&emsp&emsp&emsp "+ rs.getString(3) +"<br>");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB Driver Error!");
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("DB Connection Error!");
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) { e.printStackTrace(); }
			try { if(stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(conn != null) conn.close(); } catch (SQLException e) {e.printStackTrace(); }
		}
		
		return result;
	}
	
	public static StringBuilder AvailablePilot(String DDate, String airlineCode) {
		StringBuilder result = new StringBuilder();

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
			stmt = conn.prepareStatement("SELECT P.* FROM PILOT AS P, AIRLINE AS L " 
					+ "WHERE L.ICAOCode=? AND P.AirlineCode=L.ICAOCode AND P.PActivity='Available' "
					+ "AND P.SSN NOT IN ( SELECT F.PilotSSN FROM FLIGHT AS F "
					+ "WHERE DATE(F.DepartureTime) = ? "
					+ "OR DATE(DATE_ADD(F.DepartureTime, INTERVAL 1 DAY))=? "
					+ "OR DATE(DATE_SUB(F.DepartureTime, INTERVAL 1 DAY))=?);"	);
			
			stmt.setString(1, airlineCode);
			stmt.setString(2, DDate);
			stmt.setString(3,  DDate);
			stmt.setString(4,  DDate);

			rs = stmt.executeQuery();
			result.append("Full Name&emsp&emsp&emsp PActivity&emsp&emsp&emsp SSN&emsp&emsp&emsp&emsp&emsp&emsp BirthDate&emsp&emsp&emsp License Number&emsp EPTA Grade&emsp Flight Experience<br>");
			result.append("------------------------------------------------------------------------------------------------<br>");
			while(rs.next()) {
				String FullName = rs.getString(1);
				String PActivity = rs.getString(2);
				String SSN = rs.getString(3);
				String BDate = rs.getString(4);
				int LicenseNum = rs.getInt(5);
				int EPTAgrade = rs.getInt(6);
				int FExperience = rs.getInt(7);

				result.append(FullName + "&emsp&emsp " + PActivity + "&emsp&emsp " + SSN + "&emsp&emsp "
								+ BDate + "&emsp&emsp " + LicenseNum + "&emsp&emsp&emsp&emsp&emsp " + EPTAgrade + "&emsp&emsp&emsp&emsp&emsp&emsp " + FExperience + "<br>");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB Driver Error!");
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("DB Connection Error!");
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) { e.printStackTrace(); }
			try { if(stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(conn != null) conn.close(); } catch (SQLException e) {e.printStackTrace(); }
		}
		
		return result;
	}
	
	public static StringBuilder AvailableGate(String DDate, String DTime, String airportCode) {
		StringBuilder result = new StringBuilder();

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
			stmt = conn.prepareStatement("SELECT G.* FROM AIRPORT_GATE G, AIRPORT AS A "
					+ "WHERE A.AirportIATACode = ? AND G.AirportCode = A.AirportIATACode "
					+ "AND G.Gate NOT IN ( SELECT F.FGate FROM FLIGHT AS F "
					+ "WHERE DATE(F.DepartureTime) = ? "
					+ "OR TIME(DATE_ADD(F.DepartureTime, INTERVAL 2 HOUR))=? "
					+ "OR TIME(DATE_SUB(F.DepartureTime, INTERVAL 2 HOUR))=?);");
			
			stmt.setString(1, airportCode);
			stmt.setString(2, DDate);
			stmt.setString(3, DTime);
			stmt.setString(4, DTime);

			rs = stmt.executeQuery();
			
			while(rs.next()) {
				String Gate = rs.getString(1);
				int Terminal = rs.getInt(2);

				result.append("Terminal " + Terminal + " Gate "+ Gate + " is available.<br>");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB Driver Error!");
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("DB Connection Error!");
		} finally {
			try { if(rs != null) rs.close(); } catch(SQLException e) { e.printStackTrace(); }
			try { if(stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(conn != null) conn.close(); } catch (SQLException e) {e.printStackTrace(); }
		}
		
		return result;
	}
	
	public static String checkDorI(String OIATACode, String DIATACode) {
	    
	    String result = null;
	 
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		String sql1 = null;
		String sql2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;

		
		try{
		    // 1. driver loading
			Class.forName(driver);
			
			// 2. connection
			conn = DriverManager.getConnection(url, user, pwd);
			
			sql1 = "SELECT C.CountryCode FROM AIRPORT AS A, COUNTRY AS C WHERE A.AirportCountry = C.CountryCode AND A.AirportIATACode = ?";
			sql2 = "SELECT C.CountryCode FROM AIRPORT AS A, COUNTRY AS C WHERE A.AirportCountry = C.CountryCode AND A.AirportIATACode = ?";		
			
			pstmt1 = conn.prepareStatement(sql1);
			pstmt2 = conn.prepareStatement(sql2);
			pstmt1.setString(1, OIATACode);
			pstmt2.setString(1, DIATACode);
			
			// 4. query execute
			rs1 = pstmt1.executeQuery();
			rs2 = pstmt2.executeQuery();
			
			// 5. print result
			String OCountry = null, DCountry = null;
			while(rs1.next() && rs2.next()){
				OCountry = rs1.getString(1);
				DCountry = rs2.getString(1);
			}
			if(OCountry.equals(DCountry)) {
				result = "Domestic";
			}else {
				result = "International";
			}
		}
		catch( ClassNotFoundException e){
		    System.out.println("Driver loading failure");
		}
		catch( SQLException e){
		    System.out.println("Error " + e);
		}
		finally{
		    try{
		        if( conn != null && !conn.isClosed()){
		            conn.close();
		        }
		        if( pstmt1 != null && !pstmt1.isClosed() && pstmt2 != null && !pstmt2.isClosed() ){
		            pstmt1.close();
		            pstmt2.close();
		        }
		        if( rs1 != null && !rs1.isClosed() && rs2 != null && !rs2.isClosed()){
		            rs1.close();
		            rs2.close();
		        }
		    }
		    catch( SQLException e){
		        e.printStackTrace();
		    }
		}
		
	    return result;
  }

	public static String findflightNum(String AirlineCode) {
	  	
	  	String flightNum = null;
		String sql = null;
		
		try{
		    // 1. driver loading
			Class.forName(driver);
			
			// 2. connection
			conn = DriverManager.getConnection(url, user, pwd);
			
			// 3. Prepared Statement
			sql = "SELECT FlightCount FROM AIRLINE WHERE ICAOCode = ? ";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, AirlineCode);
			
			rs = stmt.executeQuery();
			
			// 5. print result
			String FNum = null;
			while(rs.next()){
			    int FCount = rs.getInt(1);
			    int NewFlight = FCount + 1;
			    FNum = String.format("%03d", NewFlight);
			}
			flightNum = AirlineCode + FNum;
		}
		catch( ClassNotFoundException e){
		    System.out.println("Driver loading failure");
		}
		catch( SQLException e){
		    System.out.println("Error " + e);
		}
		finally{
		    try{
		        if( conn != null && !conn.isClosed()){
		            conn.close();
		        }
		        if( stmt != null && !stmt.isClosed()){
		            stmt.close();
		        }
		    }
		    catch( SQLException e){
		        e.printStackTrace();
		    }
		} 
	  return flightNum;
  }
	public static void updatePilotInfo(String flightNum) {
		  
		String sql = null;
		
		try{
		    // 1. driver loading
			Class.forName(driver);
			
			// 2. connection
			conn = DriverManager.getConnection(url, user, pwd);
			
			// 3. Prepared Statement
			sql = "UPDATE PILOT AS P1, PILOT AS P2, FLIGHT AS F " + 
					"SET P2.FExperience=P1.FExperience + (SELECT HOUR(TIMEDIFF(F.ArrivalTime, F.DepartureTime)) FROM FLIGHT AS F WHERE F.FlightNumber = ? ) " + 
					"WHERE P2.SSN=F.PilotSSN AND P1.SSN=P2.SSN;";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, flightNum);
			
			stmt.executeUpdate();
			
		}
		catch( ClassNotFoundException e){
		    System.out.println("Driver loading failure");
		}
		catch( SQLException e){
		    System.out.println("Error " + e);
		}
		finally{
		    try{
		        if( conn != null && !conn.isClosed()){
		            conn.close();
		        }
		        if( stmt != null && !stmt.isClosed()){
		            stmt.close();
		        }
		    }
		    catch( SQLException e){
		        e.printStackTrace();
		    }
		} 
  }

  //Function to update the flight count(used when creating flight number) of the airline when inserting flight is complete.
	public static void updateAirlineInfo(String flightNum) {
	 
		String sql = null;
	
		try{
		    // 1. driver loading
			Class.forName(driver);
			
			// 2. connection
			conn = DriverManager.getConnection(url, user, pwd);
			
			// 3. Prepared Statement
			sql = "UPDATE AIRLINE AS A1, AIRLINE AS A2, FLIGHT AS F " + 
					"SET A1.FlightCount=A2.FlightCount + 1 " + 
					"WHERE A1.ICAOCode = A2.ICAOCode AND F.AirlineCode = A1.ICAOCode AND F.FlightNumber = ?;";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, flightNum);
			
			stmt.executeUpdate();
			
		}
		catch( ClassNotFoundException e){
		    System.out.println("Driver loading failure");
		}
		catch( SQLException e){
		    System.out.println("Error " + e);
		}
		finally{
		    try{
		        if( conn != null && !conn.isClosed()){
		            conn.close();
		        }
		        if( stmt != null && !stmt.isClosed()){
		            stmt.close();
		        }
		    }
		    catch( SQLException e){
		        e.printStackTrace();
		    }
		}
  }

	
	public static void InsertFlight(String flightNum, String airlineCode, String departureDate, String arrivalDate, String gate, String pilotSSN, String airplaneID, String OIATACode, String DIATACode, String DorI) {
		      
			String sql = null;
			
			try{
		    // 1. driver loading
				Class.forName(driver);
			
				// 2. connection
				conn = DriverManager.getConnection(url, user, pwd);
				
				// 3. Prepared Statement
				sql = "INSERT INTO FLIGHT (FlightNumber, AirlineCode, DepartureTime, ArrivalTime, FGate, PilotSSN, AirplaneID, OIATACode, DIATACode, DvsI) "
		        		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, flightNum);
				stmt.setString(2, airlineCode);
				stmt.setString(3, departureDate);
				stmt.setString(4, arrivalDate);
				stmt.setString(5, gate);
				stmt.setString(6, pilotSSN);
				stmt.setString(7, airplaneID);
				stmt.setString(8, OIATACode);
				stmt.setString(9, DIATACode);
				stmt.setString(10, DorI);
				
				// 4. query execute
				stmt.executeUpdate();
				
				// 5. update pilot, airline information
				updatePilotInfo(flightNum);
				updateAirlineInfo(flightNum);
			}
			catch( ClassNotFoundException e){
			    System.out.println("Driver loading failure");
			}
			catch( SQLException e){
			    System.out.println("Error " + e);
			}
			finally{
			    try{
			        if( conn != null && !conn.isClosed()){
			            conn.close();
			        }
			        if( stmt != null && !stmt.isClosed()){
			            stmt.close();
			        }
			        if( rs != null && !rs.isClosed()){
			            rs.close();
			        }
			    }
			    catch( SQLException e){
				        e.printStackTrace();
			    }
		} 
		  }

	
	public static void main(String[] args) {
		
		url = args[0];
		user = args[1];
		pwd = args[2];
		driver = args[3];
		
		new FlightManage();

	}
	
	
}

