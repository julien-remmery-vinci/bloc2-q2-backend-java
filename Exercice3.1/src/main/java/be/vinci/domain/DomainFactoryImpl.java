package be.vinci.domain;

public class DomainFactoryImpl implements DomainFactory {

    @Override
    public Page getPage() {
        return new PageImpl();
    }


    @Override
    public User getUser() {
        return new UserImpl();
    }
}
