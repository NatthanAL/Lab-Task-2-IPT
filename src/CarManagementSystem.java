import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CarManagementSystem extends JFrame implements ActionListener {

    JPanel panelTitle, panelGroupBox, panelButton;
    JLabel lblTitle, lblID, lblSerial, lblMake, lblModel, lblColour, lblYear;
    JTextField tfID, tfSerial, tfMake, tfModel, tfColour, tfYear;

    public CarManagementSystem() {

        panelTitle = new JPanel();
        panelTitle.setLayout(new BorderLayout());
        panelGroupBox = new JPanel();
        panelGroupBox.setBorder(BorderFactory.createTitledBorder("Car Details"));
        panelGroupBox.setLayout(new GridLayout(1, 2));
        panelButton = new JPanel();

        lblTitle = new JLabel("WELCOME TO THE CAR MANAGEMENT PAGE");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setVerticalAlignment(SwingConstants.CENTER);

        lblID = new JLabel("Car ID");
        lblSerial = new JLabel("Serial");
        lblMake = new JLabel("Car Make");
        lblModel = new JLabel("Car Model");
        lblColour = new JLabel("Colour");
        lblYear = new JLabel("Year");

        tfID = new JTextField(10);
        tfID.setEnabled(false);
        tfSerial = new JTextField(10);
        tfMake = new JTextField(10);
        tfModel = new JTextField(10);
        tfColour = new JTextField(10);
        tfYear = new JTextField(10);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(this);

        panelTitle.add(lblTitle, BorderLayout.CENTER);

        JPanel panelList = new JPanel(new GridLayout(0, 1));
        panelList.add(lblID);
        panelList.add(lblSerial);
        panelList.add(lblMake);
        panelList.add(lblModel);
        panelList.add(lblColour);
        panelList.add(lblYear);

        JPanel panelInputList = new JPanel(new GridLayout(0, 1));
        panelInputList.add(tfID);
        panelInputList.add(tfSerial);
        panelInputList.add(tfMake);
        panelInputList.add(tfModel);
        panelInputList.add(tfColour);
        panelInputList.add(tfYear);

        panelGroupBox.add(panelList);
        panelGroupBox.add(panelInputList);

        panelButton.add(saveButton);
        panelButton.add(searchButton);
        panelButton.add(deleteButton);
        panelButton.add(exitButton);

        setLayout(new GridLayout(3, 1));
        add(panelTitle);
        add(panelGroupBox);
        add(panelButton);

        setTitle("Car Management System");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Save")) {
            saveRecord();
        } else if (e.getActionCommand().equals("Search")) {
            searchRecord();
        } else if (e.getActionCommand().equals("Delete")) {
            deleteRecord();
        } else if (e.getActionCommand().equals("Exit")) {
            exitApplication();
        }
    }

    private void saveRecord() {
        String serial = tfSerial.getText();
        String make = tfMake.getText();
        String model = tfModel.getText();
        String colour = tfColour.getText();
        int year = Integer.parseInt(tfYear.getText());

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/CARS", "root", "");
             PreparedStatement pstmt = con.prepareStatement("INSERT INTO car_info (serialNum, make, model, colour, year) VALUES (?, ?, ?, ?, ?)")) {

            pstmt.setString(1, serial);
            pstmt.setString(2, make);
            pstmt.setString(3, model);
            pstmt.setString(4, colour);
            pstmt.setInt(5, year);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "The record has been saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save the record", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save the record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid year format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchRecord() {
        String carID = JOptionPane.showInputDialog(this, "Enter Car ID:");
        if (carID != null && !carID.isEmpty()) {
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/CARS", "root", "");
                 PreparedStatement pstmt = con.prepareStatement("SELECT * FROM car_info WHERE carID = ?")) {

                pstmt.setInt(1, Integer.parseInt(carID));
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    tfID.setText(Integer.toString(rs.getInt("carID")));
                    tfSerial.setText(rs.getString("serialNum"));
                    tfMake.setText(rs.getString("make"));
                    tfModel.setText(rs.getString("model"));
                    tfColour.setText(rs.getString("colour"));
                    tfYear.setText(Integer.toString(rs.getInt("year")));
                } else {
                    JOptionPane.showMessageDialog(this, "Record not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to search for the record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid car ID format", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteRecord() {
        String carID = JOptionPane.showInputDialog(this, "Enter Car ID:");
        if (carID != null && !carID.isEmpty()) {
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/CARS", "root", "");
                 PreparedStatement pstmt = con.prepareStatement("DELETE FROM car_info WHERE carID = ?")) {

                pstmt.setInt(1, Integer.parseInt(carID));
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "The record has been deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Record not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to delete the record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid car ID format", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exitApplication() {
        int confirmed = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit the application?", "Confirm Exit",
                JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new CarManagementSystem();
    }
}
