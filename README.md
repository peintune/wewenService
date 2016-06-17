
##概述

APICloud的 java SDK 基于HttpClient创建的工具类,将数据云Api以面向对象的思想加以封装,支持对象化操作,只需要在你的代码中引入Resource类并创建Resource对象,便可以轻松完成数据云相关api接口的调用,操作相关数据.



###**构造器**


```构造器
Resource(appId, appKey,url);
```

####参数

appId：

- 类型：字符串
- 默认值：无
- 描述：应用的id，在APICloud上应用概览里获取，不能为空

appKey：

- 类型：字符串
- 默认值：无
- 描述：应用的安全校验Key，在APICloud上应用概览里获取，不能为空

url：

- 类型：字符串
- 默认值：无
- 描述：应用服务器地址，可为空，为空时默认为编译时的服务器地址，例：http://d.apicloud.com

####示例代码

```java
Resource resource = new Resource(appId, appKey,"");
```
##操作数据权限相关
###**1.设置authorization**
```java
setAuthorization(String authorization);
```

####参数

authorization

- 类型：字符串
- 默认值：无
- 描述：登录成功后返回的id(如果数据操作需要登录后才能进行,需要先登录并调用setAuthorization方法,参数为登录成功返回的id,如果不进行设置,则使用默认的authorization)

####示例代码

```java
resource.setAuthorization("iiyvHmUgsuoTE4kcWdDisN1HnAmYe5DNDGUFkWUerQ293g3SjPkw2k9FH90GwwCL");
```


##对象基本操作
###**1.创建对象**

```java
createObject(String object,JSONObject property);
```

####参数

object

- 类型：字符串
- 默认值：无
- 描述：对象名称(即对应表的名称,例:Company)

property

- 类型：JSONObject
- 默认值：无
- 描述：对象所具有的属性

####示例代码

```
JSONObject property = new JSONObject();
property.put("name", "APICloud");
property.put("area", "北京");
File file = new File("F:/icon.png");
property.put("icon", file);
		
JSONObject json = resource.createObject("Company", property);
```

###**2.根据id获取对象**

```java
getObject(String object,String id);
```

####参数

object

- 类型：字符串
- 默认值：无
- 描述：对象名称(即对应表的名称,例:Company)

id

- 类型：字符串
- 默认值：无
- 描述：对象id

####示例代码

```
JSONObject json = resource.getObjects("Company", "556ec606745924446ea98fc3");
```

###**3.获取所有对象**

```java
getObjects(String object);
```

####参数

object

- 类型：字符串
- 默认值：无
- 描述：对象名称(即对应表的名称,例:Company)

####示例代码

```
JSONObject json = resource.getObjects("Company");
```

###**4.获取对象数量**

```java
getObjectCount(String object);
```

####参数

object

- 类型：字符串
- 默认值：无
- 描述：对象名称(即对应表的名称,例:Company)

####示例代码

```
JSONObject json = resource.getObjectCount("Company");
```

###**5.更新对象**

```java
updateObject(String object,String id,JSONObject property)
```

####参数

object

- 类型：字符串
- 默认值：无
- 描述：对象名称(即对应表的名称,例:Company)

id

- 类型：字符串
- 默认值：无
- 描述：对象id

property

- 类型：JSONObject
- 默认值：无
- 描述：所要更改的属性

####示例代码

```
JSONObject property = new JSONObject();
property.put("name", "apicloud");
JSONObject json = resource.updateObject("Company", "555ea9edc0c770a6351f40aa",property);
```

###**6.删除对象**

```java
deleteObject(String object,String id)
```

####参数

object

- 类型：字符串
- 默认值：无
- 描述：对象名称(即对应表的名称,例:Company)

id

- 类型：字符串
- 默认值：无
- 描述：对象id

####示例代码

```
JSONObject json = resource.deleteObject("Company", "555ea1464a67ea9c356ae259");
```

###**7.判断对象是否存在**

```java
checkObjectExists(String object,String id);
```

####参数

object

- 类型：字符串
- 默认值：无
- 描述：对象名称(即对应表的名称,例:Company)

id

- 类型：字符串
- 默认值：无
- 描述：对象id

####示例代码

```
JSONObject json = resource.checkObjectExists("Company", "555ea9edc0c770a6351f40aa");
```

##**Relation对象相关接口**
###**1获取关联对象**

```java
getRelationObject(String object,String id,String relationObject);
```

####参数

object

- 类型：字符串
- 默认值：无
- 描述：对象名称(即对应表的名称,例:Company)

id

- 类型：字符串
- 默认值：无
- 描述：对象id

relationObject

- 类型：字符串
- 默认值：无
- 描述：关联对象名称(即对应表的名称,例:department)

####示例代码

```
JSONObject json = resource.getRelationObject("Company", "555ea9edc0c770a6351f40aa","department");

```
###**2.创建关联对象**
createRelationObject(String object,String id,String relationObject,JSONObject property)

object

- 类型：字符串
- 默认值：无
- 描述：对象名称(即对应表的名称,例:Company)

id

- 类型：字符串
- 默认值：无
- 描述：对象id

relationObject

- 类型：字符串
- 默认值：无
- 描述：关联对象名称(即对应表的名称,例:department)

property

- 类型：JSONObject
- 默认值：无
- 描述：关联对象的属性

####示例代码

JSONObject property = new JSONObject();  
property.put("name", "研发部");  
JSONObject json = resource.createRelationObject("Company","555ea9edc0c770a6351f40aa","department",property);

###**3.统计关联对象数量**
getRelationObjectCount(String object,String id,String relationObject)

object

- 类型：字符串
- 默认值：无
- 描述：对象名称(即对应表的名称,例:Company)

id

- 类型：字符串
- 默认值：无
- 描述：对象id

relationObject

- 类型：字符串
- 默认值：无
- 描述：关联对象名称(即对应表的名称,例:department)

####示例代码

JSONObject json = resource.getRelationObjectCount("Company", "5564137876f4b52314b270f0","department");

###**4.删除所有关联对象**
deleteRelationObject(String object,String id,String relationObject)

object

- 类型：字符串
- 默认值：无
- 描述：对象名称(即对应表的名称,例:Company)

id

- 类型：字符串
- 默认值：无
- 描述：对象id

relationObject

- 类型：字符串
- 默认值：无
- 描述：关联对象名称(即对应表的名称,例:department)

####示例代码

JSONObject json = resource.deleteRelationObject("Company", "5564137876f4b52314b270f0","department");

##**用户**
###**1.新增用户**

createUser(JSONObject property)

property

- 类型：JSONObject
- 默认值：无
- 描述：用户对象的属性

####示例代码

JSONObject property = new JSONObject();  
property.put("username", "apicloud");  
property.put("password", "apicloud");  
property.put("email", "apicloud@id.apicloud.com");

JSONObject json = resource.createUser(property);

###**2.用户登录**

userLogin(String userName,String passWord)

userName

- 类型：字符串
- 默认值：无
- 描述：用户名

passWord

- 类型：字符串
- 默认值：无
- 描述：密码


####示例代码

JSONObject json = resource.userLogin("wang","19900507asd");

###**3.请求验证Email**

verifyEmail(JSONObject property)

property

- 类型：JSONObject
- 默认值：无
- 描述：用户对象的属性


####示例代码

JSONObject property = new JSONObject();  
property.put("username", "apicloud");  
property.put("language", "zh_CN");  
property.put("email", "customer@mail.apicloud.com");

JSONObject json = resource.verifyEmail(property);

###**4.密码重置**

resetRequest(JSONObject property)

property

- 类型：JSONObject
- 默认值：无
- 描述：对象的属性


####示例代码

JSONObject property = new JSONObject();  
property.put("username", "apicloud");  
property.put("language", "zh_CN");  
property.put("email", "customer@mail.apicloud.com");

JSONObject json = resource.resetRequest(property);

###**5.获取用户**

getUser(String userId)

userId

- 类型：字符串
- 默认值：无
- 描述：用户的id(登录后才能使用此方法)

####示例代码

JSONObject json = resource.getUser("555e96a4c0c770a6351f4068");

###**6.更新用户**

updateUser(String userId,JSONObject property)

userId

- 类型：字符串
- 默认值：无
- 描述：用户的id

property

- 类型：JSONObject
- 默认值：无
- 描述：对象的属性

####示例代码

JSONObject property = new JSONObject();  
property.put("username", "apicloud");  
JSONObject json=resource.updateUser("555e96a4c0c770a6351f4068",property);

###**7.删除用户**

deleteUser(String userId)

userId

- 类型：字符串
- 默认值：无
- 描述：用户的id

####示例代码

JSONObject json = resource.deleteUser("555e96a4c0c770a6351f4068");

###**8.登出**

loginOut()

####示例代码

JSONObject json = resource.loginOut();

##**角色**
###**1.创建角色**

createRole(JSONObject property)

property

- 类型：JSONObject
- 默认值：无
- 描述：角色的属性

####示例代码

JSONObject property = new JSONObject();  
property.put("name", "apicloud");  
property.put("description", "manager desc");  
JSONObject json = resource.createRole(property);

###**2.根据Id获取角色**

getRole(String id)

id

- 类型：字符串
- 默认值：无
- 描述：角色的id

####示例代码

JSONObject json = resource.getRole("555edb53880727c635bb5b93");

###**3.根据Id更新角色**

updateRole(String id,JSONObject property)

id

- 类型：字符串
- 默认值：无
- 描述：角色的id

property

- 类型：JSONObject
- 默认值：无
- 描述：角色的属性

####示例代码

JSONObject property = new JSONObject();  
property.put("name", "manager");  
JSONObject json = resource.updateRole("555edb53880727c635bb5b93",property);

###**4.根据Id删除角色**

deleteRole(String id)

id

- 类型：字符串
- 默认值：无
- 描述：角色的id

####示例代码

JSONObject json = resource.deleteRole("555edb53880727c635bb5b93");

##**批量操作**

###**1.批量执行**

batch(JSONObject params)

params

- 类型：JSONObject
- 默认值：无
- 描述：参数

####示例代码

JSONObject params = new JSONObject();		
JSONArray array = new JSONArray();		
JSONObject json = new JSONObject();  
json.put("method", "POST");  
json.put("path", "/mcm/api/Company");  
JSONObject body = new JSONObject();   
body.put("name", "apicloud");  
body.put("address", "北京市...");  
json.put("body",body);
array.add(json);
		
JSONObject json1 = new JSONObject();  
json1.put("method", "POST");  
json1.put("path", "/mcm/api/company");  
JSONObject body1 = new JSONObject();  
body1.put("name", "百度");  
body1.put("address", "北京市西二旗");
		
array.add(json1);

params.put("requests", array);  
JSONObject returnJson = resource.batch(params);

##**文件上传**

###**1.文件上传**

upload(String filePath)

filePath

- 类型：字符串
- 默认值：无
- 描述：文件路径

####示例代码

JSONObject json = resource.upload("f:/icon.png");

##**更新操作符**

###**1.更新操作符**

updateModel(String object,String id,JSONObject params)

object

- 类型：字符串
- 默认值：无
- 描述：对象名称(即对应表的名称,例:Company)

id

- 类型：字符串
- 默认值：无
- 描述：对象id

params

- 类型：JSONObject
- 默认值：无
- 描述：参数  

####示例代码

JSONObject params = new JSONObject();
params.put("$inc", "{ name: 123}");
JSONObject json = resource.updateModel("Company","5564137876f4b52314b270f0", params);

##**按照条件过滤**

###**1.按照条件过滤**

doFilterSearch(String object,String filter)

object

- 类型：字符串
- 默认值：无
- 描述：对象名称(即对应表的名称,例:Company)

filter

- 类型：字符串
- 默认值：无
- 描述：过滤条件

####示例代码

JSONObject json = resource.doFilterSearch("Company","{\"where\":{\"id\":\"5564137876f4b52314b270f0\"}}");



