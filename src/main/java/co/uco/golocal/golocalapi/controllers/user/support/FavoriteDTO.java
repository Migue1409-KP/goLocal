package co.uco.golocal.golocalapi.controllers.user.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class FavoriteDTO {
    private UUID userId;
    private UUID experienceId;
}
