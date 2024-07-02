package JacopoDemaio.gestioneDispositivi_Security.payloads;

import java.time.LocalDateTime;

public record ErrorsDTO(
        String message,
        LocalDateTime timeStamp
) {
}
