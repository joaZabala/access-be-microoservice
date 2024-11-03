package ar.edu.utn.frc.tup.lc.iv.clients.notifications;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QrEmailRequest {
    private String email;
    private String name;
    private Long docNumber;
}
