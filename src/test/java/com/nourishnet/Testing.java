package com.nourishnet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.awt.*; 
import java.util.ArrayList;
import java.util.List;


public class Testing extends JFrame {
    public Testing() {

        System.out.println(LogIn.getNumberOfUserProfiles());

        //User user = new User();
        //user.setPassword("hello");
        //System.out.println(user.checkPassword("hello"));

        String userDir = System.getProperty("user.dir");
        setLayout(new FlowLayout());
        setSize(500, 500);
        //getContentPane().setBackground(Color.BLACK);
        List<StringImagePair> profiles = LogIn.getUserProfiles();

        for (int i = 0; i < profiles.size(); i++) {
            String buttonText = profiles.get(i).getText();
            JButton button = new JButton(buttonText);

            int newWidth = 200; 
            int newHeight = 150; 

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String userJsonPath = LogIn.getUserJsonPath(buttonText);
                    try {
                        User user = DeserializeUserData.initaliseUserClass(userJsonPath);
                        System.out.println(user.getHeight());

                        //user.changeAge(69);
                        //SerializeUserData.serializeUser(user, userJsonPath);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    

                    
                }
            });

            // GridBagConstraints gbc = new GridBagConstraints();
            // gbc.gridx = 0;
            // gbc.gridy = 0;
            // gbc.insets = new Insets(10, 10, 10, 10); // Padding

            // try{
            //     ArrayList<Recipe> recipe = DeserializeUserData.initaliseRecipeClass();
            //     ImageIcon imageIcon = Tools.getRecipeImage(recipe.get(0).getName());
            //     if(imageIcon!=null){
            //         Image scaledImage = imageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            //         imageIcon = new ImageIcon(scaledImage);
            //         add(new JLabel(imageIcon));
    
            //     }else{
            //         System.out.println("No recipe image");
            //     }
               

            // } catch (Exception e1) {
            //     e1.printStackTrace();
            // }

            ImageIcon imageIcon = profiles.get(i).getImage();
            if (imageIcon != null) {
                
                Image scaledImage = imageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(scaledImage);
                add(new JLabel(imageIcon));
            }

            // gbc.gridy = -1;
            add(button);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Testing window = new Testing();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setBackground(Color.BLACK);

            window.setVisible(true);
        });
    }
}

