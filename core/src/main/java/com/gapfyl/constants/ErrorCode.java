package com.gapfyl.constants;

/**
 * @author vignesh
 * Created on 14/04/21
 **/

public interface ErrorCode {
    Integer DUPLICATE_REQUEST                   = 10001;
    Integer NOT_FOUND                           = 10002;
    Integer NO_PERMISSION                       = 10003;
    Integer IN_ACTIVE                           = 10004;

    Integer UPDATE_FAILED                       = 11001;

    Integer INVALID_REQUEST                     = 12001;

    Integer USER_NOT_FOUND                      = 20001;
    Integer USER_NOT_ACTIVATED                  = 20002;
    Integer USER_CONFLICT                       = 20003;
    Integer USER_INVALID_RECOVERY_TOKEN         = 20004;
    Integer USER_ACCOUNT_CONFLICT               = 20005;
    Integer USER_ACCOUNT_NOT_FOUND              = 20006;
    Integer USER_HAVE_NO_PERMISSION             = 20007;

    Integer UPLOAD_FAILED                       = 30001;
    Integer COPY_FAILED                         = 30002;
    Integer MOVE_FAILED                         = 30003;
    Integer DELETE_FAILED                       = 30004;

    Integer COURSE_NOT_FOUND                    = 40001;
    Integer COURSE_IN_ACTIVE                    = 40002;
    Integer COURSE_UPDATE_FAILED                = 40003;
    Integer COURSE_CONTENT_NOT_ADDED            = 40101;
    Integer COURSE_CONTENT_NOT_REMOVED          = 40102;

    Integer CONTENT_NOT_FOUND                   = 50001;
    Integer CONTENT_UPDATE_FAILED               = 50002;

    Integer DISCUSSION_NOT_FOUND                = 60001;
    Integer DISCUSSION_UPDATE_FAILED            = 60002;

    Integer NOTIFICATION_NOT_FOUND              = 70001;
    Integer NOTIFICATION_UPDATE_FAILED          = 70002;

    Integer MESSAGE_NOT_FOUND                   = 80001;
    Integer MESSAGE_SENDER_NOT_FOUND            = 80002;
    Integer MESSAGE_RECEIVER_NOT_FOUND          = 80003;

    Integer CREATOR_NOT_FOUND                   = 90001;
    Integer CREATOR_DEACTIVATED                 = 90002;
    Integer CREATOR_NO_PERMISSION               = 90003;
    Integer CREATOR_NO_ACCESS                   = 90004;
    Integer CREATOR_INVALID_REQUEST             = 90005;

    Integer COLLABORATION_REQUESTER_NOT_FOUND   = 90005;
    Integer COLLABORATION_COLLABORATOR_NOT_FOUND= 90006;

    Integer EVENT_NOT_FOUND                     = 11001;

    Integer CREATE_ORDER_FAILED_ERROR           = 12001;
    Integer ORDER_NOT_FOUND                     = 12002;
    Integer ORDER_UPDATE_FAILED                 = 12003;

    Integer PAYMENT_NOT_FOUND                   = 13001;

    Integer REFUND_FAILED                       = 14001;

    Integer COLLABORATION_REQUEST_NOT_FOUND     = 15001;
    Integer COLLABORATION_REQUEST_INVALID       = 15002;
}
