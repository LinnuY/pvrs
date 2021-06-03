package com.linuy.pvr.common.aspect.autoLogin;

import com.linuy.pvr.common.Constant;
import com.linuy.pvr.common.ResponseBean;
import com.linuy.pvr.entity.User;
import com.linuy.pvr.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author LongTeng
 * @date 2021/03/03
 **/
@Component
@Aspect
@Slf4j
public class AutoLoginAspect {

    private final static String LOGIN_URL = "login";

    private final UserRepository userRepository;

    @Autowired
    public AutoLoginAspect(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Pointcut("(execution(* com.linuy.pvr.controller..*.*(..)))" +
            "&& !(execution(* com.linuy.pvr.controller.login.*.*(..)))" +
            "&& !(execution(* com.linuy.pvr.controller.registry.*.*(..))))")
    public void controllerAspect() {

    }

    @Around("controllerAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Type type = targetMethod.getGenericReturnType();
        Class<?> returnType = targetMethod.getReturnType();
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
                break;
            }
            if (arg instanceof HttpServletResponse) {
                response = (HttpServletResponse) arg;
                break;
            }
        }

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        assert sra != null;
        if (request == null) {
            request = sra.getRequest();
        }
        if (response == null) {
            response = sra.getResponse();
        }
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();

        String username = (String) session.getAttribute(Constant.sessionUsername);
        String password = (String) session.getAttribute(Constant.sessionPassword);

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return returnType(type, returnType, "102", "Session is Empty.", uri);
        }
        List<User> users = userRepository.findByUsernameAndPassword(username, password);
        if (users.isEmpty()) {
            return returnType(type, returnType, "103", "Username or password Error!", uri);
        }
        return joinPoint.proceed(args);
    }

    Object returnType(Type type, Class<?> returnType, String code, String message, String url) {
        log.info("Intercept access: " + url + " , Intercept code: " + code);
        if (type instanceof ResponseBean) {
            ResponseBean<Object> responseBean = new ResponseBean<>();
            responseBean.addError(message, AutoLoginAspect.LOGIN_URL);
            return responseBean;
        } else if (returnType.isInstance(new ResponseBean<>())) {
            ResponseBean<Object> responseBean = new ResponseBean<>();
            responseBean.addError(message, AutoLoginAspect.LOGIN_URL);
            return responseBean;
        } else {
            return AutoLoginAspect.LOGIN_URL;
        }
    }
}
