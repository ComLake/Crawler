package com.company.util;

import com.pixelmed.dicom.*;
import com.pixelmed.display.SourceImage;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class DCM {
    private JFrame jDisplay;
    private JButton jButtonChosen;
    private JButton jButtonConvert;
    private WindowListener listener;
    private ActionListener actionListener;
    private JPanel jContainer;
    private JLabel jLabelImage;
    private JTree jTree;
    public DCM() {
        init();
    }

    private void init() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                jDisplay = new JFrame();
                jDisplay.setSize(400,500);
                jDisplay.setLocationRelativeTo(null);
                jDisplay.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                listener = new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent windowEvent) {

                    }

                    @Override
                    public void windowClosing(WindowEvent windowEvent) {
                        int result = JOptionPane.showConfirmDialog(
                                null,
                                "Exit?",
                                "Warning",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );
                        if (result == JOptionPane.YES_OPTION){
                            System.exit(0);
                        }
                    }

                    @Override
                    public void windowClosed(WindowEvent windowEvent) {

                    }

                    @Override
                    public void windowIconified(WindowEvent windowEvent) {

                    }

                    @Override
                    public void windowDeiconified(WindowEvent windowEvent) {

                    }

                    @Override
                    public void windowActivated(WindowEvent windowEvent) {

                    }

                    @Override
                    public void windowDeactivated(WindowEvent windowEvent) {

                    }
                };
                jDisplay.addWindowListener(listener);
                jContainer = new JPanel();
                jButtonChosen = new JButton();
                jButtonConvert = new JButton();
                jLabelImage = new JLabel();
                jButtonChosen.setText("Choose Image");
                jButtonChosen.setSize(20, 10);
                jButtonChosen.setLocation(10, 10);
                jLabelImage.setSize(50,50);
                jLabelImage.setLocation(10,40);
                jButtonConvert.setText("Convert");
                jButtonConvert.setSize(20, 10);
                jButtonConvert.setLocation(70, 10);
                jButtonConvert.setSize(50,50);
                jLabelImage.setLocation(10,40);
                jContainer.add(jButtonChosen);
                jContainer.add(jButtonConvert);
                jContainer.add(jLabelImage);
                //create the root node
                DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
                //create the child nodes
                DefaultMutableTreeNode vegetableNode = new DefaultMutableTreeNode("Vegetables");
                DefaultMutableTreeNode fruitNode = new DefaultMutableTreeNode("Fruits");
                //add the child nodes to the root node
                root.add(vegetableNode);
                root.add(fruitNode);
                //create the tree by passing in the root node
                jTree = new JTree(root);
                jContainer.add(jTree);
                actionListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if (actionEvent.getSource().equals(jButtonChosen)){
                            buttonBrowserActionPerformed(jContainer);
                        }else if(actionEvent.getSource().equals(jButtonConvert)){
                        }
                    }
                };
                jButtonChosen.addActionListener(actionListener);
                jDisplay.add(jContainer);
                jDisplay.setVisible(true);
            }
        });
    }
    private void loadAndDisplay(File selectedFile){
        try {
            SourceImage image = new SourceImage(selectedFile.getAbsolutePath());
            Image img = image.getBufferedImage();
            ImageIcon icon = new ImageIcon(img);
            jLabelImage.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DicomException e) {
            e.printStackTrace();
        }
    }
    private void buttonBrowserActionPerformed(JPanel jPanel){
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\User\\Downloads");
        jPanel.add(fileChooser);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            loadAndDisplay(selectedFile);
            System.out.println(selectedFile.getAbsolutePath());
            try {
                DicomInputStream dis = new DicomInputStream(selectedFile);
                AttributeList attributeList = new AttributeList();
                attributeList.read(dis);
                Set<AttributeTag> keys = attributeList.keySet();
                for (AttributeTag key: keys) {
                    System.out.println("("+key.getGroup()+","+key.getElement()+")"+attributeList.get(key).getDelimitedStringValuesOrEmptyString());
                }
//                openDiagramDirectory(selectedFile);
            } catch (IOException | DicomException e) {
                e.printStackTrace();
            }
        }
    }
    public void openDiagramDirectory(File selectedFile){
        try {
            DicomInputStream dicomInputStream = new DicomInputStream(selectedFile);
            AttributeList attributeList = new AttributeList();
            attributeList.read(dicomInputStream);
            DicomDirectory ddr = new DicomDirectory(attributeList);
            jTree.setModel(ddr);
        } catch (IOException | DicomException e) {
            e.printStackTrace();
        }
    }
}
