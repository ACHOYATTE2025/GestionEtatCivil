package com.example.birthadvance.Service;
import com.example.birthadvance.Entities.Account;
import com.example.birthadvance.Repositories.ExtraitRepository;
import com.example.birthadvance.Repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {


    @Mock
    private AccountRepository subscriberRepository;
    private ExtraitRepository extraitRepository;

    @InjectMocks
    private AccountService subscriberService;

    // test subseribeService for runnig
    @DisplayName(" [Controller] test d'enrrgistrement d'un suscriber")
    @Test
    void checkRegisterAccount() {

        //Arrange
        Long subscriberId = 1L;
        Boolean active=true;
        Account mockUser=new Account();
        mockUser.setId(subscriberId);
        mockUser.setActive(active);



        //Act && Assert
        this.subscriberRepository.save(mockUser);

    }

}
