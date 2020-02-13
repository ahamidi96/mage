package mage.client.util;

import mage.client.dialog.RegisterUserDialog;
import mage.remote.interfaces.Connect;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import mage.client.dialog.ConnectDialog;
import mage.client.dialog.RegisterUserDialog;

import javax.swing.*;

public class connectdialog_tests {
    private ConnectDialog connectDialog = new ConnectDialog();
    private RegisterUserDialog registerUserDialog = connectDialog.registerUserDialog;
    private JButton USbutton = connectDialog.btnFindUs;
    private JButton DEbutton = connectDialog.btnFindMain;
    private JButton RegisterNewUser = connectDialog.btnRegister;


    @Test
    public void testXMageDEservernamefill() throws Exception {
        String expected_result = "xmage.de";
        DEbutton.doClick();
        String servername = connectDialog.txtServer.getText();
        assertEquals(servername, expected_result);
    }

    @Test
    public void testXMageUSservernamefill() throws Exception {
        String expected_result = "us.xmage.today";
        USbutton.doClick();
        String servername = connectDialog.txtServer.getText();
        assertEquals(servername, expected_result);
    }

    @Test
    public void testPortNumberfill() throws Exception {
        String expected_result = "17171";
        USbutton.doClick();
        String serverport1 = connectDialog.txtPort.getText();
        DEbutton.doClick();
        String serverport2 = connectDialog.txtPort.getText();
        assertEquals(serverport1, expected_result);
        assertEquals(serverport2, expected_result);
    }

    @Test
    public void testRegisterNewUserpopup() throws Exception{
        RegisterNewUser.doClick();
        assertTrue(registerUserDialog.isVisible());
    }

}
