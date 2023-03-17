# CandleDragon烛龙【插件化漏洞利用工具】

## 运行环境

JDK 1.8 
打包命令：mvn package assembly:single

## 工具界面

![image](https://user-images.githubusercontent.com/113674835/225807977-b16d299c-2fba-441b-bd4a-65c50831080a.png)

## 功能

### 全局代理功能

可设置`HTTP`代理和`Socks`代理方便各种网络环境使用

![image](https://user-images.githubusercontent.com/113674835/225808018-0a75273d-f82d-4c12-a081-0a2bab21b35b.png)

### 自定义编码方式

默认使用`UTF-8`编码，可根据目标站点自行设置

![image](https://user-images.githubusercontent.com/113674835/225808059-e69c63ec-13de-49da-8ee7-e16774d669d3.png)

### 关键字搜索

可通过`信息探测插件名`、`作者名`、`描述`中的关键字进行插件筛选

![image](https://user-images.githubusercontent.com/113674835/225808169-ace79ded-b71d-4942-aa32-0496353e4f64.png)



漏洞插件部分可以通过`插件名`、`漏洞名称`、`作者名`、`漏洞描述`关键字进行筛选

![image](https://user-images.githubusercontent.com/113674835/225808183-516b4c74-2790-4d53-9a7c-745c71e2f325.png)

### 插件加载使用

#### Http工具类

直接使用`HttpTool`的get或post进行发包返回`Response`对象，通过`Response`对象获取返回包的响应码、包内容、是否出错、head等信息

![image](https://user-images.githubusercontent.com/113674835/225808144-922f998f-7395-4342-aba7-13950dfe78a5.png)

#### 信息探测插件

可以直接`双击`插件名字所在表格行进行加载并自动跳转利用界面，可以根据插件是否设置`参数`分别加载不同界面。

![image](https://user-images.githubusercontent.com/113674835/225808202-389f0a8b-d455-4178-af72-4daa23c2cf0b.png)

#### 漏洞插件

可通过列表当前行任意位置`右键`选择POC或者EXP模块，如果当前插件没有POC或EXP，按钮为灰色

![image](https://user-images.githubusercontent.com/113674835/225808215-357904dc-1a01-4412-9ad0-ddca8db994e5.png)

#### POC模块

左侧区域每行填写一个检测地址，点击扫描可以根据插件定义的条件输出到表格中。

![image](https://user-images.githubusercontent.com/113674835/225808237-52773bc0-96a4-4f98-8d0c-4a4ae25e6a1a.png)

#### EXP模块

加载漏洞插件中所有实现EXP接口的对象，可同时加载多个EXP对象，可以获取插件中定义好的参数。

![image](https://user-images.githubusercontent.com/113674835/225808252-78c8bcb3-e928-41b7-b5df-7df1507b74e7.png)

## 插件编写

### 目录结构

此类全限定名不可修改，只需要修改我们上面自自己写的`信息探测插件`和`漏洞信息插件`的对象名即可

![image](https://user-images.githubusercontent.com/113674835/225808275-20fa4ca1-d3bb-4acc-ac5e-7eb6b8709907.png)



其余的类和文件夹名称可以随便改不会影响插件加载。

![image](https://user-images.githubusercontent.com/113674835/225808283-dd52726c-d442-4668-88d7-232b8f5bf39d.png)



### 编写规范

#### 漏洞利用插件

所有关于漏洞利用的插件对象都必须实现`Exploit`接口，并重写其中的方法

其中`getExploitTabTitle()`方法返回的内容是在插件中这个`Tab`标签显示

![image](https://user-images.githubusercontent.com/113674835/225808303-834d6c86-10c0-4138-bc7d-5ada23b6d744.png)



`getExploitCustomArgs()`方法返回的是多个参数，最终在工具显示位置 **（可写可不写）**

![image](https://user-images.githubusercontent.com/113674835/225808332-c9add79d-a22a-45c0-8387-15555abc75bf.png)

需要使用一个`List<ArgsInfo>`来存储我们自定义的参数名，通过信息探测插件对象来创建参数的对象

![image](https://user-images.githubusercontent.com/113674835/225808340-410796dc-7385-42bb-9ab8-bf24e193e0f1.png)

漏洞利用部分，可以通过`targetInfo.getAddress()`方法获取主程序工具中输入的URL地址，通过`args.get("TestArg")`可以获取主程序工具中输入参数的值，最后通过`resultOutput`的各种打印方式将需要显示的结果打印到主程序工具的前端显示。

![image](https://user-images.githubusercontent.com/113674835/225808374-c5112987-c6d7-44e7-9fbe-7c2ada8d2a32.png)

#### POC插件部分

需要`漏洞插件`的`getHelpPluginInfo()`获取一个对象调用`createScanResult()`返回一个扫描结果对象用来存储扫描结果，

最后通过扫描结果对象的三个方法，将插件定义好的条件插入表格中

```
scanResult.setTarget(targetInfo.getAddress());
scanResult.setMsg("输出信息");
scanResult.setVul(true);   //是否存在漏洞
```

![image](https://user-images.githubusercontent.com/113674835/225808396-9c088dfb-720e-48fd-b4dd-9b1cf2360694.png)

#### 信息探测插件

前面的逻辑和显示位置和漏洞利用的大同小异，需要新建一个`LinkedHashMap`，用来最后返回自定义的探测信息，这个和`resultOutput`的输出内容不冲突

![image](https://user-images.githubusercontent.com/113674835/225808416-d149b113-3e10-42fa-a8ef-d443313936f7.png)

#### 插件信息编写

通过调用`InfoDetectorPluginInfo`的多个方法设置插件名字、插件作者、插件版本等信息，用于前端显示

最后将这个插件中所有的`信息探测插件`对象添加到一个`List`中，调用`registerInfoDetector()`方法传入List

漏洞利用部分同理，具体代码看Demo



## 感谢
感谢 @V1rtu0l
感谢@c0ny1师傅的woodpecker项目https://github.com/woodpecker-framework

## 问题建议
感谢师傅们多多提issues
