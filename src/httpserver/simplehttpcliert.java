package httpserver;

import javafx.concurrent.Task;

import javax.print.attribute.standard.RequestingUserName;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class simplehttpcliert {
    private static class Task implements Runnable{
        private final Socket socket;
        Task(Socket socket){
            this.socket=socket;
        }
        @Override
        public void run() {
            try {
                InputStream is=socket.getInputStream();
                OutputStream os=socket.getOutputStream();
                Request request=Request.parse(is);
                System.out.println(request);
                if (request.path.equalsIgnoreCase("/")) {
                    String body = "alert('必须关掉');";
                    byte[] bodyBuffer = body.getBytes("UTF-8");
                    StringBuilder response = new StringBuilder();
                    response.append("HTTP/1.0 200 OK\r\n");
                    response.append("Content-Type: application/javascript; charset=UTF-8\r\n");
                    response.append("Content-Length: ");
                    response.append(bodyBuffer.length);
                    response.append("\r\n");
                    response.append("\r\n");

                    os.write(response.toString().getBytes("UTF-8"));
                    os.write(bodyBuffer);
                    os.flush();
                } else if (request.path.equalsIgnoreCase("/run")) {
                    String body = "<script src='/'></script>";
                    byte[] bodyBuffer = body.getBytes("UTF-8");
                    StringBuilder response = new StringBuilder();
                    response.append("HTTP/1.0 200 OK\r\n");
                    response.append("Content-Type: text/html; charset=UTF-8\r\n");
                    response.append("Content-Length: ");
                    response.append(bodyBuffer.length);
                    response.append("\r\n");
                    response.append("\r\n");

                    os.write(response.toString().getBytes("UTF-8"));
                    os.write(bodyBuffer);
                    os.flush();
                }else if(request.path.equalsIgnoreCase("/banjia")){
                    StringBuilder respones=new StringBuilder();
                    respones.append("http/1.0 307 Temporary Redirect\r\n");
                    respones.append("Location: /run\r\n");
                    respones.append("\r\n");
                    os.write(respones.toString().getBytes("utf-8"));
                    os.flush();
                }else{
                    StringBuilder respones=new StringBuilder();
                    respones.append("http/1.0 404 NOT Found");
                    respones.append("\r\n");
                    os.write(respones.toString().getBytes("utf-8"));
                    os.flush();
                }


            }catch ( Exception e){

            }
        }
    }
    public static void main(String[] args)throws IOException {
        ServerSocket serverSocket=new ServerSocket(80);
        ExecutorService pool= Executors.newFixedThreadPool(10);
        while(true){
            Socket socket=serverSocket.accept();
            pool.execute(new simplehttpcliert.Task(socket) {
            });
        }
    }
}
