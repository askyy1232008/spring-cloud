package com.cloud.config.domain;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

public class TestRule implements IRule {

    private ILoadBalancer loadBalancer;
    @Override
    public Server choose(Object o) {
     List<Server> servers= loadBalancer.getAllServers();
        return servers.get(0);  //取列表第一个服务
    }

    @Override
    public void setLoadBalancer(ILoadBalancer iLoadBalancer) {
        this.loadBalancer=iLoadBalancer;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.loadBalancer;
    }
}
