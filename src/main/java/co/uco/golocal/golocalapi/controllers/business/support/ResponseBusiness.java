package co.uco.golocal.golocalapi.controllers.business.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public final  class ResponseBusiness<T> {
    private HttpStatus status;
    private List<T> data;
    private String message;

    public ResponseBusiness() {
        super();
        setStatus(HttpStatus.BAD_REQUEST);
        setData(new ArrayList<>());
        setMessage("");
    }
}
