# Volley 封装

对Volley框架进行二次封装，减少重复代码的编写；通过不同的Request使用链式调用获取数据并封装成对应的格式；同时将使用Volley请求网络图片进行封装。  

## 使用
### 1.全局初始化
在 `Application` 类中初始化

     RVHttpUtil.initRequestQueue(this);    // 初始化获取数据类
     RVImageLoadUtil.initImageQueue(this); // 初始化加载图片类

### 2.获取数据或者加载图片
* 获取 `String` 类型的数据
    
        Map<String, String> params = new HashMap<>();
        params.put("city", "杭州");
        RStringRequest.create()
                .method(IRequest.Method.GET)
                .url("https://www.apiopen.top/weatherApi")
                .params(params)
                .tag("GetStringData")
                .build()
                .execute()
                .onResult(new ResultCallBack.ResultListener<String>() {
                    @Override
                    public void onSucceed(String result) {
                        tvContent.setText("onSucceed => " + result);
                        Log.i("StringDataActivity", "onSucceed => " + result);
                    }
    
                    @Override
                    public void onNetWork() {
                        Log.i("StringDataActivity", "onNetWork");
                    }
    
                    @Override
                    public void onError(Throwable e) {
                        tvContent.setText("onError => " + e);
                        Log.i("StringDataActivity", "onError => " + e);
                    }
                });
 
 * 加载图片
    
       RVImageLoadUtil.newInstance().loadImageByRequest(url, ivContent);


## 所包含的Request
> * `RStringRequest`        ：返回字符串类型数据
> * `RBeanRequest`          ：返回JavaBean类型数据
> * `RJsonObjectRequest`    ：返回 `JSONObject` 类型数据
> * `RJsonArrayRequest`     ：返回 `JSONArray` 类型数据
> * `RByteArrayRequest`     ：返回 `byte`数组 类型数据
> * `RFormRequest`          ：上传表单数据
> * `RMultipartRequest`     ：上传文件
