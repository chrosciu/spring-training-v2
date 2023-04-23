package pl.wojtyna.trainings.spring.examples.injection;

import org.springframework.beans.factory.ObjectFactory;

public class SingletonServiceUsingPrototypeWithObjectProvider {
    private final ObjectFactory<PrototypeScopeService> prototypeScopeServiceObjectFactory;

    public SingletonServiceUsingPrototypeWithObjectProvider(
            ObjectFactory<PrototypeScopeService> prototypeScopeServiceObjectFactory) {
        this.prototypeScopeServiceObjectFactory = prototypeScopeServiceObjectFactory;
    }

    public String getIdOfPrototypeBean() {
        return prototypeScopeServiceObjectFactory.getObject().getId();
    }
}
