package br.com.climb.climbspring;


import br.com.climb.climbspring.annotations.ClimbNativeQuery;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.interfaces.ManagerFactory;
import br.com.climb.exception.SgdbException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

@Aspect
@Configuration
public class ManagerRepositoryAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Class<?> getTypeInterface(String className) {
//        Reflections reflections = new Reflections("");
//        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Repository.class);

        String classNameTemp = className.split("\\$")[0];

        try {
            return Class.forName(classNameTemp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Type findClassParameterType(Class<?> classe) {

        Type[] genericInterfaces = classe.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                for (Type genericType : genericTypes) {
                    return genericType;
                }
            }
        }

        //        Class<?> subClass = instance.getClass();
//        System.out.println("XXXX: " + subClass.getSuperclass());
////        while (subClass != subClass.getSuperclass()) {
//            subClass = subClass.getSuperclass().s;
//            if (subClass == null) throw new IllegalArgumentException();
//        }
//        ParameterizedType parameterizedType = (ParameterizedType) subClass.getGenericSuperclass();
//        return (Class<?>) parameterizedType.getActualTypeArguments()[parameterIndex];

        return null;
    }

//
//    @Pointcut("execution(* org.springframework.data.jpa.*.*.*(..))")
//    public void traced() {}
//
//    @Before("traced()")
//    public void trace(JoinPoint jp) {
//        Signature sig = jp.getSignature();
//        logger.info("AKI {} :", sig.toShortString());
////        NDC.push("  ");
//    }


//    @Before("execution(Object findAll*(..))")
//    public void findAll() {
//
//        System.out.println("User is findAll, but don't know from which package method was used!");
//    }

//    @After("execution(* org.springframework.data.jpa.repository.JpaRepository.*(..))")
//    public void after(JoinPoint joinPoint) {
//        //Advice
//        logger.info("ARGS: {}", joinPoint.getArgs());
//        logger.info("METHOD: {}", joinPoint.getSignature());
//    }

//    @AfterReturning(value = "execution(* br.com.teste.climbteste.repository.*.*(..))", returning = "result")
//    public void afterReturning(JoinPoint joinPoint, Object result) {
//        logger.info("{} returned with value {}", joinPoint, result);
//    }

//    @Around("@annotation(TrackTime)")
//    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("aki karaio");
//        return joinPoint.proceed();
//    }


    @Autowired
    private ManagerFactory managerFactory;
//    @Around("execution(* org.springframework.data.jpa.repository.*.*(..))")
//    @Around("@annotation(br.com.teste.climbteste.ClimbQuery) " +
//            "|| execution(* br.com.teste.climbteste.repository.*.*(..))")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        ClimbConnection connection = managerFactory.getConnection();
//

//
//        System.out.println("***************** INTERCEPT ********************");
//        logger.info("ARGS: {}", joinPoint.getArgs());
//        logger.info("METHOD: {}", joinPoint.getSignature());
////        Object proceed = joinPoint.proceed();
////        logger.info("RESULT: {}", proceed);
//
//        connection.save(joinPoint.getArgs()[0]);
//        return null;
//    }

    @Around("execution(* br.com.climb.climbspring.ClimbRepository.save(..))")
    public void save(ProceedingJoinPoint joinPoint) {

        System.out.println("***************** SAVE ********************");
        System.out.println(joinPoint.getThis());

        logger.info("ARGS: {}", Arrays.asList(joinPoint.getArgs()));
        logger.info("METHOD: {}", joinPoint.getSignature());

        ClimbConnection connection = null;

        try {
            connection = managerFactory.getConnection();
            connection.getTransaction().start();
            connection.save(joinPoint.getArgs()[0]);
            connection.getTransaction().commit();
        } catch (Exception e) {
            logger.error(joinPoint.getSignature().toString(),e);
            connection.getTransaction().rollback();
        } finally {
            connection.close();
        }

    }

    @Around("execution(* br.com.climb.climbspring.ClimbRepository.update(..))")
    public void update(ProceedingJoinPoint joinPoint) {

        System.out.println("***************** UPDATE ********************");
        logger.info("ARGS: {}", Arrays.asList(joinPoint.getArgs()));
        logger.info("METHOD: {}", joinPoint.getSignature());

        ClimbConnection connection = null;

        try {
            connection = managerFactory.getConnection();
            connection.getTransaction().start();
            connection.update(joinPoint.getArgs()[0]);
            connection.getTransaction().commit();
        } catch (Exception e) {
            logger.error(joinPoint.getSignature().toString(),e);
            connection.getTransaction().rollback();
        } finally {
            connection.close();
        }

    }

    @Around("execution(* br.com.climb.climbspring.ClimbRepository.delete(..))")
    public void delete(ProceedingJoinPoint joinPoint)  {

        System.out.println("***************** DELETE ********************");
        logger.info("ARGS: {}", Arrays.asList(joinPoint.getArgs()));
        logger.info("METHOD: {}", joinPoint.getSignature());

        ClimbConnection connection = null;

        try {
            connection = managerFactory.getConnection();
            connection.getTransaction().start();
            connection.delete(joinPoint.getArgs()[0]);
            connection.getTransaction().commit();
        } catch (Exception e) {
            logger.error(joinPoint.getSignature().toString(),e);
            connection.getTransaction().rollback();
        } finally {
            connection.close();
        }

    }

    @Around("execution(* br.com.climb.climbspring.ClimbRepository.findOne(..))")
    public Object findOne(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("***************** FIND ONE ********************");
        logger.info("ARGS: {}", Arrays.asList(joinPoint.getArgs()));
        logger.info("METHOD: {}", joinPoint.getSignature());

        Type type = findClassParameterType(getTypeInterface(joinPoint.getThis().toString()));

        ClimbConnection connection = managerFactory.getConnection();
        Object result = connection.findOne((Class) type, (Long) joinPoint.getArgs()[0]);
        return result;

    }

    @Around("execution(* br.com.climb.climbspring.ClimbRepository.find(..))")
    public Object find(ProceedingJoinPoint joinPoint) throws SgdbException {

        System.out.println("***************** FIND ONE ********************");
        logger.info("ARGS: {}", Arrays.asList(joinPoint.getArgs()));
        logger.info("METHOD: {}", joinPoint.getSignature());

        Type type = findClassParameterType(getTypeInterface(joinPoint.getThis().toString()));

        ClimbConnection connection = managerFactory.getConnection();
        return connection.find((Class) type);
    }

    @Around("@annotation(br.com.climb.climbspring.annotations.ClimbNativeQuery)")
    public Object nativeQuery(ProceedingJoinPoint joinPoint) throws SgdbException {

        System.out.println("***************** FIND ONE ********************");
        logger.info("ARGS: {}", Arrays.asList(joinPoint.getArgs()));
        logger.info("METHOD: {}", joinPoint.getSignature());

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        ClimbNativeQuery nativeQuery = method.getAnnotation(ClimbNativeQuery.class);
        logger.info("ANNOTATION: {}", nativeQuery);

        String query = nativeQuery.value();
        if (joinPoint.getArgs().length > 0) {

            int i = 1;
            for (Object arg : joinPoint.getArgs()) {
                query = query.replace("?" + i++,arg.toString());
            }

        }

        Type type = findClassParameterType(getTypeInterface(joinPoint.getThis().toString()));
        ClimbConnection connection = managerFactory.getConnection();

        String typeQuery = query.split(" ")[0].toLowerCase().trim();

        if (typeQuery.equals("select")) {
            return connection.findWithQuery((Class) type,query);
        }

        if (typeQuery.equals("delete")) {
            connection.delete(query);
        }

        if (typeQuery.equals("update")) {
            connection.update(query);
        }

        return null;
    }




}
