package com.virtualize.varejodasorte.api.configuration;

public class PropertiesConstants {
    public static final String EMAIL_ENVIROMENT = "${mail.enviroment}";
    public static final String EMAIL_JOB_NAME = "${mail.job-name}";
    public static final String EMAIL_LINK_SUMARIO_EXECUCAO = "${mail.link-sumarios-execucao}";
    public static final String EMAIL_FROM = "${mail.from}";
    public static final String EMAIL_TO = "${mail.to}";
    public static final String EMAIL_SENDER = "${mail.sender}";
    public static final String EMAIL_SEND_EMAIL = "${mail.send-email}";
    public static final String THREAD_CORE_POOL_SIZE = "${api.thread.core-pool-size:20}";
    public static final String THREAD_MAX_POOL_SIZE = "${api.thread.max-pool-size:20}";
    public static final String THREAD_QUEUE_CAPACITY = "${api.thread.queue-capacity:40000}";
    public static final String THREAD_POOL_EXECUTOR_NAME = "API-Executor-";
    public static final String CACHE_EVICTION_INTERVAL_SDC = "${cache.eviction.interval.sdc:3600}";
    public static final String CACHE_EVICTION_INTERVAL_REPOSITORY = "${cache.eviction.interval.dao:3600}";
    public static final String CACHE_INITIAL_CAPACITY = "${cache.initial.capacity:3}";
    public static final String CACHE_MAXIMUM_CAPACITY_SDC = "${cache.maximum.capacity.sdc:10000}";
    public static final String CACHE_MAXIMUM_CAPACITY_REPOSITORY = "${cache.maximum.capacity.dao:10000}";
    public static final String API_SECURY_JWT = "API_JWT_Bearer_Authentication";
    private PropertiesConstants() {
        super();
    }
}
