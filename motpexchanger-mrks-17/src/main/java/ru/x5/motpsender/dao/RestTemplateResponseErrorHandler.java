package ru.x5.motpsender.dao;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Класс для обработки ошибок при работе с ru.x5.motpsender.dao.DaoConfiguration#restTemplate() и
 * ru.x5.motpsender.dao.DaoConfiguration#authRestTemplate()
 * todo: необходимо логирование и обработка 300-х и 400-х ошибок
 */
@Component
@Log4j2
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Autowired
    SessionInfo sessionInfo;

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {
        return (
                httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR
                        || httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {

        if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
            log.error("Server error for inn {} session {} ", sessionInfo.getUserInn(), sessionInfo.getGlobalUUID());
            throw new HttpClientErrorException(httpResponse.getStatusCode());
        } else if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            if (httpResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                log.error("Unauthorized error for inn {} session {} ", sessionInfo.getUserInn(), sessionInfo.getGlobalUUID());
                log.trace(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
                //todo: вызов метода авторизации
                //разбор сообщения от МОТП
            }
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error("Server error for inn {} session {} ", sessionInfo.getUserInn(), sessionInfo.getGlobalUUID());
                throw new HttpClientErrorException(httpResponse.getStatusCode());
            }
        }
    }
}