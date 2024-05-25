package src;

import javax.swing.*;

public class InputPanel {
    private static int width;
    private static int height;
    private static int mines;

    public static void getInput() {
        String[] options = {"Beginner", "Intermediate", "Advanced", "Custom"};
        String difficulty = null;                                               //default difficulty level
        boolean validInput = false;                                             //default valid input

        while (!validInput) {                                                   //while true
            difficulty = (String) JOptionPane.showInputDialog(null, "Choose difficulty level:", //option pane
                    "Difficulty Level", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (difficulty != null) {                                           //difficulty is choosen
                switch (difficulty) {
                    case "Beginner":
                        width = 9;
                        height = 9;
                        mines = 10;
                        validInput = true;
                        break;
                    case "Intermediate":
                        width = 16;
                        height = 16;
                        mines = 40;
                        validInput = true;
                        break;
                    case "Advanced":
                        width = 30;
                        height = 16;
                        mines = 99;
                        validInput = true;
                        break;
                    default:                                                    //không thỏa mãn hết case
                        validInput = false;                                     //set false tiếp
                        while (!validInput) {                                   //while true
                        JTextField widthField = new JTextField(5);      //textfield để điền input
                        JTextField heightField = new JTextField(5);
                        JTextField minesField = new JTextField(5);

                        JPanel myPanel = new JPanel();
                        myPanel.add(new JLabel("Width:"));
                        myPanel.add(widthField);
                        myPanel.add(Box.createHorizontalStrut(15)); // khoảng trống
                        myPanel.add(new JLabel("Height:"));
                        myPanel.add(heightField);
                        myPanel.add(Box.createHorizontalStrut(15)); // khoảng trống
                        myPanel.add(new JLabel("Mines:"));
                        myPanel.add(minesField);

                        JOptionPane optionPane = new JOptionPane(myPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION); //Plain message xóa question icon
                        JDialog dialog = optionPane.createDialog("Please Enter Width, Height and Number of Mines");           //tạo dialog
                        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);                                               //set nút close không làm gì
                        dialog.setVisible(true);                                                                                  //hiện dialog

                        if (JOptionPane.OK_OPTION == ((Integer) optionPane.getValue()).intValue()) {
                            try {
                            width = Integer.parseInt(widthField.getText());
                            height = Integer.parseInt(heightField.getText());
                            mines = Integer.parseInt(minesField.getText());
                            if (width < 9 || height < 9 || mines < 10 || width > 30 || height > 24 || mines > 668) {
                                JOptionPane.showMessageDialog(null, "Width and height cannot be smaller than 9 and number of mines cannot be smaller than 10. Please enter again.",
                                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            } else{
                            validInput = true;
                            
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Please enter only numbers.",
                                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        }
                    }else{
                        difficulty = null;
                        break;
                        }
                    }
                    break;
                }
            }
        UI.N_ROWS = height;
        UI.N_COLS = width;
        UI.N_MINES = mines;
        }
    }
}
