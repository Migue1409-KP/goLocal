package co.uco.golocal.golocalapi.domain.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityDomain {
    private UUID id;
    private String name;
    private StateDomain state;
}
