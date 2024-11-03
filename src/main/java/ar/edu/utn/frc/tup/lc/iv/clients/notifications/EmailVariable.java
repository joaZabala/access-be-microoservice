package ar.edu.utn.frc.tup.lc.iv.clients.notifications;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailVariable {
    private String key;
    private String value;

}
