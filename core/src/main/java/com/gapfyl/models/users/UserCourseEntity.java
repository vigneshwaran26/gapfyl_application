package com.gapfyl.models.users;

import com.gapfyl.constants.Collections;
import com.gapfyl.enums.courses.UserCourseStatus;
import com.gapfyl.models.common.BaseAuditEntity;
import com.gapfyl.models.courses.CourseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 31/07/21
 **/

@Data
@Document(collection = Collections.USER_COURSES)
@EqualsAndHashCode(callSuper = false)
public class UserCourseEntity extends BaseAuditEntity {

    @Field("order_id")
    private String orderId;

    @Field("payment_id")
    private String paymentId;

    @Field("account_id")
    private String accountId;

    @Field("user_id")
    private String userId;

    @Field("course_id")
    private String courseId;

    @Field("status")
    private UserCourseStatus status;

}
