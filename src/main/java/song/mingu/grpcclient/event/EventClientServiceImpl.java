package song.mingu.grpcclient.event;

import org.springframework.stereotype.Service;
import song.mingu.grpcclient.GrpcClientService;
import song.mingu.proto.EventRequest;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventClientServiceImpl implements EventClientService {
    private EventClientStubFactory stubFactory;
    private EventRequest request1;
    private EventRequest request2;
    private EventRequest request3;
    private List<EventRequest> requestList;

    @PostConstruct
    private void init() {
        stubFactory = new EventClientStubFactory(GrpcClientService.HOST, GrpcClientService.PORT);

        request1 = EventRequest.newBuilder().setSourceId("src1").setEventId("event1").build();
        request2 = EventRequest.newBuilder().setSourceId("src2").setEventId("event2").build();
        request3 = EventRequest.newBuilder().setSourceId("src3").setEventId("event3").build();
        requestList = new ArrayList<>();
        requestList.add(request1);
        requestList.add(request2);
        requestList.add(request3);
    }

    @Override
    public void sendBlockingUnaryMessage() {
        stubFactory.sendBlockingUnaryMessage(request1);
    }

    @Override
    public void sendAsyncUnaryMessage() {
        stubFactory.sendAsyncUnaryMessage(request2);
    }

    @Override
    public void sendFutureUnaryMessage() {
        stubFactory.sendFutureUnaryMessage(request3);
    }

    @Override
    public void sendBlockingServerStreamingMessage() {
        stubFactory.sendBlockingServerStreamingMessage(request1);
    }

    @Override
    public void sendAsyncServerStreamingMessage() {
        stubFactory.sendAsyncServerStreamingMessage(request2);
    }

    @Override
    public void sendAsyncClientStreamingMessage() {
        stubFactory.sendAsyncClientStreamingMessage(requestList);
    }

    @Override
    public void sendBiDirectionalStreamingMessage() {
        stubFactory.sendBiDirectionalStreamingMessage(requestList);
    }
}
