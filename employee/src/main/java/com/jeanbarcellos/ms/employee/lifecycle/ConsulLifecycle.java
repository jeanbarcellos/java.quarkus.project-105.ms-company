package com.jeanbarcellos.ms.employee.lifecycle;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegistration;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class ConsulLifecycle {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsulLifecycle.class);

    private String instanceId;

    @Inject
    protected Consul consulClient;

    @ConfigProperty(name = "quarkus.application.name")
    protected String appName;

    @ConfigProperty(name = "quarkus.application.version")
    protected String appVersion;

    protected String address = "127.0.0.1";

    void onStart(@Observes StartupEvent ev) {

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.schedule(() -> {
            instanceId = appName + "-" + UUID.randomUUID();

            ImmutableRegistration registration = ImmutableRegistration.builder()
                    .id(instanceId)
                    .name(appName)
                    .address(address)
                    .port(Integer.parseInt(System.getProperty("quarkus.http.port")))
                    .putMeta("version", appVersion)
                    .build();

            consulClient.agentClient().register(registration);

            LOGGER.info("Instance registered: id={}", registration.getId());

        }, 5000, TimeUnit.MILLISECONDS);
    }

    void onStop(@Observes ShutdownEvent ev) {
        consulClient.agentClient().deregister(instanceId);

        LOGGER.info("Instance de-registered: id={}", instanceId);
    }

}
