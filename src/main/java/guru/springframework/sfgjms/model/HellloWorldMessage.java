package guru.springframework.sfgjms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HellloWorldMessage implements Serializable {

    static final long serialVersionUID = 6436030434715680506L;

    private UUID id;
    private String message;

}
