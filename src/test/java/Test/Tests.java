package Test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.service.AttendanceService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/spring-*.xml")
public class Tests {
    
    @Autowired
    private AttendanceService attendanceService;
    
    @Test
    public void test() {
        Attendance attendance = new Attendance();
        attendance.setInOutMode(3);
        attendance.setUserName("张良");
        attendance.setUserId(473L);
        attendance.setVerifyMode(1);
        attendance.setSourceMachine("192.168.1.204");
        attendance.setNumber("617");
        Date date = DateUtil.parse("2020-08-01 00:00:00");
        for (int i = 0; i < 31; i++) {
            int number = RandomUtil.randomInt(-300,300);
            DateUtil.offsetMillisecond(date,number);
            date = DateUtil.offsetDay(date, 1);
            attendance.setTime(date);
            System.out.println(attendance);
        }
    }

}
