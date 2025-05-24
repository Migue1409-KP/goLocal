package co.uco.golocal.golocalapi.utils.gson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class MapperJsonObjeto implements IMapperJsonObjeto {
    public Optional<String> ejecutar(Object objeto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return Optional.ofNullable(objectMapper.writeValueAsString(objeto));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public <T> Optional<T> ejecutar(String json, Class<T> claseDestino) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return Optional.ofNullable(objectMapper.readValue(json, claseDestino));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<String> ejecutarGson(Object objecto) {
        try {
            Gson gson = new GsonBuilder().serializeNulls()
                    .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_DATE_TIME);
                        }
                    })
                    .create();
            String objeto = gson.toJson(objecto);
            return Optional.ofNullable(objeto);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
