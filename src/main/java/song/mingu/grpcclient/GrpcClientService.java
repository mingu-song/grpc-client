package song.mingu.grpcclient;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.mingu.proto.SampleRequest;
import song.mingu.proto.SampleResponse;
import song.mingu.proto.SampleServiceGrpc;

@Slf4j
@Service
public class GrpcClientService {
    public static final int PORT = 3030;
    public static final String HOST = "localhost";
    private final SampleServiceGrpc.SampleServiceStub asyncStub = SampleServiceGrpc.newStub(
            ManagedChannelBuilder.forAddress(HOST, PORT)
                    .usePlaintext()
                    .build()
    );

    public String sampleCall() {
        final SampleRequest sampleRequest = SampleRequest.newBuilder()
                .setUserId("mingu-song")
                .setMessage("grpc request")
                .build();

        asyncStub.sampleCall(sampleRequest, new StreamObserver<SampleResponse>() {
            @Override
            public void onNext(SampleResponse value) {
                log.info("GrpcClient#sampleCall - {}", value);
            }

            @Override
            public void onError(Throwable t) {
                log.error("GrpcClient#sampleCall - onError");
            }

            @Override
            public void onCompleted() {
                log.info("GrpcClient#sampleCall - onCompleted");
            }
        });
        return "string";
    }
}
