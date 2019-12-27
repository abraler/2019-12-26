package HTTP;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ParseURL {
    private static Map<String, Integer> knownPorts = new HashMap<>();
    static {
        knownPorts.put("http", 80);
        knownPorts.put("https", 443);
        knownPorts.put("jdbc:mysql", 3306);
    }

    public static void main(String[] args) throws IOException {
        int index;
        String url = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=c%2B%2B&rsv_pq=a4fc69f200076536&rsv_t=2b96NbhzR2Ir%2BdW1z0tx0yYZJG0xZgadx7dwH0Qkvn6IH793SOANRIZNaj0&rqlang=cn&rsv_enter=1&rsv_dl=tb&rsv_sug3=3&rsv_sug1=3&rsv_sug7=100&rsv_sug2=0&prefixsug=c%252B%252B&rsp=2&inputT=1160&rsv_sug4=1161";
        index = url.indexOf("://");
        String schema = url.substring(0, index);
        url = url.substring(index + 3);
        System.out.println(schema);
        index = url.indexOf("/");
        String hostAndPort = url.substring(0, index);
        url = url.substring(index);
        String host;
        int port;
        if (hostAndPort.contains(":")) {
            String[] group = hostAndPort.split(":");
            host = group[0];
            port = Integer.valueOf(group[1]);
        } else {
            host = hostAndPort;
            port = knownPorts.get(schema);
        }
        System.out.println(host);
        System.out.println(port);

        index = url.indexOf("?");
        String path = url.substring(0, index);
        url = url.substring(index + 1);
        System.out.println(path);

        String queryString;
        String segment = "";
        index = url.indexOf("#");
        if (index != -1) {
            queryString = url.substring(0, index);
            segment = url.substring(index + 1);
        } else {
            queryString = url;
        }
        String[] kv = queryString.split("&");
        for (String s : kv) {
            System.out.println(URLDecoder.decode(s, "UTF-8"));
        }
        System.out.println(segment);
    }

    public static void main2(String[] args) throws IOException {
        String s = "?";
        String s1 = URLEncoder.encode(s, "UTF-8");
        String s2 = URLDecoder.decode(s1, "UTF-8");
        System.out.println(s1);
        System.out.println(s2);
    }
}
