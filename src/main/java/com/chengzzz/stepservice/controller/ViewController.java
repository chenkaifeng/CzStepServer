package com.chengzzz.stepservice.controller;


import com.chengzzz.stepservice.callback.resultCallBack;
import com.chengzzz.stepservice.entity.BatchUpdateStepData;
import com.chengzzz.stepservice.entity.UpdateStepData;
import com.chengzzz.stepservice.entity.Upsteps;
import com.chengzzz.stepservice.service.UpstepsService;
import com.chengzzz.stepservice.utils.ResultSet;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.sleep;

/**
 * <p>
 * ViewController
 * </p>
 *
 * @author 等什么柠檬君
 * @since 2020/8/10
 */

@RestController
@Slf4j
@RequestMapping(value = "Service")
public class ViewController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    static String tx = "手机号非法";
    @Autowired
    private UpstepsService upstepsService;

    @RequestMapping(value = "updateStep", method = RequestMethod.GET)
    public String login(@RequestParam("phone") String phone, @RequestParam("password") String password, @RequestParam("steps") String step, @RequestParam("flag") int flag, @RequestParam("date") String date, Model model) {


        if (phone.length() != 11) {
            return "{\"code\":510,\"msg\":\"手机号长度错误\"}";
        } else {
            if (flag == 1) {
                Upsteps upsteps = new Upsteps(phone, password, step, flag, "");
                upstepsService.updateOrInsert(upsteps);
                return "{\"code\":508,\"msg\":\"已加入数据库进行定时任务\"}";
            } else if (flag == 0) {
                upstepsService.updateStep(phone, password, step, flag, date, new resultCallBack() {
                    @Override
                    public void updateResult(String msg) {
                        getMsg(msg);
                        logger.info("控制器返回的msg" + msg);
                    }
                });

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return tx;
            } else {
                return "{\"code\":509,\"msg\":\"参数错误\"}";
            }


        }


    }

    @RequestMapping(value = "batchUpdateStep", method = RequestMethod.POST)
    public ResultSet batchUpdateStep(@RequestBody BatchUpdateStepData batchUpdateStepData) {

        logger.info("收到批量修改步数请求，手机号={}，修改列表={}", batchUpdateStepData.getPhone(), new Gson().toJson(batchUpdateStepData.getList()));
        if (batchUpdateStepData.getPhone().length() != 11) {
            return ResultSet.error("手机号码长度错误");
        }
        List<String> resultMsg = new ArrayList<>();

        int size = batchUpdateStepData.getList().size();
        CountDownLatch countDownLatch = new CountDownLatch(size);
        for (UpdateStepData updateStepData : batchUpdateStepData.getList()) {
            upstepsService.updateStep(batchUpdateStepData.getPhone(), batchUpdateStepData.getPassword(), updateStepData.getSteps(), 0, updateStepData.getDate().replaceAll("-", ""), (resultCallBack) msg -> {
                logger.info("修改日期={}，步数={}，服务层返回的结果={}", updateStepData.getDate(), updateStepData.getSteps(), msg);
                resultMsg.add(MessageFormat.format("修改日期=[{0}]，修改步数=[{1}]，结果=[{2}]\n", updateStepData.getDate(), updateStepData.getSteps(), msg));
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ResultSet.ok().put("resultMsg", resultMsg);
    }

    public static void getMsg(String msg) {
        tx = msg;
    }
}


