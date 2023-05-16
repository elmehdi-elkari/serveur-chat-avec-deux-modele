package blocking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadBlockingChatServer extends Thread {

    List<Socket> clientList = new ArrayList<>();
    private int clientCompteur;
    public static void main(String[] args) {
        new MultiThreadBlockingChatServer().start();
    }

    @Override
    public void run() {
        System.out.println("Le Serveur démarer sur le port 1234");
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            while (true){
                Socket socket = serverSocket.accept();
                clientList.add(socket);
                new Conversation(socket,++clientCompteur,clientList).start();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Conversation extends Thread{
        private Socket socket ;
        private int clientId;
        private List<Socket> clientList;
        public Conversation(Socket socket,int clientId,List<Socket> clientList){
            this.socket = socket;
            this.clientId = clientId;
            this.clientList = clientList;
        }

        @Override
        public void run() {
            try {

                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os,true);

                String ip = socket.getRemoteSocketAddress().toString();
                System.out.println("new Client Connection => "+clientId+" Ip: "+ip);
                pw.println("Welcome, your Id is => "+clientId);

                while (true) {
                    String requete = br.readLine();
                    System.out.println("nouveau requête => Ip: "+ip);

                    String[] messages = requete.split("=>");

                    if(messages.length > 1)
                        multicastMessage(messages[0],messages[1]);
                    else
                        broadcastMessage(clientList,requete);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void multicastMessage(String reciever , String message) throws IOException {
            String[] recievers = reciever.split(",");

            for (String ss : recievers) {
                Socket s = clientList.get(Integer.parseInt(ss)-1);
                OutputStream os = s.getOutputStream();
                PrintWriter pw = new PrintWriter(os,true);
                pw.println(message);
            }
        }

        private void broadcastMessage(List<Socket> clientList,String message) throws IOException {

            for (Socket s : clientList) {
                OutputStream os = s.getOutputStream();
                PrintWriter pw = new PrintWriter(os,true);
                if(s != socket)
                    pw.println(message);
            }

        }

    }
}
