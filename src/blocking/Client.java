package blocking;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {

            Socket s = new Socket("localhost",1234);

            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            PrintWriter pw = new PrintWriter(os,true);

            new Thread(() -> {
                try {
                    String response ;
                    while ((response = br.readLine())!=null){
                        System.out.println(response);
                    }
                }catch (IOException e){
                    //e.printStackTrace();
                    System.out.println("Perte de la connexion à l’hôte.");
                    System.exit(-1);
                }
            }).start();

            Scanner scanner = new Scanner(System.in);

            while (true){
                String requete = scanner.nextLine();
                pw.println(requete);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
