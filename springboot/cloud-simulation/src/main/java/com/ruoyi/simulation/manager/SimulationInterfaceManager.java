package com.ruoyi.simulation.manager;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ruoyi.simulation.compoment.SimulationInterface;
import com.ruoyi.simulation.domain.ProductRequest;
import com.ruoyi.simulation.domain.ProductSimulation;
import com.ruoyi.simulation.enums.RequestEnum;
import com.ruoyi.simulation.enums.TypeEnum;
import com.ruoyi.simulation.service.ProductSimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @auther: KUN
 * @date: 2023/3/29
 * @description: SimulationInterface管理者
 */
@Component
@RequiredArgsConstructor
public class SimulationInterfaceManager implements BeanFactoryAware {

    private ListableBeanFactory beanFactory;


    private final ProductSimulationService productSimulationService;




    /**
     * key: 模拟层组件名
     * value：模拟层组件
     */
    private final Map<String, SimulationInterface<?>> nameToSimulationMap = new HashMap<>();


    /**
     * key：产品ID
     * value：模拟层组件名
     */
    private final Map<Integer, String> productIDtoSimulationNameMap = new HashMap<>();

    /**
     * key： 模拟层组件名
     * value：产品ID
     */
    private final Map<String, List<Integer>> simulationNameToProductIDsMap = new HashMap<>();




    /**
     * key：产品id
     * value：
     *      key: ResultEnum
     *      value: 该方法的参树map
     */
    private final Map<Integer, Map<RequestEnum, Map<Integer, Object>>> productResultEnumToParametersMap = new HashMap<>();

    /**
     * key: 产品id
     * value:
     *      key: 属性名
     *      value：属性类型
     */
    private final Map<Integer, Map<String, TypeEnum>> productIDToPropertyTypeEnum = new HashMap<>();
    /**
     * key: 产品id
     * value:
     *      key: 动作名
     *      value：动作类型
     */
    private final Map<Integer, Map<String, TypeEnum>> productIDToActionTypeEnum = new HashMap<>();





    @PostConstruct
    public void init() {
        Map<String, SimulationInterface> beansOfType = beanFactory.getBeansOfType(SimulationInterface.class);
        beansOfType.forEach(nameToSimulationMap::put);
        initSimulationServiceOfMap();


    }

    private void initSimulationServiceOfMap() {
        List<ProductSimulation> productSimulations = productSimulationService.selectAll();

        productSimulations.forEach(p->{
            productIDtoSimulationNameMap.put(p.getProductId(), p.getSimulationName());
            List<Integer> productIDs = simulationNameToProductIDsMap.computeIfAbsent(p.getSimulationName(), key -> new ArrayList<>());
            productIDs.add(p.getProductId());
        });

//        productSimulations
//                .stream()
//                .map(ProductSimulation::getProductId)
//                .forEach(
//                        productId->{
//                            productRequests
//                                    .stream()
//                                    .filter(productRequest -> Objects.equals(productRequest.getProductID(), productId))
//        });
    }

    public void attach(ProductRequest productRequest) {
        Integer productID = productRequest.getProductID();
        Integer requestCode = productRequest.getRequestCode();
        Integer typeEnum = productRequest.getTypeEnum();
        String identifier = productRequest.getIdentifier();
        //注册产品的属性映射的requestEnum 注册产品属性对应的类型
        if (productRequest.getThingsModelType() == 1) {
//            setProductPropertyResultEnum(productID, identifier,  RequestEnumManager.getRequestEnum(getSimulationForProductID(productID).getClass(), requestCode));
            setTypeEnumForProductProperty(productID, identifier, TypeEnum.getInstance(typeEnum));
        }else if (productRequest.getThingsModelType() == 2){ //注册产品的动作映射的requestEnum 注册产品动作对应的类型
//            setProductActionResultEnum(productID, identifier,  RequestEnumManager.getRequestEnum(getSimulationForProductID(productID).getClass(), requestCode));
            setTypeEnumForProductAction(productID, identifier, TypeEnum.getInstance(typeEnum));
        }
        //注册产品执行requestEnum 所需的参数
        setProductRequestEnumParams(productID,  RequestEnumManager.getRequestEnum(getSimulationForProductID(productID).getClass(), requestCode)
                , JSONObject.parseObject(productRequest.getRequestParams(), new TypeReference<Map<Integer, Object>>(){}));

    }

    /**
     * 注销一个 productRequest
     */
    public void logOut(ProductRequest productRequest) {
        Integer productID = productRequest.getProductID();
        Integer requestCode = productRequest.getRequestCode();
        String identifier = productRequest.getIdentifier();
        //注销产品的属性映射的requestEnum 注册产品属性对应的类型
        if (productRequest.getThingsModelType() == 1) {
//            removeProductPropertyResultEnum(productID, identifier);
            removeTypeEnumForProductProperty(productID, identifier);
        }else if (productRequest.getThingsModelType() == 2){ //注销产品的动作映射的requestEnum 注册产品动作对应的类型
//            removeProductActionResultEnum(productID, identifier);
            removeTypeEnumForProductAction(productID, identifier);
        }
        //注销产品执行requestEnum 所需的参数
        removeProductRequestEnumParams(productID, RequestEnumManager.getRequestEnum(getSimulationForProductID(productID).getClass(), requestCode));
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    /**
     * 通过产品id获取到该组件
     */
    private SimulationInterface<?> getSimulationForProductID(Integer productID) {
        String simulationName = getSimulationNameByProductID(productID);
        return getSimulationForName(simulationName);
    }


    /**
     * 通过名字获取到Simulation
     */
    public SimulationInterface<?> getSimulationForName(String name) {
        return nameToSimulationMap.get(name);
    }

    /**
     * 获取到系统中所有到simulation的名字
     */
    public Set<String> getSimulationNames() {
        return nameToSimulationMap.keySet();
    }

    /**
     * @param simulationName simulation名字
     * @return 获取到simulation 所有的可用方法枚举
     */
    public List<? extends RequestEnum> getResultEnumsForSimulationName(String simulationName) {
        return RequestEnumManager.getSimulationRequestEnums(getSimulationForName(simulationName).getClass());
    }

    /**
     *  设置刚产品id对应的 simulation
     * @param productID
     * @param SimulationName
     */
    public void setProductIDTosimulationName(Integer productID, String SimulationName) {
        productIDtoSimulationNameMap.put(productID, SimulationName);

    }

    /**
     * 通过产品id 获取 到 simulation name
     *
     * @param productID
     * @return
     */
    public String getSimulationNameByProductID (Integer productID) {
        return productIDtoSimulationNameMap.get(productID);
    }

    /**
     * 通过产品id 获取 到 simulation name
     *
     * @param productID
     * @return
     */
    public SimulationInterface<?> getSimulationByProductID (Integer productID) {
        return nameToSimulationMap.get(productIDtoSimulationNameMap.get(productID));
    }


    /**
     * 设置产品requestEnum的所需要的参数
     * @param productID
     * @param requestEnum
     * @param params
     */
    private void setProductRequestEnumParams(Integer productID, RequestEnum requestEnum, Map<Integer, Object> params) {

        Map<RequestEnum, Map<Integer, Object>> requestEnumMapMap = productResultEnumToParametersMap.computeIfAbsent(productID, k -> new HashMap<>());
        requestEnumMapMap.put(requestEnum, params);
    }

    private void removeProductRequestEnumParams(Integer productID, RequestEnum requestEnum) {
        Map<RequestEnum, Map<Integer, Object>> requestEnumMapMap = productResultEnumToParametersMap.get(productID);
        if (requestEnumMapMap != null) {
            requestEnumMapMap.remove(requestEnum);
        }
    }

    /**
     * 获取产品requestEnum的所需要的参数
     * @param productID
     * @param requestEnum
     */
    public Map<Integer, Object> getProductRequestEnumParams(Integer productID, RequestEnum requestEnum) {
        Map<RequestEnum, Map<Integer, Object>> requestEnumMap = productResultEnumToParametersMap.get(productID);
        if (requestEnumMap != null) {
            return requestEnumMap.get(requestEnum);
        }
        return null;
    }

    private void setTypeEnumForProductProperty(Integer productID, String propertyName, TypeEnum typeEnum) {
        Map<String, TypeEnum> productPropertyTypeEnumMap = productIDToPropertyTypeEnum.computeIfAbsent(productID, k -> new HashMap<>());
        productPropertyTypeEnumMap.put(propertyName, typeEnum);
    }

    private void removeTypeEnumForProductProperty(Integer productID, String propertyName) {
        Map<String, TypeEnum> productPropertyTypeEnumMap = productIDToPropertyTypeEnum.get(productID);
        if (productPropertyTypeEnumMap != null) {
            productPropertyTypeEnumMap.remove(propertyName);
        }
    }

    private void setTypeEnumForProductAction(Integer productID, String actionName, TypeEnum typeEnum) {
        Map<String, TypeEnum> productActionTypeEnumMap = productIDToActionTypeEnum.computeIfAbsent(productID, k -> new HashMap<>());
        productActionTypeEnumMap.put(actionName, typeEnum);
    }

    private void removeTypeEnumForProductAction(Integer productID, String actionName) {
        Map<String, TypeEnum> productActionTypeEnumMap = productIDToActionTypeEnum.get(productID);
        if (productActionTypeEnumMap != null) {
            productActionTypeEnumMap.remove(actionName);
        }
    }
    public TypeEnum getTypeEnumToProductProperty(Integer productID, String propertyName) {
        Map<String, TypeEnum> productPropertyTypeEnumMap = productIDToPropertyTypeEnum.get(productID);
        if (productPropertyTypeEnumMap != null) {
            return productPropertyTypeEnumMap.get(propertyName);
        }
        return null;
    }

    public TypeEnum getTypeEnumToProductAction(Integer productID, String actionName) {
        Map<String, TypeEnum> productActionTypeEnumMap = productIDToActionTypeEnum.get(productID);
        if (productActionTypeEnumMap != null) {
            return productActionTypeEnumMap.get(actionName);
        }
        return null;
    }

    public RequestEnum getRequestEnum(String simulationName, int code) {
        return RequestEnumManager.getRequestEnum(getSimulationForName(simulationName).getClass(), code);
    }

    public List<Integer> getProductIdsForSimulationName(String simulationName) {
        return simulationNameToProductIDsMap.get(simulationName);
    }



}
