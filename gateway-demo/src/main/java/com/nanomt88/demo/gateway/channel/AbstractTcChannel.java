package com.nanomt88.demo.gateway.channel;

import com.nanomt88.demo.gateway.common.TcRequest;
import com.nanomt88.demo.gateway.common.TcResponse;
import com.nanomt88.demo.gateway.component.ITcComponent;
import com.nanomt88.demo.gateway.source.ITcClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 通道处理类，用于处理请求进来之后处理流程
 * 通道概念是对服务端来说的， 请求从服务端来了之后，经过网关，网关需要对请求进行部分处理（如鉴权、校验、报文映射等）
 * 通道的处理流程是固定的，主流程是：
 * 服务端接收到请求后预处理 --> 客户端请求预处理 --> 客户端调用
 */
@Slf4j
@Data
public abstract class AbstractTcChannel implements ITcChannel{

    /**
     * 父通道，父通道主要是对公共的部分进行处理（比如日志、限流、鉴权等）
     */
    private AbstractTcChannel parentChannel;

    /**
     * 远程调用的客户端
     */
    protected ITcClient client;


    /**
     * 服务端收到请求后预处理 （所属通讯层面）
     */
    protected List<ITcComponent> serverRequestPerProcessor;

    /**
     * 请求处理逻辑，在服务端收到请求预处理完成之后，进行统一处理
     */
    protected List<ITcComponent> requestProcessor;

    /**
     * 客户端调用之前的预处理，在请求处理完成之后
     */
    protected List<ITcComponent> clientDoRequestPreProcessor;

    /**
     * 客户端完成处理之后，收到下游服务返回的响应，对响应进行预处理
     */
    protected List<ITcComponent> clientDoRequestAfterProcessor;

    /**
     * 网关收到客户端响应后，对响应进行处理和包装
     */
    protected List<ITcComponent> responseProcessor;

    /**
     * 服务端收到请求后预处理（所属通讯层面）
     */
    protected List<ITcComponent> responsePerProcessor;

    /**
     *  服务端收到请求后预处理 （所属通讯层面）
     *
     * @param request
     * @param response
     */
    protected void doServerRequestPreProcess(TcRequest request, TcResponse response) {
        log.info("doServerRequestPreProcess , request:[{}], response:[{}]" , request, response);
        if (parentChannel != null && !CollectionUtils.isEmpty(parentChannel.serverRequestPerProcessor)) {
            for (ITcComponent component : parentChannel.serverRequestPerProcessor) {
                component.doProcess(request, response);
            }
        }
        if (!CollectionUtils.isEmpty(serverRequestPerProcessor)) {
            for (ITcComponent component : serverRequestPerProcessor) {
                component.doProcess(request, response);
            }
        }
    }

    /**
     * 请求处理逻辑，在服务端收到请求预处理完成之后，进行统一处理
     * @param request
     * @param response
     */
    protected void doRequestProcess(TcRequest request, TcResponse response){
        log.info("doRequestProcess , request:[{}], response:[{}]" , request, response);
        if (parentChannel != null && !CollectionUtils.isEmpty(parentChannel.requestProcessor)) {
            for (ITcComponent component : parentChannel.requestProcessor) {
                component.doProcess(request, response);
            }
        }
        if (!CollectionUtils.isEmpty(requestProcessor)) {
            for (ITcComponent component : requestProcessor) {
                component.doProcess(request, response);
            }
        }
    }

    /**
     * 客户端调用之前的预处理，在请求处理完成之后
     * @param request
     * @param response
     */
    protected void doClientRequestPreProcess(TcRequest request, TcResponse response) {
        log.info("doClientRequestPreProcess , request:[{}], response:[{}]" , request, response);

        if (!CollectionUtils.isEmpty(clientDoRequestPreProcessor)) {
            for (ITcComponent component : clientDoRequestPreProcessor) {
                component.doProcess(request, response);
            }
        }

        if (parentChannel != null && !CollectionUtils.isEmpty(parentChannel.clientDoRequestPreProcessor)) {
            for (ITcComponent component : parentChannel.clientDoRequestPreProcessor) {
                component.doProcess(request, response);
            }
        }
    }

    /**
     * 客户端完成处理之后，收到下游服务返回的响应，对响应进行预处理
     * @param request
     * @param response
     */
    protected void doClientRequestAfterProcess(TcRequest request, TcResponse response) {
        log.info("doClientRequestAfterProcess , request:[{}], response:[{}]" , request, response);
        if (parentChannel != null && !CollectionUtils.isEmpty(parentChannel.clientDoRequestAfterProcessor)) {
            for (ITcComponent component : parentChannel.clientDoRequestAfterProcessor) {
                component.doProcess(request, response);
            }
        }
        if (!CollectionUtils.isEmpty(clientDoRequestAfterProcessor)) {
            for (ITcComponent component : clientDoRequestAfterProcessor) {
                component.doProcess(request, response);
            }
        }
    }

    /**
     *  网关收到客户端响应后，对响应进行处理和包装
     * @param request
     * @param response
     */
    protected void doResponseProcess(TcRequest request, TcResponse response){
        log.info("doResponseProcess , request:[{}], response:[{}]" , request, response);

        if (!CollectionUtils.isEmpty(responseProcessor)) {
            for (ITcComponent component : responseProcessor) {
                component.doProcess(request, response);
            }
        }

        if (parentChannel != null && !CollectionUtils.isEmpty(parentChannel.responseProcessor)) {
            for (ITcComponent component : parentChannel.responseProcessor) {
                component.doProcess(request, response);
            }
        }
    }

    /**
     *  服务端收到请求后预处理（所属通讯层面）
     * @param request
     * @param response
     */
    protected void doResponsePerProcessor(TcRequest request, TcResponse response) {
        log.info("doResponsePerProcessor , request:[{}], response:[{}]" , request, response);

        if (!CollectionUtils.isEmpty(responsePerProcessor)) {
            for (ITcComponent component : responsePerProcessor) {
                component.doProcess(request, response);
            }
        }

        if (parentChannel != null && !CollectionUtils.isEmpty(parentChannel.responsePerProcessor)) {
            for (ITcComponent component : parentChannel.responsePerProcessor) {
                component.doProcess(request, response);
            }
        }
    }

}
