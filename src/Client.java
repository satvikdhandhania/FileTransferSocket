import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
 
public class Client extends JFrame implements ActionListener {
 
    private JTextField txtFile;
   
    public static void main(String args[]){
  
        Client clientForm = new Client();
        clientForm.Display();
    }
 
    public void Display(){
 
        JFrame frame = new JFrame();
        frame.setTitle("Client");
 
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
 
        JLabel lblFile = new JLabel("Filename:");
       
        txtFile = new JTextField();
        txtFile.setPreferredSize(new Dimension(150,30));
 
        JButton btnTransfer = new JButton("Transfer");
        btnTransfer.addActionListener(this);
 
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        mainPanel.add(lblFile);
        mainPanel.add(txtFile);
        mainPanel.add(btnTransfer);
 
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
 
    public void actionPerformed(ActionEvent e) {
 
 
        JFileChooser fileDlg = new JFileChooser();
        fileDlg.showOpenDialog(this);
        String filename = fileDlg.getSelectedFile().getAbsolutePath();
        txtFile.setText(filename);
 
        try{
 
            Socket sk = new Socket("169.254.6.70", 5555);
            OutputStream output = sk.getOutputStream();
 
            OutputStreamWriter outputStream = new OutputStreamWriter(sk.getOutputStream());
            outputStream.write(fileDlg.getSelectedFile().getName() + "\n");
            outputStream.flush();
            BufferedReader inReader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            String serverStatus = inReader.readLine();
 
            if ( serverStatus.equals("READY") ){
 
                FileInputStream file = new FileInputStream(filename);
 
                byte[] buffer = new byte[sk.getSendBufferSize()];
 
                int bytesRead = 0;
           while((bytesRead = file.read(buffer))>0)
                {
                    output.write(buffer,0,bytesRead);
                }
                output.close();
                file.close();
                sk.close();
                JOptionPane.showMessageDialog(this, "Transfer complete");
            }
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}