/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.luthfiarifin.bad_tp_2;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author luthfiarifin
 */
public class BAD_TP_2 {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtName, txtAddress, txtNIK;
    private JFormattedTextField txtBirthdate;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new BAD_TP_2()::createAndShowGUI);
    }

    private void createAndShowGUI() {
        frame = new JFrame("Clinic CRUD Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel lblName = new JLabel("Name:");
        JLabel lblAddress = new JLabel("Address:");
        JLabel lblNIK = new JLabel("NIK:");
        JLabel lblBirthdate = new JLabel("Birthdate:");

        txtName = new JTextField(50);
        txtAddress = new JTextField(50);
        txtNIK = new JTextField(50);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        txtBirthdate = new JFormattedTextField(format);
        txtBirthdate.setColumns(50);

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnList = new JButton("List");
        JButton btnExit = new JButton("Exit");

        JButton btnPrev = new JButton("Prev");
        JButton btnNext = new JButton("Next");

        // Set table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("No");
        tableModel.addColumn("Name");
        tableModel.addColumn("Address");
        tableModel.addColumn("NIK");
        tableModel.addColumn("Birthdate");

        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        // Set layout
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel txtPanel = new JPanel();
        txtPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        txtPanel.add(lblName, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        txtPanel.add(txtName, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        txtPanel.add(lblAddress, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        txtPanel.add(txtAddress, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        txtPanel.add(lblNIK, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        txtPanel.add(txtNIK, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        txtPanel.add(lblBirthdate, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        txtPanel.add(txtBirthdate, constraints);

        frame.add(txtPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnPrev);
        buttonPanel.add(btnNext);
        buttonPanel.add(btnList);
        buttonPanel.add(btnExit);

        frame.add(buttonPanel);

        frame.add(scrollPane);

        btnAdd.addActionListener((ActionEvent e) -> {
            addData();
        });

        btnUpdate.addActionListener((ActionEvent e) -> {
            updateData();
        });

        btnDelete.addActionListener((ActionEvent e) -> {
            deleteData();
        });

        btnList.addActionListener((ActionEvent e) -> {
            showDataList();
        });

        btnExit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        btnPrev.addActionListener((ActionEvent e) -> {
            showPrevData();
        });

        btnNext.addActionListener((ActionEvent e) -> {
            showNextData();
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    setFields(selectedRow);
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private void setFields(int row) {
        txtName.setText((String) tableModel.getValueAt(row, 1));
        txtAddress.setText((String) tableModel.getValueAt(row, 2));
        txtNIK.setText((String) tableModel.getValueAt(row, 3));
        txtBirthdate.setText((String) tableModel.getValueAt(row, 4));
    }

    private void addData() {
        String name = txtName.getText();
        String address = txtAddress.getText();
        String nik = txtNIK.getText();
        String birthdate = txtBirthdate.getText();

        if (!isNIKExists(nik)) {
            Object[] rowData = {tableModel.getRowCount() + 1, name, address, nik, birthdate};
            tableModel.addRow(rowData);
            clearFields();
        } else {
            showErrorDialog(frame, "NIK already exists!");
        }
    }

    private void updateData() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            String name = txtName.getText();
            String address = txtAddress.getText();
            String nik = txtNIK.getText();
            String birthdate = txtBirthdate.getText();

            if (!isNIKExists(nik, selectedRow)) {
                tableModel.setValueAt(name, selectedRow, 1);
                tableModel.setValueAt(address, selectedRow, 2);
                tableModel.setValueAt(nik, selectedRow, 3);
                tableModel.setValueAt(birthdate, selectedRow, 4);
                clearFields();
            } else {
                showErrorDialog(frame, "NIK already exists!");
            }
        } else {
            showErrorDialog(frame, "Please select a row");
        }
    }

    private void deleteData() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            clearFields();
        } else {
            showErrorDialog(frame, "Please select a row");
        }
    }

    private void showDataList() {
        JFrame listFrame = new JFrame("Patient List");
        JTable listTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(listTable);

        listFrame.add(scrollPane);
        listFrame.pack();
        listFrame.setVisible(true);
    }

    private boolean isNIKExists(String nik) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 3).equals(nik)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNIKExists(String nik, int excludeRow) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (i != excludeRow && tableModel.getValueAt(i, 3).equals(nik)) {
                return true;
            }
        }
        return false;
    }

    private void clearFields() {
        txtName.setText("");
        txtAddress.setText("");
        txtNIK.setText("");
        txtBirthdate.setText("");
    }

    private void showPrevData() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow > 0) {
            table.setRowSelectionInterval(selectedRow - 1, selectedRow - 1);
            setFields(selectedRow - 1);
        } else {
            showErrorDialog(frame, "No previous data!");
        }
    }

    private void showNextData() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow < table.getRowCount() - 1) {
            table.setRowSelectionInterval(selectedRow + 1, selectedRow + 1);
            setFields(selectedRow + 1);
        } else {
            showErrorDialog(frame, "No next data!");
        }
    }

    private void showMessageDialog(Component parentComponent, String message, String title, int messageType) {
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
    }

    private void showErrorDialog(Component parentComponent, String message) {
        showMessageDialog(parentComponent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
