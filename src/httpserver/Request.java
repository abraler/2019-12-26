package httpserver;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Request {
    String method;
    String path;
    String version;
    Map<String ,String>headers=new HashMap<>();
    public static Request parse(InputStream is){
        Request request=new Request();
        Scanner in=new Scanner(is,"utf-8");
        parseRuestLine(request,in.nextLine());
        String line;
        while(!(line=in.nextLine()).isEmpty()){
            String []grp=line.split(":");
            String key=grp[0];
            String val=grp[1];
            request.headers.put(key,val);

        }
        return request;
    }

    private static void parseRuestLine(Request request, String b) {
        String []grp=b.split(" ");
        request.method=grp[0];
        request.path=grp[1];
        request.version=grp[2];
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", version='" + version + '\'' +
                ", headers=" + headers +
                '}';
    }
}
