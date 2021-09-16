package com.weservice.catering.wtakeout.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.data.util.TypeInformation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderIdGenerator implements IdentifierGenerator {
    private Integer machineNum = 0;
    private AtomicInteger next = new AtomicInteger();


    @Override
    public Number nextId(Object entity) {
        if (next.get() > 99) {
            next = new AtomicInteger(0);
        }
        return next.incrementAndGet();
    }

    @Override
    public String nextUUID(Object entity) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String format = LocalDateTime.now().format(dateTimeFormatter);
        if (next.get() > 99) {
            next = new AtomicInteger(0);
        }
        int i = next.incrementAndGet();
        return format + String.valueOf(machineNum) + String.format("%02d", i);
    }
}
