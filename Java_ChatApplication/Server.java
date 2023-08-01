import java.io.*;
import java.net.*;

class Server{
    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;
    public Server(){
        try{
            server = new ServerSocket(7778);
            System.out.println("server is ready to accept connections");
            System.out.println("waiting ....");
            socket=server.accept();

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

            startReadings();
            startWritings();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void startReadings(){
        // create a thread to read data from the client using runnable interface
        Runnable r1=()->{
            System.out.println("reader started...");
            try{
                while(true){
                        String msg=br.readLine();
                        if(msg.equals("exit")){
                            System.out.println("Client terminated the chat..");
                            socket.close();
                            break;
                        }
                        System.out.println("Client : "+msg);
                    
                }
            }catch(Exception e){
                System.out.println("Connection terminated");
                // e.printStackTrace();
            }
        };
        new Thread(r1).start();

    }
    
    public void startWritings(){
        Runnable r2=()->{
            System.out.println("Writer started...");
            try{
                while(!socket.isClosed()){
                    
                        BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                        String content=br1.readLine();
                        out.println(content);
                        out.flush();
                        if(content.equals("exit")){
                            socket.close();
                            break;
                        }
                        
                    
                }
                
            }catch(Exception e){
                System.out.println("Connection terminated");
                // e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("this is server.. going to start server ....");
        new Server();
    }
}