package song.mingu.grpcclient.event;

import org.springframework.stereotype.Service;
import song.mingu.grpcclient.GrpcClientService;
import song.mingu.proto.EventRequest;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventClientService {
    @PostConstruct
    private void init() {
        EventClientStubFactory stubFactory = new EventClientStubFactory(GrpcClientService.HOST, GrpcClientService.PORT);

        EventRequest request1 = EventRequest.newBuilder().setSourceId("src1").setEventId("event1").build();
        EventRequest request2 = EventRequest.newBuilder().setSourceId("src2").setEventId("event2").build();
        EventRequest request3 = EventRequest.newBuilder().setSourceId("src3").setEventId("event3").build();
        List<EventRequest> requestList = new ArrayList<>();
        requestList.add(request1);
        requestList.add(request2);
        requestList.add(request3);

        // Blocking Unary
//        stubFactory.sendBlockingUnaryMessage(request1);
        // async Unary
//        stubFactory.sendAsyncUnaryMessage(request1);
        //future Unary

        //blockingServerStream

        //biStream
        stubFactory.sendBiDirectionalStreamingMessage(requestList);
    }
}
