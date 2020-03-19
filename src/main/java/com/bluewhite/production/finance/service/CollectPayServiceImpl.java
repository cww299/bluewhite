package com.bluewhite.production.finance.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.finance.attendance.dao.AttendancePayDao;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.bacth.service.BacthService;
import com.bluewhite.production.farragotask.entity.FarragoTask;
import com.bluewhite.production.farragotask.service.FarragoTaskService;
import com.bluewhite.production.finance.dao.CollectPayDao;
import com.bluewhite.production.finance.dao.FarragoTaskPayDao;
import com.bluewhite.production.finance.dao.NonLineDao;
import com.bluewhite.production.finance.dao.PayBDao;
import com.bluewhite.production.finance.entity.CollectPay;
import com.bluewhite.production.finance.entity.FarragoTaskPay;
import com.bluewhite.production.finance.entity.GroupProduction;
import com.bluewhite.production.finance.entity.MonthlyProduction;
import com.bluewhite.production.finance.entity.NonLine;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.group.dao.GroupDao;
import com.bluewhite.production.group.dao.TemporarilyDao;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.production.group.entity.Temporarily;
import com.bluewhite.production.group.service.GroupService;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.production.task.service.TaskService;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Service
public class CollectPayServiceImpl extends BaseServiceImpl<CollectPay, Long> implements CollectPayService {

    @Autowired
    private CollectPayDao dao;
    @Autowired
    private ProcedureDao procedureDao;
    @Autowired
    private BacthService bacthService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FarragoTaskService farragoTaskService;
    @Autowired
    private AttendancePayService attendancePayService;
    @Autowired
    private PayBService payBService;
    @Autowired
    private FarragoTaskPayService farragoTaskPayService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private NonLineDao nonLineDao;
    @Autowired
    private PayBDao payBDao;
    @Autowired
    private AttendancePayDao attendancePayDao;
    @Autowired
    private FarragoTaskPayDao farragoTaskPayDao;
    @Autowired
    private TemporarilyDao temporarilyDao;

    private static String rework = "返工再验";

    @Override
    public PageResult<CollectPay> findPages(CollectPay param, PageParameter page) {
        Page<CollectPay> pages = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按id过滤
            if (param.getId() != null) {
                predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
            }
            // 按id过滤
            if (param.getUserId() != null) {
                predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
            }
            // 按员工姓名
            if (!StringUtils.isEmpty(param.getUserName())) {
                predicate.add(cb.like(root.get("userName").as(String.class), "%" + param.getUserName() + "%"));
            }
            // 按类型
            if (!StringUtils.isEmpty(param.getType())) {
                predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
            }
            // 按时间过滤
            if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
                predicate.add(cb.between(root.get("allotTime").as(Date.class), param.getOrderTimeBegin(),
                    param.getOrderTimeEnd()));
            }
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
        }, page);
        PageResult<CollectPay> result = new PageResult<CollectPay>(pages, page);
        return result;
    }

    @Override
    public List<CollectPay> collect(CollectPay collectPay) {
        PageParameter page = new PageParameter();
        page.setSize(Integer.MAX_VALUE);
        List<CollectPay> collectPayList = this.findPages(collectPay, page).getRows();
        Map<Object, List<CollectPay>> mapCollectPay =
            collectPayList.stream().collect(Collectors.groupingBy(CollectPay::getUserId, Collectors.toList()));
        List<CollectPay> list = new ArrayList<CollectPay>();
        for (Object ps : mapCollectPay.keySet()) {
            List<CollectPay> psList = mapCollectPay.get(ps);
            // 计算出加绩总和
            double sumPay = psList.stream().filter(CollectPay -> CollectPay.getAddPerformancePay() != null)
                .mapToDouble(CollectPay::getAddPerformancePay).sum();
            // 计算A工资总和
            double sumPayA = psList.stream().mapToDouble(CollectPay::getPayA).sum();
            // 计算b工资总和
            double sumPayB = psList.stream().mapToDouble(CollectPay::getPayB).sum();
            // 计算上浮后b工资总和
            double sumAddPayB = psList.stream().filter(CollectPay -> CollectPay.getAddPayB() != null)
                .mapToDouble(CollectPay::getAddPayB).sum();

            CollectPay collect = new CollectPay();
            collect.setOrderTimeBegin(collectPay.getOrderTimeBegin());
            collect.setOrderTimeEnd(collectPay.getOrderTimeEnd());
            collect.setUserName(psList.get(0).getUserName());
            collect.setAddPerformancePay(sumPay);
            collect.setPayA(sumPayA);
            collect.setPayB(sumPayB);
            collect.setAddPayB(sumAddPayB);
            list.add(collect);
        }
        return list;
    }

    @Override
    public List<MonthlyProduction> monthlyProduction(MonthlyProduction monthlyProduction) {
        PageParameter page = new PageParameter();
        page.setSize(Integer.MAX_VALUE);
        List<MonthlyProduction> monthlyProductionList = new ArrayList<MonthlyProduction>();
        long size = DatesUtil.getDaySub(monthlyProduction.getOrderTimeBegin(), monthlyProduction.getOrderTimeEnd());

        for (int j = 0; j < size; j++) {
            Date beginTimes = null;
            if (j != 0) {
                // 获取下一天的时间
                beginTimes = DatesUtil.nextDay(monthlyProduction.getOrderTimeBegin());
            } else {
                // 获取第一天的开始时间
                beginTimes = monthlyProduction.getOrderTimeBegin();
            }
            // 获取一天的结束时间
            Date endTimes = DatesUtil.getLastDayOftime(beginTimes);
            Integer type = monthlyProduction.getType();
            Integer machinist = monthlyProduction.getMachinist();
            monthlyProduction = new MonthlyProduction();
            monthlyProduction.setOrderTimeBegin(beginTimes);
            monthlyProduction.setOrderTimeEnd(endTimes);
            monthlyProduction.setType(type);
            monthlyProduction.setMachinist(machinist);
            AttendancePay attendancePay = new AttendancePay();
            attendancePay.setOrderTimeBegin(monthlyProduction.getOrderTimeBegin());
            attendancePay.setOrderTimeEnd(monthlyProduction.getOrderTimeEnd());
            attendancePay.setType(monthlyProduction.getType());
            List<AttendancePay> attendancePayList = attendancePayService.findPages(attendancePay, page).getRows();
            // 考勤人数
            List<AttendancePay> list = attendancePayList.stream()
                .filter(AttendancePay -> AttendancePay.getWorkTime() != 0).collect(Collectors.toList());
            if (monthlyProduction.getType() == 3 || monthlyProduction.getType() == 4) {
                // 去除管理组的员工
                List<Group> groupList = groupDao.findByKindWorkIdAndType((long)116, monthlyProduction.getType());
                for (Group gp : groupList) {
                    List<User> userList = userDao.findByGroupId(gp.getId());
                    if (userList.size() > 0) {
                        for (User user : userList) {
                            list =
                                list.stream().filter(AttendancePay -> !AttendancePay.getUserId().equals(user.getId()))
                                    .collect(Collectors.toList());
                        }
                    }
                }
            }
            int peopleNumber = list.size();
            monthlyProduction.setPeopleNumber(peopleNumber);
            // 考勤总时间
            double time = list.stream().mapToDouble(AttendancePay::getWorkTime).sum();
            monthlyProduction.setTime(time);
            // 当天产量
            Bacth bacth = new Bacth();
            bacth.setOrderTimeBegin(monthlyProduction.getOrderTimeBegin());
            bacth.setOrderTimeEnd(monthlyProduction.getOrderTimeEnd());
            bacth.setType(monthlyProduction.getType());
            if (monthlyProduction.getType() == 3) {
                bacth.setFlag(0);
                bacth.setStatus(1);
                bacth.setStatusTime(monthlyProduction.getOrderTimeBegin());
            }
            List<Bacth> bacthList = bacthService.findPages(bacth, page).getRows();
            double productNumber = 0;

            double productPricethree = 0.0;
            // 当类型为机工时，产值和产量的计算方式变化
            if (monthlyProduction.getType() == 4) {
                // 已完成的任务
                Task task1 = new Task();
                task1.setOrderTimeBegin(monthlyProduction.getOrderTimeBegin());
                task1.setOrderTimeEnd(monthlyProduction.getOrderTimeEnd());
                task1.setType(monthlyProduction.getType());
                task1.setMachinist(machinist);
                List<Task> taskList = taskService.findPages(task1, page).getRows();
                Map<Long, List<Task>> maptask = taskList.stream().filter(Task -> Task.getBacthId() != null)
                    .collect(Collectors.groupingBy(Task::getBacthId, Collectors.toList()));

                for (Long ps1 : maptask.keySet()) {
                    List<Task> psList1 = maptask.get(ps1);
                    Bacth bac = bacthService.findOne(ps1);
                    List<Procedure> procedureList =
                        procedureDao.findByProductIdAndTypeAndFlag(bac.getProductId(), bac.getType(), bac.getFlag());
                    double procedureTime = procedureList.stream().mapToDouble(Procedure::getWorkingTime).sum();
                    // 总工序完成用时
                    double sumProTime = (procedureTime * bac.getNumber()) / 60;
                    // 工序完成用时
                    double sunTaskTime = psList1.stream().mapToDouble(Task::getExpectTime).sum();
                    bac.setNumber(NumUtils.roundTwo(NumUtils.round((bac.getNumber() * (sunTaskTime / sumProTime)), 0)));
                    bac.setHairPrice(
                        bac.getBacthHairPrice() == 0 ? bac.getBacthDepartmentPrice() : bac.getBacthHairPrice());
                    productNumber += bac.getNumber();
                    productPricethree += bac.getProductPrice();
                }

                if (monthlyProduction.getType() == 5) {
                    Map<String, List<Bacth>> map =
                        bacthList.stream().collect(Collectors.groupingBy(Bacth::getBacthNumber, Collectors.toList()));
                    for (Object ps : map.keySet()) {
                        List<Bacth> psList = map.get(ps);
                        Map<Long, List<Bacth>> map1 =
                            psList.stream().collect(Collectors.groupingBy(Bacth::getProductId, Collectors.toList()));
                        for (Object ps1 : map1.keySet()) {
                            List<Bacth> psList1 = map1.get(ps1);
                            if (psList1 != null && psList1.size() <= 2) {
                                productNumber += psList1.get(0).getNumber();
                            }
                        }
                    }
                }
            }

            if (monthlyProduction.getType() == 1 || monthlyProduction.getType() == 2
                || monthlyProduction.getType() == 3) {
                productNumber = bacthList.stream().mapToDouble(Bacth::getNumber).sum();
            }

            monthlyProduction.setProductNumber(productNumber);

            // 当天产值(外发单价乘以质检的个数)
            for (Bacth bac : bacthList) {
                if (monthlyProduction.getType() == 3) {
                    bac.setHairPrice(bac.getBacthDeedlePrice());
                } else {
                    bac.setHairPrice(bac.getBacthHairPrice());
                }
            }
            double productPrice = bacthList.stream().mapToDouble(Bacth::getProductPrice).sum();
            if (monthlyProduction.getType() == 4) {
                monthlyProduction.setProductPrice(NumUtils.round(productPricethree, 2));
            } else {
                monthlyProduction.setProductPrice(NumUtils.round(productPrice, 2));
            }
            // 返工出勤人数
            Task task = new Task();
            task.setOrderTimeBegin(monthlyProduction.getOrderTimeBegin());
            task.setOrderTimeEnd(monthlyProduction.getOrderTimeEnd());
            task.setType(monthlyProduction.getType());
            task.setFlag(1);
            List<Task> taskList = taskService.findPages(task, page).getRows();
            List<Long> userList = new ArrayList<Long>();
            // 返工出勤时间
            double reworkTurnTime = 0;
            for (Task ta : taskList) {
                if (!StringUtils.isEmpty(ta.getUserIds())) {
                    String[] idArr = ta.getUserIds().split(",");
                    if (idArr.length > 0) {
                        for (int i = 0; i < idArr.length; i++) {
                            Long userid = Long.parseLong(idArr[i]);
                            User user = userService.findOne(userid);
                            if (!userList.contains(userid)) {
                                attendancePay.setUserId(userid);
                                attendancePayList = attendancePayService.findPages(attendancePay, page).getRows();
                                if (attendancePayList.size() > 0) {
                                    reworkTurnTime += attendancePayList.get(0).getWorkTime();
                                }
                                monthlyProduction.setUserName(monthlyProduction.getUserName() == null
                                    ? user.getUserName() : monthlyProduction.getUserName() + "," + user.getUserName());
                                userList.add(userid);
                            }
                        }
                    }
                }
            }

            // 质检返工出勤时间
            if (monthlyProduction.getType() == 1) {

                monthlyProduction.setReworkTurnTime(reworkTurnTime);

            } else if (monthlyProduction.getType() == 2) {
                monthlyProduction.setReworkTurnTime(reworkTurnTime);

                // 针工返工出勤时间
            } else if ((monthlyProduction.getType() == 3 || monthlyProduction.getType() == 4)) {
                reworkTurnTime = taskList.stream().mapToDouble(Task::getTaskTime).sum();
                monthlyProduction.setReworkTurnTime(reworkTurnTime);
            }
            // 返工再验个数
            double reworkCount = taskList.stream().filter(Task -> Task.getProcedureName().equals(rework))
                .mapToDouble(Task::getNumber).sum();
            monthlyProduction.setReworkCount(reworkCount);

            int reworkNumber = userList.size();
            monthlyProduction.setReworkNumber(reworkNumber);
            // 返工个数
            double rework =
                taskList.stream().filter(Task -> Task.getNumber() != null).mapToDouble(Task::getNumber).sum();
            monthlyProduction.setRework(rework - reworkCount);
            // 返工时间
            double reworkTime = reworkTurnTime;
            monthlyProduction.setReworkTime(reworkTime / 60);

            if (monthlyProduction.getType() == 1) {
                int count = 0;
                String name = "";
                double atttime = 0.0;

                attendancePay.setUserId((long)98);
                attendancePayList = attendancePayService.findPages(attendancePay, page).getRows();

                if (userList.contains((long)98)) {
                    name = name + "," + attendancePayList.get(0).getUserName();
                    count = count + 1;
                    atttime = atttime + attendancePayList.get(0).getWorkTime();
                }

                attendancePay.setUserId((long)336);
                attendancePayList = attendancePayService.findPages(attendancePay, page).getRows();
                if (userList.contains((long)336)) {
                    name = attendancePayList.get(0).getUserName();
                    count = count + 1;
                    atttime = atttime + attendancePayList.get(0).getWorkTime();
                }

                attendancePay.setUserId((long)337);
                attendancePayList = attendancePayService.findPages(attendancePay, page).getRows();
                if (userList.contains((long)337)) {
                    name = name + "," + attendancePayList.get(0).getUserName();
                    count = count + 1;
                    atttime = atttime + attendancePayList.get(0).getWorkTime();
                }
                monthlyProduction.setReworkNumber(count);
                monthlyProduction.setUserName(name);
                monthlyProduction.setPeopleNumber(peopleNumber - count);
                monthlyProduction.setReworkTime(atttime);
                monthlyProduction.setReworkTurnTime(atttime);
                monthlyProduction.setTime(time - atttime);
            }

            // 杂工
            if (monthlyProduction.getType() == 5) {
                FarragoTask farragoTask = new FarragoTask();
                farragoTask.setOrderTimeBegin(monthlyProduction.getOrderTimeBegin());
                farragoTask.setOrderTimeEnd(monthlyProduction.getOrderTimeEnd());
                farragoTask.setType(monthlyProduction.getType());
                List<FarragoTask> farragoTaskList = farragoTaskService.findPages(farragoTask, page).getRows();
                double sumTime = farragoTaskList.stream().mapToDouble(FarragoTask::getTime).sum();
                monthlyProduction.setFarragoTaskTime(sumTime);
                monthlyProduction.setFarragoTaskPrice(sumTime * 14);;
            }
            monthlyProductionList.add(monthlyProduction);

        }
        return monthlyProductionList;
    }

    @Override
    public List<Map<String, Object>> bPayAndTaskPay(MonthlyProduction monthlyProduction) {
        List<Map<String, Object>> bPayAndTaskPay = new ArrayList<Map<String, Object>>();
        Group gp = new Group();
        gp.setType(3);
        List<Group> groupList = groupService.findList(gp);
        // A当天工资
        List<AttendancePay> attendancePayList = attendancePayDao.findByTypeAndAllotTimeBetween(
            monthlyProduction.getType(), monthlyProduction.getOrderTimeBegin(), monthlyProduction.getOrderTimeEnd());
        // B当天工资
        List<PayB> payBList = payBDao.findByTypeAndAllotTimeBetween(monthlyProduction.getType(),
            monthlyProduction.getOrderTimeBegin(), monthlyProduction.getOrderTimeEnd());
        // 杂工当天工资
        List<FarragoTaskPay> farragoTaskPayList = farragoTaskPayDao.findByTypeAndAllotTimeBetween(
            monthlyProduction.getType(), monthlyProduction.getOrderTimeBegin(), monthlyProduction.getOrderTimeEnd());
        Map<String, Object> map = null;
        for (Group group : groupList) {
            map = new HashMap<String, Object>();
            List<AttendancePay> list = attendancePayList.stream().filter(
                AttendancePay -> AttendancePay.getGroupId() != null && AttendancePay.getGroupId().equals(group.getId()))
                .collect(Collectors.toList());
            // 考勤总时间
            double sunTime = list.stream().mapToDouble(AttendancePay::getWorkTime).sum();
            if (list.size() == 0) {
                List<Temporarily> temporarilyList = temporarilyDao.findByTypeAndGroupIdAndTemporarilyDateBetween(3,
                    group.getId(), monthlyProduction.getOrderTimeBegin(), monthlyProduction.getOrderTimeEnd());
                if (temporarilyList.size() > 0) {
                    sunTime = temporarilyList.stream().mapToDouble(Temporarily::getWorkTime).sum();
                }
            }
            // 分组人员B工资总和
            double sumBPay =
                payBList.stream().filter(PayB -> PayB.getGroupId() != null && PayB.getGroupId().equals(group.getId())
                    && PayB.getPayNumber() != null).mapToDouble(PayB::getPayNumber).sum();
            // 分组人员杂工工资总和
            double sumfarragoTaskPay = farragoTaskPayList.stream()
                .filter(FarragoTaskPay -> FarragoTaskPay.getGroupId() != null
                    && FarragoTaskPay.getGroupId().equals(group.getId()))
                .mapToDouble(FarragoTaskPay::getPayNumber).sum();
            map.put("id", group.getId());
            map.put("name", group.getName());
            map.put("sunTime", sunTime);
            map.put("sumBPay", NumUtils.sum(sumBPay, sumfarragoTaskPay));
            Double sum = 0.0;
            if (sunTime != 0.0) {
                sum = NumUtils.div(NumUtils.sum(sumBPay, sumfarragoTaskPay), sunTime, 5);
            }
            map.put("specificValue", sum);
            map.put("price", list.size() > 0 ? list.get(0).getWorkPrice() : 0);
            bPayAndTaskPay.add(map);
        }
        return bPayAndTaskPay;
    }

    @Override
    public List<NonLine> headmanPay(NonLine nonLine) {
        // A工资
        AttendancePay attendancePay = new AttendancePay();
        attendancePay.setOrderTimeBegin(nonLine.getOrderTimeBegin());
        attendancePay.setOrderTimeEnd(nonLine.getOrderTimeEnd());
        attendancePay.setType(nonLine.getType());
        // B工资
        PayB payB = new PayB();
        payB.setOrderTimeBegin(nonLine.getOrderTimeBegin());
        payB.setOrderTimeEnd(nonLine.getOrderTimeEnd());
        payB.setType(nonLine.getType());

        List<NonLine> nonLineList = nonLineDao.findAll();
        for (NonLine nl : nonLineList) {
            attendancePay.setUserId(nl.getUserId());
            List<AttendancePay> manAttendancePayList = attendancePayService.findAttendancePay(attendancePay);
            List<AttendancePay> list = manAttendancePayList.stream()
                .filter(AttendancePay -> AttendancePay.getWorkTime() != 0).collect(Collectors.toList());
            // 产生考勤工作时间
            double sunTime = list.stream().mapToDouble(AttendancePay::getWorkTime).sum();
            nl.setTime(sunTime);
            // A工资总和
            double sumAPay = list.stream().mapToDouble(AttendancePay::getPayNumber).sum();
            payB.setUserId(nl.getUserId());
            List<PayB> payBList = payBService.findPayB(payB);
            // B工资总和
            double sumBPay = payBList.stream().mapToDouble(PayB::getPayNumber).sum();
            // 产生考勤工资和已发绩效
            double sumABPay = sumAPay + sumBPay;
            nl.setPay(sumABPay);
        }
        nonLineDao.save(nonLineList);
        return nonLineList;
    }

    @Override
    public NonLine updateHeadmanPay(NonLine nonLine) {
        NonLine nl = nonLineDao.findOne(nonLine.getId());
        // 将当月产量拼接到往月产量中
        // 往月产量解析
        if (nl.getYields() != null) {
            JSONObject nlJsonObj = JSONObject.parseObject(nl.getYields());
            JSONArray nlOn = nlJsonObj.getJSONArray("data");
            for (int i = 0; i < nlOn.size(); i++) {
                JSONObject jo = nlOn.getJSONObject(i);
                String name = jo.getString("name");
                // 当月产量解析
                JSONObject nonLineJsonObj = JSONObject.parseObject(nonLine.getYields());
                JSONArray nonLineOn = nonLineJsonObj.getJSONArray("data");
                for (int j = 0; j < nonLineOn.size(); j++) {
                    String name1 = nonLineOn.getJSONObject(j).getString("name");
                    String value1 = nonLineOn.getJSONObject(j).getString("value");
                    String price1 =
                        String.valueOf(Double.parseDouble(value1.equals("") ? "0" : value1) * nonLine.getOnePay());
                    if (name.equals(name1)) {
                        jo.put("name", name);
                        jo.put("value", value1);
                        jo.put("price", price1);
                    }
                }
            }
            nonLine.setYields(JSONObject.toJSONString(nlJsonObj));
        }

        nl.setYields(nonLine.getYields());
        // 产量
        Integer accumulateYield = 0;
        if (nl.getYields() != null) {
            JSONObject jsonObj = JSONObject.parseObject(nl.getYields());
            JSONArray on = jsonObj.getJSONArray("data");
            for (int i = 0; i < on.size(); i++) {
                JSONObject jo = on.getJSONObject(i);
                String value = jo.getString("value");
                String price1 =
                    String.valueOf(Double.parseDouble(value.equals("") ? "0" : value) * nonLine.getOnePay());
                jo.put("price", price1);
                value = value.equals("") ? "0" : value;

                accumulateYield += Integer.parseInt(value);
            }
            nl.setYields(JSONObject.toJSONString(jsonObj));

        }
        // 获取各组的产量
        nl.setAccumulateYield(accumulateYield);

        // 单只协助发货费用/元选择
        if (nonLine.getOnePay() != null) {
            nl.setOnePay(nonLine.getOnePay());
            // 累计产生的发货绩效
            nl.setCumulative(nl.getAccumulateYield() * nonLine.getOnePay());
        }

        // 人为手动加减量化绩效比
        if (nonLine.getAddition() != null) {
            nl.setAddition(nonLine.getAddition());
            // 人为改变后产生的量化绩效出勤时间
            nl.setChangeTime(nl.getAddition() * nl.getTime());
        }
        return nonLineDao.save(nl);
    }

    @Override
    public List<CollectPay> twoPerformancePay(CollectPay collectPay) {
        PageParameter page = new PageParameter();
        page.setSize(Integer.MAX_VALUE);
        if (collectPay.getDetail() != null) {
            collectPay.setOrderTimeBegin(collectPay.getOrderTimeBegin());
            collectPay.setOrderTimeEnd(collectPay.getOrderTimeEnd());
        } else {
            // 获取整个月的数据
            collectPay.setOrderTimeBegin(
                DatesUtil.getfristDayOftime(DatesUtil.getFirstDayOfMonth(collectPay.getOrderTimeBegin())));
            collectPay.setOrderTimeEnd(
                DatesUtil.getLastDayOftime(DatesUtil.getLastDayOfMonth(collectPay.getOrderTimeBegin())));
        }

        List<CollectPay> collectPayList = new ArrayList<CollectPay>();
        AttendancePay attendancePay = new AttendancePay();
        attendancePay.setOrderTimeBegin(collectPay.getOrderTimeBegin());
        attendancePay.setOrderTimeEnd(collectPay.getOrderTimeEnd());
        attendancePay.setType(collectPay.getType());
        attendancePay.setGroupId(collectPay.getGroupId());
        attendancePay.setUserName(collectPay.getUserName());
        List<AttendancePay> attendancePayList = attendancePayService.findPages(attendancePay, page).getRows();
        // 将一个月考勤人员按员工id分组
        Map<Long, List<AttendancePay>> mapCollectPay =
            attendancePayList.stream().filter(AttendancePay -> AttendancePay.getWorkTime() != 0)
                .collect(Collectors.groupingBy(AttendancePay::getUserId, Collectors.toList()));
        // b工资
        PayB payB = new PayB();
        payB.setOrderTimeBegin(collectPay.getOrderTimeBegin());
        payB.setOrderTimeEnd(collectPay.getOrderTimeEnd());
        payB.setType(collectPay.getType());

        // 杂工工资
        FarragoTaskPay farragoTaskPay = new FarragoTaskPay();
        farragoTaskPay.setOrderTimeBegin(collectPay.getOrderTimeBegin());
        farragoTaskPay.setOrderTimeEnd(collectPay.getOrderTimeEnd());
        farragoTaskPay.setType(collectPay.getType());
        CollectPay collect = null;
        for (Object ps : mapCollectPay.keySet()) {
            List<AttendancePay> psList = mapCollectPay.get(ps);
            if (collectPay.getDetail() == null) {
                collectPay.setUserId((Long)ps);
                // 通过条件查找绩效是否已入库
                collect = this.findCollectPay(collectPay);
            }
            if (collect == null || collect.getId() == null) {
                collect = new CollectPay();
                collect.setAllotTime(collectPay.getOrderTimeEnd());
                collect.setType(collectPay.getType());
                collect.setUserId(psList.get(0).getUserId());
                collect.setUserName(psList.get(0).getUserName());
                collect.setTimePay(psList.get(0).getWorkPrice());
            }

            // 分别统计出考勤总时间
            double sunTime = psList.stream().mapToDouble(AttendancePay::getWorkTime).sum();
            // 统计出A工资
            double payA = psList.stream().filter(AttendancePay -> AttendancePay.getPayNumber() != 0.0)
                .mapToDouble(AttendancePay::getPayNumber).sum();

            // B工资
            double sumBPay = 0;
            payB.setUserId((Long)ps);
            List<PayB> payBList = payBService.findPayB(payB);
            // 分组人员B工资总和
            sumBPay =
                payBList.stream().filter(PayB -> PayB.getPayNumber() != null).mapToDouble(PayB::getPayNumber).sum();
            // 杂工工资
            double sumfarragoTaskPay = 0;
            farragoTaskPay.setUserId((Long)ps);
            List<FarragoTaskPay> farragoTaskPayList = farragoTaskPayService.findFarragoTaskPay(farragoTaskPay);
            // 分组人员杂工工资总和
            sumfarragoTaskPay = farragoTaskPayList.stream().mapToDouble(FarragoTaskPay::getPayNumber).sum();
            // 汇总考勤总时间
            collect.setTime(sunTime);
            // 汇总B工资+杂工工资
            collect.setPayB(NumUtils.round(sumBPay + sumfarragoTaskPay, null));
            // 汇总A工资
            collect.setPayA(NumUtils.round(payA, null));
            Double sum = collect.getPayB() / collect.getPayA() * 100;
            collect.setRatio(NumUtils.round(sum.isNaN() || sum.isInfinite() ? 0.0 : sum, 2));
            // 小时单价
            collect.setTimePrice(NumUtils.round(collect.getPayB() / collect.getTime(), null));
            if (collect.getAddPerformancePay() == null) {
                collect.setAddPerformancePay(0.0);
            }
            collectPayList.add(collect);
        }
        if (collectPay.getDetail() == null) {
            dao.save(collectPayList);
        }
        return collectPayList;
    }

    @Override
    public synchronized CollectPay findCollectPay(CollectPay collectPay) {
        return dao.findByUserIdAndTypeAndAllotTimeBetween(collectPay.getUserId(), collectPay.getType(),
            collectPay.getOrderTimeBegin(), collectPay.getOrderTimeEnd());
    }

    @Override
    public CollectPay upadtePerformancePay(CollectPay collectPay) {
        CollectPay collect = dao.findOne(collectPay.getId());
        User user = userService.findOne(collect.getUserId());
        if (collectPay.getTimePrice() != null) {
            collectPay.setTimePay(NumUtils.round(
                user.getPrice() + (collectPay.getAddSelfNumber() == null ? 0.0 : collectPay.getAddSelfNumber()), null));
            collectPay.setAddPerformancePay(NumUtils.round(collect.getTime() * collectPay.getAddSelfNumber(), null));
            if (collectPay.getTimePrice() != null && collectPay.getAddSelfNumber() != null) {
                collectPay.setTimePay(NumUtils.round(user.getPrice() + collectPay.getAddSelfNumber(), null));
                collectPay
                    .setAddPerformancePay(NumUtils.round(collect.getTime() * collectPay.getAddSelfNumber(), null));
            }
        }
        collect.setTimePrice(NumUtils.round(collectPay.getTimePrice(), null));
        collect.setTimePay(NumUtils.round(collectPay.getTimePay(), null));
        collect.setAddSelfNumber(NumUtils.round(collectPay.getAddSelfNumber(), null));
        collect.setAddPerformancePay(NumUtils.round(collectPay.getAddPerformancePay(), null));
        dao.save(collect);
        return collect;
    }

    @Override
    public List<CollectPay> cottonOtherTask(CollectPay collectPay) {
        PageParameter page = new PageParameter();
        page.setSize(Integer.MAX_VALUE);

        List<CollectPay> collectPayList = new ArrayList<CollectPay>();
        Group group = new Group();
        group.setKindWorkId((long)111);
        List<Group> groupList = groupService.findList(group);
        // 组装出只属于充棉工的人员
        CollectPay collect = null;
        for (Group rp : groupList) {
            List<User> userList = userDao.findByGroupId(rp.getId());
            if (userList.size() > 0) {
                for (User us : userList) {
                    collect = new CollectPay();
                    double sumPayNumber = 0;
                    // 任务里查出除了充棉的类型，以外的所有任务
                    Task task = new Task();
                    task.setOrderTimeBegin(collectPay.getOrderTimeBegin());
                    task.setOrderTimeEnd(collectPay.getOrderTimeEnd());
                    task.setType(collectPay.getType());
                    List<Task> taskList = taskService.findPages(task, page).getRows();
                    taskList = taskList.stream().filter(Task -> Task.getProcedure().getProcedureTypeId() != (long)142)
                        .collect(Collectors.toList());

                    // 遍历任务，组装出符合不是充棉的任务
                    for (Task ta : taskList) {
                        if (!StringUtils.isEmpty(ta.getUserIds())) {
                            // 获取做任务的人员id
                            String[] ids = ta.getUserIds().split(",");
                            if (ids.length > 0) {
                                for (int i = 0; i < ids.length; i++) {
                                    Long id = Long.parseLong(ids[i]);
                                    // 当做任务的人员和充棉人员id匹配时，取得员工b工资
                                    if (us.getId().equals(id)) {
                                        PayB payb = new PayB();
                                        payb.setOrderTimeBegin(collectPay.getOrderTimeBegin());
                                        payb.setOrderTimeEnd(collectPay.getOrderTimeEnd());
                                        payb.setType(collectPay.getType());
                                        payb.setUserId(us.getId());
                                        payb.setTaskId(ta.getId());
                                        List<PayB> payBList = payBService.findPages(payb, page).getRows();
                                        double payNumber = payBList.stream().mapToDouble(PayB::getPayNumber).sum();
                                        sumPayNumber += payNumber;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    // 统计充棉组所做费充棉组的任务价值
                    collect.setUserName(us.getUserName());
                    collect.setPayB(sumPayNumber);
                    collectPayList.add(collect);
                }
            }
        }
        return collectPayList;
    }

    @Override
    public List<GroupProduction> groupProduction(GroupProduction groupProduction) {
        PageParameter page = new PageParameter();
        page.setSize(Integer.MAX_VALUE);
        // 检验组
        Group group = new Group();
        group.setKindWorkId((long)113);
        List<Group> groupList = groupService.findList(group);

        // 查出当日所有检验任务
        Task task = new Task();
        task.setOrderTimeBegin(groupProduction.getOrderTimeBegin());
        task.setOrderTimeEnd(groupProduction.getOrderTimeEnd());
        task.setType(groupProduction.getType());
        task.setProcedureTypeId((long)99);
        task.setFlag(0);
        List<Task> taskList = taskService.findPages(task, page).getRows();

        List<GroupProduction> groupProductionList = new ArrayList<GroupProduction>();

        // 将检验任务先按批次id分组，统计出数量
        Map<Object, List<Task>> mapTask =
            taskList.stream().collect(Collectors.groupingBy(Task::getBacthId, Collectors.toList()));

        GroupProduction production = null;

        for (Object ps : mapTask.keySet()) {
            List<Task> psList = mapTask.get(ps);

            // 在按产品id分组
            Map<Object, List<Task>> mapTaskProduct =
                psList.stream().collect(Collectors.groupingBy(Task::getProductId, Collectors.toList()));
            for (Object ps1 : mapTaskProduct.keySet()) {
                production = new GroupProduction();
                List<Task> psList1 = mapTaskProduct.get(ps1);
                // 该产品检验组总数量
                Integer sumNumber = psList1.get(0).getBacth().getNumber();

                Set<String> stringSet = new HashSet<String>();// 用Set存放不同的字符串
                Map<String, Integer> stringMap = new HashMap<String, Integer>();// 用Map记录相同的元素的个数
                String data = null;
                for (int i = 0; i < psList1.size(); i++) {
                    // 取出数组中的数据
                    data = psList1.get(i).getProcedureName();
                    // 如果Set集合里面有同样的数据，就用Map记录这个数据个数+1
                    if (stringSet.contains(data)) {
                        stringMap.put(data, stringMap.get(data) + 1);
                    } else {
                        // 否则，如果Set里面没有相同的数据，就放进Set里面，然后用Map记录这个数据个数为1
                        stringSet.add(data);// 添加不同的数据
                        stringMap.put(data, 1);// 个数记录为1
                    }
                }
                int count = stringSet.size();
                // 遍历任务，通过任务 的员工id和分组人员的员工id相匹配，相同则记录任务数
                Map<Object, Integer> map = new HashMap<Object, Integer>(4);
                map.put(groupList.get(0).getId(), 0);
                map.put(groupList.get(1).getId(), 0);
                map.put(groupList.get(2).getId(), 0);
                map.put(groupList.get(3).getId(), 0);

                for (Task ta : psList1) {
                    if (!StringUtils.isEmpty(ta.getUserIds())) {
                        String[] ids = ta.getUserIds().split(",");
                        if (ids.length > 0) {
                            int bre = 0;
                            for (int i = 0; i < ids.length; i++) {
                                Long id = Long.parseLong(ids[i]);
                                User user = userService.findOne(id);
                                for (Object groupId : map.keySet()) {
                                    if (user.getGroupId() != null && user.getGroupId().equals(groupId)) {
                                        int num = map.get(groupId);
                                        map.put(groupId, num + ta.getNumber());
                                        bre = 1;
                                    }
                                }
                                if (bre == 1) {
                                    break;
                                }
                            }
                        }
                    }
                }
                production.setName(psList1.get(0).getBacthNumber() + psList1.get(0).getProductName());
                production.setOneNumber(map.get(groupList.get(0).getId()) / count);
                production.setTwoNumber(map.get(groupList.get(1).getId()) / count);
                production.setThreeNumber(map.get(groupList.get(2).getId()) / count);
                production.setFourNumber(map.get(groupList.get(3).getId()) / count);
                production.setSumNumber(sumNumber);
                production.setOrderTimeBegin(groupProduction.getOrderTimeBegin());
                production.setRemark(psList1.get(0).getBacth().getRemarks());
                groupProductionList.add(production);

            }
        }
        return groupProductionList;
    }

    @Override
    public Object getMouthYields(Long id, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long size = 0;
        try {
            size = DatesUtil.getDaySub(DatesUtil.getfristDayOftime(DatesUtil.getFirstDayOfMonth(format.parse(date))),
                DatesUtil.getLastDayOftime(DatesUtil.getLastDayOfMonth(format.parse(date))));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        JSONObject outData = new JSONObject();
        JSONArray gResTable = new JSONArray();
        Date beginTimes = null;
        NonLine nonLine = nonLineDao.findOne(id);
        // 当产量不为null时，将数据取出，返回前端
        if (nonLine.getYields() != null) {
            JSONObject jsonObj = JSONObject.parseObject(nonLine.getYields());
            JSONArray on = jsonObj.getJSONArray("data");
            for (int i = 0; i < on.size(); i++) {
                JSONObject jo = on.getJSONObject(i);
                String value = jo.getString("name");
                try {
                    if (DatesUtil.equalsDate(format.parse(value), format.parse(date))) {
                        gResTable.add(jo);
                    }
                } catch (ParseException e) {
                }
            }
            outData.put("data", gResTable);

            // 当产量为null时，填充无数据json格式返回
        }
        if (nonLine.getYields() == null || gResTable.size() == 0) {
            for (int j = 0; j < size; j++) {
                if (j != 0) {
                    // 获取下一天的时间
                    beginTimes = DatesUtil.nextDay(beginTimes);
                } else {
                    // 获取第一天的开始时间
                    try {
                        beginTimes = DatesUtil.getfristDayOftime(DatesUtil.getFirstDayOfMonth(format.parse(date)));
                    } catch (ParseException e) {
                    }
                }
                JSONObject name = new JSONObject();
                name.put("name", sdf.format(beginTimes));
                name.put("value", "");
                name.put("price", "");
                gResTable.add(name);

                // 当月产量字段中没有当月json数据是，将当月json数据拼接到，原产量数据中
                if (nonLine.getYields() != null) {
                    JSONObject jsonObj = JSONObject.parseObject(nonLine.getYields());
                    JSONArray on = jsonObj.getJSONArray("data");
                    on.add(name);
                    JSONObject data = new JSONObject();
                    data.put("data", on);
                    nonLine.setYields(JSONObject.toJSONString(data));
                    nonLineDao.save(nonLine);
                }

            }
            outData.put("data", gResTable);
        }
        return outData;

    }

    @Override
    public List<CollectPay> findByTypeAndAllotTimeBetween(Integer type, Date startTime, Date endTime) {
        return dao.findByTypeAndAllotTimeBetween(type, startTime, endTime);
    }

}
