package com.dk.oa.controller;

import com.dk.oa.biz.EmployeeBiz;
import com.dk.oa.biz.GlobalBiz;
import com.dk.oa.dao.EmployeeDao;
import com.dk.oa.entity.Employee;
import com.dk.oa.global.FaceSpot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

//登陆和个人中心模块
@Controller("globalController")
public class GloablController {

    @Autowired
    private EmployeeBiz employeeBiz;

    @Autowired
    private GlobalBiz globalBiz;


    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    //登陆
    @RequestMapping("/login")
    public String login(HttpSession session, @RequestParam String sn, @RequestParam String password) {
        Employee employee = globalBiz.login(sn, password);
        if (employee == null) {
            return "redirect:to_login";
        }
        session.setAttribute("employee", employee);
        return "redirect:self";
    }

    //自登陆
    @RequestMapping(value = "/faceLogin/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String faceLogin(@PathVariable("id")String id,HttpSession session ){

        Employee employee = employeeBiz.get(id);
        if (employee!=null){
            session.setAttribute("employee",employee);
            return "SUC";
        }else {
            return "FAL";
        }
    }

    //二维码存储个人信息
    @RequestMapping("/to_getCode")
    @ResponseBody
    public String to_getCode(HttpSession session) throws Exception {

        Employee employee = (Employee) session.getAttribute("employee");

        String sn = employee.getSn();//用户id
        String name = employee.getName();//用户姓名
        String post = employee.getPost();//职位
        String depa = employee.getDepartment().getName();//部门名称
        String depaAddr = employee.getDepartment().getAddress();//部门地址
        //1.二维码中的信息
        String content = "用户id:"+sn+"\n"+"用户姓名:"+name+"\n"+"用户职位:"+post+"\n"+"用户部门名称:"+depa+"\n"+"用户部门地址:"+depaAddr;
        //2.通过zxing生成二维码(保存到本地图片，支持以data url的形式体现)
        //创建QRCodeWriter对象
        QRCodeWriter writer = new QRCodeWriter();
        Map hints = new HashMap();
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //基本配置
        // 1.二维码信息。2.图片类型。3.宽度。4.长度。5.编码格式
        BitMatrix bt = writer.encode(content, BarcodeFormat.QR_CODE, 200, 200,hints);
        //保存二维码到本地
        Path path = new File("D:\\java1701\\eclipse-workspace\\oa\\oa_web\\src\\main\\webapp\\assets\\img\\emp\\emp.png").toPath();
        MatrixToImageWriter.writeToPath(bt,"png",path);
        if (employee != null){
            String address = "assets/img/emp";
            return address;
        }else {
            return "FAL";
        }
    }

    //个人中心页面
    @RequestMapping("/self")
    public String self() {
        return "self";
    }

    //退出
    @RequestMapping("/quit")
    public String quit(HttpSession session) {
        session.setAttribute("employee", null);
        return "redirect:to_login";
    }

    //去修改密码
    @RequestMapping("/to_change_password")
    public String toChangePassword() {
        return "change_password";
    }

    //修改密码
    @RequestMapping("/change_password")
    public String changePassword(HttpSession session, @RequestParam String old, @RequestParam String new1, @RequestParam String new2) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee.getPassword().equals(old)) {
            if (new1.equals(new2)) {//成功
                employee.setPassword(new1);
                globalBiz.changePassword(employee);
                return "redirect:self";
            }
        }
        //失败，返回去修改，重新修改
        return "redirect:to_change_password";
    }

}
