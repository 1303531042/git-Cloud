package com.ruoyi.web.controller.tool;

 
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruoyi.common.core.controller.BaseController;

/**
 * swagger 接口
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/tool/swagger")
public class SwaggerController extends BaseController
{
    @GetMapping()
    @SaCheckPermission("tool:swagger:view")
    public String index()
    {
        return redirect("/swagger-ui.html");
    }
}
