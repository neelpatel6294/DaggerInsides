package com.techyourchance.journeytodependencyinjection.common.dependencyinjection.application;

import com.techyourchance.journeytodependencyinjection.common.dependencyinjection.presentation.PresentationComponent;
import com.techyourchance.journeytodependencyinjection.common.dependencyinjection.presentation.PresentationModule;
import com.techyourchance.journeytodependencyinjection.common.dependencyinjection.service.ServiceComponent;
import com.techyourchance.journeytodependencyinjection.common.dependencyinjection.service.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkingModule.class})
public interface ApplicationComponent {
    public PresentationComponent newPresentationComponent(PresentationModule presentationModule);
    public ServiceComponent newServiceComponent(ServiceModule serviceModule);
}
