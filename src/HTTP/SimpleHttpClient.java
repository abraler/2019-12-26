package HTTP;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SimpleHttpClient {
    public static void main(String[] args) throws IOException {
        String request = "GET / HTTP/1.0\r\nHost: www.bitedu.vip\r\n\r\n";

        Socket socket = new Socket("www.bitedu.vip", 80);
        socket.getOutputStream().write(request.getBytes("UTF-8"));
        // 版本   状态码     状态描述
        // 响应头打印
        // 把响应正文保存下来
        byte[] bytes = new byte[4096];
        int 第一次读到的数据长度 = socket.getInputStream().read(bytes);
        // 假设 4096 字节已经包含 响应行 + 所有响应头 + 一部分正文
        int index = -1;
        for (int i = 0; i < 第一次读到的数据长度 - 3; i++) {
            if (bytes[i] == '\r' && bytes[i+1] == '\n' && bytes[i+2] == '\r' && bytes[i+3] == '\n') {
                index = i;
                break;
            }
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes, 0, index + 4);
        Scanner scanner = new Scanner(byteArrayInputStream, "UTF-8");
        String statusLine = scanner.nextLine();
        System.out.println("状态行: " + statusLine);
        String[] group = statusLine.split(" ");
        System.out.println("响应版本: " + group[0].trim());
        System.out.println("响应状态码: " + group[1].trim());
        System.out.println("响应状态描述: " + group[2].trim());
        String line;
        int 正文长度 = -1;
        while (!(line = scanner.nextLine()).isEmpty()) {
            String[] kv = line.split(":");
            String key = kv[0].trim();
            String value = kv[1].trim();
            System.out.println("响应头: " + key + " = " + value);
            if (key.equalsIgnoreCase("Content-Length")) {
                正文长度 = Integer.valueOf(value);
            }
        }
        System.out.println(正文长度);
        System.out.println(index);

        int 已经读了 = 第一次读到的数据长度 - index - 4;
        int 还应该读 = 正文长度 - 已经读了;
        byte[] body = new byte[正文长度];
        System.arraycopy(bytes, index + 4, body, 0, 已经读了);
        int 实际读了 = socket.getInputStream().read(body, 已经读了, 还应该读);
        System.out.println(已经读了);
        System.out.println(还应该读);
        System.out.println(实际读了);
        //System.out.println(new String(body, 0, body.length, "UTF-8"));
        FileOutputStream fileOutputStream = new FileOutputStream("百度.html");
        fileOutputStream.write(body);

    }

}
