package interfaces;

public interface ResponseCallback {
    void resolve(String message);

    void reject(String error);
}
