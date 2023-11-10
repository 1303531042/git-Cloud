package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * HTTP返回状态
 *
 * @author csy
 */

@Getter
@AllArgsConstructor
public enum HttpStatusEnum {
    SUCCESS(200, "操作成功"),
    CREATED(201, "对象创建成功"),
    ACCEPTED(202, "请求已被接受"),
    NO_CONTENT(204, "操作已经执行成功，但是没有返回数据"),
    ELECTRICITY_PRICE(205, "本月电价已调整，不允许再作调整！"),
    NULL_PRICE(207, "未上传电价，请先去上传电价！"),
    ELECTRICITY_NOT(206, "电价不能输入为空"),
    NULL_BILL(208, "尚未生成账单！"),
    FAIL_QKL(209, "区块链上链失败！"),
    MOVED_PERM(301, "资源已被移除"),
    SEE_OTHER(302, "重定向"),
    NOT_MODIFIED(304, "资源没有被修改"),
    BAD_REQUEST(400, "参数列表错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "访问受限，，授权过期"),
    NOT_FOUND(404, "资源，服务未找到"),
    BAD_METHOD(405, "不允许http方法"),
    ERROR_PASSWORD(406, "用户名密码错误"),
    NOT_USER(407, "用户不存在"),
    CONFLICT(409, "资源冲突，或者资源被锁定"),
    NOT_ROLE(410, "权限不存在"),
    ROLE_DEFICIENCY(411, "权限不足"),
    CORRESPONDENCE(422, "查询的权限与用户编号不对应"),
    UNSUPPORTED_TYPE(415, "不支持的数据，媒体类型"),
    ERROR(500, "系统内部错误"),
    NOT_IMPLEMENTED(501, "接口未实现");
    private final Integer code;
    private final String message;
}
