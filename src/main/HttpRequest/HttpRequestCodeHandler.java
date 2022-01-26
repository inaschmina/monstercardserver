package HttpRequest;

public class HttpRequestCodeHandler {


    public String getHttpRequestMessage(int code) {
        switch(code) {
            case 200 -> {
                return "OK";
            }
            case 400 -> {
                return "Bad request";
            }
            case 404 -> {
                return "Not found";
            }
            case 401 -> {
                return "Not authorized";
            }
            default -> {
                return "no message for code specified";
            }
        }
    }
}
