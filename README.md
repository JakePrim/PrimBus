# PrimBus
消息事件通信框架,比EventBus更优雅的实现.

## Support

1. 支持tag消息事件,减少了类
2. 支持传递多个参数或无参数传递
3. 支持跨进程通信(正在实现中...)
4. 支持跨线程通信
5. 支持可在任何Class中接收消息

## Log


## 如何使用？

可以在任何类中声明，要接收的消息
可传递多个参数，可传递多个tag，tag 必须声明
```
    @Subscribe(tag = "testMain", thread = ThreadMethod.MAIN)
    public void runMain(String test) {
        textView.setText("我是TextView,我一直运行在主线程中..." + test + " --> " + Thread.currentThread().getName());
    }

    @Subscribe(tag = "testAsync", thread = ThreadMethod.BACKGROUND)
    public void runAsync(String test, Integer i) {
        Log.e(TAG, "runAsync: 我一直运行在子线程中. " + " test:" + test + " i: " + i + " --> "
                + Thread.currentThread().getName());
    }

   //根据tag即可接收消息
    @Subscribe(tag = "notParams")
    public void notParams() {
        textView.setText("我是不需要参数的,通知我即可");
    }

    //支持声明多个 tag，增加可读性
    @Subscribe(tag = {"moreParams", "moreParams2"})
    public void moreParams(String s, Boolean b, More more) {
        textView.setText("我可以接收到多个参数: s -> " + s + " b-> " + b + " more" + more.toString());
    }
```
发送事件，post必须发送一个tag 更具tag 更加简单明确的接收/发送消息

```
   //在主线程发送事件，可在子线程接收事件
   public void mainT(View view) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                PrimBus.getDefault().post("testAsync", "mainT", 1);
            }
        });
    }

    //在子线程发送事件，可在主线程接收事件
    public void async(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PrimBus.getDefault().post("testMain", "async");
            }
        }).start();
    }

    //可传递无参
    public void not(View view) {
        PrimBus.getDefault().post("notParams");
    }

    //可传递多个参数
    public void more(View view) {
        PrimBus.getDefault().post("moreParams", "Parmas", true, new Main2Activity.More("jake", 100));
    }

    //支持多个tag
    public void more2(View view) {
            PrimBus.getDefault().post("moreParams2", "Parmas2", true, new Main2Activity.More("jake2", 1002));

        }
```
