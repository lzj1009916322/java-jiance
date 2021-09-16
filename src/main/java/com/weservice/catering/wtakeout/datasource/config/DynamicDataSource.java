/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 *
 *
 *
 */

package com.weservice.catering.wtakeout.datasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多数据源
 *
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicContextHolder.peek();
    }

}
