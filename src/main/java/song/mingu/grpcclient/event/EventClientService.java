package song.mingu.grpcclient.event;

public interface EventClientService {
    void sendBlockingUnaryMessage();
    void sendAsyncUnaryMessage();
    void sendFutureUnaryMessage();

    void sendBlockingServerStreamingMessage();
    void sendAsyncServerStreamingMessage();

    void sendAsyncClientStreamingMessage();

    void sendBiDirectionalStreamingMessage();
}
