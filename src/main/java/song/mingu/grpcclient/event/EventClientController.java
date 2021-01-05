package song.mingu.grpcclient.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/event")
public class EventClientController {
    private final EventClientService clientService;

    @GetMapping("/unary/blocking")
    public ResponseEntity<?> unaryBlocking() {
        clientService.sendBlockingUnaryMessage();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unary/async")
    public ResponseEntity<?> unaryAsync() {
        clientService.sendAsyncUnaryMessage();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unary/future")
    public ResponseEntity<?> unaryFuture() {
        clientService.sendFutureUnaryMessage();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/server/blocking")
    public ResponseEntity<?> serverBlocking() {
        clientService.sendBlockingServerStreamingMessage();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/server/async")
    public ResponseEntity<?> serverAsync() {
        clientService.sendAsyncServerStreamingMessage();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/client/async")
    public ResponseEntity<?> clientAsync() {
        clientService.sendAsyncClientStreamingMessage();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bi")
    public ResponseEntity<?> bi() {
        clientService.sendBiDirectionalStreamingMessage();
        return ResponseEntity.ok().build();
    }
}
