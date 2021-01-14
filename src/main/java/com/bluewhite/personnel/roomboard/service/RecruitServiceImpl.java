package com.bluewhite.personnel.roomboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.roomboard.dao.AdvertisementDao;
import com.bluewhite.personnel.roomboard.dao.RecruitDao;
import com.bluewhite.personnel.roomboard.dao.RewardDao;
import com.bluewhite.personnel.roomboard.entity.Advertisement;
import com.bluewhite.personnel.roomboard.entity.Recruit;
import com.bluewhite.personnel.roomboard.entity.Reward;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

import cn.hutool.core.date.DateUtil;

@Service
public class RecruitServiceImpl extends BaseServiceImpl<Recruit, Long> implements RecruitService {
    @Autowired
    private RecruitDao dao;
    @Autowired
    private BaseDataDao baseDataDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BaseDataDao baseDataDao2;
    @Autowired
    private UserService userService;
    @Autowired
    private RewardDao rewardDao;
    @Autowired
    private AdvertisementDao advertisementDao;

    @Override
    public PageResult<Recruit> findPage(Recruit sundry, PageParameter page) {
        Page<Recruit> pages = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按姓名查找
            if (!StringUtils.isEmpty(sundry.getName())) {
                predicate.add(cb.like(root.get("name").as(String.class), "%" + sundry.getName() + "%"));
            }
            // 按手机号查询
            if (!StringUtils.isEmpty(sundry.getPhone())) {
                predicate.add(cb.like(root.get("phone").as(String.class), "%" + sundry.getPhone() + "%"));
            }
            // 按部门查找
            if (!StringUtils.isEmpty(sundry.getOrgNameId())) {
                predicate.add(cb.equal(root.get("orgName").get("id").as(Long.class), sundry.getOrgNameId()));
            }
            // 按招聘人查询
            if (!StringUtils.isEmpty(sundry.getRecruitId())) {
                predicate.add(cb.equal(root.get("recruitId").as(Long.class), sundry.getRecruitId()));
            }
            // 按职位查找
            if (!StringUtils.isEmpty(sundry.getPositionId())) {
                predicate.add(cb.equal(root.get("position").get("id").as(Long.class), sundry.getPositionId()));
            }
            // 按平台查找
            if (!StringUtils.isEmpty(sundry.getPlatformId())) {
                predicate.add(cb.equal(root.get("platform").get("id").as(Long.class), sundry.getPlatformId()));
            }
            // 是否应面
            if (sundry.getType() != null) {
                predicate.add(cb.equal(root.get("type").as(Integer.class), sundry.getType()));
            }
            if (sundry.getState() != null) {
                predicate.add(cb.equal(root.get("state").as(Integer.class), sundry.getState()));
            }
            // 面试状态
            if (sundry.getAdopt() != null) {
                predicate.add(cb.equal(root.get("adopt").as(Integer.class), sundry.getAdopt()));
            }
            // 是否离职
            if (sundry.getQuit() != null) {
                predicate.add(cb.equal(root.get("user").get("quit").as(Integer.class), sundry.getQuit()));
            }
            // 按日期
            if (sundry.getTime() != null) {
                if (!StringUtils.isEmpty(sundry.getOrderTimeBegin())
                        && !StringUtils.isEmpty(sundry.getOrderTimeEnd())) {
                    predicate.add(cb.between(root.get("time").as(Date.class), sundry.getOrderTimeBegin(),
                            sundry.getOrderTimeEnd()));
                }
            }
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
        }, page);
        PageResult<Recruit> result = new PageResult<>(pages, page);
        return result;
    }

    @Override
    public Recruit addRecruit(Recruit recruit) {
        if (recruit.getPhone() != null) {
            User user = userService.findByPhone(recruit.getPhone());
            if (user != null && user.getQuit().equals(0)) {
                throw new ServiceException(recruit.getName() + "员工花名册中已入职，无法多次新增");
            }
        }
        return dao.save(recruit);
    }

    @Override
    public List<Map<String, Object>> Statistics(Recruit recruit) {
        Date OrderTimeBegin = recruit.getOrderTimeBegin();
        Date OrderTimeEnd = recruit.getOrderTimeEnd();
        if (recruit.getOrderTimeBegin() == null) {
            OrderTimeBegin = DatesUtil.getFirstDayOfMonth(recruit.getTime());
            OrderTimeEnd = DatesUtil.getLastDayOfMonth(recruit.getTime());
        }

        List<Recruit> list = dao.findByTimeBetween(OrderTimeBegin, OrderTimeEnd);
        List<Map<String, Object>> allList = new ArrayList<>();
        Map<String, Object> allMap = null;
        Map<Long, List<Recruit>> map = list.stream().filter(Recruit -> Recruit.getOrgNameId() != null && Recruit.getOrgNameId() != 318)
                .collect(Collectors.groupingBy(Recruit::getOrgNameId, Collectors.toList()));
        for (Long ps1 : map.keySet()) {
            allMap = new HashMap<>();
            Date date = new Date();
            List<Recruit> psList1 = map.get(ps1);
            Long d = psList1.stream().filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())).count();// 邀约面试
            Long c = psList1.stream().filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                    && Recruit.getType() != null && Recruit.getType().equals(1)).count();// 应邀面试
            Long b = psList1.stream().filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                    && Recruit.getAdopt() != null && Recruit.getAdopt().equals(1)).count();// 面试合格
            Long e = psList1.stream().filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                    && Recruit.getState() != null && Recruit.getState().equals(2)).count();// 拒绝入职
            Long f = psList1.stream()
                    .filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                            && Recruit.getState() != null && Recruit.getUserId() != null && Recruit.getState().equals(1)
                            && Recruit.getUser().getQuit().equals(0))
                    .count();// 已入职且在职
            Long g = psList1.stream().filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                    && Recruit.getState() != null && Recruit.getState().equals(3)).count();// 即将入职

            Long h = psList1.stream()
                    .filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                            && Recruit.getUserId() != null && Recruit.getUser().getQuit().equals(1)
                            && DatesUtil.getDaySub(date, Recruit.getUser().getQuitDate()) < 32)
                    .count();// 短期入职离职
            Long k = psList1.stream().filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                    && Recruit.getState() != null && Recruit.getState().equals(1)).count();// 已入职
            Long l = psList1.stream()
                    .filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                            && Recruit.getState() != null && Recruit.getUserId() != null && Recruit.getState().equals(1)
                            && Recruit.getUser().getQuit().equals(1))
                    .count();// 已入职且离职
            List<Recruit> list2 = psList1.stream()
                    .filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId()))
                    .collect(Collectors.toList());
            List<Recruit> list3 = psList1.stream()
                    .filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                            && Recruit.getType() != null && Recruit.getType().equals(1))
                    .collect(Collectors.toList());// 应邀面试
            List<Recruit> list4 = psList1.stream()
                    .filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                            && Recruit.getAdopt() != null && Recruit.getAdopt().equals(1))
                    .collect(Collectors.toList());// 面试合格
            List<Recruit> list5 = psList1.stream()
                    .filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                            && Recruit.getState() != null && Recruit.getState().equals(2))
                    .collect(Collectors.toList());// 拒绝入职
            List<Recruit> list6 = psList1.stream()
                    .filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                            && Recruit.getState() != null && Recruit.getUserId() != null && Recruit.getState().equals(1)
                            && Recruit.getUser().getQuit().equals(0))
                    .collect(Collectors.toList());// 已入职且在职
            List<Recruit> list7 = psList1.stream()
                    .filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                            && Recruit.getState() != null && Recruit.getState().equals(3))
                    .collect(Collectors.toList());// 即将入职
            List<Recruit> list8 = psList1.stream()
                    .filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                            && Recruit.getUserId() != null && Recruit.getUser().getQuit().equals(1)
                            && DatesUtil.getDaySub(date, Recruit.getUser().getQuitDate()) < 32)
                    .collect(Collectors.toList());// 短期入职离职
            BaseData baseData = baseDataDao.findOne(ps1);
            String string = baseData.getName();
            allMap.put("username", string);
            allMap.put("mod1", d);
            allMap.put("mod2", c);
            allMap.put("mod3", b);
            allMap.put("mod4", e);
            allMap.put("mod5", f);
            allMap.put("mod6", g);
            allMap.put("mod7", h);
            allMap.put("mod8", k);
            allMap.put("mod9", l);
            allMap.put("mod10", list2);
            allMap.put("mod11", list3);
            allMap.put("mod12", list4);
            allMap.put("mod13", list5);
            allMap.put("mod14", list6);
            allMap.put("mod15", list7);
            allMap.put("mod16", list8);
            allList.add(allMap);
        }
        return allList;
    }

    @Override
    public List<Recruit> soon(Recruit recruit) {
        // Date date = new Date();
        if (recruit.getOrderTimeBegin() == null || recruit.getOrderTimeEnd() == null) {
        	throw new ServiceException("请输入查找的时间范围");
        }
        List<Recruit> list = dao.findByTestTimeBetween(recruit.getOrderTimeBegin(),
        		recruit.getOrderTimeEnd());
        List<Recruit> recruits = list.stream()
                .filter(Recruit -> 
            		Recruit.getUserId() != null &&
            		Recruit.getUser().getQuit().equals(1) &&
            		DatesUtil.getDaySub(Recruit.getUser().getEntry(), Recruit.getUser().getQuitDate()) < 32
            		// DateUtil.isIn(Recruit.getUser().getQuitDate(), recruit.getOrderTimeBegin(), recruit.getOrderTimeEnd())
                )
                .collect(Collectors.toList());
        //  && DatesUtil.getDaySub(date, Recruit.getUser().getQuitDate()) < 32
        return recruits;
    }

    @Override
    public Map<String, List<Map<String, Object>>> users(Recruit recruit) {
        Map<String, Object> allMap = null;
        Map<String, Object> countMap = null;
        List<Map<String, Object>> allList = new ArrayList<>();
        List<Map<String, Object>> countList = new ArrayList<>();
        Map<String, List<Map<String, Object>>> allMapList = new HashMap<>();
        List<User> list = userDao.findByQuitDateBetween(DatesUtil.getFirstDayOfMonth(recruit.getTime()),
                DatesUtil.getLastDayOfMonth(recruit.getTime()));
        List<User> list2 = list.stream().filter(User -> User.getQuit().equals(1)).collect(Collectors.toList());
        for (User user : list2) {
            allMap = new HashMap<>();
            String userName = user.getUserName();
            String orgName = null;
            if (user.getOrgName() != null) {
                orgName = user.getOrgName().getName();
            }
            String positionName = null;
            if (user.getPosition() != null) {
                positionName = user.getPosition().getName();
            }
            String quitType = null;
            if (user.getQuitType() != null) {
                quitType = user.getQuitType().getName();
            }
            Date entry = user.getEntry();
            Date quitDate = user.getQuitDate();
            String reason = user.getReason();
            allMap.put("userName", userName);
            allMap.put("orgName", orgName);
            allMap.put("positionName", positionName);
            allMap.put("entry", entry);
            allMap.put("quitDate", quitDate);
            allMap.put("reason", reason);
            allMap.put("quitType", quitType);
            allList.add(allMap);
        }

        List<User> list3 = userDao.findByQuitDateBetween(DatesUtil.getFirstDayOfMonth(recruit.getTime()),
                DatesUtil.getLastDayOfMonth(recruit.getTime()));
        Map<Long, List<User>> map2 = list3.stream().filter(User -> User.getOrgNameId() != null)
                .collect(Collectors.groupingBy(User::getOrgNameId, Collectors.toList()));
        for (Long ps2 : map2.keySet()) {
            countMap = new HashMap<>();
            List<User> psList2 = map2.get(ps2);
            Long i = psList2.stream()
                    .filter(User -> User.getOrgNameId().equals(User.getOrgNameId()) && User.getQuit().equals(1))
                    .count();
            Long j = psList2.stream()
                    .filter(User -> User.getQuitTypeId() != null && User.getOrgNameId().equals(User.getOrgNameId()) && User.getQuit().equals(1) && User.getQuitTypeId().equals((long) 435)).count();//自离
            Long k = psList2.stream()
                    .filter(User -> User.getQuitTypeId() != null && User.getOrgNameId().equals(User.getOrgNameId()) && User.getQuit().equals(1) && User.getQuitTypeId().equals((long) 436)).count();//劝退
            Long l = psList2.stream()
                    .filter(User -> User.getQuitTypeId() != null && User.getOrgNameId().equals(User.getOrgNameId()) && User.getQuit().equals(1) && User.getQuitTypeId().equals((long) 437)).count();//开除
            String orgName = baseDataDao2.findOne(ps2).getName();
            countMap.put("count", i);
            countMap.put("count1", j);
            countMap.put("count2", k);
            countMap.put("count3", l);
            countMap.put("orgName", orgName);
            countList.add(countMap);
        }
        allMapList.put("StringUser", allList);
        allMapList.put("countUser", countList);
        return allMapList;
    }

    @Override
    public Map<String, List<Map<String, Object>>> analysis(Recruit recruit) {
        List<Map<String, Object>> maps = Statistics(recruit);
        List<Map<String, Object>> allList = new ArrayList<>();
        List<Map<String, Object>> countList = new ArrayList<>();
        Map<String, Object> allMap = new HashMap<>();
        Map<String, Object> countMap = null;
        Map<String, List<Map<String, Object>>> allMapList = new HashMap<>();
        List<User> list = userDao.findByQuitDateBetween(DatesUtil.getFirstDayOfMonth(recruit.getTime()),
                DatesUtil.getLastDayOfMonth(recruit.getTime()));
        double sum4 = list.stream().filter(User -> User.getQuit().equals(1)).count();// 离职人数
        double sum = 0;// 应邀面试人数汇总
        double sum1 = 0;// 面试合格人数汇总
        double sum2 = 0;// 入职人数
        double sum3 = 0;// 入职且离职
        double sum6 = 0;// 入职且在职人数
        User user = new User();
        user.setIsAdmin(false);
        double sum5 = userService.findUserList(user).stream()
                .filter(User -> (User.getQuitDate() == null || User.getQuitDate().after(recruit.getTime()))
                        && (User.getEntry() != null && User.getEntry().before(recruit.getTime())))
                .count();// 初期人员
        for (Map<String, Object> map : maps) {
            Object aInteger = map.get("mod2");// 应邀面试人数
            Object aInteger2 = map.get("mod3");// 面试合格人数
            Object aInteger3 = map.get("mod8");// 已入职
            Object aInteger4 = map.get("mod9");// 已入职且离职
            Object aInteger5 = map.get("mod5");// 已入职且在职
            sum = sum + Integer.parseInt(aInteger == null ? "" : aInteger.toString());// 应邀面试人数
            sum1 = sum1 + Integer.parseInt(aInteger2 == null ? "" : aInteger2.toString());// 面试合格人数
            sum2 = sum2 + Integer.parseInt(aInteger3 == null ? "" : aInteger3.toString());// 已入职
            sum3 = sum3 + Integer.parseInt(aInteger4 == null ? "" : aInteger4.toString());// 已入职且离职
            sum6 = sum6 + Integer.parseInt(aInteger5 == null ? "" : aInteger5.toString());// 已入职且在职
        }
        double a = 0;
        if (sum != 0) {
            a = NumUtils.div(sum1 * 100, sum, 2);// 面试通过率
        }
        double b = 0;
        if (sum1 != 0) {
            b = NumUtils.div(sum2 * 100, sum1, 2);// 入职率
        }
        double c = 0;
        if (sum2 != 0) {
            c = NumUtils.div(sum3 * 100, sum2, 2);// 短期流失率
        }
        double d = 0;
        if ((sum5 + sum2) != 0) {
            d = NumUtils.div(sum4 * 100, (sum5 + sum2), 2);// 离职率
        }
        double e = 0;
        if (sum1 != 0) {
            e = NumUtils.div(sum6 * 100, sum1, 2);// 留用率
        }

        allMap.put("md1", a);
        allMap.put("md2", b);
        allMap.put("md3", c);
        allMap.put("md4", d);
        allMap.put("md5", e);
        allList.add(allMap);
        allMapList.put("Analysis", allList);

        List<Recruit> list2 = dao.findByTimeBetween(DatesUtil.getFirstDayOfMonth(recruit.getTime()),
                DatesUtil.getLastDayOfMonth(recruit.getTime()));
        Map<Long, List<Recruit>> map = list2.stream().filter(Recruit -> Recruit.getPlatformId() != null)
                .collect(Collectors.groupingBy(Recruit::getPlatformId, Collectors.toList()));
        for (Long ps1 : map.keySet()) {
            countMap = new HashMap<>();
            List<Recruit> psList1 = map.get(ps1);
            Long f = psList1.stream().filter(Recruit -> Recruit.getPlatformId().equals(Recruit.getPlatformId()))
                    .count();// 统计入职途径的人数
            BaseData baseData = baseDataDao.findOne(ps1);
            String string = baseData.getName();
            countMap.put("md7", string);
            countMap.put("md6", f);
            countList.add(countMap);
        }
        allMapList.put("summaryCount", countList);
        return allMapList;
    }

    @Override
    public List<Recruit> findList() {
        List<Recruit> recruits = dao.findAll();
        List<Recruit> list = recruits.stream().filter(Recruit -> Recruit.getUserId() != null && Recruit.getState() == 1)
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Map<String, Object>> findfGroupList() {
        List<Recruit> recruits = dao.findAll();
        List<Map<String, Object>> allList = new ArrayList<>();
        Map<String, Object> allMap = null;
        List<Recruit> list = recruits.stream().filter(Recruit -> Recruit.getUserId() != null && Recruit.getState() == 1)
                .collect(Collectors.toList());
        Map<Long, List<Recruit>> map = list.stream().filter(Recruit -> Recruit.getRecruitId() != null)
                .collect(Collectors.groupingBy(Recruit::getRecruitId, Collectors.toList()));
        for (Long ps1 : map.keySet()) {
            allMap = new HashMap<>();
            List<Recruit> psList1 = map.get(ps1);
            List<Recruit> recruits2 = psList1.stream()
                    .filter(Recruit -> Recruit.getRecruitId().equals(Recruit.getRecruitId()))
                    .collect(Collectors.toList());
            for (Recruit recruit : recruits2) {
                String string = null;
                string = recruit.getRecruitName();
                Long integer = recruit.getRecruitId();
                List<Reward> rewards = rewardDao.findByRecruitIdAndType(integer, 0);
                List<Reward> rewards2 = rewardDao.findByRecruitIdAndType(integer, 1);
                Long integer2 = recruit.getId();
                double price = 0;// 汇总奖励多少钱
                double ReceivePrice = 0;// 汇总领取多少钱
                if (rewards.size() > 0) {
                    for (Reward reward : rewards) {
                        price = price + reward.getPrice();
                    }
                }
                if (rewards2.size() > 0) {
                    for (Reward reward : rewards2) {
                        ReceivePrice = ReceivePrice + reward.getPrice();
                    }
                }
                allMap.put("recruitName", string);
                allMap.put("coverRecruitId", integer2);
                allMap.put("recruitId", integer);
                allMap.put("price", price);
                allMap.put("ReceivePrice", ReceivePrice);
            }
            allList.add(allMap);
        }
        return allList;
    }

    /*
     * 按条件查询被招聘的人
     */
    @Override
    public List<Recruit> findCondition(Recruit recruit) {
        List<Recruit> recruits = dao.findByRecruitIdAndState(recruit.getRecruitId(), 1);
        return recruits;
    }

    @Override
    public Recruit findPrice(Recruit recruit) {
        List<Reward> rewards = rewardDao.findBycoverRecruitIdAndType(recruit.getId(), 0);
        double ReceivePrice = 0;
        for (Reward reward2 : rewards) {
            ReceivePrice = ReceivePrice + reward2.getPrice();
        }
        recruit.setReceivePrice(ReceivePrice);
        return recruit;
    }

    @Override
    @Transactional
    public int deletes(String[] ids) {
        int count = 0;
        if (!StringUtils.isEmpty(ids)) {
            for (int i = 0; i < ids.length; i++) {
                Long id = Long.parseLong(ids[i]);
                Recruit recruit = dao.findOne(id);
                Long userId = recruit.getUserId();
                recruit.setUserId(null);
                recruit.setUser(null);
                List<Advertisement> advertisement = advertisementDao.findByRecruitIdAndType(id, 1);
                if (advertisement.size() > 0) {
                    for (Advertisement advertisement2 : advertisement) {
                        advertisementDao.delete(advertisement2.getId());
                    }
                }
                List<Reward> reward = rewardDao.findBycoverRecruitId(id);
                if (reward.size() > 0) {
                    for (Reward reward2 : reward) {
                        rewardDao.delete(reward2.getId());
                    }
                }
                dao.delete(id);
                if (userId != null) {
                    userService.delete(userId);
                }
                count++;
            }
        }
        return count;
    }

    /*
     * 按每天的汇总
     */
    @Override
    public List<Map<String, Object>> sumday(Recruit recruit) {
        List<Recruit> list = dao.findByTimeBetween(recruit.getOrderTimeBegin(), recruit.getOrderTimeEnd());
        List<Map<String, Object>> allList = new ArrayList<>();
        Map<String, Object> allMap = new HashMap<>();
        Long f = list.stream().filter(Recruit -> Recruit.getType() != null && Recruit.getType().equals(1)).count();// 应邀面试
        Long a = list.stream().filter(Recruit -> Recruit.getType() != null && Recruit.getType().equals(0)).count();// 没有应邀面试
        Long b = list.stream().filter(Recruit -> Recruit.getAdopt() != null && Recruit.getAdopt().equals(1)).count();// 面试合格
        Long c = list.stream().filter(Recruit -> Recruit.getAdopt() != null && Recruit.getAdopt().equals(2)).count();// 待定
        Long d = list.stream().filter(Recruit -> Recruit.getAdopt() != null && Recruit.getAdopt().equals(0)).count();// 面试不合格
        int e = list.size();
        allMap.put("md1", b);
        allMap.put("md2", c);
        allMap.put("md3", d);
        allMap.put("md4", e);
        allMap.put("md5", a);
        allMap.put("md6", f);
        allList.add(allMap);
        return allList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRecruit(String[] ids, Integer state) {
        int count = 0;
        if (!StringUtils.isEmpty(ids)) {
            for (int i = 0; i < ids.length; i++) {
                Long id = Long.parseLong(ids[i]);
                Recruit recruit = dao.findOne(id);
                if (state == 1) {
                    User user = new User();
                    user.setQuit(0);
                    user.setEntry(recruit.getTestTime());
                    user.setOrgNameId(recruit.getOrgNameId());
                    user.setPositionId(recruit.getPositionId());
                    user.setUserName(recruit.getName());
                    user.setPhone(recruit.getPhone());
                    user.setGender(recruit.getGender());
                    User oldUser = userService.findByPhone(recruit.getPhone());
                    if (oldUser != null) {
                        user.setId(oldUser.getId());
                        userService.save(user);
                    } else {
                        userService.addUser(user);
                    }
                    recruit.setUserId(user.getId());
                    recruit.setState(state);
                    dao.save(recruit);
                    count++;
                }
                if (state == 2) {
                    recruit.setState(state);
                    dao.save(recruit);
                    count++;
                }
                if (state == 3) {
                    recruit.setState(state);
                    dao.save(recruit);
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> quitDeposit(Recruit recruit) {
        List<Map<String, Object>> maps = Statistics(recruit);
        List<Map<String, Object>> allList = new ArrayList<>();
        Map<String, Object> allMap = new HashMap<>();
        List<User> list = userDao.findByQuitDateBetween(recruit.getOrderTimeBegin(),
                recruit.getOrderTimeEnd());
        double sum4 = list.stream().filter(User -> User.getQuit().equals(1)).count();// 离职人数
        double sum = 0;// 应邀面试人数汇总
        double sum1 = 0;// 面试合格人数汇总
        double sum2 = 0;// 入职人数
        double sum3 = 0;// 入职且离职
        double sum6 = 0;// 入职且在职人数
        User user = new User();
        user.setIsAdmin(false);
        double sum5 = userService.findUserList(user).stream()
                .filter(User -> (User.getQuitDate() == null || User.getQuitDate().after(recruit.getOrderTimeBegin()))
                        && (User.getEntry() != null && User.getEntry().before(recruit.getOrderTimeEnd())))
                .count();// 初期人员
        for (Map<String, Object> map : maps) {
            Object aInteger = map.get("mod2");// 应邀面试人数
            Object aInteger2 = map.get("mod3");// 面试合格人数
            Object aInteger3 = map.get("mod8");// 已入职
            Object aInteger4 = map.get("mod9");// 已入职且离职
            Object aInteger5 = map.get("mod5");// 已入职且在职
            sum = sum + Integer.parseInt(aInteger == null ? "" : aInteger.toString());// 应邀面试人数
            sum1 = sum1 + Integer.parseInt(aInteger2 == null ? "" : aInteger2.toString());// 面试合格人数
            sum2 = sum2 + Integer.parseInt(aInteger3 == null ? "" : aInteger3.toString());// 已入职
            sum3 = sum3 + Integer.parseInt(aInteger4 == null ? "" : aInteger4.toString());// 已入职且离职
            sum6 = sum6 + Integer.parseInt(aInteger5 == null ? "" : aInteger5.toString());// 已入职且在职
        }
        double a = 0;
        if (sum != 0) {
            a = NumUtils.div(sum1 * 100, sum, 2);// 面试通过率
        }
        double b = 0;
        if (sum1 != 0) {
            b = NumUtils.div(sum2 * 100, sum1, 2);// 入职率
        }
        double c = 0;
        if (sum2 != 0) {
            c = NumUtils.div(sum3 * 100, sum2, 2);// 短期流失率
        }
        double d = 0;
        if ((sum5) != 0) {
            d = NumUtils.div(sum4 * 100, (sum5), 2);// 离职率
        }
        double e = 0;
        if (sum1 != 0) {
            e = NumUtils.div(sum6 * 100, sum1, 2);// 留用率
        }

        allMap.put("md1", a);
        allMap.put("md2", b);
        allMap.put("md3", c);
        allMap.put("md4", d);
        allMap.put("md5", e);
        allList.add(allMap);
        return allList;
    }

    @Override
    public List<Map<String, Object>> orgName(Recruit recruit) {
        List<Recruit> list = dao.findByTimeBetween(recruit.getOrderTimeBegin(), recruit.getOrderTimeEnd());
        List<Map<String, Object>> allList = new ArrayList<>();
        Map<String, Object> allMap = null;
        Map<Long, List<Recruit>> map = list.stream().filter(Recruit -> Recruit.getOrgNameId() != null)
                .collect(Collectors.groupingBy(Recruit::getOrgNameId, Collectors.toList()));
        for (Long ps1 : map.keySet()) {
            allMap = new HashMap<>();
            List<Recruit> psList1 = map.get(ps1);
            Long sum = psList1.stream().filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                    && Recruit.getType() != null && Recruit.getType().equals(1)).count();// 应邀面试
            Long sum1 = psList1.stream().filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                    && Recruit.getAdopt() != null && Recruit.getAdopt().equals(1)).count();// 面试合格
            Long sum2 = psList1.stream().filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                    && Recruit.getState() != null && Recruit.getState().equals(1)).count();// 已入职
            Long sum3 = psList1.stream()
                    .filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                            && Recruit.getState() != null && Recruit.getUserId() != null && Recruit.getState().equals(1)
                            && Recruit.getUser().getQuit().equals(1))
                    .count();// 已入职且离职
            Long sum6 = psList1.stream()
                    .filter(Recruit -> Recruit.getOrgNameId().equals(Recruit.getOrgNameId())
                            && Recruit.getState() != null && Recruit.getUserId() != null && Recruit.getState().equals(1)
                            && Recruit.getUser().getQuit().equals(0))
                    .count();// 已入职且在职
            List<User> list1 = userDao.findByQuitDateBetween(recruit.getOrderTimeBegin(),
                    recruit.getOrderTimeEnd());
            double sum4 = list1.stream().filter(User -> User.getOrgNameId().equals(ps1) && User.getQuit().equals(1)).count();// 离职人数
            User user = new User();
            user.setIsAdmin(false);
            user.setOrgNameId(ps1);
            double sum5 = userService.findUserList(user).stream()
                    .filter(User -> (User.getQuitDate() == null || User.getQuitDate().after(recruit.getOrderTimeBegin()))
                            && (User.getEntry() != null && User.getEntry().before(recruit.getOrderTimeEnd())) && User.getOrgNameId() != null && User.getOrgNameId().equals(ps1))
                    .count();// 初期人员
            double a = 0;
            if (sum != 0) {
                a = NumUtils.div(sum1 * 100, sum, 2);// 面试通过率
            }
            double b = 0;
            if (sum1 != 0) {
                b = NumUtils.div(sum2 * 100, sum1, 2);// 入职率
            }
            double c = 0;
            if (sum2 != 0) {
                c = NumUtils.div(sum3 * 100, sum2, 2);// 短期流失率
            }
            double d = 0;
            if ((sum5) != 0) {
                d = NumUtils.div(sum4 * 100, (sum5), 2);// 离职率
            }
            double e = 0;
            if (sum1 != 0) {
                e = NumUtils.div(sum6 * 100, sum1, 2);// 留用率
            }
            BaseData baseData = baseDataDao.findOne(ps1);
            String string = baseData.getName();
            allMap.put("md1", a);
            allMap.put("md2", b);
            allMap.put("md3", c);
            allMap.put("md4", d);
            allMap.put("md5", e);
            allMap.put("orgName", string);
            allList.add(allMap);
        }
        return allList;
    }

}
