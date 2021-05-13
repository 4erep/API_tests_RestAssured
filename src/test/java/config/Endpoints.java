package config;

public enum Endpoints {
    USERS("/users"), REGISTER("/register"), LOGIN("/login"), ADDTOCART("/addproducttocart"),
    MAILTOAFRIEND("/productemailafriend");
    String path;

    Endpoints(String path) {
        this.path = path;
    }

    public String getPath(String s) {
        return path;
    }

    public String addPath(String additionalPath) {
        return path + additionalPath;
    }
}
