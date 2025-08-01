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
package org.kie.kogito.handlers;

import io.vertx.mutiny.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import org.kogito.workitem.rest.RestWorkItemHandler;
import static org.kogito.workitem.rest.RestWorkItemHandlerUtils.sslWebClientOptions;

@jakarta.enterprise.context.ApplicationScoped()
public class loanRestWorkItemHandler extends RestWorkItemHandler {

    public loanRestWorkItemHandler() {
        this(Vertx.vertx(), sslWebClientOptions());
    }

    @jakarta.inject.Inject()
    public loanRestWorkItemHandler(Vertx vertx, WebClientOptions sslOptions) {
        super(WebClient.create(vertx), WebClient.create(vertx, sslOptions));
    }

    @Override
    public String getName() {
        return "loanRestWorkItemHandler";
    }
}
