package src;

import javax.swing.*;

class InputPanel {
    
    public static void getInput() {
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

        // int result = JOptionPane.showConfirmDialog(null, myPanel, 
        //        "Please Enter Width, Height and Number of Mines", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);;
        // if (result == JOptionPane.OK_OPTION) {
            JOptionPane optionPane = new JOptionPane(myPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
            JDialog dialog = optionPane.createDialog("Please Enter Width, Height and Number of Mines");
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);
    
            if (JOptionPane.OK_OPTION == ((Integer) optionPane.getValue()).intValue()) {
                    //input to N_ROWS, N_COLS, N_MINES in UI
                    int width = Integer.parseInt(widthField.getText());
                    int height = Integer.parseInt(heightField.getText());
                    int mines = Integer.parseInt(minesField.getText());
        UI.N_ROWS = height;
        UI.N_COLS = width;
        UI.N_MINES = mines;
        }


    }
    

}
