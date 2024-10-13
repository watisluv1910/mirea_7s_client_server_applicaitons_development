package com.wladischlau.app.pract4_client.client;

import org.atteo.evo.inflector.English;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract sealed class BasicCrudClient<T> permits CarClient, CustomerClient {

    private final RSocketRequester rSocketRequester;

    protected BasicCrudClient(RSocketRequester.Builder rSocketRequesterBuilder) {
        this.rSocketRequester = rSocketRequesterBuilder.tcp("localhost", 7000);
    }

    public Mono<Void> add(T entity) {
        return rSocketRequester.route("add" + entity.getClass().getSimpleName())
                .data(entity)
                .send();
    }

    public Mono<T> getById(Object id, Class<T> clazz) {
        return rSocketRequester.route("get" + clazz.getSimpleName())
                .data(id)
                .retrieveMono(clazz);
    }

    public Flux<T> getAll(Class<T> clazz) {
        String plural = English.plural(clazz.getSimpleName());
        return rSocketRequester.route("getAll" + plural)
                .retrieveFlux(clazz);
    }
}
