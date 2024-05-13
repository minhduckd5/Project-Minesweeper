package src;

// import javax.swing.*;

// class InputPanel {
    
//     public static void getInput() {
//         JTextField widthField = new JTextField(5);
//         JTextField heightField = new JTextField(5);
//         JTextField minesField = new JTextField(5);

//         JPanel myPanel = new JPanel();
//         myPanel.add(new JLabel("Width:"));
//         myPanel.add(widthField);
//         myPanel.add(Box.createHorizontalStrut(15)); // a spacer

//         myPanel.add(new JLabel("Height:"));
//         myPanel.add(heightField);
//         myPanel.add(Box.createHorizontalStrut(15)); // a spacer

//         myPanel.add(new JLabel("Mines:"));
//         myPanel.add(minesField);

//         // int result = JOptionPane.showConfirmDialog(null, myPanel, 
//         //        "Please Enter Width, Height and Number of Mines", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);;
//         // if (result == JOptionPane.OK_OPTION) {
//             JOptionPane optionPane = new JOptionPane(myPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
//             JDialog dialog = optionPane.createDialog("Please Enter Width, Height and Number of Mines");
//             dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
//             dialog.setVisible(true);
    
//             if (JOptionPane.OK_OPTION == ((Integer) optionPane.getValue()).intValue()) {
//                     //input to N_ROWS, N_COLS, N_MINES in UI
//                     int width = Integer.parseInt(widthField.getText());
//                     int height = Integer.parseInt(heightField.getText());
//                     int mines = Integer.parseInt(minesField.getText());
//         UI.N_ROWS = height;
//         UI.N_COLS = width;
//         UI.N_MINES = mines;
//         }


//     }
    

// }
import javax.swing.*;

public class InputPanel {
    private static int width;
    private static int height;
    private static int mines;

    public static void getInput() {
        String[] options = {"Beginner", "Intermediate", "Advanced", "Custom"};
        String difficulty = null;
        boolean validInput = false;

        while (!validInput) {
            difficulty = (String) JOptionPane.showInputDialog(null, "Choose difficulty level:",
                    "Difficulty Level", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (difficulty != null) {
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
                    default:
                        validInput = false;
                        while (!validInput) {
                        JTextField widthField = new JTextField(5);
                        JTextField heightField = new JTextField(5);
                        JTextField minesField = new JTextField(5);

                        JPanel myPanel = new JPanel();
                        myPanel.add(new JLabel("Width:"));
                        myPanel.add(widthField);
                        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                        myPanel.add(new JLabel("Height:"));
                        myPanel.add(heightField);
                        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                        myPanel.add(new JLabel("Mines:"));
                        myPanel.add(minesField);

                        JOptionPane optionPane = new JOptionPane(myPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
                        JDialog dialog = optionPane.createDialog("Please Enter Width, Height and Number of Mines");
                        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                        dialog.setVisible(true);

                        if (JOptionPane.OK_OPTION == ((Integer) optionPane.getValue()).intValue()) {
                            try {
                            width = Integer.parseInt(widthField.getText());
                            height = Integer.parseInt(heightField.getText());
                            mines = Integer.parseInt(minesField.getText());
                            if (width < 9 || height < 9 || mines < 10) {
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
