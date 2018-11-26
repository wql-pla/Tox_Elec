package com.example;

import com.tox.Application;
import com.tox.service.ElecStationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class ApplicationTests {
    @Autowired
    ElecStationService esService;
    @Test
    public  void  esServiceTest(){

        try {
            esService.updateActiveUser("18920378921","2018-11-26 10:22:22");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
