package com.woowacourse.teatime.teatime.support;

import com.woowacourse.teatime.auth.controller.AuthController;
import com.woowacourse.teatime.teatime.controller.CoachController;
import com.woowacourse.teatime.teatime.controller.CrewController;
import com.woowacourse.teatime.teatime.controller.ReservationController;
import com.woowacourse.teatime.teatime.controller.ScheduleController;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@WebMvcTest({
        CoachController.class,
        CrewController.class,
        ReservationController.class,
        ScheduleController.class,
        AuthController.class
})
public @interface ControllerTest {

}
