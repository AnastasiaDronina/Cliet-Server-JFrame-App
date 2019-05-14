package server2;

import com.sun.management.OperatingSystemMXBean;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.*;
import java.util.*;
//import com.sun.jna.*;

public class Server2 extends javax.swing.JFrame {
    
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
                        OperatingSystemMXBean osMBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                        RuntimeMXBean runtimeMBean = ManagementFactory.getRuntimeMXBean();
                        
                        long totalPhysicalMemory = osMBean.getTotalPhysicalMemorySize();
                        long freePhysicalMemory = osMBean.getFreePhysicalMemorySize();                      
                        long percentOfUsagePhysMem = freePhysicalMemory/(totalPhysicalMemory / 100);
                        
                        long totalVirtualMemory = osMBean.getTotalSwapSpaceSize();
                        long freeVirtualMemory = osMBean.getFreeSwapSpaceSize();
                        long percentOfUsageVirMem = freeVirtualMemory/(totalVirtualMemory / 100);
                        
                        String physical = percentOfUsagePhysMem+"%";
                        String virtual = percentOfUsageVirMem+"%";                    
                        
                        tellEveryone("something:Connect:Процент используемой физической памяти - "
                                +physical+":Процент используемой виртуальной памяти - "+virtual);
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
    
    public Server2() {
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
        setTitle("Сервер 2");

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_start)
                .addGap(136, 136, 136))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(btn_start)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_startActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();
        
        txt_area.append("Сервер 2 запущен...\n");
    }//GEN-LAST:event_btn_startActionPerformed

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server2().setVisible(true);
            }
        });      
    }
    public class ServerStart implements Runnable {
        @Override
        public void run() 
        {
            clientOutputStreams = new ArrayList();
            try {
                ServerSocket serverSock = new ServerSocket(1234);
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
