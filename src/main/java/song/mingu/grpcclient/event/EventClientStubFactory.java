package song.mingu.grpcclient.event;

import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import song.mingu.proto.EventRequest;
import song.mingu.proto.EventResponse;
import song.mingu.proto.EventServiceGrpc;
import song.mingu.proto.EventServiceGrpc.*;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
public class EventClientStubFactory {
    private final ManagedChannel channel;
    private final EventServiceBlockingStub blockingStub;
    private final EventServiceStub asyncStub;
    private final EventServiceFutureStub futureStub;

    public EventClientStubFactory(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.blockingStub = EventServiceGrpc.newBlockingStub(this.channel);
        this.asyncStub = EventServiceGrpc.newStub(this.channel);
        this.futureStub = EventServiceGrpc.newFutureStub(this.channel);
    }

    // unary 3 cases
    public void sendBlockingUnaryMessage(EventRequest eventRequest) {
        log.info("Request = {}", eventRequest);
        EventResponse eventResponse = blockingStub.unaryEvent(eventRequest);
        log.info("Response = {}", eventResponse);
    }

    public void sendAsyncUnaryMessage(EventRequest eventRequest) {
        log.info("Request = {}", eventRequest);
        asyncStub.unaryEvent(eventRequest, new StreamObserver<EventResponse>() {
            @Override
            public void onNext(EventResponse value) {
                log.info("Response = {}", value);
            }

            @Override
            public void onError(Throwable t) {
                log.error("onError", t);
            }

            @Override
            public void onCompleted() {
                log.info("onCompleted");
            }
        });
    }

    public void sendFutureUnaryMessage(EventRequest eventRequest) {
        log.info("Request = {}", eventRequest);
        EventResponse eventResponse = null;
        ListenableFuture<EventResponse> future = futureStub.unaryEvent(eventRequest);
        try {
            eventResponse = future.get(2, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        log.info("Response = {}", eventResponse);
    }

    // server streaming 2 cases
    public void sendBlockingServerStreamingMessage(EventRequest eventRequest) {
        log.info("Request = {}", eventRequest);
        Iterator<EventResponse> responseIter;
        responseIter = blockingStub.serverStreamingEvent(eventRequest);
        responseIter.forEachRemaining(it -> log.info("Response = {}", it));
        log.info("Blocking Server Streaming onCompleted");
    }

    public void sendAsyncServerStreamingMessage(EventRequest eventRequest) {
        log.info("Request = {}", eventRequest);
        asyncStub.serverStreamingEvent(eventRequest, new StreamObserver<EventResponse>() {
            @Override
            public void onNext(EventResponse value) {
                log.info("Async Response = {}", value);
            }

            @Override
            public void onError(Throwable t) {
                log.info("onError");
            }

            @Override
            public void onCompleted() {
                log.info("Async Server Streaming onCompleted");
            }
        });
    }

    // client streaming 1 case
    public void sendAsyncClientStreamingMessage(List<EventRequest> eventRequestList) {
        StreamObserver<EventResponse> responseStreamObserver = new StreamObserver<EventResponse>() {
            @Override
            public void onNext(EventResponse value) {
                log.info("Async Client Streaming Response = {}", value);
            }

            @Override
            public void onError(Throwable t) {
                log.info("onError");
            }

            @Override
            public void onCompleted() {
                log.info("Async Client Streaming onCompleted");
            }
        };

        StreamObserver<EventRequest> requestStreamObserver = asyncStub.clientStreamingEvent(responseStreamObserver);
        eventRequestList.forEach(requestStreamObserver::onNext);
        requestStreamObserver.onCompleted();
    }

    // biStream 1 case
    public void sendBiDirectionalStreamingMessage(List<EventRequest> eventRequestList) {
        StreamObserver<EventResponse> responseStreamObserver = new StreamObserver<EventResponse>() {
            @Override
            public void onNext(EventResponse value) {
                log.info("BiStream Response = {}", value);
            }

            @Override
            public void onError(Throwable t) {
                log.info("onError");
            }

            @Override
            public void onCompleted() {
                log.info("BiStream onCompleted");
            }
        };
        StreamObserver<EventRequest> requestStreamObserver = asyncStub.biStreamingEvent(responseStreamObserver);
        eventRequestList.forEach(requestStreamObserver::onNext);
        requestStreamObserver.onCompleted();
    }
}
