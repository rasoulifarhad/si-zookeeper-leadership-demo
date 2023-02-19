package com.farhad.example.zookeeper.leadership.aspect;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.zookeeper.leader.LeaderInitiator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LeaderElectionAspect {

    @Value("${spring.application.name:default_value}")
    private String appName;

    private final LeaderInitiator leaderInitiator;
    
     /**
     * Execute method annotated with {@link Leader} only if the current node is a leader
     * @param joinPoint
     * @throws Throwable
     */
    @Around(value = "@annotation(com.farhad.example.zookeeper.leadership.annotation.Leader)")
    public void aroundLeaderAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        if(this.leaderInitiator.getContext().isLeader()){
            log.info("=====  I'm the leader({}) I'll execute the Scheduled tasks =====",appName);
            joinPoint.proceed();
        }
    }
}
