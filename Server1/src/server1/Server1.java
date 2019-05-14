package server1;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server1 extends javax.swing.JFrame {
    
ArrayList clientOutputStreams;
    
    public class ClientHandler implements Runnable	
   {
       BufferedReader reader;
       Socket sock;
       PrintWriter client;

       public ClientHandler(Socket clientSocket, PrintWriter user) 
       {
            client = user;
            try 
            {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex) 
            {
                txt_area.append("Произошла ошибка чтения информации от клиента... \n");
            }

       }

       @Override
       public void run() 
       {
            String message, connect = "Connect", disconnect = "Disconnect";
            String[] data;

            try 
            {
                while ((message = reader.readLine()) != null) 
                {
                    txt_area.append("Получен запрос от клиента: " + message + "\n");
                    data = message.split(":");

                    if (data[1].equals(connect)) 
                    {
                        String computerName = InetAddress.getLocalHost().getHostName();
                        String userName = System.getProperty("user.name");
                        String osName = System.getProperty("os.name");
                        
                        String msg = "Имя компьютера - " + computerName + ":Имя пользователя - " 
                                + userName + ":Версия операционной системы - " + osName ;                       
                        
                        tellEveryone("server1:Connect:"+msg);
                        txt_area.append("Отправил информацию клиенту \n");   
                    } 
                    else if (data[1].equals(disconnect)) 
                    {
                       txt_area.append("Клиент отключен от сервера...");
                    } 
                    else 
                    {
                        txt_area.append("Клиент ничего не запросил... \n");
                    }
                } 
             } 
             catch (Exception ex) 
             {
                txt_area.append("Соединение потеряно. \n");
                ex.printStackTrace();
             } 
	} 
    }
    
    public Server1() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_area = new javax.swing.JTextArea();
        btn_start = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Сервер 1");

        txt_area.setColumns(20);
        txt_area.setRows(5);
        jScrollPane1.setViewportView(txt_area);

        btn_start.setText("Запуск");
        btn_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_startActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(btn_start)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btn_start)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_startActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();
        
        txt_area.append("Сервер 1 запущен...\n");
    }//GEN-LAST:event_btn_startActionPerformed
    public static void main(String args[]) {        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server1().setVisible(true);
            }
        });  
    }
    
    public class ServerStart implements Runnable {
        @Override
        public void run() 
        {
            clientOutputStreams = new ArrayList();
            try {
                ServerSocket serverSock = new ServerSocket(2222);
                while (true) { 
                    Socket clientSock = serverSock.accept();
		    PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
		    clientOutputStreams.add(writer);

		    Thread listener = new Thread(new ClientHandler(clientSock, writer));
		    listener.start();
		}
            }
            catch (Exception ex){
                txt_area.append("Не удалось отправить информацию клиенту... \n");
            }
        }
    }
    
    public void tellEveryone(String message) 
    {
	Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) 
        {
            try 
            {             
                PrintWriter writer = (PrintWriter) it.next();
                writer.flush();
		writer.println(message);
                writer.flush();

            } 
            catch (Exception ex) 
            {
            }
        } 
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_start;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private static javax.swing.JTextArea txt_area;
    // End of variables declaration//GEN-END:variables
}
