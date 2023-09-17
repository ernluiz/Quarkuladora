package org.acme.controller;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import static java.util.Objects.requireNonNull;
import org.acme.dto.TemplateDataDTO;
import org.acme.model.CalculadoraModel;

@Path("/calculator")
public class CalculadoraController {

    private final Template page;

    @Inject
    CalculadoraModel calculatorModel;

    private Object resultado;

    @Inject
    public CalculadoraController(Template page, CalculadoraModel calculatorModel) {
        this.page = requireNonNull(page, "page is required");
        this.calculatorModel = requireNonNull(calculatorModel, "calculatorModel is required");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance retornarTelaInicial() {
        return page.data("name", "Luiz Fernando").data("resultado", resultado != null ? resultado : "Aguardando cálculo...");
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance calcular(
            @FormParam("numero1") Double numero1,
            @FormParam("numero2") Double numero2,
            @FormParam("operacao") String operacao) {
        System.out.println(numero1);
        System.out.println(numero2);
        System.out.println(operacao);

        TemplateDataDTO dto = new TemplateDataDTO();
        String resultado = calculatorModel.realizarCalculo(numero1, numero2, operacao);
        return page.data("name", "Seu Nome Aqui").data("resultado", resultado != null ? resultado : 0.0);
    }
}