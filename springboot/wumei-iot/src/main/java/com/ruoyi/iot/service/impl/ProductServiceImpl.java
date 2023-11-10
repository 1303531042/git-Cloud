package com.ruoyi.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.iot.domain.Device;
import com.ruoyi.iot.domain.Product;
import com.ruoyi.iot.domain.ThingsModel;
import com.ruoyi.iot.mapper.DeviceMapper;
import com.ruoyi.iot.mapper.ProductMapper;
import com.ruoyi.iot.mapper.ThingsModelMapper;
import com.ruoyi.iot.model.ChangeProductStatusModel;
import com.ruoyi.iot.model.vo.product.IdAndName;
import com.ruoyi.iot.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 产品Service业务层处理
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Service
public class ProductServiceImpl implements IProductService 
{
    private String tslPreKey ="tsl";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ThingsModelMapper thingsModelMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private RedisCache redisCache;



    /**
     * 查询产品
     * 
     * @param productId 产品主键
     * @return 产品
     */
    @Override
    public Product selectProductByProductId(Long productId)
    {
        return productMapper.selectVoById(productId);
    }

    /**
     * 查询产品列表
     *
     * @param product 产品
     * @return 产品
     */
    @Override
    public  List<Product> selectProductList( Product product) {
        return productMapper.selectVoList(new QueryWrapper<Product>().setEntity(product));
    }

    /**
     * 查询产品简短列表
     *
     * @return 产品
     */
    @Override
    public List<IdAndName> selectProductShortList(Product product) {

        return productMapper.selectVoList(new QueryWrapper<Product>().select("product_id", "product_name"))
                .stream()
                .map(p -> {
                    IdAndName idAndName = new IdAndName();
                    idAndName.setId(p.getProductId());
                    idAndName.setName(p.getProductName());
                    return idAndName;
                }).collect(Collectors.toList());
    }

    /**
     * 新增产品
     * 
     * @param product 产品
     * @return 结果
     */
    @Override
    public Product insertProduct(Product product)
    {
        product.setStatus(product.getStatus()==null?1:product.getStatus());
        productMapper.insert(product);
        return product;
    }

    /**
     * 修改产品
     * 
     * @param product 产品
     * @return 结果
     */
    @Override
    public int updateProduct(Product product)
    {
        product.setUpdateTime(DateUtils.getNowDate());
        return productMapper.update(product, new UpdateWrapper<Product>().eq("product_id", product.getProductId()));
    }

    /**
     * 更新产品状态,1-未发布，2-已发布
     *
     * @param model
     * @return 结果
     */
    @Override
    public AjaxResult changeProductStatus(ChangeProductStatusModel model)
    {
        if(model.getStatus()==1){
            // 产品下不能有设备
            Long[] productIds = new Long[1];
            productIds[0]=model.getProductId();
            int deviceCount=productMapper.deviceCountInProducts(productIds);
            if(deviceCount>0){
                return AjaxResult.error("取消发布失败，请先删除产品下的设备");
            }
        }else if(model.getStatus()==2){
            // 产品下必须包含物模型
            int thingsCount=productMapper.thingsCountInProduct(model.getProductId());
            if(thingsCount==0){
                return AjaxResult.error("发布失败，请先添加产品的物模型");
            }
            // 产品下物模型的标识符必须唯一
            int repeatCount=productMapper.thingsRepeatCountInProduct(model.getProductId());
            if(repeatCount>1){
                return AjaxResult.error("发布失败，产品物模型的标识符必须唯一");
            }
        }else{
            return AjaxResult.error("状态更新失败,状态值有误");
        }
        Product product = new Product();
        product.setStatus(model.getStatus());
        if (productMapper.update(product, new UpdateWrapper<Product>().eq("product_id", model.getProductId())) > 0) {
            return AjaxResult.success("操作成功");
        }
        return AjaxResult.error("状态更新失败");
    }

    /**
     * 批量删除产品
     * 
     * @param productIds 需要删除的产品主键
     * @return 结果
     */
    @Override
    @Transactional
    public AjaxResult deleteProductByProductIds(Long[] productIds)
    {
        // 删除物模型JSON缓存
        for(int i=0;i<productIds.length;i++){
            redisCache.deleteObject(tslPreKey+productIds[i]);
        }

        // 产品下不能有设备
        Long deviceCount = deviceMapper.selectCount(new QueryWrapper<Device>().in("product_id", productIds));
        if(deviceCount>0){
            return AjaxResult.error("删除失败，请先删除对应产品下的设备");
        }
        // 删除产品物模型
        thingsModelMapper.delete(new QueryWrapper<ThingsModel>().in("product_id", productIds));
        // 删除产品
        if(productMapper.delete(new QueryWrapper<Product>().in("product_id", productIds))>0){
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("删除失败");
    }


    /**
     * 删除产品信息
     * 
     * @param productId 产品主键
     * @return 结果
     */
    @Override
    public int deleteProductByProductId(Long productId)
    {
        // 删除物模型JSON缓存
        redisCache.deleteObject(tslPreKey+productId);
        return productMapper.delete(new QueryWrapper<Product>().eq("product_id", productId));
    }
}
