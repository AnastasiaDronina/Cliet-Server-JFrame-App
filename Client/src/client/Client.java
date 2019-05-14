package client;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client extends javax.swing.JFrame {
    
    String address = "localhost";
    int port = 2222, port2 = 1234;
    Boolean isConnected1 = false, isConnected2 = false;
    
    Socket sock, sock2;
    BufferedReader reader, reader2;
    PrintWriter writer, writer2;    
    
    public Client() {
        initComponents();
    }
    
    public void ListenThread() 
    {
         Thread IncomingReader = new Thread(new IncomingReader());
         IncomingReader.start();
    }
    
    public void ListenThread2() 
    {
         Thread IncomingReader2 = new Thread(new IncomingReader2());
         IncomingReader2.start();
    }
    
    public void sendDisconnect1() 
    {
        String bye = ("fromServer1:Disconnect");
        try
        {
            writer.println(bye); 
            writer.flush(); 
        } catch (Exception e) 
        {
            txt_area1.append("Не получает отправить серверу сообщение о том, что соединение прервано...\n");
        }
    }
    
    public void Disconnect1() 
    {
        try 
        {
            txt_area1.append("Соединение завершено.\n");
            sock.close();
        } catch(Exception ex) {
            txt_area1.append("При попытке отключиться от сервера произошла ошибка... \n");
        }
        isConnected1 = false;
    }
    
    
    
    public void sendDisconnect2() 
    {
        String bye = ("fromServer2:Disconnect");
        try
        {
            writer2.println(bye); 
            writer2.flush(); 
        } catch (Exception e) 
        {
            txt_area2.append("Не получает отправить серверу сообщение о том, что соединение прервано...\n");
        }
    }
    
    public void Disconnect2() 
    {
        try 
        {
            txt_area2.append("Соединение завершено.\n");
            sock2.close();
        } catch(Exception ex) {
            txt_area2.append("При попытке отключиться от сервера произошла ошибка... \n");
        }
        isConnected2 = false;
    }
    
    
    
    public class IncomingReader implements Runnable
    {
        @Override
        public void run() 
        {
            String[] data;
            String stream, connect = "Connect";

            try 
            {
                while ((stream = reader.readLine()) != null) 
                {
                     data = stream.split(":");
                     if (data[1].equals(connect))
                     {
                        txt_area1.append("\n"+data[2]+"\n"+data[3]+"\n"+data[4]);
                     } 
                }
           }catch(Exception ex) { }
        }
    }
    
    public class IncomingReader2 implements Runnable
    {
        @Override
        public void run() 
        {
            String[] data2;
            String stream2, connect = "Connect";

            try 
            {
                while ((stream2 = reader2.readLine()) != null) 
                {
                     data2 = stream2.split(":");
                     if (data2[1].equals(connect))
                     {
                        txt_area2.append("\n"+data2[2]+"\n"+data2[3]);
                     }                      
                }
           }catch(Exception ex) { }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        server1 = new javax.swing.JButton();
        server2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_area1 = new javax.swing.JTextArea();
        exit1 = new javax.swing.JButton();
        exit2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_area2 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Клиент");

        server1.setText("Сервер 1");
        server1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                server1ActionPerformed(evt);
            }
        });

        server2.setText("Сервер 2");
        server2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                server2ActionPerformed(evt);
            }
        });

        txt_area1.setColumns(20);
        txt_area1.setRows(5);
        jScrollPane1.setViewportView(txt_area1);

        exit1.setText("Отключиться от Сервера 1");
        exit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exit1ActionPerformed(evt);
            }
        });

        exit2.setText("Отключиться от Сервера 2");
        exit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exit2ActionPerformed(evt);
            }
        });

        txt_area2.setColumns(20);
        txt_area2.setRows(5);
        jScrollPane3.setViewportView(txt_area2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(server1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(server2)
                .addGap(147, 147, 147))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(exit1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exit2)
                .addGap(72, 72, 72))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(server1)
                    .addComponent(server2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exit1)
                    .addComponent(exit2))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void server1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_server1ActionPerformed
        
            try {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println("server1:Connect");
                writer.flush(); 
                isConnected1 = true; 
                
            } 
            catch (Exception ex) 
            {
                txt_area1.append("Подключение не удалось... \n");
            }            
            ListenThread();
        
               
    }//GEN-LAST:event_server1ActionPerformed

    private void server2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_server2ActionPerformed
        
            try {
                sock2 = new Socket(address, port2);
                InputStreamReader streamreader2 = new InputStreamReader(sock2.getInputStream());
                reader2 = new BufferedReader(streamreader2);
                writer2 = new PrintWriter(sock2.getOutputStream());
                writer2.println("server2:Connect");
                writer2.flush(); 
                isConnected2 = true; 
            } 
            catch (Exception ex){
                txt_area2.append("Подключение не удалось... \n");
            }            
            ListenThread2();            
         
    }//GEN-LAST:event_server2ActionPerformed

    private void exit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit1ActionPerformed
        sendDisconnect1();
        Disconnect1();
    }//GEN-LAST:event_exit1ActionPerformed

    private void exit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit2ActionPerformed
       sendDisconnect2();
        Disconnect2();
    }//GEN-LAST:event_exit2ActionPerformed

    public static void main(String args[]) {  
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });   
        
        
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exit1;
    private javax.swing.JButton exit2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton server1;
    private javax.swing.JButton server2;
    private static javax.swing.JTextArea txt_area1;
    private static javax.swing.JTextArea txt_area2;
    // End of variables declaration//GEN-END:variables

}