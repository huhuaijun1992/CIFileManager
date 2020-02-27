# 一、项目介绍
该文件管理项目主要是作为Android项目的baseModule来使用的。

## 1、主要功能
* 对文件的管理（文件的数据隔离、保存的方式和保存路径统一管理、数据文件的读写管理等文件性操作）
* 对偏好设置的统一管理（添加、删除、清除所有，查看所有的数据key）
* 对缓存数据的管理（添加、删除、周期性删除缓存数据）
# 二、接入方式
## 1、在项目的根build中添加私有仓地址:
示例：

```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://maven.oneitfarm.com/content/repositories/releases/" }

    }
}
```
## 2、在module中引入地址
示例：

```
implementation 'com.ci123.library:CIFile:0.0.1'
```
# 三、使用说明
### 1、初始化
使用前建议在项目的application中的onCreare中初始化

示例：

```
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CIFileManager.getInstance().init(getApplicationContext());
    }
```
### }

2、文件管理操作（CIFileManager）
（1）、设置数据隔离空间

```
/**
 * 设置数据隔离文件夹
 * @param nameSpace 数据隔离名称
 */
void setNameSpace(String nameSpace);
```
说明：使用前必须使用此方法设置文件数据的隔离，可以多次调用切换数据隔离的空间
示例：

```
CIFileManager.getInstance().setNameSpace("fileManager");
```
（2）、获取当前数据隔离的地址

```
/**
 * 获取当前使用的数据分割文件夹
 */
String getCurrentSpacePath()
```
示例：
```
String namePpace = CIFileManager.getInstance().getCurrentSpacePath();
```
（3）、读取raw资源文件
```
/**
 * 从raw文件夹中读取文件
 * @param rawFileId 文件id:R.raw.file名
 * @param encodeType 文件的编码类型（例："UTF-8"等，如果编码不对会出现乱码）
 * @param listener 读取监听
 * */
void readRawFile(int rawFileId, String encodeType, ReadFileListener listener);
```
示例：

```
CIFileManager.getInstance().readRawFile(R.raw.hello, "UTF-8", new ReadFileListener() {
    @Override
    public void onSuccess(String data) {
        Toast.makeText(getBaseContext(), "读取文件成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onfail(String errorMsg) {
    }
});
```
（4）、读取assets资源文件内容
```
/**
 * 读取应用asset文件内容
 * @param fileName 文件名
 * @param encodeType 文件编码类型
 * @param listener 读取监听
 * */
void readAssetFile(String fileName, String encodeType, ReadFileListener listener);
```
使用示例：
```
CIFileManager.getInstance().readAssetFile("hello.txt", null, new ReadFileListener() {
    @Override
    public void onSuccess(String data) {
        Toast.makeText(getBaseContext(), "读取文件成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onfail(String errorMsg) {

    }
});
```
（5）、向沙盒app专属路径中写入文件
```
/**
 *在应用沙盒路径中写数据"data/data/<应用程序包名>/files/"
 * @param data 写入的数据
 * @param fileName 写入的文件名
 * @param listener 文件监听
 * 注意：如果向同一个文件里面写入文件会覆盖之前写入的内容，如非必须请务必重复写入同一个文件
 * */
void writeFile(String fileName, String data, FileListener listener);
```
示例：

```
String data = "这是一个写入app专属文件夹的内容，用户在不可见的文件，一般文件不是私密文件不使用这种方式写入文件";
CIFileManager.getInstance().writeFile("internal.txt", data, new FileListener() {
    @Override
    public void onSuccess(boolean isSuccess, String msg) {
        Toast.makeText(getBaseContext(), "写入专属app路径文件夹" + isSuccess + msg, Toast.LENGTH_SHORT).show();
    }
});
```
（6）、读取沙盒app专属路径数据
```
/**
 * 读取沙盒路径中的文件数据"data/data/<应用程序包>/files/"
 * @param fileName 文件名
 * @param listener 读取监听
 * */
void readFile(String fileName, ReadFileListener listener);
```
示例：
```
CIFileManager.getInstance().readFile("internal.txt", new ReadFileListener() {
    @Override
    public void onSuccess(String data) {
        Toast.makeText(getBaseContext(), "文件内容" + data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onfail(String errorMsg) {
        Toast.makeText(getBaseContext(), "读取文件失败" + errorMsg, Toast.LENGTH_SHORT).show();
    }
});
```
（7）、向外存app专属路径文件写入数据
```
/**
 * 在外存专属app文件目录中写文件,多次写入会覆盖之前的数据
 * @param data 写入内容
 * @param listener 文件监听
 * @param paths 文件路径（“img”,"ceshi.txt"）表示“img/ceshi.txt”
 * */
void writeExtfile(String data, FileListener listener,String... paths);
```
使用示例：
```
CIFileManager.getInstance().writeExtfile("你好啊陌生人，这是测试将内容写进外存文件", new FileListener() {
    @Override
    public void onSuccess(boolean isSuccess, String msg) {
        Toast.makeText(getBaseContext(), "写入文件" + isSuccess, Toast.LENGTH_SHORT).show();
    }
}, "ceshi","ceshi.txt");
```
（8）、读取外存app专属路径文件数据
```
/**
 * 读取外存专属目录文件
 * @param encodeType 编码方式 默认UTF-8
 * @param paths 读取路径（"ceshi","ceshi.txt"）表示"ceshi/ceshi.txt"
 * @param listener 读取监听
 * */
void readExtFile(String encodeType,ReadFileListener listener, String... paths);
```
示例：
```
CIFileManager.getInstance().readExtFile(null, new ReadFileListener() {
    @Override
    public void onSuccess(String data) {
        Toast.makeText(getBaseContext(), "读取文件：" + data, Toast.LENGTH_SHORT).show();
       

    @Override
    public void onfail(String errorMsg) {

    }
}, "ceshi","ceshi.txt");
```
（9）、获取文件夹下所有文件
```
/**
 * 文件夹下的文件所有文件
 *
 * @param dirPaths   文件夹名称例（"img","ceshi"）表示文件路径下的的“/img/ceshi"文件夹
 */
File[] getFileList(String... dirPaths);
```
使用示例：
```
File[] files = CIFileManager.getInstance().getFileList("ceshi");
```
（10）、创建文件夹或者文件
```
/**
 * 创建文件夹或者文件
 *
 * @param dirPath 示例("cache","img")
 */
boolean createDirOrFile(String... dirPath) throws IOException;
```
使用示例：在ceshi/ceshi2/ceshi3/文件夹下创建ceshi.txt文件
```
try {
   boolean b= CIFileManager.getInstance().createDirOrFile("ceshi","ceshi2","ceshi3","ceshi.txt");
   if (b){
       Toast.makeText(getBaseContext(),"创建成功",Toast.LENGTH_SHORT).show();
   }else {
       Toast.makeText(getBaseContext(),"创建失败",Toast.LENGTH_SHORT).show();
   }
} catch (IOException e) {
    e.printStackTrace();
    Toast.makeText(getBaseContext(),"创建失败",Toast.LENGTH_SHORT).show();
}
```
（11）、删除文件夹或者文件
```
/**
 * 删除文件夹或者文件
 *
 * @param paths
 */
boolean deleteDirOrFile(String... paths) throws FileNotFoundException;
```
示例：删除ceshi/ceshi.txt文件
```
try {
  boolean b=  CIFileManager.getInstance().deleteDirOrFile("ceshi","ceshi.txt");
  if (b){
      Toast.makeText(getBaseContext(),"删除成功",Toast.LENGTH_SHORT).show();
  }else {
      Toast.makeText(getBaseContext(),"删除失败",Toast.LENGTH_SHORT).show();
  }
} catch (FileNotFoundException e) {
    e.printStackTrace();
    Toast.makeText(getBaseContext(),"文件夹不存在",Toast.LENGTH_SHORT).show();
}
```
### 3、缓存数据管理类（CICacheDataManager）
功能说明：缓存String、JSONObject、JSONArray、bytes、serializable、bitmap 格式的数据

并支持删除和清空缓存功能(以下为一种String类型存取删数据的操作)，

（1）、缓存String类型数据 

```
/**
 * 缓存String，到期后自动删除
 *
 * @param key      保存的key
 * @param value    保存的值
 * @param saveTime 保存的时间，（例保存10天，savetime = 10*CICachDataManager.TIME_DAY），永久保存就填0
 * @description:
 */
public void putString(String key, String value, int saveTime) {
   
}
```
示例：存储键=accountId, 值的内容为：测试值，在内存中存储10秒"，存储时间为10秒
```
CICachDataManager.getInstance().putString("accountId", "测试值，在内存中存储10秒", 10);
```
（2）、获取String类型数据
```
/**
 * 获取string类型值
 *
 * @param key 键值
 */
public String getAsString(String key) 
```
示例：获取键值为accountId的数据值

```
String s = CICachDataManager.getInstance().getAsString("accountId");
```
（3）、删除某key值

```
/**
 * 清除某个值
 * @param key 键值
 * */
public boolean remove(String key)
```
示例：移除键值为accountId的值
```
boolean b = CICachDataManager.getInstance().remove("accountId");
```
（4）、清空缓存

```
/**
 * 清除所有值
 * */
public void clear()
```
示例:
```
CICachDataManager.getInstance().clear();
```
### 4、偏好设置类（永久）（CISpManager）
（1）、存普通类型值（int、long、String、float、double）

```
/**
 *  存
 * @param key 键
 * @param value 值
 * @param <E> 泛型，自动根据值进行处理
 */
```
public  <E>void put(@NonNull String key,@NonNull E value)

示例：存储键为accountId,值为112。
```
CISpManager.getInstance().put("accountId", 112);
```
(2)读取普通值

```
/**
 *  取
 * @param key 键
 * @param defaultValue 默认值
 * @param <E> 泛型，自动根据值进行处理
 * @return
 */
public <E>E get(@NonNull String key,@NonNull E defaultValue) {
}
```
示例：读取键值为accountId的值，默认为0
```
int accountId = CISpManager.getInstance().get("accountId", 0)
```
（3）保存序列化对象
```
/**
 * 保存对象到sp文件中 被保存的对象须要实现 Serializable 接口
 * @param key
 * @param value
 */
public  void saveObject( String key, Object value) {
  
}
```
示例：保存book数据，注意序列化对象的构造器必须有无参的
```
/**
*book类
*/
public class Book implements Serializable {
    int bid;
    String bookName;
    String author;

    public Book(int bid, String bookName, String author) {
        this.bid = bid;
        this.bookName = bookName;
        this.author = author;
    }

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "bid=" + bid +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
Book book = new Book(1, "数学书", "张三");
CISpManager.getInstance().saveObject("book", book);
```
（4)、读取序列化对象
```
/**
 * desc:获取保存的Object对象
 * @param key 键值
 * @return modified:
 */
public  <T>T readObject(String key,  Class<T> clazz)
```
示例：
```
Book book1 = CISpManager.getInstance().readObject("book", Book.class);
```
(5)、移除某个key
```
/**
 * 移除某个key值已经对应的值
 * @param key
 */
public void remove(String key)
```
示例：移除book
```
CISpManager.getInstance().remove("book");
```
(6)清空key
```
/**
 * 清除所有数据
 */
public void clear()
```
示例：
```
CISpManager.getInstance().clear();
```
# 四、api
## 1、CIFileManager类
```
/**
 * 初始化，
 *
 * @param context 上下文
 */
boolean init(Context context);

/**
 * 设置数据隔离文件夹
 * @param nameSpace 数据隔离名称
 */
void setNameSpace(String nameSpace);

/**
 * 获取当前使用的数据分割文件夹
 */
String getCurrentSpacePath() throws FileNotFoundException;

/**
 * 从raw文件夹中读取文件
 * @param rawFileId 文件id:R.raw.file名
 * @param encodeType 文件的编码类型（例："UTF-8"等，如果编码不对会出现乱码）
 * @param listener 读取监听
 * */
void readRawFile(int rawFileId, String encodeType, ReadFileListener listener);

/**
 * 读取应用asset文件内容
 * @param fileName 文件名
 * @param encodeType 文件编码类型
 * @param listener 读取监听
 * */
void readAssetFile(String fileName, String encodeType, ReadFileListener listener);

/**
 *在应用沙盒路径中写数据"data/data/<应用程序包名>/files/"
 * @param data 写入的数据
 * @param fileName 写入的文件名
 * @param listener 文件监听
 * 注意：如果向同一个文件里面写入文件会覆盖之前写入的内容，如非必须请务必重复写入同一个文件
 * */
void writeFile(String fileName, String data, FileListener listener);

/**
 * 读取沙盒路径中的文件数据"data/data/<应用程序包>/files/"
 * @param fileName 文件名
 * @param listener 读取监听
 * */
void readFile(String fileName, ReadFileListener listener);

/**
 * 在外存专属app文件目录中写文件,多次写入会覆盖之前的数据
 * @param data 写入内容
 * @param listener 文件监听
 * @param paths 文件路径（“img”,"ceshi.txt"）表示“img/ceshi.txt”
 * */
void writeExtfile(String data, FileListener listener,String... paths);

/**
 * 读取外存专属目录文件
 * @param encodeType 编码方式 默认UTF-8
 * @param paths 读取路径（“img”,"ceshi.txt"）表示“img/ceshi.txt”
 * @param listener 读取监听
 * */
void readExtFile(String encodeType,ReadFileListener listener, String... paths);

/**
 * 拷贝文件
 *
 * @param source             源文件
 * @param copyListener       复制监听
 * @param dirNames           文件夹名称例（"img","ceshi.txt"）表示文件路径下的的“/img/ceshi.txt”
 */
void copyFile(File source, CopyListener copyListener, String... dirNames);

/**
 * 文件夹下的文件所有文件
 *
 * @param dirPaths   文件夹名称例（"img","ceshi"）表示文件路径下的的“/img/ceshi"文件夹
 */
File[] getFileList(String... dirPaths);

/**
 * 删除文件夹或者文件
 *
 * @param paths
 */
boolean deleteDirOrFile(String... paths) throws FileNotFoundException;

/**
 * 创建文件夹或者文件
 *
 * @param dirPath 示例("cache","img")
 */
boolean createDirOrFile(String... dirPath) throws IOException;

/**
 * 清除缓存
 */
void clearCache(FileListener listener);

/**
 * 获取app缓存大小
 */
long getTotalCacheSize();

/**
 * 获取app保存根路径
 */
String getSaveRootPath();

/**
 * 获取缓存保存路径
 */
String getcaCehePath();

/**
 * 获取文件夹路径
 *
 * @param DirNames 文件夹名
 */
String getDirPath(String... DirNames);

/**
 * 获取文件大小
 *
 * @param dirNames 文件夹名称
 */
long getDirSize(String... dirNames);

/**
 * 获取手机存储总大小
 */
long getPhoneTotalSize();

/**
 * 获取目录可用控件大小
 @param path 文件夹路径
 */
long getAvailSpace(String path);
```
## 2、CISpManager类
```
/**
 *  存
 * @param key 键
 * @param value 值
 * @param <E> 泛型，自动根据值进行处理
 */
public  <E>void put(@NonNull String key,@NonNull E value) {
    put(context,key,value);
}

/**
 *  取
 * @param key 键
 * @param defaultValue 默认值
 * @param <E> 泛型，自动根据值进行处理
 * @return
 */
public <E>E get(@NonNull String key,@NonNull E defaultValue) {
    return get(context,key,defaultValue);
}

/**
 * 移除某个key值已经对应的值
 * @param key
 */
public void remove(String key)
/**
 * 清除所有数据
 */
public void clear() 

/**
 * 查询某个key是否已经存在
 *
 * @param key
 * @return
 */
public  boolean contains(String key)

/**
 * 返回所有的键值对
 *
 * @param context
 * @return
 */
public Map<String, ?> getAll(Context context)

/**
 * 保存对象到sp文件中 被保存的对象须要实现 Serializable 接口
 * @param key
 * @param value
 */
public  void saveObject( String key, Object value)
/**
 * desc:获取保存的Object对象
 * @param key
 * @return modified:
 */
```
public  <T>T readObject(String key,  Class<T> clazz) 


## 3、CICacheDataManager类
```
/**
 * 缓存String，到期后自动删除
 *
 * @param key      保存的key
 * @param value    保存的值
 * @param saveTime 保存的时间，（例保存10天，savetime = 10*CICachDataManager.TIME_DAY），永久保存就填0
 * @description:
 */
public void putString(String key, String value, int saveTime) 

/**
 * 缓存JSON
 *
 * @param key
 * @param jsonObject
 * @param saveTime   保存时间，（例保存10天，savetime = 10*CICachDataManager.TIME_DAY），永久保存就填0
 */
public void putJSONObject(String key, JSONObject jsonObject, int saveTime)

/**
 * 缓存jsonObject
 *
 * @param key
 * @param jsonArray
 * @param saveTime  保存时间，（例保存10天，savetime = 10*CICachDataManager.TIME_DAY），永久保存就填0
 */
public void putJSONArray(String key, JSONArray jsonArray, int saveTime)
/**
 * 缓存序列化对象
 *
 * @param key
 * @param serializable
 * @param saveTime     保存时间，（例保存10天，savetime = 10*CICachDataManager.TIME_DAY），永久保存就填0
 */
public void putSerializable(String key, Serializable serializable, int saveTime)

/**
 * 缓存bytes
 *
 * @param key      键值
 * @param bytes
 * @param saveTIme 保存时间（例保存10天，savetime = 10*CICachDataManager.TIME_DAY），永久保存就填0
 */
public void putBytes(String key, Byte[] bytes, int saveTIme)
/**
 * 缓存bitmap
 *
 * @param key      键值
 * @param bitmap
 * @param saveTime 保存时长
 */
public void putBitmap(String key, Bitmap bitmap, int saveTime) 

/**
 * 获取string类型值
 *
 * @param key 键值
 */
public String getAsString(String key) 

/**
 * 获取字节
 *
 * @param key 键值
 */
public byte[] getAsBytes(String key) 

/**
 * 获取序列化对象
 *
 * @param key
 */
public Object getAsSerializable(String key)

/**
 * 获取jsonObject对象
 *
 * @param key 键值
 */
public JSONObject gettAsJSONObject(String key)

/**
 * 获取jsonArray
 *
 * @param key 键值
 */
public JSONArray getAsJSONarry(String key) 

/**
 * 获取bitmap
 *
 * @param key 键值
 */
public Bitmap getAsBitmap(String key) 

/**
 * 清除某个值
 * @param key 键值
 * */
public boolean remove(String key)

/**
 * 清除所有值
 * */
public void clear()
```
