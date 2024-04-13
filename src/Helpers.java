import Servidor.UDPServer;

public class Helpers {
    public static String clearWord(String word) {
        return word.trim();
    }

    public static String createContents(UDPServer.Services serv, String ... contents){
        StringBuilder sb = new StringBuilder();
        sb.append(serv.ordinal() + ":[");

        for(int i = 0; i < contents.length; i++) {
            String content = contents[i];
            sb.append(content);
            if (i < contents.length - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    };
}
