package song.mingu.grpcclient;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class GrpcController {
    private final GrpcClientService grpcClientService;

    @GetMapping("/")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body(Mono.just(grpcClientService.sampleCall()));
    }
}
