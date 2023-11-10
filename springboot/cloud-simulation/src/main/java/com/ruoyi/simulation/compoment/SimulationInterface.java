package com.ruoyi.simulation.compoment;


import com.ruoyi.simulation.automessage.AutoRequestExpression;
import com.ruoyi.simulation.automessage.AutoRequestMessage;
import com.ruoyi.simulation.domain.SimulationDeviceWrapper;
import com.ruoyi.simulation.domain.vo.ParamExplainVo;
import com.ruoyi.simulation.enums.RequestEnum;
import com.ruoyi.simulation.model.SimulationDeviceRequestTemplate;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/3/27
 * @description: 模拟数据层向上下两层提供可执行方法
 */
public interface SimulationInterface<T extends RequestEnum>{
    /**
     * 获取该模拟类名字
     * @return
     */
    String getSimulationName();


    /**
     * 获取调用RPC所有需要注入的参数内容
     *
     * @param requestEnum
     * @return
     */
    List<ParamExplainVo> getParamExplainVos(RequestEnum requestEnum);

    /**
     * 发送请求并处理请求
     */
    void requestAndHandlerResponse(SimulationDeviceRequestTemplate template);

    /**
     * 听取请求并做出响应
     */
    void listenRequestThenResponse(AutoRequestMessage autoRequestMessage);






}
