package skillregister.co.ribot.skllregister.test.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import skillregister.co.ribot.skllregister.sprytechies.skillregister.injection.component.ApplicationComponent;
import skillregister.co.ribot.skllregister.test.common.injection.module.ApplicationTestModule;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
