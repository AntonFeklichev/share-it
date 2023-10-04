package anton.myshareit.user.service;

public class UserServiceImplTest extends UserServiceTest<UserServiceImpl> {
    @Override
    UserServiceImpl getUserService() {

        return new UserServiceImpl(userRepository);
    }
}
