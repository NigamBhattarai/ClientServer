/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;

/**
 *
 * @author anmol
 */
public class ClientApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Test test = new Test();
        test.setDefaultCloseOperation(3);
        test.waitForPackets();//calling wait for packets
    }
    
}
