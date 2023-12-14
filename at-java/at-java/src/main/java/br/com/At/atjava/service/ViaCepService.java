package br.com.At.atjava.service;

import br.com.At.atjava.model.Endereco;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

@Slf4j
@Service
public class ViaCepService {

    public Endereco getEndereco(String cep) throws IOException {
        HttpGet request = new HttpGet("https://viacep.com.br/ws/" + cep + "/json/");
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
             CloseableHttpResponse response = httpClient.execute(request)) {

            log.info("Status Code da Resposta da API ViaCep: {}", response.getStatusLine().getStatusCode());

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                Gson gson = new Gson();
                return gson.fromJson(result, Endereco.class);
            }
        }
        return null;
    }
}
