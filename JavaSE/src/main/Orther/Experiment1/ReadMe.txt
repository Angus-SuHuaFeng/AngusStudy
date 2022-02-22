1.            connected = true;
              System.out.println("FTP服务器连接成功...");
                应放到try里而不是catch之后
2.            command = dataInputStream.readUTF();
              如果客户端没有操作，直接停止会报错，因为服务端没有读到数据，readUTF
              ServerSocket serverSocket = new ServerSocket(8888);
     //            处理用户指令
                 String command;
                 while (true){
                     Socket accept = serverSocket.accept();
                     DataOutputStream dataOutputStream = new DataOutputStream(accept.getOutputStream());
                     DataInputStream dataInputStream = new DataInputStream(accept.getInputStream());
                     if (accept.isConnected()){
                         command = dataInputStream.readUTF();
                         if (command.startsWith("put ")){
                             receive(command.substring(4),dataInputStream);
                         }else if (command.startsWith("get ")){
                             send(command.substring(4),dataOutputStream);
                         }else if (command.equals("disconnect")){
                             System.out.println(accept.getPort()+"已退出连接");
                             accept.close();
                         }
                     }
                 }
3.