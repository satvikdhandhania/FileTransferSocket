 
import java.io.*;
import java.net.*;
 
class Server
{
    public static void main(String args[])throws Exception
    {
        System.out.println("Server running...");
 
        /* Listen on port 5555 */
 
        ServerSocket server = new ServerSocket(5555); 
        Socket sk = server.accept();
        System.out.println("Server accepted client");
        InputStream input = sk.getInputStream();
        BufferedReader inReader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
        BufferedWriter outReader = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream()));
 
        //Read the filename
        String filename = inReader.readLine();
 
        if ( !filename.equals("") ){

            outReader.write("READY\n");
            outReader.flush();
        }
 
        FileOutputStream wr = new FileOutputStream(new File("C:\\Users\\Satvik\\Documents\\NetBeansProjects\\Zap\\File\\" + filename));
 
        byte[] buffer = new byte[sk.getReceiveBufferSize()];
 
        int bytesReceived = 0;
 
        while((bytesReceived = input.read(buffer))>0)
        {
           wr.write(buffer,0,bytesReceived);
        }
    }
}