/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package https_58_47_47kie_46org_47dmn_47__1436A937_45AE9A_454F01_4598B4_45334645FC4538;

import java.io.InputStream;
import java.util.Objects;
import java.util.stream.Collectors;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import org.kie.dmn.feel.codegen.feel11.CodegenStringUtil;
import org.kie.kogito.Application;
import org.kie.kogito.dmn.rest.DMNEvaluationErrorException;
import org.kie.kogito.dmn.rest.DMNJSONUtils;
import org.kie.kogito.dmn.rest.KogitoDMNResult;
import org.kie.kogito.dmn.util.StronglyTypedUtils;
import org.kie.kogito.monitoring.core.common.system.metrics.SystemMetricsCollector;
import org.kie.kogito.monitoring.core.common.system.metrics.DMNResultMetricsBuilder;
import org.kie.kogito.monitoring.core.common.system.metrics.SystemMetricsCollectorProvider;

@Path("/DMN_404C597C-0053-4AC4-BD3D-D1B29072495E")
public class DMN__404C597C_450053_454AC4_45BD3D_45D1B29072495EResource {

    @jakarta.inject.Inject()
    Application application;

    private static final String KOGITO_DECISION_INFOWARN_HEADER = "X-Kogito-decision-messages";

    private static final String KOGITO_EXECUTION_ID_HEADER = "X-Kogito-execution-id";

    private static final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule()).registerModule(new com.fasterxml.jackson.databind.module.SimpleModule().addSerializer(org.kie.dmn.feel.lang.types.impl.ComparablePeriod.class, new org.kie.kogito.dmn.rest.DMNFEELComparablePeriodSerializer())).disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);

    @jakarta.inject.Inject()
    SystemMetricsCollectorProvider systemMetricsCollectorProvider;

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @org.eclipse.microprofile.openapi.annotations.parameters.RequestBody(content = @org.eclipse.microprofile.openapi.annotations.media.Content(mediaType = "application/json", schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(ref = "/CreditEvaluator.json#/definitions/InputSet")), description = "DMN input")
    @org.eclipse.microprofile.openapi.annotations.responses.APIResponse(content = @org.eclipse.microprofile.openapi.annotations.media.Content(mediaType = "application/json", schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(ref = "/CreditEvaluator.json#/definitions/OutputSet")), description = "DMN output")
    public Response dmn(java.util.Map<String, Object> variables) {
        long startTime = System.nanoTime();
        org.kie.kogito.decision.DecisionModel decision = application.get(org.kie.kogito.decision.DecisionModels.class).getDecisionModel("https://kie.org/dmn/_1436A937-AE9A-4F01-98B4-334645FC4538", "DMN_404C597C-0053-4AC4-BD3D-D1B29072495E");
        org.kie.dmn.api.core.DMNResult decisionResult = decision.evaluateAll(DMNJSONUtils.ctx(decision, variables));
        KogitoDMNResult result = new KogitoDMNResult("https://kie.org/dmn/_1436A937-AE9A-4F01-98B4-334645FC4538", "DMN_404C597C-0053-4AC4-BD3D-D1B29072495E", decisionResult);
        long endTime = System.nanoTime();
        systemMetricsCollectorProvider.get().registerElapsedTimeSampleMetrics("DMN_404C597C-0053-4AC4-BD3D-D1B29072495E", endTime - startTime);
        return enrichResponseHeaders(decisionResult, extractContextIfSucceded(result));
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String dmn() throws java.io.IOException {
        try (InputStream is = this.getClass().getResourceAsStream(CodegenStringUtil.escapeIdentifier("DMN_404C597C-0053-4AC4-BD3D-D1B29072495E") + ".dmn_nologic")) {
            return new String(org.drools.util.IoUtils.readBytesFromInputStream(Objects.requireNonNull(is)));
        }
    }

    private ResponseBuilder extractContextIfSucceded(KogitoDMNResult result) {
        if (!result.hasErrors()) {
            return Response.ok(buildResponse(result.getDmnContext()));
        } else {
            return buildFailedEvaluationResponse(result);
        }
    }

    private ResponseBuilder buildFailedEvaluationResponse(KogitoDMNResult result) {
        systemMetricsCollectorProvider.get().registerException("DMN_404C597C-0053-4AC4-BD3D-D1B29072495E", result.getMessages().stream().filter(x -> org.kie.dmn.api.core.DMNMessage.Severity.ERROR.equals(x.getSeverity())).map(x -> x.getMessage()).collect(Collectors.joining(",")));
        return Response.status(jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).entity(result);
    }

    private ResponseBuilder extractSingletonDSIfSucceded(KogitoDMNResult result) {
        if (!result.hasErrors()) {
            return Response.ok(buildResponse(result.getDecisionResults().get(0).getResult()));
        } else {
            return buildFailedEvaluationResponse(result);
        }
    }

    private Response buildDMNResultResponse(KogitoDMNResult result) {
        if (!result.hasErrors()) {
            return Response.ok(buildResponse(result)).build();
        } else {
            return buildFailedEvaluationResponse(result).build();
        }
    }

    private String buildResponse(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Response enrichResponseHeaders(org.kie.dmn.api.core.DMNResult result, ResponseBuilder responseBuilder) {
        if (!result.hasErrors() && !result.getMessages().isEmpty()) {
            String infoWarns = result.getMessages().stream().map(m -> m.getLevel() + " " + m.getMessage()).collect(java.util.stream.Collectors.joining(", "));
            responseBuilder.header(KOGITO_DECISION_INFOWARN_HEADER, infoWarns);
        }
        org.kie.kogito.decision.DecisionExecutionIdUtils.getOptional(result.getContext()).ifPresent(executionId -> responseBuilder.header(KOGITO_EXECUTION_ID_HEADER, executionId));
        return responseBuilder.build();
    }

    @POST
    @Path("/dmnresult")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @org.eclipse.microprofile.openapi.annotations.parameters.RequestBody(content = @org.eclipse.microprofile.openapi.annotations.media.Content(mediaType = "application/json", schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(ref = "/CreditEvaluator.json#/definitions/InputSet")), description = "DMN input")
    public Response dmn_dmnresult(java.util.Map<String, Object> variables) {
        org.kie.kogito.decision.DecisionModel decision = application.get(org.kie.kogito.decision.DecisionModels.class).getDecisionModel("https://kie.org/dmn/_1436A937-AE9A-4F01-98B4-334645FC4538", "DMN_404C597C-0053-4AC4-BD3D-D1B29072495E");
        org.kie.dmn.api.core.DMNResult decisionResult = decision.evaluateAll(DMNJSONUtils.ctx(decision, variables));
        KogitoDMNResult result = new KogitoDMNResult("https://kie.org/dmn/_1436A937-AE9A-4F01-98B4-334645FC4538", "DMN_404C597C-0053-4AC4-BD3D-D1B29072495E", decisionResult);
        return buildDMNResultResponse(result);
    }
}
