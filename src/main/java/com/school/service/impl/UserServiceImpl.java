//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.school.dao.UserMapper;
import com.school.exception.EmailNotFoundException;
import com.school.exception.ExcelDataException;
import com.school.exception.FileFormattingException;
import com.school.exception.UserNotFoundException;
import com.school.exception.UsernameAlreadyExistException;
import com.school.model.Role;
import com.school.model.User;
import com.school.model.UserExample;
import com.school.model.Usertorole;
import com.school.model.User.Column;
import com.school.model.UserExample.Criteria;
import com.school.utils.AssertUtil;
import com.school.utils.RoleEnum;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;

import io.swagger.models.auth.In;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${password.encoded.prefix:}")
    private String encodedPasswordPrefix;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private PicsServiceImpl picsService;
    @Autowired
    private UserToRoleServiceImpl userToRoleService;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    RoleServiceImpl roleService;

    public UserServiceImpl() {
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = this.findByUsername(username);
        if (users != null && users.size() != 0) {
            User user = (User)users.get(0);
            List<Usertorole> usertoroles = this.userToRoleService.getUserToRoleByUserId(user.getId());
            List<Role> roles = new ArrayList();
            Iterator var6 = usertoroles.iterator();
            Role role;
            while(var6.hasNext()) {
                Usertorole usertorole = (Usertorole)var6.next();
                role = this.roleService.querySelective(usertorole.getRoleid(), (String)null);
                roles.add(role);
            }

            Collection<GrantedAuthority> roles_ = new HashSet();
            Iterator var10 = roles.iterator();

            while(var10.hasNext()) {
                role = (Role)var10.next();
                roles_.add(new SimpleGrantedAuthority(role.getName()));
            }

            return new org.springframework.security.core.userdetails.User(username, this.encodedPasswordPrefix + user.getPassword(), roles_);
        } else {
            throw new UsernameNotFoundException("用户名不存在！");
        }
    }

    public List<User> findByUsername(String username) {
        return this.querySelectiveLike((Integer)null, username, (String)null, (String)null, (String)null, (String)null, (Integer)null, (String)null, (String)null, (Integer)null, (String)null, (String)null, (Integer)null, (String)null, (Integer)null, (Integer)null, (String)null, (String)null);
    }

    public void delete(User user) throws UserNotFoundException {
        user.setDeleted(true);
        this.update(user);
    }

    public User findById(Integer id) throws UserNotFoundException {
        List<User> users = this.querySelectiveLike(id, (String)null, (String)null, (String)null, (String)null, (String)null, (Integer)null, (String)null, (String)null, (Integer)null, (String)null, (String)null, (Integer)null, (String)null, (Integer)null, (Integer)null, (String)null, (String)null);
        if (users.size() == 0) {
            throw new UserNotFoundException("用户id不存在！");
        } else {
            return (User)users.get(0);
        }
    }

    private void add(User user, Integer roleId) throws UsernameAlreadyExistException {
        this.userMapper.insertSelective(user);
        List<User> users = this.findByUsername(user.getUsername());
        this.userToRoleService.add(((User)users.get(0)).getId(), roleId);
    }

    public List<User> querySelectiveLike(Integer userId, String username, String schoolName, String contact, String address, String telephone, Integer updateYear, String schoolCode, String location, Integer addYear, String lastLoginIp, String lastLoginTime, Integer accountStatus, String profession, Integer page, Integer pageSize, String sort, String order) {
        UserExample userExample = new UserExample();
        Criteria criteria = userExample.createCriteria();
        if (StringUtils.hasText(sort) && StringUtils.hasText(order)) {
            userExample.setOrderByClause(sort + " " + order);
        }

        if (!StringUtils.isEmpty(userId)) {
            criteria.andIdEqualTo(userId);
        }

        if (StringUtils.hasText(username)) {
            criteria.andUsernameEqualTo(username);
        }

        if (StringUtils.hasText(schoolName)) {
            criteria.andSchoolnameLike("%" + schoolName + "%");
        }

        if (StringUtils.hasText(contact)) {
            criteria.andContactLike("%" + contact + "%");
        }

        if (StringUtils.hasText(address)) {
            criteria.andAddressLike("%" + address + "%");
        }

        if (StringUtils.hasText(telephone)) {
            criteria.andTelephoneLike("%" + telephone + "%");
        }

        LocalDateTime end;
        LocalDateTime start;
        if (!StringUtils.isEmpty(updateYear)) {
            end = LocalDateTime.of(updateYear, 12, 31, 23, 59);
            start = LocalDateTime.of(updateYear, 1, 1, 0, 0);
            criteria.andUpdateTimeBetween(start, end);
        }

        if (StringUtils.hasText(schoolCode)) {
            criteria.andSchoolcodeLike("%" + schoolCode + "%");
        }

        if (StringUtils.hasText(location)) {
            criteria.andLocationLike("%" + location + "%");
        }

        if (!StringUtils.isEmpty(addYear)) {
            end = LocalDateTime.of(addYear, 12, 31, 23, 59);
            start = LocalDateTime.of(addYear, 1, 1, 0, 0);
            criteria.andAddTimeBetween(start, end);
        }

        if (StringUtils.hasText(lastLoginIp)) {
            criteria.andLastloginipLike("%" + lastLoginIp + "%");
        }

        if (!StringUtils.isEmpty(accountStatus)) {
            criteria.andAccountstatusEqualTo(accountStatus);
        }

        if (StringUtils.hasText(profession)) {
            criteria.andProfessionLike("%" + profession + "%");
        }

        if (page != null || pageSize != null) {
            if (page == null) {
                PageHelper.startPage(1, pageSize);
            } else if (pageSize == null) {
                PageHelper.startPage(page, 10);
            } else {
                PageHelper.startPage(page, pageSize);
            }
        }

        criteria.andDeletedEqualTo(false);
        List<User> users = this.userMapper.selectByExampleSelective(userExample, new Column[0]);
        PageInfo<User> pageInfo = new PageInfo(users);
        return pageInfo.getList();
    }

//    public List<User> querySelective(Integer userId,String username,String schoolName,String ){
//        return querySelectiveLike();
//    }

    public void deleteById(Integer id) throws UserNotFoundException {
        User user = new User();
        user.setId(id);
        user.setDeleted(true);
        this.delete(user);
    }

    public User retrieveUserByToken() {
        UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> users = this.findByUsername(principal.getUsername());
        return (User)users.get(0);
    }

    public void clearPassword(List<User> users) {
        Iterator var2 = users.iterator();

        while(var2.hasNext()) {
            User user = (User)var2.next();
            user.setPassword("[PROTECTED]");
        }

    }

    public User update(Integer id, String username, String password, String schoolName, String contact, String address, String telephone, String schoolCode, String location, String lastLoginIP, LocalDateTime lastLoginTime, Boolean deleted, String avatarUrl, Integer accountStatus, String profession) throws UserNotFoundException {
        User user = this.findById(id);
        if (user == null) {
            throw new UserNotFoundException("用户id不存在！");
        } else {
            if (StringUtils.hasText(username)) {
                user.setUsername(username);
            }

            if (StringUtils.hasText(password)) {
                user.setPassword(this.bCryptPasswordEncoder.encode(password));
            }

            if (StringUtils.hasText(schoolName)) {
                user.setSchoolname(schoolName);
            }

            if (StringUtils.hasText(contact)) {
                user.setContact(contact);
            }

            if (StringUtils.hasText(address)) {
                user.setAddress(address);
            }

            if (StringUtils.hasText(telephone)) {
                user.setTelephone(telephone);
            }

            if (StringUtils.hasText(schoolCode)) {
                user.setSchoolcode(schoolCode);
            }

            if (StringUtils.hasText(location)) {
                user.setLocation(location);
            }

            if (StringUtils.hasText(lastLoginIP)) {
                user.setLastloginip(lastLoginIP);
            }

            if (!StringUtils.isEmpty(deleted)) {
                user.setDeleted(deleted);
            }

            if (StringUtils.hasText(avatarUrl)) {
                user.setAvatarurl(avatarUrl);
            }

            if (!StringUtils.isEmpty(accountStatus)) {
                user.setAccountstatus(accountStatus);
            }

            if (StringUtils.hasText(profession)) {
                user.setProfession(profession);
            }

            if (!StringUtils.isEmpty(lastLoginTime)) {
                user.setLastlogintime(lastLoginTime);
            }

            return this.update(user);
        }
    }

    public User update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        this.userMapper.updateByPrimaryKeySelective(user);
        List<User> users = this.querySelectiveLike(user.getId(), (String)null, (String)null, (String)null, (String)null, (String)null, (Integer)null, (String)null, (String)null, (Integer)null, (String)null, (String)null, (Integer)null, (String)null, (Integer)null, (Integer)null, (String)null, (String)null);
        return users.size() == 0 ? null : (User)users.get(0);
    }

    public void openLogin() throws UserNotFoundException {
        List<User> users = this.querySelectiveLike((Integer)null, (String)null, (String)null, (String)null, (String)null, (String)null, (Integer)null, (String)null, (String)null, (Integer)null, (String)null, (String)null, (Integer)null, (String)null, (Integer)null, (Integer)null, (String)null, (String)null);
        Iterator var2 = users.iterator();

        while(var2.hasNext()) {
            User user = (User)var2.next();
            user.setAccountstatus(1);
            this.update(user);
        }

    }

    public void add(String username, String password, String schoolName, String contact, String address, String telephone, String schoolCode, String location, String lastLoginIp, LocalDateTime lastLoginTime, String avatarUrl, String profession, Integer accountStatus) throws UsernameAlreadyExistException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(this.bCryptPasswordEncoder.encode(password));
        user.setSchoolname(schoolName);
        user.setContact(contact);
        user.setAddress(address);
        user.setTelephone(telephone);
        user.setSchoolcode(schoolCode);
        user.setAvatarurl(avatarUrl);
        user.setProfession(profession);
        user.setLocation(location);
        user.setLastloginip(lastLoginIp);
        user.setLastlogintime(lastLoginTime);
        user.setUpdateTime(LocalDateTime.now());
        user.setAddTime(LocalDateTime.now());
        user.setDeleted(false);
        user.setAccountstatus(accountStatus);
        this.add(user, RoleEnum.USER.value());
    }

    public void importRegistrationForm(MultipartFile file) throws FileFormattingException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ExcelDataException, UsernameAlreadyExistException {
        String originalFilename = file.getOriginalFilename();
        String format = originalFilename.substring(originalFilename.lastIndexOf("."));
        AssertUtil.isExcel(format);
        Workbook workbook = null;
        if (format.equals(".xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else {
            if (!format.equals(".xls")) {
                throw new FileFormattingException("文件格式不支持，仅支持.xls以及.xlsx");
            }

            workbook = new HSSFWorkbook(file.getInputStream());
        }

        int numberOfSheets = ((Workbook)workbook).getNumberOfSheets();

        label82:
        for(int i = 0; i < numberOfSheets; ++i) {
            Sheet sheetAt = ((Workbook)workbook).getSheetAt(i);
            Iterator<Row> rowIterator = sheetAt.iterator();
            if (rowIterator.hasNext()) {
                Row preRow = (Row)rowIterator.next();
                Map info = this.preConstruct(preRow);

                while(true) {
                    while(true) {
                        User user;
                        do {
                            do {
                                if (!rowIterator.hasNext()) {
                                    continue label82;
                                }

                                Row aRow = (Row)rowIterator.next();
                                Constructor<User> constructor = User.class.getConstructor();
                                user = (User)constructor.newInstance();
                                Iterator cellIterator = aRow.iterator();

                                while(cellIterator.hasNext()) {
                                    Cell cell = (Cell)cellIterator.next();
                                    CellType cellType = cell.getCellType();
                                    String fieldValue = null;
                                    if (cellType == CellType.NUMERIC) {
                                        fieldValue = String.valueOf(cell.getNumericCellValue()).trim();
                                    } else if (cellType == CellType.STRING) {
                                        fieldValue = cell.getStringCellValue().trim();
                                    }

                                    int columnIndex = cell.getColumnIndex();
                                    String fieldName = (String)info.get(columnIndex);

                                    try {
                                        this.invokeValue(user, fieldName, fieldValue);
                                    } catch (NoSuchMethodException var21) {
                                        throw new ExcelDataException("Excel表中第一行字段与数据中的字段不对应！");
                                    }
                                }
                            } while(user.getUsername() == null);
                        } while(user.getUsername().trim().equals(""));

                        List<User> byUsername = this.findByUsername(user.getUsername());
                        if (byUsername.size() != 0) {
                            System.out.println("该用户名已经被注册过了！");
                        } else {
                            String defaultPassword = this.generateDefaultPassword();
                            user.setPassword(defaultPassword);

                            try {
                                this.emailService.sendVerificationCode("签约系统临时授权码", "签约系统临时授权码(3天内有效，请尽快重设您的密码)", user.getUsername(), 3, TimeUnit.DAYS);
                            } catch (EmailNotFoundException var22) {
                                this.logger.warn("邮箱号有误,无法发送邮件到指定用户:" + user.getUsername());
                                continue;
                            }

                            this.add(user, RoleEnum.USER.value());
                            System.out.println(user.toString());
                        }
                    }
                }
            }
        }

    }

    private String generateDefaultPassword() {
        String s = String.valueOf(System.currentTimeMillis());
        return s.substring(s.length() - 6);
    }

    private void invokeValue(User user, String fieldName, String fieldValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method method = User.class.getMethod("set" + fieldName, String.class);
        method.invoke(user, fieldValue);
    }

    private Map<Integer, String> preConstruct(Row aRow) {
        Map<Integer, String> map = new HashMap();
        Iterator<Cell> cellIterator = aRow.iterator();
        int var4 = 0;

        while(cellIterator.hasNext()) {
            Cell cell = (Cell)cellIterator.next();
            map.put(var4++, cell.getStringCellValue());
        }

        return map;
    }

    public Workbook exportRegistrationForm() throws IOException {
        List<User> users = this.querySelectiveLike((Integer)null, (String)null, (String)null, (String)null, (String)null, (String)null, (Integer)null, (String)null, (String)null, (Integer)null, (String)null, (String)null, (Integer)null, (String)null, (Integer)null, (Integer)null, (String)null, (String)null);
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        Map<String, Integer> map = new HashMap();
        Field[] declaredFields = User.class.getDeclaredFields();
        int index = 0;
        Field[] var8 = declaredFields;
        int var9 = declaredFields.length;

        for(int var10 = 0; var10 < var9; ++var10) {
            Field declaredField = var8[var10];
            String fieldName = declaredField.getName();
            if (!fieldName.startsWith("IS_") && !fieldName.startsWith("NOT_")) {
                map.put(fieldName, index);
                Cell cell = row.createCell(index);
                ++index;
                cell.setCellValue(fieldName);
            }
        }

        for(int i = 1; i <= users.size(); ++i) {
            Row eachUserRow = sheet.createRow(i);
            Iterator var17 = map.entrySet().iterator();

            while(var17.hasNext()) {
                Entry<String, Integer> entry = (Entry)var17.next();
                Cell cell = eachUserRow.createCell((Integer)entry.getValue());

                try {
                    Object value = this.valueInvoke((User)users.get(i - 1), (String)entry.getKey());
                    cell.setCellValue(String.valueOf(value));
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException var14) {
                    var14.printStackTrace();
                }
            }
        }

        return workbook;
    }

    private Object valueInvoke(User user, String fieldName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method method = User.class.getMethod("get" + fieldName);
        return method.invoke(user);
    }

    public Integer count() {
        return this.count((String)null);
    }

    public Integer count(String schoolName) {
        UserExample userExample = new UserExample();
        Criteria criteria = userExample.createCriteria();
        if (StringUtils.hasText(schoolName)) {
            criteria.andSchoolnameLike("%" + schoolName + "%");
        }

        return Math.toIntExact(this.userMapper.countByExample(userExample));
    }

    public List<User> querySelectiveAllByPage(Integer page, Integer limit) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andDeletedEqualTo(false);
        if (page != null || limit != null) {
            if (page == null) {
                PageHelper.startPage(1, limit);
            } else if (limit == null) {
                PageHelper.startPage(page, 10);
            } else {
                PageHelper.startPage(page, limit);
            }
        }

        List<User> users = this.userMapper.selectByExampleSelective(userExample, new Column[0]);
        PageInfo<User> pageInfo = new PageInfo(users);
        return pageInfo.getList();
    }

    public List<User> querySelectiveAllDim(String name) {
        UserExample userExample1 = new UserExample();
        UserExample userExample2 = new UserExample();
        UserExample userExample3 = new UserExample();
        new UserExample();
        UserExample userExample5 = new UserExample();
        userExample1.createCriteria().andTelephoneLike("%" + name + "%");
        userExample2.createCriteria().andSchoolnameLike("%" + name + "%");
        userExample3.createCriteria().andContactLike("%" + name + "%");
        userExample5.createCriteria().andAddressLike("%" + name + "%");
        List<User> user1 = this.userMapper.selectByExampleSelective(userExample1, new Column[0]);
        List<User> user2 = this.userMapper.selectByExampleSelective(userExample2, new Column[0]);
        List<User> user3 = this.userMapper.selectByExampleSelective(userExample3, new Column[0]);
        List<User> user5 = this.userMapper.selectByExampleSelective(userExample5, new Column[0]);
        Set set = new HashSet();
        set.addAll(user1);
        set.addAll(user2);
        set.addAll(user3);
        set.addAll(user5);
        List<User> user = new ArrayList();
        user.addAll(set);
        return user;
    }

    public List<User> querySelectiveBySchoolnameDim(String schoolname, Integer page, Integer limit) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andSchoolnameLike("%" + schoolname + "%");
        if (page != null || limit != null) {
            if (page == null) {
                PageHelper.startPage(1, limit);
            } else if (limit == null) {
                PageHelper.startPage(page, 10);
            } else {
                PageHelper.startPage(page, limit);
            }
        }

        List<User> users = this.userMapper.selectByExampleSelective(userExample, new Column[0]);
        PageInfo<User> pageInfo = new PageInfo(users);
        return pageInfo.getList();
    }
}
